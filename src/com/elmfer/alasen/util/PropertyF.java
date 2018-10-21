package com.elmfer.alasen.util;

import java.util.Collection;
import java.util.function.Function;

public class PropertyF extends Property{
	
	private Float value;

	public PropertyF(String name) {
		
		super(name);
		value = 0.0F;
	}
	
	public PropertyF(String name, Float value) {
		
		super(name, value);
		value = 0.0F;
	}
	
	public PropertyF(String name, Float startingValue, Float endingValue) {
		
		super(name, startingValue, endingValue);
		value = 0.0F;
	}
	
	public PropertyF(String name, Float startingValue, Float endingValue, Function<Double, Double> easingFunc) {
		
		super(name, startingValue, endingValue, easingFunc);
		value = 0.0F;
	}
	
	public void update(double fracTime) {
		
		super.update(fracTime);
		
		float k2v = nextKeyframe.getValue() != null ? (float) nextKeyframe.getValue() : 0.0F;
		float k1v = currentKeyframe.getValue() != null ? (float) currentKeyframe.getValue() : 0.0F;
		double k2t = nextKeyframe.getFracTimeStamp();
		double k1t = currentKeyframe.getFracTimeStamp();
		
		double partialFracTime = (fracTime - k1t) / (k2t - k1t);
		value = (float) ((k2v - k1v) * currentKeyframe.getValueShader().apply(partialFracTime) + k1v);
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
	
	public Float getValue() {
		
		return value;
		
	}
	
	private void validateKeyframe(Keyframe keyframe) {
		
		if(keyframe.getValue() instanceof Float) {
			
			super.addKeyframes(keyframe);
		}else {
			
			super.showTypeError(this);
		}
		
	}

}
