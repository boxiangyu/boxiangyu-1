package com.jason.logtest;

import org.apache.log4j.*;

public class NewTest {
  public static Logger logger1=Logger.getLogger(NewTest.class);

  public static void main(String args[]){
    logger1.trace("1.trace");
    logger1.debug("2.debug");
    logger1.info("3.info");
    logger1.warn("我是logger1，warn");
    logger1.error("我是logger1，error");
    logger1.fatal("我是logger1，fatal");
  }

}
