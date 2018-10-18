package com.elmfer.alasen.util.compositon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.elmfer.alasen.util.Property;
import com.elmfer.alasen.util.Timeline;

public class GroupedTimelines implements IAct {

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

