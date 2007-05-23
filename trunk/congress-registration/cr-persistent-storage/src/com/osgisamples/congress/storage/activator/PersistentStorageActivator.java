package com.osgisamples.congress.storage.activator;

import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.osgisamples.congress.dataaccess.CongressDao;
import com.osgisamples.congress.dataaccess.RegistrantDao;
import com.osgisamples.congress.dataaccess.SessionDao;
import com.osgisamples.congress.dataaccess.impl.CongressDaoImpl;
import com.osgisamples.congress.dataaccess.impl.RegistrantDaoImpl;
import com.osgisamples.congress.dataaccess.impl.SessionDaoImpl;

public class PersistentStorageActivator implements BundleActivator {

	public void start(BundleContext context) throws Exception {
		Object congressDaoService = new CongressDaoImpl();
		Object registrantDaoService = new RegistrantDaoImpl();
		Object sessionsDaoService = new SessionDaoImpl();
		Properties props =  new Properties();
		context.registerService(CongressDao.class.getName(), congressDaoService, props);
		context.registerService(RegistrantDao.class.getName(), registrantDaoService, props);
		context.registerService(SessionDao.class.getName(), sessionsDaoService, props);
	}

	public void stop(BundleContext context) throws Exception {
		// nothing to do
	}

}
