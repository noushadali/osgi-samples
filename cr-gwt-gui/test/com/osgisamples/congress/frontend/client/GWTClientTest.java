package com.osgisamples.congress.frontend.client;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * GWT JUnit tests must extend GWTTestCase.
 */
public class GWTClientTest extends GWTTestCase {

  /**
   * Must refer to a valid module that sources this class.
   */
  public String getModuleName() {
    return "com.osgisamples.congress.frontend.GWTClient";
  }

  /**
   * Add as many tests as you like.
   */
  public void testSimple() {
    assertTrue(true);
  }

}
