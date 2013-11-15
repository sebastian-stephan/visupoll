package com.uni.hs13.visupoll.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sun.java.swing.plaf.windows.WindowsBorders;
import com.uni.hs13.visupoll.client.rpc.PollDataService;
import com.uni.hs13.visupoll.client.rpc.PollDataServiceAsync;
import com.uni.hs13.visupoll.datastructures.CantonData;
import com.uni.hs13.visupoll.datastructures.DistrictData;
import com.uni.hs13.visupoll.datastructures.Poll;
import com.uni.hs13.visupoll.datastructures.TownshipData;

public class Home implements EntryPoint {
	PollDataServiceAsync pollDataService = (PollDataServiceAsync) GWT
			.create(PollDataService.class);

	private static final String DEFAULT_COMMENT_BODY_AREA_TEXT = "Your comment";
	private static final String DEFAULT_COMMENT_EMAIL_ADDRESS_TEXT = "E-mail address";
	private static final String DEFAULT_SHARE_EMAIL_ADDRESS_TEXT = "E-mail address";

	private static final String DEAD_DROPBOX_ITEM = "NA";
	
	protected Poll curPoll = null;
		
	FlexTable dataTable;
	
	ListBox yearList;
	ListBox cantonList;
	OptGroupListBox pollList;
	ListBox districtList;
	
	VerticalPanel vPanel_1;
	VerticalPanel vPanel_2;
	VerticalPanel vPanel_3;
	VerticalPanel comment_vPanel;
	VerticalPanel share_vPanel;
	VerticalPanel help_vPanel;
	VerticalPanel about_vPanel;
	
	HorizontalPanel hPanel_1;
	HorizontalPanel hPanel_2;
	HorizontalPanel comment_hPanel;
	HorizontalPanel share_hPanel;
	HorizontalPanel about_hPanel;
	
	Button helpButton;
	Button aboutButton;
	Button commentButton;
	Button shareButton;
	
	Button commentCancelButton;
	Button commentSendButton;
	Button shareCancelButton;
	Button shareSendButton;
	Button helpCloseButton;
	Button aboutCloseButton;
	
	TextArea commentEmailArea;
	TextArea commentBodyArea;
	TextArea shareEmailArea;
	TextArea helpTextArea;
	TextArea aboutTextArea;
		

