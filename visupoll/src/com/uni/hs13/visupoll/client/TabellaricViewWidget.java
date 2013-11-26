package com.uni.hs13.visupoll.client;

import java.util.ArrayList;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.uni.hs13.visupoll.client.rpc.PollDataService;
import com.uni.hs13.visupoll.client.rpc.PollDataServiceAsync;
import com.uni.hs13.visupoll.datastructures.CantonData;
import com.uni.hs13.visupoll.datastructures.DistrictData;
import com.uni.hs13.visupoll.datastructures.Poll;
import com.uni.hs13.visupoll.datastructures.TownshipData;

public class TabellaricViewWidget extends Composite {
	
	PollDataServiceAsync pollDataService = (PollDataServiceAsync) GWT
			.create(PollDataService.class);
	
	private static final String DEAD_DROPBOX_ITEM = "NA";
	
	protected Poll curPoll = null;
	
	FlexTable dataTable;
	
	ListBox cantonList;
	OptGroupListBox pollList;
	ListBox districtList;
	
	HorizontalPanel navigation;
	VerticalPanel fullTable;
	
	@UiField
	HTMLPanel main;	
	
	//Loads uiBinder
	private static TabellaricViewWidgetUiBinder uiBinder = GWT
			.create(TabellaricViewWidgetUiBinder.class);

	interface TabellaricViewWidgetUiBinder extends
			UiBinder<Widget, TabellaricViewWidget> {
	}

	public TabellaricViewWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		
		//generate all Panels and set up List Boxes
		navigation = new HorizontalPanel();
		fullTable = new VerticalPanel();
		
		cantonList = new ListBox();
		cantonList.setWidth("150px");
		cantonList.addItem("Canton", DEAD_DROPBOX_ITEM);
		cantonList.setEnabled(false);
		
		pollList = new OptGroupListBox();
		
		districtList = new ListBox();
		districtList.setWidth("150px");
		districtList.addItem("District", DEAD_DROPBOX_ITEM);
		districtList.setEnabled(false);

		navigation.add(pollList);
		navigation.add(cantonList);
		navigation.add(districtList);
		fullTable.add(navigation);
		
		// List box containing all polls
		pollList.addItem("--- Please select the election ---");
		pollList.setVisibleItemCount(1);
		pollList.setWidth("300px");
				
		// Table showing data
		dataTable = new FlexTable();
		dataTable.getElement().setId("data-table");
		dataTable.setVisible(false);
		fullTable.add(dataTable);

		// Load list of polls
		pollDataService.getListOfPolls(pollListLoaded);
		setWaitCursor();

		main.add(fullTable);
		
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
			if (cantonList.getValue(cantonList.getSelectedIndex()).equals(DEAD_DROPBOX_ITEM))
				return;
			
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
			if (districtList.getValue(districtList.getSelectedIndex()).equals(DEAD_DROPBOX_ITEM))
				return;
			
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

	// JQuery eyecandy
	public static native void setWaitCursor() /*-{
		$wnd.jQuery("body").css("cursor", "wait");
	}-*/;

	public static native void setDefaultCursor() /*-{
		$wnd.jQuery("body").css("cursor", "default");
	}-*/;

}
