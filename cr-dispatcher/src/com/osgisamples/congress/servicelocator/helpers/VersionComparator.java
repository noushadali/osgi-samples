package com.osgisamples.congress.servicelocator.helpers;

import org.osgi.framework.Version;

public class VersionComparator {

	public static boolean isCompatible(final String referenceVersion, final String testVersion) {
		boolean compatible = false;
		try
		{
			String fullTestVersion = Version.parseVersion(testVersion).toString();
			if(referenceVersion.equals(testVersion) || fullTestVersion.startsWith(referenceVersion+".")) {
				compatible = true;
			}
		}
		catch (NumberFormatException ex) {
		}
		return compatible;
	}

}
