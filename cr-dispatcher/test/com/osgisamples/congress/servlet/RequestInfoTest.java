package com.osgisamples.congress.servlet;

import junit.framework.TestCase;

public class RequestInfoTest extends TestCase {
	public void testWsdlUrl() {
		checkRequestInfo("/servicename/1.0.0/servicename.wsdl",
				"servicename","1.0.0",null,"servicename.wsdl",null);
	}

	public void testXsdUrl() {
		checkRequestInfo("/servicename/1.0.0/servicename.xsd",
				"servicename","1.0.0","servicename.xsd",null,null);
	}

	public void testFolderUrl() {
		checkRequestInfo("/servicename/1.0.0/servicename",
				"servicename","1.0.0",null,null,"servicename");
	}

	public void testWsdlNoVersionUrl() {
		checkRequestInfo("/servicename/servicename",
				"servicename",null,null,"servicename",null);
	}

	private void checkRequestInfo(String url, String servicename, String version, String xsd, String wsdl, String requestedFolder) {
		RequestInfo requestInfo = new RequestInfo(url);
		assertEquals(servicename,requestInfo.getService());
		assertEquals(version, requestInfo.getVersion());
		assertEquals(xsd, requestInfo.getXsd());
		assertEquals(wsdl, requestInfo.getWsdl());
		assertEquals(requestedFolder, requestInfo.getRequestedFolder());
	}
}
