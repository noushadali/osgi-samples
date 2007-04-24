package com.osgisamples.congress.servlet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestInfo {
	private final Pattern urlPattern = Pattern.compile("(?:/([^/]*)(?:/([^/]*)(?:/(.*))?)?)?");
	private String version;
	private String service;
	private String wsdl;
	private String xsd;
	private String requestedFolder;
	
	public RequestInfo(final String pathInfo) {
		Matcher matcher = urlPattern.matcher(pathInfo);
		if(!matcher.find()) {
			throw new RuntimeException("Wrong URL");
		}
		this.service = matcher.group(1);
		this.version = matcher.group(2);
		cutRequestedFolder(matcher.group(3));
	}
	
	private void cutRequestedFolder(final String requestedFolder) {
		if (requestedFolder.endsWith(".wsdl")) {
			this.wsdl = requestedFolder;
		} else if (requestedFolder.endsWith(".xsd")) {
			this.xsd = requestedFolder;
		} else {
			this.requestedFolder = requestedFolder;
		}
	}

	public String getRequestedFolder() {
		return requestedFolder;
	}

	public String getService() {
		return service;
	}

	public String getVersion() {
		return version;
	}

	public String getWsdl() {
		return wsdl;
	}

	public String getXsd() {
		return xsd;
	}
	

}
