package com.osgisamples.congress.provider;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public interface XmlWebServiceProvider {
	public Document doService(final Node request);
}
