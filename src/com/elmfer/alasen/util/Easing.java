package com.elmfer.alasen.util;

import java.util.function.Function;

public enum Easing {
	
	LINEAR(new Function<Double, Double>(){
		
		@Override
		public Double apply(Double t) {
			
			return t;
		}
	}),
	IN_SINE(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			return Math.sin((Math.PI * t - Math.PI) / 2.0D);
		}
	}),
	OUT_SINE(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			return Math.sin((Math.PI * t) / 2.0D);
		}
	}),
	INOUT_SINE(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			return (Math.sin(Math.PI * t - (Math.PI) / 2.0D)) / 2.0D + 0.5D;
		}
	}),
	IN_QUAD(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			return Math.pow(t, 2.0D);
		}
	}),
	OUT_QUAD(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			return -1.0D * Math.pow(t, 2.0D) + 2.0D * t;
		}
	}),
	INOUT_QUAD(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			if(t <= 0.5D) {
				
				return 2 * Math.pow(t, 2);
			}else {
				
				return -2.0D * Math.pow(t, 2.0D) - 4.0D * t - 1.0D;
			}
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
