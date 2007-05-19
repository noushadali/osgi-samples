package com.osgisamples.congress.servlet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.osgisamples.congress.provider.exceptions.ServiceProviderException;

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
			throw new ServiceProviderException("Provided URL does not match the expected format : e.g. /ServiceName/version/ServiceName.wsdl");
		}
		this.service = matcher.group(1);
		this.version = matcher.group(2);
		cutRequestedFolder(matcher.group(3));
	}
	
	private void cutRequestedFolder(final String requestedFolder) {
		String folder = requestedFolder;
		if (folder == null) {
			folder = "";
		}
		if (folder.endsWith(".wsdl")) {
			this.wsdl = folder;
		} else if (folder.endsWith(".xsd")) {
			this.xsd = folder;
		} else {
			this.requestedFolder = folder;
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
