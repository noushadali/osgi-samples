package com.osgisamples.congress.wsdlgenerator;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.ServiceReference;

import com.osgisamples.congress.servicelocator.ServiceLocator;
import com.osgisamples.congress.utils.FileLoadingUtil;

public class WsdlServlet extends HttpServlet {
	private static final long serialVersionUID = 1527886958277847001L;
	private static final Pattern urlPattern = Pattern.compile("(?:/([^/]*)(?:/([^/]*)(?:/(.*))?)?)?");
	
	private ServiceLocator locator;
	
	public void setServiceLocator(ServiceLocator locator) {
		this.locator = locator;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		WsdlGenerator wsdlGenerator = new WsdlGenerator();
		Matcher matcher = urlPattern.matcher(request.getPathInfo());
		if(!matcher.find()) {
			throw new RuntimeException("Wrong URL");
		}
		String serviceName = matcher.group(1);
		String version = matcher.group(2);
		String requestedFolder = matcher.group(3);
		
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		ServiceReference sr = locator.findServiceReference(serviceName + "Request", version);
		if(sr != null) {
			FileLoadingUtil fileLoadingUtil = new FileLoadingUtil();
			
			if(requestedFolder != null && !"".equals(requestedFolder) && !requestedFolder.endsWith("/")) {
				writer.write(fileLoadingUtil.loadFileFromBundle(sr.getBundle(), requestedFolder));
			}
			else {
				if(requestedFolder != null) {
					fileLoadingUtil.setRootFolder(requestedFolder);
				}
				List<String> fileList = fileLoadingUtil.listFilesInBundle(sr.getBundle());
				for (String file : fileList) {
					writer.write("<a href=\"");
					writer.write(file.substring(1));
					writer.write("\"/>");
					writer.write(file);
					writer.write("</a>");
					writer.write("<br>");
				}
			}
			
		}
		else {
			writer.write("File not found");
		}
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
