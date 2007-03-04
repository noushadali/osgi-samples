package com.osgisamples.cr.xsdinterface;

import generated.CongressRegistrationResponse;
import generated.ObjectFactory;

import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.custommonkey.xmlunit.XMLTestCase;

public class MarshallerTest extends XMLTestCase {

	public MarshallerTest(String name) {
		super(name);
	}

	public void testMarshallingCongressRegistrationResponse() throws Exception {
		JAXBContext context = JAXBContext.newInstance(new Class[]{CongressRegistrationResponse.class});
		Marshaller marshaller = context.createMarshaller();

		ObjectFactory factory = new ObjectFactory();
		CongressRegistrationResponse response = factory.createCongressRegistrationResponse();
		response.setRegistrationCode(2);

		Writer responseString = new StringWriter();
		marshaller.marshal(response, responseString);

		String expectedResponseString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
				"<CongressRegistrationResponse><RegistrationCode>2</RegistrationCode></CongressRegistrationResponse>";

		assertXMLEqual(expectedResponseString,responseString.toString());
	}
}
