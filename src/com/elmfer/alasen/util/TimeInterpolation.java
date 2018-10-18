package com.elmfer.alasen.util;

import java.util.function.Function;

public enum TimeInterpolation {
	
	LINEAR(new Function<Double, Double>() {
		
		@Override
		public Double apply(Double t) {
			
			return t;
		}
	}),
	FAST(new Function<Double, Double>() {
		
		@Override
		public Double apply(Double t) {
			
			return -1 * Math.pow(t, 2) + 2 * t;
		}
	}),
	SLOW(new Function<Double, Double>() {
		
		@Override
		public Double apply(Double t) {
			
			return Math.pow(t, 2);
		}
	}),
	SMOOTH(new Function<Double, Double>() {
		
		@Override
		public Double apply(Double t) {
			
			if(t <= 0.5D) {
				
				return 2.0D * Math.pow(t, 2);
			}else {
				
				return (-2 * Math.pow(t, 2)) + 4 * t - 1;
			}
		}
	}),
	INSTANT(new Function<Double, Double>() {
		
		@Override
		public Double apply(Double t) {
			
			if(t >= 1.0D) {
				
				return 1.0D;
			}else {
				
				return 0.0D;
			}
		}
	}),
	SUPER_FAST(new Function<Double, Double>() {
		
		@Override
		public Double apply(Double t) {
			
			return -1 * Math.pow(t - 1, 4) + 1;
		}
	}),
	SUPER_SLOW(new Function<Double, Double>() {
		
		@Override
		public Double apply(Double t) {
			
			return Math.pow(t, 4);
		}
	}),
	SUPER_SMOOTH(new Function<Double, Double>() {
		
		@Override
		public Double apply(Double t) {
			
			if(t <= 0.5D) {
				
				return 8 * Math.pow(t, 4);
			}else {
				
				return -8 * Math.pow(t - 1, 4) + 1;
			}
		}
	});
	
	private Function<Double, Double> function;
	
	TimeInterpolation(Function<Double, Double> func){
		
		function = func;
	}
	
	public Function<Double, Double> getFunction() {
		
		return function;
	}
	
	public static TimeInterpolation getDefault() {
		
		return LINEAR;
	}

}
