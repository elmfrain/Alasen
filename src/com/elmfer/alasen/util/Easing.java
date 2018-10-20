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
			
			return -1.0D * Math.pow(t - 1.0D, 2.0D) + 1.0D;
		}
	}),
	INOUT_QUAD(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			if(t <= 0.5D) {
				
				return 2.0D * Math.pow(t, 2);
			}else {
				
				return -2.0D * Math.pow(t - 1.0D, 2.0D) + 1.0D;
			}
		}
	}),
	IN_CUBIC(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			return Math.pow(t , 3.0D);
		}
	}),
	OUT_CUBIC(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			return Math.pow(t - 1.0D, 3.0D) + 1.0D;
		}
	}),
	INOUT_CUBIC(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			if(t <= 0.5D) {
				
				return 4.0D * Math.pow(t, 3.0D);
			}else {
				
				return 4.0D * Math.pow(t - 1.0D, 3.0D) + 1.0D;
			}
		}
	}),
	IN_QUART(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			return Math.pow(t, 4.0D);
		}
	}),
	OUT_QUART(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			return -1.0D * Math.pow(t - 1.0D, 4.0D) + 1.0D;
		}
	}),
	INOUT_QUART(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			if(t <= 0.5D) {
				
				return 8.0D * Math.pow(t, 4.0D);
			}else {
				
				return -8.0D * Math.pow(t - 1.0D, 4.0D) + 1.0D;
			}
		}
	}),
	IN_QUINT(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			return Math.pow(t, 5.0D);
		}
	}),
	OUT_QUINT(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			return Math.pow(t - 1.0D, 5.0D) + 1.0D;
		}
	}),
	INOUT_QUINT(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			if(t <= 0.5D) {
				
				return 16.0D * Math.pow(t, 5.0D);
			}else {
				
				return 16.0D * Math.pow(t - 1.0D, 5.0D) + 1.0D;
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
