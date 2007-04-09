package com.osgisamples.congress.storage.activator;

import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.osgisamples.congress.dataaccess.CongressDao;
import com.osgisamples.congress.dataaccess.RegistrantDao;
import com.osgisamples.congress.dataaccess.impl.CongressDaoImpl;
import com.osgisamples.congress.dataaccess.impl.RegistrantDaoImpl;

public class PersistentStorageActivator implements BundleActivator {

	public void start(BundleContext context) throws Exception {
		Object congressDaoService = new CongressDaoImpl();
		Object registrantDaoService = new RegistrantDaoImpl();
		Properties props =  new Properties();
		context.registerService(CongressDao.class.getName(), congressDaoService, props);
		context.registerService(RegistrantDao.class.getName(), registrantDaoService, props);
	}

	public void stop(BundleContext context) throws Exception {
		// nothing to do
	}

}