	@Override
	public void onModuleLoad() {

		final DialogBox commentDialog;
		final DialogBox shareDialog;
		final DialogBox helpDialog;
		final DialogBox aboutDialog;

		vPanel_1 = new VerticalPanel(); 					// Left set of controls
		vPanel_1.setSpacing(10);
		vPanel_1.setWidth("600px");
		
		hPanel_1 = new HorizontalPanel(); 					// List boxes
		hPanel_1.setSpacing(10);

		yearList = new ListBox();
		yearList.setWidth("100px");
		yearList.addItem("Year");
		yearList.addItem("2013");							// Sample years
		yearList.addItem("2012");
		yearList.addItem("2011");
		yearList.addItem("2010");

		cantonList = new ListBox();
		cantonList.setWidth("100px");
		cantonList.addItem("Canton", DEAD_DROPBOX_ITEM);
		cantonList.setEnabled(false);
		
		

		pollList = new OptGroupListBox();
		districtList = new ListBox();
		districtList.setWidth("100px");
		districtList.addItem("District", DEAD_DROPBOX_ITEM);
		districtList.setEnabled(false);

		//hPanel_1.add(yearList);
		hPanel_1.add(pollList);
		
		hPanel_1.add(cantonList);
		hPanel_1.add(districtList);

		vPanel_1.add(hPanel_1);

		
		vPanel_2 = new VerticalPanel(); 					// Help, About, Comment and Share buttons
		vPanel_2.setSpacing(10);
				
		
		
		//******* Dialog box for Help Button *******//
		
		helpDialog = new DialogBox();
		helpDialog.setAnimationEnabled(true);
		helpDialog.setGlassEnabled(true);
		helpDialog.setText("HELP");
		help_vPanel = new VerticalPanel();
		help_vPanel.setSpacing(10);
		
		helpTextArea = new TextArea();
		helpTextArea.setEnabled(false);
		helpTextArea.setWidth("500px");
		helpTextArea.setHeight("170px");
		helpTextArea.setText("Select the year and election type which you would like to visualize."
				+ "\nSelecting canton and/or district will show "
				+ "information only in the selected region.\n\nBy clicking 'Comment' button you can send "
				+ "comment on the current visualization to the e-mail address specified."
				+ "\n\nBy clicking 'Share' button you can send the current visualization to "
				+ "the e-mail address specified.");
				
		helpCloseButton = new Button("Ok");
		helpCloseButton.setWidth("100px");
		helpCloseButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				helpDialog.hide();
			}
		});
		
		help_vPanel.add(helpTextArea);
		help_vPanel.add(helpCloseButton);
		helpDialog.add(help_vPanel);
		help_vPanel.setCellHorizontalAlignment(helpTextArea, HasHorizontalAlignment.ALIGN_CENTER);
		help_vPanel.setCellHorizontalAlignment(helpCloseButton, HasHorizontalAlignment.ALIGN_CENTER);
		
		helpButton = new Button("Help");
		helpButton.setWidth("100px");
		helpButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				helpDialog.center();
				helpDialog.show();
			}
		});
		
		//******* END - Dialog box for Help Button *******//

		
		
		//******* Dialog box for About Button *******//
		
		aboutDialog = new DialogBox();
		aboutDialog.setAnimationEnabled(true);
		aboutDialog.setGlassEnabled(true);
		aboutDialog.setText("About");
		about_vPanel = new VerticalPanel();
		about_vPanel.setSpacing(10);
		
		aboutTextArea = new TextArea();
		aboutTextArea.setWidth("500px");
		aboutTextArea.setHeight("150px");
		aboutTextArea.setText("This web application offers a graphical and tabular visualization of "
				+ "the results of elections in Switzerland in the last few years.\n\n"
				+ "Authors:\nGroup '4 Musketiere'\n\nAutumn 2013\nUniversity of Zurich");
		aboutTextArea.setEnabled(false);
		
		aboutCloseButton = new Button("Ok");
		aboutCloseButton.setWidth("100px");
		aboutCloseButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				aboutDialog.hide();
			}
		});
		
		about_vPanel.add(aboutTextArea);
		about_vPanel.add(aboutCloseButton);
		aboutDialog.add(about_vPanel);
		about_vPanel.setCellHorizontalAlignment(aboutTextArea, HasHorizontalAlignment.ALIGN_CENTER);
		about_vPanel.setCellHorizontalAlignment(aboutCloseButton, HasHorizontalAlignment.ALIGN_CENTER);
		
		aboutButton = new Button("About");
		aboutButton.setWidth("100px");
		aboutButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				aboutDialog.center();
				aboutDialog.show();
			}
		});
		
		//******* END - Dialog box for About Button *******//

		
		
		//******* Dialog box for Comment Button *******//
		
		commentDialog = new DialogBox();
		commentDialog.setText("Comment");
		commentDialog.setGlassEnabled(true);
		commentDialog.setAnimationEnabled(true);

		comment_vPanel = new VerticalPanel();
		comment_vPanel.setSpacing(10);
		
		commentDialog.add(comment_vPanel);
		
		commentEmailArea = new TextArea();
		commentEmailArea.setWidth("200px");
		commentEmailArea.setHeight("20px");
		
		commentEmailArea.addFocusHandler(new FocusHandler() {			// Removes sample text ("E-mail address") after the first click
			@Override
			public void onFocus(FocusEvent event) {
				if(commentEmailArea.getText().equals(DEFAULT_COMMENT_EMAIL_ADDRESS_TEXT)) commentEmailArea.setText("");
			}
		});
		commentEmailArea.addBlurHandler(new BlurHandler() {				// ... and put's it back when user didn't enter anything
			@Override
			public void onBlur(BlurEvent event) {
				if(commentEmailArea.getText().equals("")) commentEmailArea.setText(DEFAULT_COMMENT_EMAIL_ADDRESS_TEXT);
			}
			
		});
						
		commentBodyArea = new TextArea();
		commentBodyArea.setWidth("200px");
		commentBodyArea.setHeight("150px");
						
		commentBodyArea.addFocusHandler(new FocusHandler() {			// Removes sample text ("Your comment") after the first click
			@Override
			public void onFocus(FocusEvent event) {
				if (commentBodyArea.getText().equals(DEFAULT_COMMENT_BODY_AREA_TEXT)) commentBodyArea.setText("");
			}			
		});
		commentBodyArea.addBlurHandler(new BlurHandler() {				// ... and put's it back when user didn't enter anything
			@Override
			public void onBlur(BlurEvent event) {						
				if (commentBodyArea.getText().equals("")) commentBodyArea.setText(DEFAULT_COMMENT_BODY_AREA_TEXT);
			}
		});
		
		comment_hPanel = new HorizontalPanel();
		comment_hPanel.setSpacing(10);
		commentSendButton = new Button("Send");
		commentSendButton.setWidth("100px");
		
		commentCancelButton = new Button("Cancel");
		commentCancelButton.setWidth("100px");
		commentCancelButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				commentDialog.hide();
			}
		});
		
		
		comment_hPanel.add(commentSendButton);
		comment_hPanel.add(commentCancelButton);
				
		comment_vPanel.add(commentEmailArea);
		comment_vPanel.add(commentBodyArea);
		comment_vPanel.add(comment_hPanel);
		comment_vPanel.setCellHorizontalAlignment(commentEmailArea, HasHorizontalAlignment.ALIGN_CENTER);
		comment_vPanel.setCellHorizontalAlignment(commentBodyArea, HasHorizontalAlignment.ALIGN_CENTER);
		comment_vPanel.setCellHorizontalAlignment(comment_hPanel, HasHorizontalAlignment.ALIGN_CENTER);
		
		commentButton = new Button("Comment");
		commentButton.setWidth("100px");
		commentButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				commentDialog.center();
				commentDialog.show();
				commentEmailArea.setText("E-mail address");
				commentBodyArea.setText("Your comment");
			}
		});
		
		//******* END - Dialog box for Comment Button *******//
		
		
		
		//******* Dialog box for Share Button *******//
		
		shareDialog = new DialogBox();
		shareDialog.setText("Share");
		shareDialog.setGlassEnabled(true);
		shareDialog.setAnimationEnabled(true);
		
		share_vPanel = new VerticalPanel();
		share_vPanel.setSpacing(10);
		
		shareDialog.add(share_vPanel);
		
		shareEmailArea = new TextArea();
		shareEmailArea.setWidth("200px");
		shareEmailArea.setHeight("20px");
				
		shareEmailArea.addFocusHandler(new FocusHandler() {					// Removes sample text ("E-mail address") after the first click
			@Override
			public void onFocus(FocusEvent event) {
				if (shareEmailArea.getText().equals(DEFAULT_SHARE_EMAIL_ADDRESS_TEXT)) shareEmailArea.setText("");
			}
		});
		shareEmailArea.addBlurHandler(new BlurHandler() {					// ... and put's it back when user didn't enter anything
			@Override
			public void onBlur(BlurEvent event) {
				if (shareEmailArea.getText().equals("")) shareEmailArea.setText(DEFAULT_SHARE_EMAIL_ADDRESS_TEXT);				
			}
		});
						
		share_hPanel = new HorizontalPanel();
		share_hPanel.setSpacing(10);
		shareSendButton = new Button("Send");
		shareSendButton.setWidth("100px");
		
		shareCancelButton = new Button("Cancel");
		shareCancelButton.setWidth("100px");
		shareCancelButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				shareDialog.hide();
			}
		});
				
		share_hPanel.add(shareSendButton);
		share_hPanel.add(shareCancelButton);
				
		share_vPanel.add(shareEmailArea);
		share_vPanel.add(share_hPanel);
		share_vPanel.setCellHorizontalAlignment(shareEmailArea, HasHorizontalAlignment.ALIGN_CENTER);
		share_vPanel.setCellHorizontalAlignment(share_hPanel, HasHorizontalAlignment.ALIGN_CENTER);
		
		shareButton = new Button("Share");
		shareButton.setWidth("100px");
		shareButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				shareDialog.center();
				shareDialog.show();
				shareEmailArea.setText(DEFAULT_SHARE_EMAIL_ADDRESS_TEXT);
			}
		});
		
		//******* END - Dialog box for Share Button *******//

		
		vPanel_2.add(helpButton);
		vPanel_2.add(aboutButton);
		vPanel_2.add(commentButton);
		vPanel_2.add(shareButton);

		vPanel_3 = new VerticalPanel();
		vPanel_1.add(vPanel_3);

		vPanel_3.setWidth("502px");

		HorizontalPanel hPanel_2 = new HorizontalPanel(); // final panel
		hPanel_2.setSpacing(10);
		hPanel_2.add(vPanel_1);
		hPanel_2.add(vPanel_2);

		AbsolutePanel aPanel = new AbsolutePanel();
		aPanel.add(hPanel_2);

		RootPanel.get().add(aPanel);

		// List box containing all polls
		pollList.addItem("--- Please select the election ---");
		pollList.setVisibleItemCount(1);
		pollList.setWidth("300px");
		
		// Table showing data
		dataTable = new FlexTable();
		dataTable.getElement().setId("data-table");
		dataTable.setVisible(false);
		vPanel_3.add(dataTable);

		// Load list of polls
		pollDataService.getListOfPolls(pollListLoaded);
		setWaitCursor();

	}

	// List of polls was loaded (only run once)
	AsyncCallback<ArrayList<Poll>> pollListLoaded = new AsyncCallback<ArrayList<Poll>>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Failed to load list of polls");
			caught.printStackTrace();
		}

		@Override
		public void onSuccess(ArrayList<Poll> polls) {
			setDefaultCursor();
			// Fill Poll Dropdown list
			int curYear=0;
			for (int i = 0; i < polls.size(); i++) {
				// find out year of poll (workaround since calendar is not supported in GWT, TODO)
				if (curYear != polls.get(i).date.getYear()+1900) {
					curYear=polls.get(i).date.getYear()+1900;
					pollList.addGroup("--- " + Integer.toString(curYear) + " ---");
				}
				pollList.addGroupItem(Integer.toString(polls.get(i).pollID), polls.get(i).description);

			}
			pollList.addChangeHandler(pollSelected);
		}

	};

	private ChangeHandler pollSelected = new ChangeHandler() {
		// Handler for change of Poll dropbox
		@Override
		public void onChange(ChangeEvent event) {
			
			if (pollList.getSelectedIndex() != 0) {
				setWaitCursor();
				pollDataService.getPoll(getSelectedPollID(), pollLoaded);
			}
		}
		
	};

	// Poll was loaded (after selection in dropbox)
	AsyncCallback<Poll> pollLoaded = new AsyncCallback<Poll>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Failed to load selected Poll");
			caught.printStackTrace();
		}

		@Override
		public void onSuccess(Poll p) {
			// Save the poll in the instance variable curPoll
			curPoll = p;
			
			// Remove everything in datatable and add heading row
			dataTable.removeAllRows();
			dataTable.setText(0, 0, "Canton");
			dataTable.setText(0, 1,	"Yes Votes");
			RowFormatter rf = dataTable.getRowFormatter();
			rf.addStyleName(0, "dataTableHeaderRow");
			
			// Clear canton dropdownlist
			cantonList.clear();
			cantonList.addItem("Canton", DEAD_DROPBOX_ITEM);

			for (final CantonData canton : curPoll.cantons) {
				// Fill data table
				int row = dataTable.getRowCount();
				dataTable.setText(row, 0, canton.cantonNameLong);
				dataTable.setText(row, 1, Math.round(canton.getYesPercent() * 10.0)/ 10.0 + "%");
				// Also fill Canton Dropdown
				cantonList.addItem(canton.cantonNameLong, Integer.toString(canton.cantonID));
			}
			cantonList.setEnabled(true);
			cantonList.addChangeHandler(cantonSelected);
			districtList.setEnabled(false);
			dataTable.setVisible(true);
			setDefaultCursor();
		}
	};
	
	private ChangeHandler cantonSelected = new ChangeHandler() {
		@Override
		public void onChange(ChangeEvent event) {
			setWaitCursor();
			
			// Remove everything in datatable and add heading row
			dataTable.removeAllRows();
			dataTable.setText(0, 0, "District");
			dataTable.setText(0, 1,	"Yes Votes");
			RowFormatter rf = dataTable.getRowFormatter();
			rf.addStyleName(0, "dataTableHeaderRow");
			
			// Clear district dropdown
			districtList.clear();
			districtList.addItem("District",DEAD_DROPBOX_ITEM);
			for(final DistrictData district : getSelectedCanton().districts) {
				// Fill Data table
				int row = dataTable.getRowCount();
				dataTable.setText(row, 0, district.districtName);
				dataTable.setText(row, 1, Math.round(district.getYesPercent() * 10.0)/ 10.0 + "%");
				// Also fill district dropdown
				districtList.addItem(district.districtName, Integer.toString(district.districtID));
			}
			districtList.setEnabled(true);
			districtList.addChangeHandler(districtSelected);
			setDefaultCursor();
		}
	};
	
	private ChangeHandler districtSelected = new ChangeHandler() {
		@Override
		public void onChange(ChangeEvent event) {
			setWaitCursor();
			
			// Remove everything in datatable and add heading row
			dataTable.removeAllRows();
			dataTable.setText(0, 0, "Town");
			dataTable.setText(0, 1,	"Yes Votes");
			RowFormatter rf = dataTable.getRowFormatter();
			rf.addStyleName(0, "dataTableHeaderRow");
			
			for(final TownshipData township : getSelectedDistrict().townships) {
				// Fill Data table
				int row = dataTable.getRowCount();
				dataTable.setText(row, 0, township.townshipName);
				dataTable.setText(row, 1, Math.round(township.getYesPercent() * 10.0)/ 10.0 + "%");
			}
			setDefaultCursor();
			
		}
	};
	
	// Getter Methods to retrieve the position of selected items better
	public int getSelectedPollID() {
		return Integer.parseInt(pollList.getValue(pollList.getSelectedIndex()));
	}
	public CantonData getSelectedCanton() {
		return curPoll.getCanton(Integer.parseInt(cantonList.getValue(cantonList.getSelectedIndex())));
	}
	public DistrictData getSelectedDistrict() {
		return getSelectedCanton().getDistrict(Integer.parseInt(districtList.getValue(districtList.getSelectedIndex())));
	}

	// Jquery eyecandy
	public static native void setWaitCursor() /*-{
		$wnd.jQuery("body").css("cursor", "wait");
	}-*/;

	public static native void setDefaultCursor() /*-{
		$wnd.jQuery("body").css("cursor", "default");
	}-*/;

}
