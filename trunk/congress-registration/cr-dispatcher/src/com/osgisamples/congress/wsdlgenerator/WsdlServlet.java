package com.osgisamples.congress.wsdlgenerator;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WsdlServlet extends HttpServlet {
	private static final long serialVersionUID = 1527886958277847001L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WsdlGenerator wsdlGenerator = new WsdlGenerator();
		response.setContentType("text/xml");
		PrintWriter writer = response.getWriter();
		writer.write(wsdlGenerator.generatewsdl("xsd/1.0.0/CongressRegistration.xsd", "CongressRegistration"));
	}

	
}
