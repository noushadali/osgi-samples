package com.osgisamples.congress.frontend.server;

import java.util.ArrayList;
import java.util.Set;

import org.osgi.util.tracker.ServiceTracker;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.osgisamples.congress.business.CongressManager;
import com.osgisamples.congress.domain.Congress;
import com.osgisamples.congress.domain.Registrant;
import com.osgisamples.congress.frontend.client.CongressService;

public class CongressServiceImpl extends RemoteServiceServlet implements
		CongressService {
	private static final long serialVersionUID = -839111399482441907L;

	private ServiceTracker congressManagerServiceTracker;
	
	public CongressServiceImpl(final ServiceTracker congressManagerServiceTracker) {
		this.congressManagerServiceTracker = congressManagerServiceTracker;
	}

	public ArrayList listRegistrants() {
		CongressManager congressManager = (CongressManager)congressManagerServiceTracker.getService();
		ArrayList returnRegistrants = new ArrayList();
		if (congressManager != null) {
			Congress searchCongress = new Congress();
			searchCongress.setName("NLJug");
			try {
				Set<Registrant> foundRegistrants = congressManager.listAllRegistrantsForCongress(searchCongress);
				convertFoundRegistrants(returnRegistrants, foundRegistrants);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
		return returnRegistrants;
	}
	
	private void convertFoundRegistrants(final ArrayList returnRegistrants, final Set<Registrant> foundRegistrants) {
		for (Registrant registrant : foundRegistrants) {
			com.osgisamples.congress.frontend.client.Registrant returnRegistrant = new com.osgisamples.congress.frontend.client.Registrant();
			returnRegistrant.setCompany(registrant.getCompany());
			returnRegistrant.setEmailAddress(registrant.getEmailAddress());
			returnRegistrant.setId(registrant.getId());
			returnRegistrant.setName(registrant.getName());
			returnRegistrant.setRegistrationNumber(registrant.getRegistrationNumber());
			returnRegistrants.add(returnRegistrant);
		}
	}	
}
