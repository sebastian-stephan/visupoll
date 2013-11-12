package com.uni.hs13.visupoll.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.uni.hs13.visupoll.client.rpc.PollDataService;
import com.uni.hs13.visupoll.client.rpc.PollDataServiceAsync;
import com.uni.hs13.visupoll.datastructures.CantonData;
import com.uni.hs13.visupoll.datastructures.Poll;


public class Home implements EntryPoint {
	PollDataServiceAsync pollDataService = (PollDataServiceAsync) GWT.create(PollDataService.class);
	ListBox pollDropDown;
	FlexTable dataTable;
	DialogBox helpDialog;
	Button helpButton;
	
	@Override
	public void onModuleLoad() {
	    // List box containing all polls
	    pollDropDown = new ListBox();
	    pollDropDown.addItem("--- Bitte Abstimmung auswählen ---");
	    pollDropDown.setVisibleItemCount(1); 
	    pollDropDown.setWidth("300px");
	    RootPanel.get().add(pollDropDown);
	    
	    // Help dialog/button
	    helpDialog = new DialogBox(true);
	    helpDialog.setText("Blabla help for you");
	    helpDialog.setGlassEnabled(true);   
	 
	    helpButton = new Button("Help mee");
	    helpButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				helpDialog.center();
				helpDialog.show();
			}
	    });
	    RootPanel.get().add(helpButton);
	    
	
	    
	    
	    
	    // Table showing data
		dataTable = new FlexTable();
		dataTable.getElement().setId("data-table");
		dataTable.setVisible(false);
		RootPanel.get().add(dataTable);
	    
		// Load list of polls
	    pollDataService.getListOfPolls(pollListLoaded);
	    setWaitCursor();
		
	}
	
	// List of polls was loaded
	AsyncCallback< ArrayList<Poll> > pollListLoaded = new AsyncCallback<ArrayList<Poll>> () {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Failed to load list of polls");
			caught.printStackTrace();	
		}
		@Override
		public void onSuccess(ArrayList<Poll> polls) {
			setDefaultCursor();
			// Fill Dropdown list
			for(int i=0;i<polls.size();i++) {
				pollDropDown.addItem(polls.get(i).description);
			}
			pollDropDown.addChangeHandler(new ChangeHandler() {
				// Handler for change of Dropbox
				@Override
				public void onChange(ChangeEvent event) {
					if (pollDropDown.getSelectedIndex() > 0) {
						setWaitCursor();
						pollDataService.getPoll(pollDropDown.getSelectedIndex() - 1, pollLoaded);
					}
				}
			});
		}
		
	};
	
	// Poll was loaded (after selection in dropbox)
	AsyncCallback<Poll> pollLoaded = new AsyncCallback<Poll> (){
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Failed to load selected Poll");
			caught.printStackTrace();
		}
		@Override
		public void onSuccess(Poll p) {
			setDefaultCursor();
			dataTable.removeAllRows();
			for(final CantonData canton : p.cantons) {
				dataTable.setText(dataTable.getRowCount(), 0, 
						canton.getCantonNameLong() + " ("+ Math.round(canton.getYesPercent()*10.0)/10.0 + "%)");
			}
			dataTable.setVisible(true);
		}
	};
	

	// Jquery eyecandy
	public static native void setWaitCursor() /*-{
		$wnd.jQuery("body").css("cursor", "wait");
	}-*/;
	
	public static native void setDefaultCursor() /*-{
		$wnd.jQuery("body").css("cursor", "default");
	}-*/;
	
}
