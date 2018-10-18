package com.elmfer.alasen.util.compositon;

import java.util.ArrayList;
import java.util.List;

import com.elmfer.alasen.util.Timeline;

public class QueuedTimeline implements IAct{
	
	private final Timeline timeline;
	private final List<Byte> actions = new ArrayList<Byte>();
	
	protected QueuedTimeline(Timeline value) {
		
		timeline = value;
	}
	
	public void addAction(byte action) {
		
	}
	
	public void applyAction() {
		
	}
}
