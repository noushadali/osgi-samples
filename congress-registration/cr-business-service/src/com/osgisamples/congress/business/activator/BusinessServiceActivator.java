package com.osgisamples.congress.business.activator;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.osgisamples.congress.business.CongressManager;
import com.osgisamples.congress.business.impl.CongressManagerImpl;
import com.osgisamples.congress.dataaccess.CongressDao;
import com.osgisamples.congress.dataaccess.RegistrantDao;
import com.osgisamples.congress.dataaccess.impl.CongressDaoImpl;
import com.osgisamples.congress.dataaccess.impl.RegistrantDaoImpl;



public class BusinessServiceActivator implements BundleActivator {
	private final static Log logger = LogFactory.getLog(BusinessServiceActivator.class);

	public void start(BundleContext context) throws Exception {
		logger.debug("Starting the bundle.");
		CongressDao congressDao = new CongressDaoImpl();
		RegistrantDao registrantDao = new RegistrantDaoImpl();
		Object service = new CongressManagerImpl(congressDao,registrantDao);
		Properties props =  new Properties();
//		props.put("version", context.getBundle().getHeaders().get("version"));
		context.registerService(CongressManager.class.getName(), service, props);		
	}

	public void stop(BundleContext context) throws Exception {
		logger.debug("Stopping the bundle.");
	}

}
