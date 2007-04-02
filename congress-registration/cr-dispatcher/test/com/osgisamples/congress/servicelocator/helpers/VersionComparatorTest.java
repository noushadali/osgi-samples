package com.osgisamples.congress.servicelocator.helpers;

import junit.framework.TestCase;

public class VersionComparatorTest extends TestCase {

	public void testVersionCompatibility() {
		// compatible requests
		assertTrue(VersionComparator.isCompatible("1", "1"));
		assertTrue(VersionComparator.isCompatible("1.0", "1.0"));
		assertTrue(VersionComparator.isCompatible("1", "1.1"));
		assertTrue(VersionComparator.isCompatible("1.1", "1.1.2"));
		assertTrue(VersionComparator.isCompatible("1.0", "1"));
		
		// incompatible requests
		assertFalse(VersionComparator.isCompatible("1.1", "1.11.0"));
		assertFalse(VersionComparator.isCompatible("1.2", "1.11.0"));
		assertFalse(VersionComparator.isCompatible("1.11", "1.12.0"));
		assertFalse(VersionComparator.isCompatible("1.0", "1.1"));
		assertFalse(VersionComparator.isCompatible("2", "1.12.0"));
		
		// erroneous requests
		assertFalse(VersionComparator.isCompatible("2", "1.12.0"));
	}
	
	
}
