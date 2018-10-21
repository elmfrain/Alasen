package com.elmfer.alasen.util;

import java.util.Collection;
import java.util.function.Function;

public class PropertyInt extends Property {
	
	private Integer value;

	public PropertyInt(String name) {
		
		super(name);
		value = 0;
		
	}
	
	public PropertyInt(String name, Integer value) {
		
		super(name, value);
		value = 0;
	}
	
	public PropertyInt(String name, Integer startingValue, Integer endingValue) {
		
		super(name, startingValue, endingValue);
		value = 0;
		
	}
	
	public PropertyInt(String name, Integer startingValue, Integer endingValue, Function<Double, Double> easingFunc) {
		
		super(name, startingValue, endingValue, easingFunc);
		value = 0;
		
	}
	
	public PropertyInt(String name, Integer startingValue, Integer endingValue, Easing easingEnum) {
		
		super(name, startingValue, endingValue, easingEnum.getFunction());
		value = 0;
		
	}
	
	public void update(double fracTime) {
		
		super.update(fracTime);
		
		int k2v = nextKeyframe.getValue() != null ? (int) nextKeyframe.getValue() : 0;
		int k1v = currentKeyframe.getValue() != null ? (int) currentKeyframe.getValue() : 0;
		double k2t = nextKeyframe.getFracTimeStamp();
		double k1t = currentKeyframe.getFracTimeStamp();
		
		double partialFracTime = (fracTime - k1t) / (k2t - k1t);
		value = (int) ((k2v - k1v) * currentKeyframe.getValueShader().apply(partialFracTime) + k1v);
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
	
	public Integer getValue() {
		
		return value;
	}
	
	private void validateKeyframe(Keyframe keyframe) {
		
		if(keyframe.getValue() instanceof Integer) {
			
			super.addKeyframes(keyframe);
		}else {
			
			super.showTypeError(this);
		}
		
	}

}
