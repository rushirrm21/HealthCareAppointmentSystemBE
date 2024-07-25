package com.hcaptsys.rlutil;

public class DataManipulation {

	  public String updateFirstLetter(String str) {
	        char firstLetter = Character.toUpperCase(str.charAt(0));
	        return firstLetter + str.substring(1);
	    }
	
}
