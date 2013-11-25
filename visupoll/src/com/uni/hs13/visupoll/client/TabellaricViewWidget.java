package com.uni.hs13.visupoll.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;


public class TabellaricViewWidget extends Composite {

	private Button button; //Test, can be deleted.
	@UiField
	HTMLPanel main;		//same in xml file.
	
	private static TabellaricViewWidgetUiBinder uiBinder = GWT
			.create(TabellaricViewWidgetUiBinder.class);

	interface TabellaricViewWidgetUiBinder extends
			UiBinder<Widget, TabellaricViewWidget> {
	}

	public TabellaricViewWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		
		
		//Testing the UI Binder: Adding a button.
		button = new Button();
		button.setText("Button");
		
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				Window.alert("Hello!");
			}
		});
		main.add(button);
	}

}
