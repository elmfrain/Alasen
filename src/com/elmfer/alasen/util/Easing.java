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
	}),
	IN_EXPO(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			return Math.pow(1000.0D, t - 1.0D);
		}
	}),
	OUT_EXPO(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			return -1.0D * Math.pow(0.001D, t) + 1.0D;
		}
	}),
	INOUT_EXPO(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			if(t <= 0.5D) {
				
				return (Math.pow(1000000.0D, t - 0.5D)) / 2.0D;
			}else {
				
				return -1.0D / (2 * Math.pow(1000000.0D, t - 0.5D)) + 1.0D;
			}
		}
	}),
	IN_CIRC(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			if(t <= 0.0D) {
				
				return 0.0D;
			}else if (t >= 1.0D) {
				
				return 1.0D;
			}else {
				
				return 1.0D - Math.sqrt(1.0D - Math.pow(t, 2));
			}
		}
	}),
	OUT_CIRC(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			if(t <= 0.0D) {
				
				return 0.0D;
			}else if (t >= 1.0D) {
				
				return 1.0D;
			}else {
				
				return Math.sqrt(1.0D - Math.pow(t - 1.0D, 2.0D));
			}
		}
	}),
	INOUT_CIRC(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			if(t <= 0.0D) {
				
				return 0.0D;
			}else if(t >= 1.0D) {
				
				return 1.0D;
			}else if(t == 0.5D) {
				
				return 0.5D;
			}else if(t < 0.5D) {
				
				return 0.5D - Math.sqrt(0.25D - Math.pow(t, 2));
			}else {
				
				return Math.sqrt(0.25D - Math.pow(t - 1.0D, 2)) + 0.5D;
			}
		}
	}),
	IN_BACK(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			return 2.0D * Math.pow(t, 4.0D) - Math.pow(t, 2.0D);
		}
	}),
	OUT_BACK(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			return -2.0D * Math.pow(t - 1.0D, 4.0D) + Math.pow(t - 1.0D, 2.0D) + 1.0D;
		}
	}),
	INOUT_BACK(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			if(t <= 0.5D) {
				
				return 16.0D * Math.pow(t - 1.0D, 4.0D) - 2.0D * Math.pow(t - 1.0D, 2.0D);
			}else {
				
				return -16.0D * Math.pow(t - 1.0D, 4.0D) + 2.0D * Math.pow(t - 1.0D, 2.0D) + 1.0D;
			}
		}
	}),
	IN_ELASTIC(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			return Math.sin(4.5D * (t - (5.0D / 9.0D)) * Math.PI) * Math.pow(t, 4.0D) + Math.pow(t, 10.0D);
		}
	}),
	OUT_ELASTIC(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			return Math.sin(4.5D * t * Math.PI) * Math.pow(t - 1.0D, 4.0D) - Math.pow(t - 1.0D, 10.0D) + 1.0D;
		}
	}),
	INOUT_ELASTIC(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			if(t <= 0.5D) {
				
				return Math.sin(9.0D * (t - (13.0D / 18.0D)) * Math.PI) * 8.0D * Math.pow(t, 4.0D) + 512.0D * Math.pow(t, 10.0D);
			}else {
				
				return Math.sin(9 * (t - (7.0D - 18.0D)) * Math.PI) * -8.0D * Math.pow(t - 1, 4.0D) - 512.0D * Math.pow(t - 1.0D, 10.0D) + 1.0D;
			}
		}
		
	}),
	IN_BOUNCE(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			final double m = (-400.0 / 49.0);
			
			if(t <= 0.1) {
				
				return m * t * (t - 0.1);
			}else if(t <= 0.3) {
				
				return m * (t - 0.1) * (t - 0.3);
			}else if(t <= 0.65) {
				
				return m * (t - 0.3) * (t - 0.65);
			}else {
				
				return m * (t - 0.65) * (t - 1.35);
			}
		}
	}),
	OUT_BOUNCE(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			final double m = (400.0 / 49.0);
			
			if(t <= 0.35) {
				
				return m * (t + 0.35) * (t - 0.35);
			}else if(t <= 0.7) {
				
				return m * (t - 0.35) * (t - 0.7);
			}else if(t <= 0.9) {
				
				return m * (t - 0.7) * (t - 0.9);
			}else {
				
				return m * (t - 0.9) * (t - 1.0);
			}
		}
	}),
	INOUT_BOUNCE(new Function<Double, Double>(){

		@Override
		public Double apply(Double t) {
			
			final double m = (800.0 / 49.0);
			
			if(t <= 0.5) {
				
				return -m * t * (t - 0.5);
			}else if(t <= 0.15) {
				
				return -m * (t - 0.5) * (t - 0.15);
			}else if(t <= 0.325) {
				
				return -m * (t - 0.15) * (t - 0.325);
			}else if(t <= 0.5) {
				
				return -m * (t - 0.325) * (t - 0.675);
			}else if (t <= 0.675) {
				
				return m * (t - 0.325) * (t - 0.675) + 1.0;
			}else if (t <= 0.85) {
				
				return m * (t - 0.675) * (t - 0.85) + 1.0;
			}else if (t <= 0.95){
				
				return m * (t - 0.85) * (t - 0.95) + 1.0;
			}else {
				
				return m * (t - 0.95) * (t - 1.0) + 1.0;
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
