package com.osgisamples.congress.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.osgisamples.congress.business.CongressManager;
import com.osgisamples.congress.domain.Congress;
import com.osgisamples.congress.domain.Registrant;

public class ShowRegistrationsServlet extends HttpServlet {
	private static final long serialVersionUID = -171101234555472133L;

	private CongressManager congressManager;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.write("<html><head><title>Hello</title><body>");
		writer.write(createListOfRegistrants());
		writer.write("</body></html>");
	}

	private String createListOfRegistrants() {
		StringBuilder builder = new StringBuilder();
		Congress congress = new Congress();
		congress.setName("NLJug");
		Set<Registrant> registrants = congressManager.listAllRegistrantsForCongress(congress);
		for (Registrant registrant : registrants) {
			builder.append(registrant.getName());
			builder.append("<br/>");
		}
		return builder.toString();
	}
	
	public void setCongressManager(CongressManager congressManager) {
		this.congressManager = congressManager;
	}

	
}
