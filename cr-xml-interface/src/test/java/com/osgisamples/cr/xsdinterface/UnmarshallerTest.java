package com.osgisamples.cr.xsdinterface;

import generated.CongressRegistrationRequest;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import junit.framework.TestCase;

public class UnmarshallerTest extends TestCase {

	public void testMarshallingCongressRegistrationRequest() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(new Class[]{CongressRegistrationRequest.class});
		Unmarshaller unmarshaller = context.createUnmarshaller();
		File xmlFile = new File("target/test-classes/CongressRegistrationRequest.xml");
		CongressRegistrationRequest request = (CongressRegistrationRequest) unmarshaller.unmarshal(xmlFile);
		assertEquals(0, request.getCongress().getCongressId());
		assertEquals("CongressName", request.getCongress().getCongressName());
	}
}
