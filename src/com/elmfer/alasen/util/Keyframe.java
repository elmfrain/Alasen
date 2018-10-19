package com.elmfer.alasen.util;

import java.util.function.Function;

public class Keyframe {
	
	private final Function<Double, Double> timeShader;
	private final Number value;
	private final double fracTimeStamp;
	
	public Keyframe(double fracTimeStamp, Number value) {
		
		this.fracTimeStamp = fracTimeStamp;
		this.value = value;
		this.timeShader = Easing.getDefault().getFunction();
	}
	
	public Keyframe(double fracTimeStamp, Number value, Function<Double, Double> timeShaderFunc) {
		
		this.fracTimeStamp = fracTimeStamp;
		this.value = value;
		this.timeShader = timeShaderFunc;
	}
	
	public double getFracTimeStamp() {
		
		return fracTimeStamp;
	}
	
	public Function<Double, Double> getValueShader() {
		
		return timeShader;
	}
	
	public Number getValue() {
		
		return value;
	}

}
