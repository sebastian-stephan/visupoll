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
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.uni.hs13.visupoll.client.rpc.PollDataService;
import com.uni.hs13.visupoll.client.rpc.PollDataServiceAsync;
import com.uni.hs13.visupoll.datastructures.CantonData;
import com.uni.hs13.visupoll.datastructures.Poll;


public class Home implements EntryPoint {
	PollDataServiceAsync pollDataService = (PollDataServiceAsync) GWT.create(PollDataService.class);
	
	FlexTable dataTable;
	ListBox pollList;
	
	@Override
	public void onModuleLoad() {
		
				/*MapView mapView = new MapView();
				RootPanel.get().add(mapView);*/
				
				 
				final DialogBox dialogContents;	//dialogBox
				
				VerticalPanel vPanel_1 = new VerticalPanel(); // left set of controls
				vPanel_1.setSpacing(10);
				vPanel_1.setWidth("600px");

				RadioButton mapViewButton = new RadioButton("radioGroup", "Map view");
				RadioButton tableViewButton = new RadioButton("radioGroup",	"Table view");
				mapViewButton.setValue(true);

				vPanel_1.add(mapViewButton);
				vPanel_1.add(tableViewButton);

				HorizontalPanel hPanel_1 = new HorizontalPanel(); // list boxes and Show
																	// button
				hPanel_1.setSpacing(10);

				ListBox yearList = new ListBox();
				yearList.setWidth("100px");
				yearList.addItem("Year");
				yearList.addItem("2013");
				yearList.addItem("2012");
				yearList.addItem("2011");
				yearList.addItem("2010");

				ListBox cantonList = new ListBox();
				cantonList.setWidth("100px");
				cantonList.addItem("Canton");
				cantonList.addItem("Zurich");
				cantonList.addItem("Aargau");
				cantonList.addItem("Bern");
				cantonList.addItem("And so on..");
				
				pollList = new ListBox();
				pollList.setWidth("100px");
								
				ListBox districtList = new ListBox();
				districtList.setWidth("100px");
				districtList.addItem("District");
				districtList.addItem("District 1");
				districtList.addItem("District 2");
				districtList.addItem("District 3");
				districtList.addItem("And so on..");

				Button showButton = new Button("Show");
				showButton.setWidth("50px");

				hPanel_1.add(yearList);
				hPanel_1.add(pollList);
				hPanel_1.add(cantonList);
				hPanel_1.add(districtList);
				hPanel_1.add(showButton);

				vPanel_1.add(hPanel_1);

				VerticalPanel vPanel_2 = new VerticalPanel(); // Help and About buttons
				vPanel_2.setSpacing(10);
				VerticalPanel vPanel_3 = new VerticalPanel(); // empty panel (spacing
																// between pairs of
																// buttons)
				vPanel_3.setHeight("200px");
				VerticalPanel vPanel_4 = new VerticalPanel(); // Comment and Share
																// buttons
				vPanel_4.setSpacing(10);

				Button helpButton = new Button("Help");
				helpButton.setWidth("100px");
				helpButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						Window.alert("It is the HELP");
						/*DialogBox dialogBox = new DialogBox(true);
						dialogBox.setAnimationEnabled(true);
						dialogBox.setTitle("Text example(title");
						dialogBox.setText("Text of help button");
						dialogBox.show();
						dialogBox.setGlassEnabled(true);
						dialogBox.center();*/
					}
				});
				
				Button aboutButton = new Button("About");
				aboutButton.setWidth("100px");
				aboutButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						Window.alert("It is ABOUT \nThis should be on a new line\nIt is ABOUT");
					}
				});
				
				dialogContents = new DialogBox(true); //Dialog box
				VerticalPanel dialogPanel = new VerticalPanel();
				dialogContents.add(dialogPanel);
				Button testButton = new Button("TEST");
				dialogPanel.add(testButton);
				dialogContents.setGlassEnabled(true);
				dialogContents.setAnimationEnabled(true);
				
				
				Button commentButton = new Button("Comment");
				commentButton.addClickHandler(new ClickHandler(){
					
					@Override
					public void onClick(ClickEvent event) {
						
						dialogContents.center();
						dialogContents.show();
						
						
					}
					
				});
				commentButton.setWidth("100px");
				Button shareButton = new Button("Share");
				shareButton.setWidth("100px");

				vPanel_2.add(helpButton);
				vPanel_2.add(aboutButton);
				vPanel_4.add(commentButton);
				vPanel_4.add(shareButton);
				
				VerticalPanel vPanel_5 = new VerticalPanel(); // right set of controls
																// (4 buttons with
																// spacing)
				vPanel_5.add(vPanel_2);
				vPanel_5.add(vPanel_3);
				vPanel_5.add(vPanel_4);

				

					
					VerticalPanel vPanel_6 = new VerticalPanel();
					vPanel_1.add(vPanel_6);
					
					vPanel_6.setWidth("502px");

				HorizontalPanel hPanel_2 = new HorizontalPanel(); // final panel
				hPanel_2.setSpacing(10);
				hPanel_2.add(vPanel_1);
				hPanel_2.add(vPanel_5);
				
				AbsolutePanel aPanel = new AbsolutePanel();
				aPanel.add(hPanel_2);

				RootPanel.get().add(aPanel);

			
		

	    // List box containing all polls
	    
		pollList.addItem("--- Bitte Abstimmung auswählen ---");
	    pollList.setVisibleItemCount(1); 
	    pollList.setWidth("300px");
	    //RootPanel.get().add(pollList);
	    
	        
	    
	    // Table showing data
		dataTable = new FlexTable();
		dataTable.getElement().setId("data-table");
		dataTable.setVisible(false);
		//RootPanel.get().add(dataTable);
		vPanel_6.add(dataTable);
	    
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
				pollList.addItem(polls.get(i).description);
			}
			pollList.addChangeHandler(new ChangeHandler() {
				// Handler for change of Dropbox
				@Override
				public void onChange(ChangeEvent event) {
					if (pollList.getSelectedIndex() > 0) {
						setWaitCursor();
						pollDataService.getPoll(pollList.getSelectedIndex() - 1, pollLoaded);
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
