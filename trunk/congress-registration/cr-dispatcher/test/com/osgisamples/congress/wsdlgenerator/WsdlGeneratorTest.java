package com.osgisamples.congress.wsdlgenerator;

import junit.framework.TestCase;

public class WsdlGeneratorTest extends TestCase {
	public void testGenerateWsdl() {
		WsdlGenerator wsdlGenerator = new WsdlGenerator();
		wsdlGenerator.generatewsdl("CongressRegistration.xml", "CongressRegistration");
	}
}
