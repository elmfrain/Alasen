package com.elmfer.alasen.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Composition {
	
	private final Map<String, Timeline> timelines = new HashMap<String, Timeline>();
	private final List<QueuedTimeline> queuedTimelines = new ArrayList<QueuedTimeline>();
	private final List<GroupedTimelines> groupedTimelines = new ArrayList<GroupedTimelines>();
	
	public void queue(String... timelines) {
		
		if(!queuedTimelines.isEmpty()) apply();
		
		for(String name : timelines) {
			if(this.timelines.containsKey(name)) {
				QueuedTimeline queuedTimeline = new QueuedTimeline(this.timelines.get(name));
				queuedTimelines.add(queuedTimeline);
			}
		}
		
		Consumer<GroupedTimelines> action = (GroupedTimelines gT) -> gT.queue(Arrays.asList(timelines));
		groupedTimelines.forEach(action);
	}
	
	public void force(String timeline) {
		
		Consumer<GroupedTimelines> action = (GroupedTimelines gT) -> gT.force(timeline);
		groupedTimelines.forEach(action);
	}
	
	public void clearQueue() {
		
		queuedTimelines.clear();
		
		Consumer<GroupedTimelines> action = (GroupedTimelines gT) -> gT.clearQueue();
		groupedTimelines.forEach(action);
	}
	
	public void removeFromQueue(String... strings) {
		
		Predicate<QueuedTimeline> filter = p -> Arrays.asList(strings).contains(p.timeline.getName());
		queuedTimelines.removeIf(filter);
		
		Consumer<GroupedTimelines> action = (GroupedTimelines gT) -> gT.removeFromQueue(Arrays.asList(strings));
		groupedTimelines.forEach(action);
	}
	
	public void play() {
		
		action((byte) 1);
	}
	
	public void rewind() {
		
		action((byte) 2);
	}
	
	public void resume() {
		
		action((byte) 3);
	}
	
	public void pause() {
		
		action((byte) 4);
	}
	
	public void stop() {
		
		action((byte) 5);
	}
	
	public void apply() {
		
		Consumer<IAct> action = (IAct actor) -> actor.applyActions();
		
		queuedTimelines.forEach(action);
		groupedTimelines.forEach(action);
		queuedTimelines.clear();
	}
	
	public void addTimelines(Timeline... timelines) {
		
		for(Timeline timeline : timelines) {
			if(timeline.getName() != null) {
				sortTimeline(timeline);
			}else {
				String name = Integer.toString(this.timelines.size());
				System.out.println("[!Alasen-Warn!] : " + timeline + " doesn't have a name! Setting it to " + name + ".");
				sortTimeline(timeline);
			}
		}
		
	}
	
	public Property getProperty(String prop) {
		
		for(Timeline timeline : timelines.values()) {
			if(timeline.getProperties().containsKey(prop)) {
				return timeline.getProperty(prop);
			}
		}
		
		for(GroupedTimelines timelineGroup : groupedTimelines) {
			if(timelineGroup.hasProperty(prop)) {
				return timelineGroup.getProperty(prop);
			}
		}
		
		return null;
	}
	
	public Property getProperty(String timeline, String prop) {
		
		if(timelines.containsKey(timeline)) {
			
			if(timelines.get(timeline).hasProperty(prop)) {
				return timelines.get(timeline).getProperty(prop);
			}
		}
		
		for(GroupedTimelines timelineGroup : groupedTimelines) {
			
			if(timelineGroup.hasTimeline(timeline)) {
				
				if(timelineGroup.getTimeline(timeline).hasProperty(prop)) {
					
					return timelineGroup.getTimeline(timeline).getProperty(prop);
				}
			}
		}
		
		return null;
	}
	
	public void tick() {
		
		Consumer<Timeline> action = (Timeline tL) -> tL.tick();
		timelines.values().forEach(action);
		
		Consumer<GroupedTimelines> gAction = (GroupedTimelines gT) -> gT.tick();
		groupedTimelines.forEach(gAction);
	}
	
	public Timeline getTimelineCopy(String name) {
		
		if(timelines.containsKey(name)) {
			return new Timeline(timelines.get(name));
		}
		
		for(GroupedTimelines timelineGroup : groupedTimelines) {
			if(timelineGroup.hasTimeline(name)) {
				return new Timeline(timelineGroup.getTimeline(name));
			}
		}
		
		return null;
	}
	
	private void sortTimeline(Timeline timeline) {
		
		for(Property prop : timeline.getProperties().values()) {
			for(Timeline timeline0 : timelines.values()) {
				if(timeline0.hasProperty(prop.getName())) {
					timelines.remove(timeline0.getName());
					GroupedTimelines group = new GroupedTimelines(timeline0);
					group.addTimeline(timeline);
					groupedTimelines.add(group);
					return;
				}
			}
		}
		
		for(GroupedTimelines timelineGroup : groupedTimelines) {
			if(timelineGroup.addTimeline(timeline)) {
				return;
			}
		}

		timelines.put(timeline.getName(), timeline);
	}
	
	private void action(byte id) {
		
		Consumer<IAct> action = (IAct actor) -> actor.addAction(id);
		queuedTimelines.forEach(action);
		groupedTimelines.forEach(action);
	}
	
	private interface IAct{
		
		public void addAction(byte action);
		public void applyActions();
	}
	
	private class QueuedTimeline implements IAct{
		
		public final Timeline timeline;
		private List<Byte> actions = new ArrayList<Byte>();
		
		public QueuedTimeline(Timeline timeline) {
			
			this.timeline = timeline;
		}
		
		public void addAction(byte action) {
			
			actions.add(action);
		}
		
		public void applyActions() {
			
			for(Byte action : actions) {
				
				switch(action) {
					case 1:
						timeline.play();
						break;
					case 2:
						timeline.rewind();
						break;
					case 3:
						timeline.resume();
						break;
					case 4:
						timeline.pause();
						break;
					case 5:
						timeline.stop();
						break;
				}
			}
		}
		
	}
	
	private class GroupedTimelines implements IAct{
		
		private final Map<String, Timeline> timelines = new HashMap<String, Timeline>();
		private final List<QueuedTimeline> queuedTimelines = new ArrayList<QueuedTimeline>();
		
		private QueuedTimeline forcedTimeline = null;
		
		private Timeline lastestTimeline = null;
		
		private int currentQueueBuffer = 0;
		private boolean currentIsFinished = false;
		private boolean force = false;
		
		public GroupedTimelines(Timeline firstTimeline) {
			
			timelines.put(firstTimeline.getName(), firstTimeline);
		}
		
		public boolean addTimeline(Timeline timeline) {
			
			for(Property prop : timeline.getProperties().values()) {
				for(Timeline timeline0 : timelines.values()) {
					if(timeline0.hasProperty(prop.getName())) {
						timelines.put(timeline.getName(), timeline);
						return true;
					}
				}
			}
			return false;
		}
		
		public void queue(Collection<String> names) {
			
			for(String name : names) {
				if(timelines.containsKey(name)) {
					queuedTimelines.add(new QueuedTimeline(timelines.get(name)));
					currentQueueBuffer++;
				}
			}
		}
		
		public void force(String timeline) {
			
			if(timelines.containsKey(timeline)) {
				
				forcedTimeline = new QueuedTimeline(timelines.get(timeline));
				force = true;
			}
		}
		
		public void clearQueue() {
			
			queuedTimelines.clear();
		}
		
		public void removeFromQueue(Collection<String> timelines) {
			
			Predicate<QueuedTimeline> filter = p -> timelines.contains(p.timeline.getName());
			queuedTimelines.removeIf(filter);
		}
		
		public void addAction(byte action) {
			
			if(force) {
				
				forcedTimeline.addAction(action);
			}else if(currentQueueBuffer > 0){
				
				for(int i = queuedTimelines.size() - 1 ; i >= queuedTimelines.size() - currentQueueBuffer ; i--) {
					
					queuedTimelines.get(i).addAction(action);
				}
			}
		}
		
		public void applyActions() {
			
			currentQueueBuffer = 0;
			
			if(force) {
				
				forcedTimeline.applyActions();
				lastestTimeline = forcedTimeline.timeline;
				queuedTimelines.clear();
				force = false;
			}
		}
		
		public Property getProperty(String prop) {
			
			if(lastestTimeline != null) {
				
				if(lastestTimeline.hasProperty(prop)) {
					
					return lastestTimeline.getProperty(prop);
				}
			}
			
			for(Timeline timeline : timelines.values()) {
				
				if(timeline.hasProperty(prop)) {
					
					return timeline.getProperty(prop);
				}
			}
			
			return null;
		}
		
		public boolean hasProperty(String prop) {
			
			for(Timeline timeline : timelines.values()) {
				
				if(timeline.hasProperty(prop)) {
					
					return true;
				}
			}
			
			return false;
		}
		
		public boolean hasTimeline(String name) {
			
			if(timelines.containsKey(name)) {
				return true;
			}else {
				return false;
			}
		}
		
		public Timeline getTimeline(String name) {
			
			if(timelines.containsKey(name)) {
				
				return timelines.get(name);
			}
			
			return null;
		}
		
		public void tick() {
			
			update();
			
			Consumer<Timeline> action = (Timeline tL) -> tL.tick();
			timelines.values().forEach(action);
		}
		
		private void update() {
			
			if(!queuedTimelines.isEmpty()) {
				
				QueuedTimeline currentTimeline = queuedTimelines.get(0);
				
				if(currentTimeline.timeline.hasStopped() && !currentIsFinished) {
					
					currentTimeline.applyActions();
					lastestTimeline = currentTimeline.timeline;
					
					currentIsFinished = true;
				}
				
				if(currentTimeline.timeline.hasStopped() && currentIsFinished) {
					
					queuedTimelines.remove(0);
					currentIsFinished = false;
				}
				
				if(!queuedTimelines.isEmpty()) {
					
					currentTimeline = queuedTimelines.get(0);
					
					if(currentTimeline.timeline.hasStopped() && !currentIsFinished) {
						currentTimeline.applyActions();
						
						lastestTimeline = currentTimeline.timeline;
						
						currentIsFinished = true;
					}
				}else {
					
					currentIsFinished = false;
				}
			}else {
				currentIsFinished = false;
			}
			
		}
		
	}

}
