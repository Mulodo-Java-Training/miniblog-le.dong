package com.mulodo.miniblog.util;

import java.util.Random;



public class Util {
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static Random rnd = new Random();

	public static String randomString() 
	{
	   StringBuilder sb = new StringBuilder( 40 );
	   for( int i = 0; i < 40; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}
	
	public static void main(String[] args) {
		String a = Util.randomString();
		System.out.println(a +"   "+a.length());
	}
}
