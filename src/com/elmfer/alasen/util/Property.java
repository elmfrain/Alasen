package com.elmfer.alasen.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public abstract class Property {
	
	protected final List<Keyframe> keyframeList = new ArrayList<Keyframe>();
	private final String name;
	
	protected Keyframe currentKeyframe = new Keyframe(0.0D, null);
	protected Keyframe nextKeyframe = new Keyframe(1.0D, null);
	
	private Number value;
	
	private static final Comparator<Keyframe> compare = new Comparator<Keyframe>() {

		@Override
		public int compare(Keyframe o1, Keyframe o2) {
			
			if(o1.getFracTimeStamp() < o2.getFracTimeStamp()) return -1;
			if(o1.getFracTimeStamp() > o2.getFracTimeStamp()) return 1;
			
			return 0;
		}
		
	};
	
	public Property(String name) {
		
		this.name = name;
	}
	
	public Property(String name, Number value) {
		
		this.name = name;
		
		addKeyframes(new Keyframe(0.5D, value));
	}
	
	public Property(String name, Number startingValue, Number endingValue) {
		
		this.name = name;
		
		addKeyframes(new Keyframe(0.0D, startingValue), new Keyframe(1.0D, endingValue));
		
	}
	
	public Property(String name, Number startingValue, Number endingValue, Function<Double, Double> easingFunc) {
		
		this.name = name;
		
		addKeyframes(new Keyframe(0.0D, startingValue, easingFunc), new Keyframe(1.0D, endingValue));
	}
	
	public Property(String name, Number startingValue, Number endingValue, Easing easingFunc) {
		
		this.name = name;
		
		addKeyframes(new Keyframe(0.0D, startingValue, easingFunc.getFunction()), new Keyframe(1.0D, endingValue));
	}
	
	public void update(double fracTime) {
		
		if(keyframeList.size() == 0) {
			
			return;
		}
		
		getKeyframes(fracTime);
	}
	
	public Number getValue() {
		
		return value;
	}
	
	public String getName() {
		
		return name;
	}
	
	public void addKeyframes(Keyframe... keyframes) {
		
		for(Keyframe keyframe : keyframes) {
			
			validateKeyframe(keyframe);
		}
		
		sortKeyframes();
	}
	
	public void addKeyframes(Collection<Keyframe> keyframes) {
		
		for(Keyframe keyframe : keyframes) {
			
			validateKeyframe(keyframe);
		}
		
		sortKeyframes();
	}
	
	public static void showTypeError(Property property) {
		
		System.out.println("[!Alasen-Error!] : " + property + " ,keyframe value must be the same type as the property type!");
	}
	
	private void getKeyframes(double fracTime) {
		
		if(!keyframeList.isEmpty()) {
			
			for(int i = 0; i < keyframeList.size(); i++) {
				
				if(keyframeList.size() - 1 == i || keyframeList.size() == 1) {
					
					currentKeyframe = keyframeList.get(i);
					nextKeyframe = new Keyframe(2.0D, currentKeyframe.getValue());
					break;
				}else if(keyframeList.get(i).getFracTimeStamp() <= fracTime && fracTime < keyframeList.get(i + 1).getFracTimeStamp()) {
					
					currentKeyframe = keyframeList.get(i);
					nextKeyframe = keyframeList.get(i + 1);
					break;
				}else if(fracTime < keyframeList.get(i).getFracTimeStamp()){
					
					currentKeyframe = new Keyframe(0.0D, keyframeList.get(0).getValue());
					nextKeyframe = keyframeList.get(0);
					break;
				}else {
					
					currentKeyframe = new Keyframe(0.0D, null);
					nextKeyframe = new Keyframe(1.0D, null);
				}
				
			}
		}
		
	}
	
	private void validateKeyframe(Keyframe keyframe) {
		
		if(keyframeList.size() == 0) {
			
			keyframeList.add(keyframe);
		}else {
			
			for(int i = 0; i < keyframeList.size(); i++) {
				
				if(keyframeList.get(i).getFracTimeStamp() == keyframe.getFracTimeStamp()) {
					
					keyframeList.set(i, keyframe);
				}else {
					
					keyframeList.add(keyframe);
				}
			}
		}
	}
	
	private void sortKeyframes() {
		
		keyframeList.sort(compare);
	}
	
}
