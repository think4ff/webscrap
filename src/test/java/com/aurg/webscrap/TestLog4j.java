package com.aurg.webscrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLog4j {
//	protected static Logger logger = Logger.getLogger( TestLog4j.class.getName());
	static Logger logger = LoggerFactory.getLogger(TestLog4j.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String var = "logTest...";
		String kor = "한글로그....";
		//DEBUG < INFO < WARN < ERROR < FATAL

//		logger.fatal("log4j:logger.fatal()");

		logger.error("log4j:logger.error() {}", var);

		logger.warn("log4j:logger.warn() {}", kor);

		logger.info("log4j:logger.info()");

		logger.debug("log4j:logger.debug()");
	}
}
