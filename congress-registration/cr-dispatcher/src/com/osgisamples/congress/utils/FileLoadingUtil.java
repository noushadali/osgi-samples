package com.osgisamples.congress.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.osgi.framework.Bundle;

public class FileLoadingUtil {

	private String rootFolder = "/";

	public List<String> listFilesInBundle(Bundle bundle) {
		List<String> filesFound = new ArrayList<String>();
		Enumeration<URL> foundUrls = bundle.findEntries(rootFolder, "*", false);
		while(foundUrls.hasMoreElements()) {
			URL foundUrl = foundUrls.nextElement();
			String name = foundUrl.getFile().substring(rootFolder.length());
			filesFound.add(name);
		}
		Collections.sort(filesFound);
		return filesFound;
	}
	
	public String loadFileFromBundle(Bundle bundle, String fileName) throws IOException {
		String folder = "";
		if(fileName.lastIndexOf("/") > 0) {
			folder = fileName.substring(0, fileName.lastIndexOf("/"));
		}
		String file = fileName.substring(fileName.lastIndexOf("/") + 1);
		Enumeration<URL> foundUrls = bundle.findEntries(rootFolder + folder, file, false);
		if(foundUrls != null && foundUrls.hasMoreElements()) {
			URL firstUrl = foundUrls.nextElement();
			return loadStringFromStream(firstUrl.openStream());
		}
		throw new FileNotFoundException("Not found: " + rootFolder + fileName);
	}
	
	public void setRootFolder(String rootFolder) {
		this.rootFolder = rootFolder;
	}
	
	protected String loadStringFromStream(InputStream inputStream) throws IOException {
		int ch = 0;
		StringBuffer buf = new StringBuffer();
		while ((ch = inputStream.read()) > -1) {

			buf.append((char) ch);
		}
		inputStream.close();
		return buf.toString();
	}
}
