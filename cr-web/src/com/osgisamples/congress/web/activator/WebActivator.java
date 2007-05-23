package com.osgisamples.congress.web.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

import com.osgisamples.congress.business.CongressManager;
import com.osgisamples.congress.web.ShowRegistrationsServlet;

public class WebActivator implements BundleActivator {
	private HttpServiceTracker serviceTracker;

	public void start(BundleContext context) throws Exception {
		serviceTracker = new HttpServiceTracker(context);
		serviceTracker.setCongressManager(findCongressManager(context));
		serviceTracker.open();
	}

	public void stop(BundleContext context) throws Exception {
		serviceTracker.close();
		serviceTracker = null;
	}

	private CongressManager findCongressManager(BundleContext context) {
		ServiceReference serviceReference = context.getServiceReference(CongressManager.class.getName());
		CongressManager congressManager = (CongressManager)context.getService(serviceReference);
		return congressManager;
	}
	
	private class HttpServiceTracker extends ServiceTracker {
		private ShowRegistrationsServlet showRegistrationsServlet;
		
		public void setCongressManager(CongressManager congressManager) {
			showRegistrationsServlet.setCongressManager(congressManager);
		}

		public HttpServiceTracker(BundleContext context) {
			super(context, HttpService.class.getName(), null);
			showRegistrationsServlet = new ShowRegistrationsServlet();
		}

		public Object addingService(ServiceReference reference) {
			HttpService httpService = (HttpService) context.getService(reference);
			try {
				httpService.registerServlet("/registrants", showRegistrationsServlet, null, null); //$NON-NLS-1$
			} catch (Exception e) {
				e.printStackTrace();
			}
			return httpService;
		}

		public void removedService(ServiceReference reference, Object service) {
			HttpService httpService = (HttpService) service;
			httpService.unregister("/registrants"); //$NON-NLS-1$
			super.removedService(reference, service);
		}
	}

}
