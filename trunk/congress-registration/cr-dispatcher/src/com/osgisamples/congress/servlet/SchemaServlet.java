package com.osgisamples.congress.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.ServiceReference;

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
		response.setContentType("text/xml");
		PrintWriter writer = response.getWriter();
		String completeUrlPath = request.getPathInfo();
		
		RequestInfo requestInfo = new RequestInfo(completeUrlPath);
		
		if (requestInfo.getWsdl() != null) {
			writer.write(generateWsdl(requestInfo,request));
		} else if (requestInfo.getXsd() != null) {
			writer.write(obtainXsd(requestInfo));
		} else {
			
		}
		// show directory contents
		// other cases 
		//    - show only files in directory ending with .xsd
		//    - show link to generated wsdl
		writer.close();
	}
	
	private String obtainXsd(RequestInfo requestInfo) {
		ServiceReference serviceReference = 
			locator.findServiceReference(requestInfo.getService() + "Request", requestInfo.getVersion());
		String xsd = null;
		if(serviceReference != null) {
			FileLoadingUtil fileLoadingUtil = new FileLoadingUtil();
			try {
				xsd = fileLoadingUtil.loadFileFromBundle(serviceReference.getBundle(), "WS-INF/" + requestInfo.getXsd());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return xsd;
	}

	private String generateWsdl(RequestInfo requestInfo, HttpServletRequest request) {
		WsdlGenerator wsdlGenerator = new WsdlGenerator();
		String schema = requestInfo.getService() + ".xsd";
		String servernameport = request.getLocalName() + ":" + request.getLocalPort();
		String wsdl = wsdlGenerator.generatewsdl(schema, requestInfo.getService(), servernameport);
		return wsdl;
	}
}
