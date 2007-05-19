package com.osgisamples.congress.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

import com.osgisamples.congress.provider.exceptions.ServiceProviderException;
import com.osgisamples.congress.servicelocator.ServiceLocator;
import com.osgisamples.congress.utils.FileLoadingUtil;
import com.osgisamples.congress.wsdlgenerator.WsdlGenerator;

public class SchemaServlet extends HttpServlet {
	private static final long serialVersionUID = 139999992756301122L;
	private ServiceLocator locator;
	
	public void setServiceLocator(ServiceLocator locator) {
		this.locator = locator;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		String completeUrlPath = request.getPathInfo();
		RequestInfo requestInfo = new RequestInfo(completeUrlPath);
		//String requestedFolder = requestInfo.getRequestedFolder();
		if(requestInfo.getXsd() != null || requestInfo.getWsdl() != null) {
			response.setContentType("text/xml");
			if (requestInfo.getWsdl() != null) {
				writer.write(generateWsdl(requestInfo,request));
			} else if (requestInfo.getXsd() != null) {
				writer.write(obtainXsd(requestInfo));
			} 
		} else {
			response.setContentType("text/html");
			writer.write(generateDirectoryListingPage(requestInfo));
		}
		writer.close();
	}
	
	private String generateDirectoryListingPage(RequestInfo requestInfo) {
		String returnString = "<html><head><title>Directory listing</title></head><body>";
		Bundle bundle = getBundleForRequestedService(requestInfo);
		if(bundle != null) {
			FileLoadingUtil fileLoadingUtil = new FileLoadingUtil();
			if(requestInfo.getRequestedFolder() != null) {
				fileLoadingUtil.setRootFolder(requestInfo.getRequestedFolder());
			}
			List<String> fileList = fileLoadingUtil.listFilesInBundle(bundle);
			StringBuilder stringBuilder = new StringBuilder();
			for (String file : fileList) {
				stringBuilder.append("<a href=\"")
					.append(file.substring(1))
					.append("\"/>")
					.append(file)
					.append("</a>")
					.append("<br>");
			}
			returnString = stringBuilder.toString();
		}
		returnString += "</body></html>"; 
		return returnString;
	}

	private String obtainXsd(RequestInfo requestInfo) {
		String returnXsd = null;
		Bundle bundle = getBundleForRequestedService(requestInfo);
		if(bundle != null) {
			FileLoadingUtil fileLoadingUtil = new FileLoadingUtil();
			try {
				returnXsd = fileLoadingUtil.loadFileFromBundle(bundle, "WS-INF/" + requestInfo.getXsd());
			} catch (IOException e) {
				throw new ServiceProviderException("Problem while obtaining xsd", e);
			}
		}
		return returnXsd;
	}

	private String generateWsdl(RequestInfo requestInfo, HttpServletRequest request) {
		WsdlGenerator wsdlGenerator = new WsdlGenerator();
		String schema = requestInfo.getService() + ".xsd";
		String version = requestInfo.getVersion();
		String servernameport = request.getLocalName() + ":" + request.getLocalPort();
		String wsdl = wsdlGenerator.generatewsdl(schema, requestInfo.getService(),version, servernameport);
		return wsdl;
	}
	
	private Bundle getBundleForRequestedService(RequestInfo requestInfo) {
		Bundle returnBundle = null;
		ServiceReference serviceReference = 
			locator.findServiceReference(requestInfo.getService() + "Request", requestInfo.getVersion());
		if (serviceReference != null) {
			returnBundle = serviceReference.getBundle();
		}
		return returnBundle;
	}
}
