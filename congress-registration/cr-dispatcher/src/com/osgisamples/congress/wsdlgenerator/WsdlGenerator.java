package com.osgisamples.congress.wsdlgenerator;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class WsdlGenerator {
	private final static String PLACEHOLDER = "@@";

	private final static String PLACEHOLDER_SCHEMALOCATION = "SCHEMA";

	private final static String PLACEHOLDER_SERVICE = "SERVICE";

	private final static String WSDL_TEMPLATE_FILENAME = "com/osgisamples/congress/wsdlgenerator/congress.wsdl.template";

	public String generatewsdl(String schema, String service) {
		String readTemplate = createWsdlTemplate();
//		try {
//			readTemplate = loadStringFromFile();
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new RuntimeException("error while reading the wsdl template",e);
//		}
		
		readTemplate = replacePlaceHolders(readTemplate, schema, service);
		return readTemplate;
	}

	protected String createWsdlTemplate() {
		return 
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
		"<definitions" +
		"	xmlns=\"http://schemas.xmlsoap.org/wsdl/\" " +
		"	xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\" " +
		"	xmlns:http=\"http://schemas.xmlsoap.org/wsdl/http/\" " +
		"	xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" " +
		"	xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" " +
		"	xmlns:mime=\"http://schemas.xmlsoap.org/wsdl/mime/\" " +
		"	xmlns:doservice=\"http://dispatcher.congress.osgisamples.com\" " +
		"	xmlns:curr=\"http://www.osgisamples.com/services/doservice\" " +
		"	targetNamespace=\"http://www.osgisamples.com/services/doservice\">" +
		"	<types>" +
		"		<xs:schema>" +
		"			<xs:import namespace=\"http://dispatcher.congress.osgisamples.com\" " +
		"					schemaLocation=\"@@SCHEMA@@\"/>" +
		"		</xs:schema>" +
		"	</types>" +
		"	<message name=\"CongressRegistrationRequestMsg\">" +
		"		<part name=\"theInputMessage\" element=\"doservice:@@SERVICE@@Request\" />" +
		"	</message>" +
		"	<message name=\"CongressRegistrationResponseMsg\">" +
		"		<part name=\"theResponseMessage\" element=\"doservice:@@SERVICE@@Response\" /> " + 
		"	</message>" +
		"	<portType name=\"SOAPDispatcher\">" +
		"		<operation name=\"doService\">" +
		"			<input name=\"@@SERVICE@@Request\" message=\"curr:CongressRegistrationRequestMsg\"/>" +
		"			<output name=\"@@SERVICE@@Response\" message=\"curr:CongressRegistrationResponseMsg\" />" +
		"		</operation>" +
		"	</portType>" +
		"	<binding name=\"congressSoapBinding\" type=\"curr:SOAPDispatcher\">" +
		"		<soap:binding style=\"document\" transport=\"http://schemas.xmlsoap.org/soap/http\" />" +
		"		<operation name=\"doService\">" +
		"			<soap:operation soapAction=\"\" />" +
		"			<input>" +
		"				<soap:body use=\"literal\" />" +
		"			</input>" +
		"			<output>" +
		"				<soap:body use=\"literal\" />" +
		"			</output>" +
		"		</operation>" +
		"	</binding>" +
		"	<service name=\"SOAPDispatcherService\">" +
		"		<port binding=\"curr:congressSoapBinding\" name=\"congress\">" +
		"			<soap:address location=\"http://localhost:81/services/congress\" />" +
		"		</port>" +
		"	</service>" +
		"</definitions>";
	}
	
	protected String loadStringFromFile() throws IOException {
		InputStream inputStream = ClassLoader.getSystemResourceAsStream(WSDL_TEMPLATE_FILENAME);
		BufferedInputStream bin = new BufferedInputStream(inputStream);
		int ch = 0;
		StringBuffer buf = new StringBuffer();
		while ((ch = bin.read()) > -1) {

			buf.append((char) ch);
		}
		bin.close();
		return buf.toString();
	}
	
	protected String replacePlaceHolders(final String original, final String schema, final String service) {
		String replaced = original.replace(PLACEHOLDER+PLACEHOLDER_SCHEMALOCATION+PLACEHOLDER, schema);
		replaced = replaced.replace(PLACEHOLDER+PLACEHOLDER_SERVICE+PLACEHOLDER, service);
		return replaced;
	}
}
