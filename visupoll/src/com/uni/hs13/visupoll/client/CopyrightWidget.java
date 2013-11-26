package com.uni.hs13.visupoll.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CopyrightWidget extends Composite {

	Label text;
	
	@UiField
	HTMLPanel main;
	
	private static CopyrightWidgetUiBinder uiBinder = GWT
			.create(CopyrightWidgetUiBinder.class);

	interface CopyrightWidgetUiBinder extends UiBinder<Widget, CopyrightWidget> {
	}

	public CopyrightWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		
		text = new Label();
		
		text.setText("© 2013, I. Ryzhov - J. Oesch - S. Stephan - L. Klopfer");
		
		main.add(text);
	}

}
