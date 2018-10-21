package com.elmfer.alasen.util;

import java.util.Collection;
import java.util.function.Function;

public class PropertyLong extends Property{
	
	private Long value;

	public PropertyLong(String name) {
		
		super(name);
		value = 0L;
	}
	
	public PropertyLong(String name, Long value) {
		
		super(name, value);
		value = 0L;
	}
	
	public PropertyLong(String name, Long startingValue, Long endingValue) {
		
		super(name, startingValue, endingValue);
		value = 0L;
	}
	
	public PropertyLong(String name, Long startingValue, Long endingValue, Function<Double, Double> easingFunc) {
		
		super(name, startingValue, endingValue, easingFunc);
		value = 0L;
	}
	
	public PropertyLong(String name, Long startingValue, Long endingValue, Easing easingEnum) {
		
		super(name, startingValue, endingValue, easingEnum.getFunction());
		value = 0L;
	}
	
	public void update(double fracTime) {
		
		super.update(fracTime);
		
		float k2v = nextKeyframe.getValue() != null ? (long) nextKeyframe.getValue() : 0L;
		float k1v = currentKeyframe.getValue() != null ? (long) currentKeyframe.getValue() : 0L;
		double k2t = nextKeyframe.getFracTimeStamp();
		double k1t = currentKeyframe.getFracTimeStamp();
		
		double partialFracTime = (fracTime - k1t) / (k2t - k1t);
		value = (long) ((k2v - k1v) * currentKeyframe.getValueShader().apply(partialFracTime) + k1v);
	}
	
	public void addKeyframes(Keyframe... keyframes) {
		
		for(Keyframe keyframe : keyframes) {
			
			validateKeyframe(keyframe);
		}
	}
	
	public void addKeyframes(Collection<Keyframe> keyframes) {
		
		for(Keyframe keyframe : keyframes) {
			
			validateKeyframe(keyframe);
		}
	}
	
	public Long getValue() {
		
		return value;
	}
	
	private void validateKeyframe(Keyframe keyframe) {
		
		if(keyframe.getValue() instanceof Long) {
			
			super.addKeyframes(keyframe);
		}else {
			
			super.showTypeError(this);
		}
		
	}

}
