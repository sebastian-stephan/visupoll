package com.uni.hs13.visupoll.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class GeographicViewWidget extends Composite {

	//TODO: Place variables here
	@UiField
	HTMLPanel main;
	
	private static GeographicViewWidgetUiBinder uiBinder = GWT
			.create(GeographicViewWidgetUiBinder.class);

	interface GeographicViewWidgetUiBinder extends
			UiBinder<Widget, GeographicViewWidget> {
	}

	public GeographicViewWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		
		//TODO: Code goes here.
		//For every object that has to be added write: main.add(<name>);
		
	}

}
