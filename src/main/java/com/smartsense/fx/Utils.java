package com.smartsense.fx;

import java.text.DecimalFormat;

public class Utils {

	public static final int MAX_WAIT_TIME = 5000;
	public static final int MIN_WAIT_TIME = 500;
	public static void main(String[] args){
		String val = "1.23/45";
		double askPrice = Double.parseDouble(val.split("/")[0]);
		System.out.println(askPrice);
		System.out.println(formatVal(1.23337334));
	}
	private static  String formatVal(double val){
		DecimalFormat decimalFormat = new DecimalFormat("#.0000");
		return decimalFormat.format(val);
	}
}
