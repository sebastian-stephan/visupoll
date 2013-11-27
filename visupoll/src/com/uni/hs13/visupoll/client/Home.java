package com.uni.hs13.visupoll.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class Home implements EntryPoint {
	
	/*	Layout structure:

		vMasterPanel
		+------+----------------------------------------+
		| hMainPanel                                    |
		|  +-----------------------------+------------+ |
		|  |  tContentTab                | tSidebar-  | |
		|  |                             |   Panel    | |
		|  |                             |            | |
		|  |                             |            | |
		|  |                             |            | |
		|  |                             |            | |
		|  +--------------------------+---------------+ |
		+-----------------------------------------------+
		| Copyright                                     |
		+-----------------------------------------------+
		

	 */

	VerticalPanel 	vMasterPanel = new VerticalPanel();
	HorizontalPanel	hMainPanel = new HorizontalPanel();
	TabPanel 		tContentTab = new TabPanel();
	VerticalPanel	vSidebarPanel = new VerticalPanel();
	VerticalPanel	vCopyrightPanel = new VerticalPanel();
		

	@Override
	public void onModuleLoad() {
	
	// Content (left side)
	TabellaricViewWidget tabView = new TabellaricViewWidget();
	GeographicViewWidget geoView = new GeographicViewWidget();
	tContentTab.setWidth("830px");
	tContentTab.add(tabView, "Tabelle");
	tContentTab.add(geoView, "Karte");
	tContentTab.selectTab(0);
	hMainPanel.add(tContentTab);
	
	// Sidebar (right side)
	vSidebarPanel.setSpacing(10);
	SidebarWidget sidebar = new SidebarWidget();
	vSidebarPanel.add(sidebar);
	hMainPanel.add(vSidebarPanel);
	
	// Master panel (main + copyright)
	vMasterPanel.add(hMainPanel);
	CopyrightWidget copyright = new CopyrightWidget();
	vMasterPanel.add(copyright);
	
	RootPanel.get().add(vMasterPanel);

	}
}
