package com.uni.hs13.visupoll.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Home implements EntryPoint {

	@Override
	public void onModuleLoad() {
		RootPanel.get().add(new Label("This label was added dymanically by JS ;-)"));
		System.out.println("Hallo");
		
	}

}
