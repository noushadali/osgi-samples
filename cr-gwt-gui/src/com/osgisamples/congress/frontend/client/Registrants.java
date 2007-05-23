package com.osgisamples.congress.frontend.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Registrants implements IsSerializable {
	
	/**
	 * @gwt.typeArgs <com.osgisamples.congress.frontend.client.Registrant>
	 */
	private ArrayList listOfRegistrants;

	public ArrayList getListOfRegistrants() {
		return listOfRegistrants;
	}

	public void setListOfRegistrants(ArrayList listOfRegistrants) {
		this.listOfRegistrants = listOfRegistrants;
	}
}
