package com.kambhi.betapi.logger;

public class Logger {
	/**
	 * Logging Debug.
	 *
	 * @param message the message
	 */
	public static void debug(String message) {
		System.out.println("DEBUG : " + message);
	}

	/**
	 * Logging Error.
	 *
	 * @param message the message
	 */
	public static void error(String message) {
		System.out.println("ERROR : " + message);
	}

	/**
	 * Logging Info.
	 *
	 * @param message the message
	 */
	public static void info(String message) {
		System.out.println("INFO : " + message);
	}
	
	/**
	 * Logging Info.
	 *
	 * @param message the size
	 */
	public static void info(int size) {
		System.out.println("INFO : " + size);
	}

}
