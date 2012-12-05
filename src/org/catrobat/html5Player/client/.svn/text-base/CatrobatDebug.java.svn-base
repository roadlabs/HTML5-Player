package org.catrobat.html5Player.client;

import com.google.gwt.core.client.GWT;

public class CatrobatDebug {

	private static boolean debug = true;
	
	private CatrobatDebug() {
		
	}
	
	/**
	 * 
	 * @param message
	 */
	public static void console(String message) {
		if(debug)
			System.out.println(message);
	}
	
	/**
	 * 
	 * @param message
	 */
	public static void GWTLogger(String message) {
		if(debug)
			GWT.log(message);
	}
	
	/**
	 * 
	 */
	public static void on() {
		debug = true;
	}
	
	/**
	 * 
	 */
	public static void off() {
		debug = false;
	}
	
}
