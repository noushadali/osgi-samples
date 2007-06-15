package com.osgisamples.congress.domain;

import java.io.Serializable;

public abstract class BaseDomain implements Serializable {
	@Override
	public abstract String toString();
	
	@Override
	public abstract boolean equals(Object obj);
	
	@Override
	public abstract int hashCode();
}
