package com.elmfer.alasen.util;

import java.util.function.Function;

public enum Easing {
	
	LINEAR(new Function<Double, Double>(){
		
		@Override
		public Double apply(Double t) {
			
			return t;
		}
	});
	
	private final Function<Double, Double> function;
	
	Easing(Function<Double, Double> timeShader){
		
		function = timeShader;
	}
	
	public Function<Double, Double> getFunction(){
		
		return function;
	}
	
	public static Easing getDefault(){
		
		return LINEAR;
	}
	
}
