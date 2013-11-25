package com.uni.hs13.visupoll.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class SidebarWidget extends Composite {

	//TODO: Place variables here
	@UiField
	HTMLPanel main;
	
	private static SidebarWidgetUiBinder uiBinder = GWT
			.create(SidebarWidgetUiBinder.class);

	interface SidebarWidgetUiBinder extends
			UiBinder<Widget, SidebarWidget> {
	}

	public SidebarWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		
		//TODO: Code goes here.
		//For every object that has to be added write: main.add(<name>);
		
	}

}
