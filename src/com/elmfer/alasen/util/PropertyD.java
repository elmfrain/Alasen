package com.elmfer.alasen.util;

import java.util.Collection;
import java.util.function.Function;

public class PropertyD extends Property{
	
	private Double value;

	public PropertyD(String name) {
		
		super(name);
		value = 0.0D;
	}
	
	public PropertyD(String name, Double value) {
		
		super(name, value);
		value = 0.0D;
	}
	
	public PropertyD(String name, Double startingValue, Double endingValue) {
		
		super(name, startingValue, endingValue);
		value = 0.0D;
	}
	
	public PropertyD(String name, Double startingValue, Double endingValue, Function<Double, Double> easingFunc) {
		
		super(name, startingValue, endingValue, easingFunc);
		value = 0.0D;
	}
	
	public PropertyD(String name, Double startingValue, Double endingValue, Easing easingEnum) {
		
		super(name, startingValue, endingValue, easingEnum.getFunction());
		value = 0.0D;
	}
	
	public void update(double fracTime) {
		
		super.update(fracTime);
		
		double k2v = nextKeyframe.getValue() != null ? (double) nextKeyframe.getValue() : 0.0D;
		double k1v = currentKeyframe.getValue() != null ? (double) currentKeyframe.getValue() : 0.0D;
		double k2t = nextKeyframe.getFracTimeStamp();
		double k1t = currentKeyframe.getFracTimeStamp();
		
		double partialFracTime = (fracTime - k1t) / (k2t - k1t);
		value = (double) ((k2v - k1v) * currentKeyframe.getValueShader().apply(partialFracTime) + k1v);
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
	
	public Double getValue() {
		
		return value;
	}
	
	private void validateKeyframe(Keyframe keyframe) {
		
		if(keyframe.getValue() instanceof Double) {
			
			super.addKeyframes(keyframe);
		}else {
			
			super.showTypeError(this);
		}
		
	}

}
