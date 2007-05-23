package com.osgisamples.congress.frontend.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;

public abstract class UpdateableComposite extends Composite {
	private Timer timer = null;
	
	public abstract void update();
	
	public void startUpdateTimer(int seconds) {
		if (timer != null) {
			stopUpdateTimer();
		}
		
		timer = new Timer() {
			public void run() {
				update();
			}
		};
		timer.scheduleRepeating(seconds * 1000);
	}
	
	public void stopUpdateTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}
}
