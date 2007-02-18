package sample.http;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

import tutorial.example2.service.DictionaryService;

public class Activator implements BundleActivator,ServiceListener {

	private HttpServiceTracker serviceTracker;
    private ServiceReference dictionaryServiceReference;
    private BundleContext context;

	public void start(BundleContext context) throws Exception {
		initializeThisBundle(context);
        serviceTracker.setDictionaryService(findDictionaryService());
        serviceTracker.open();
	}

	public void stop(BundleContext context) throws Exception {
		serviceTracker.close();
		serviceTracker = null;
	}

	private void initializeThisBundle(BundleContext context) throws InvalidSyntaxException {
		this.context = context;
		serviceTracker = new HttpServiceTracker(context);
        this.context.addServiceListener(this,
                "(&(objectClass=" + DictionaryService.class.getName() + ")" +
                "(Language=*))");
	}

	private DictionaryService findDictionaryService() throws InvalidSyntaxException {
        // Query for any service references matching any language.
        ServiceReference[] foundDictionaryServiceReferences =
        	context.getServiceReferences(DictionaryService.class.getName(), "(Language=*)");

        // If we found references to any dictionary service(s), then just get
        // a reference to the first one so we can use it.
        DictionaryService dictionaryService = null;
        if (foundDictionaryServiceReferences != null) {
            dictionaryServiceReference = foundDictionaryServiceReferences[0];
            dictionaryService = (DictionaryService) context.getService(dictionaryServiceReference);
        }
        return dictionaryService;
	}

	public void serviceChanged(ServiceEvent serviceEvent) {
        if (serviceEvent.getType() == ServiceEvent.REGISTERED) {
        	newServiceIsRegistered(serviceEvent);
        } else if (serviceEvent.getType() == ServiceEvent.UNREGISTERING) {
        	serviceIsRemoved(serviceEvent);
        }
	}

	private void newServiceIsRegistered(ServiceEvent serviceEvent) {
        if (dictionaryServiceReference == null) {
            // Get a reference to the service object.
            dictionaryServiceReference = serviceEvent.getServiceReference();
            DictionaryService newDictionaryService = (DictionaryService) context.getService(dictionaryServiceReference);
            serviceTracker.setDictionaryService(newDictionaryService);
            System.out.println("Dictionary activated");
        } else {
        	System.out.println("Dictservice of language "+ dictionaryServiceReference.getProperty("Language") +" already available...");
        }

	}

	private void serviceIsRemoved(ServiceEvent serviceEvent) {
        if (serviceEvent.getServiceReference() == dictionaryServiceReference) {
            System.out.println("Active dictionary de-activated. Looking for another dictionary...");
        	// Unget service object and null references.
            context.ungetService(dictionaryServiceReference);
            dictionaryServiceReference = null;
            serviceTracker.setDictionaryService(null);

            // Query to see if we can get another service.
            ServiceReference[] refs = null;
			try {
				refs = context.getServiceReferences(DictionaryService.class.getName(), "(Language=*)");
			} catch (InvalidSyntaxException e) {
				e.printStackTrace();
			}
            if (refs != null)
            {
                // Get a reference to the first service object.
                dictionaryServiceReference = refs[0];
                DictionaryService newDictionaryService = (DictionaryService) context.getService(dictionaryServiceReference);
                serviceTracker.setDictionaryService(newDictionaryService);
                System.out.println("Found dictionary for language: " + dictionaryServiceReference.getProperty("Language"));
            }
            else
            {
            	System.out.println("No other languages found");
            }
        }
        else
        {
        	System.out.println("Unregistered service is not active one.");
        }
	}

	private class HttpServiceTracker extends ServiceTracker {
		private HelloWorldServlet helloWorldServlet;

		public HttpServiceTracker(BundleContext context) {
			super(context, HttpService.class.getName(), null);
			helloWorldServlet = new HelloWorldServlet();
		}

		public Object addingService(ServiceReference reference) {
			HttpService httpService = (HttpService) context.getService(reference);
			try {
				httpService.registerResources("/helloworld.html", "/helloworld.html", null); //$NON-NLS-1$ //$NON-NLS-2$
				httpService.registerServlet("/helloworld", helloWorldServlet, null, null); //$NON-NLS-1$
			} catch (Exception e) {
				e.printStackTrace();
			}
			return httpService;
		}

		public void removedService(ServiceReference reference, Object service) {
			HttpService httpService = (HttpService) service;
			httpService.unregister("/helloworld.html"); //$NON-NLS-1$
			httpService.unregister("/helloworld"); //$NON-NLS-1$
			super.removedService(reference, service);
		}

		public void setDictionaryService(DictionaryService dictionaryService) {
			helloWorldServlet.setDictionaryService(dictionaryService);
		}
	}

}
