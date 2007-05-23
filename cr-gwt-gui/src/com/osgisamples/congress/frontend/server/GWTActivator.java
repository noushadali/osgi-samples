package com.osgisamples.congress.frontend.server;

import javax.servlet.Servlet;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

import com.osgisamples.congress.business.CongressManager;

public class GWTActivator implements BundleActivator{

	private ServiceTracker congressManagerTracker;
	private ServiceTracker servletRegistrar;
	
	public void start(BundleContext context) throws Exception {
		// find the CongressManager with service tracker
		congressManagerTracker = new ServiceTracker(context,CongressManager.class.getName(), null);
		congressManagerTracker.open();
		// initialize servlet
		CongressServiceImpl congressServlet = new CongressServiceImpl(congressManagerTracker);
		servletRegistrar = new ServletRegistrar(
				context,"/com.osgisamples.congress.frontend.GWTClient/registrants",congressServlet);
		servletRegistrar.open();
	}

	public void stop(BundleContext context) throws Exception {
		congressManagerTracker.close();
		congressManagerTracker = null;
	}

	private class ServletRegistrar extends ServiceTracker {
		private Servlet registeredServlet;
		private String contextPath;
		
		public ServletRegistrar(BundleContext context,String contextPath, Servlet servlet) {
			super(context, HttpService.class.getName(), null);
			this.registeredServlet = servlet;
			this.contextPath = contextPath;
		}

		public Object addingService(ServiceReference reference) {
			HttpService httpService = (HttpService) context.getService(reference);
			try {
				httpService.registerServlet(contextPath, registeredServlet, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return httpService;
		}

		public void removedService(ServiceReference reference, Object service) {
			HttpService httpService = (HttpService) service;
			httpService.unregister(contextPath);
			super.removedService(reference, service);
		}
	}

}
