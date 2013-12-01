package com.uni.hs13.visupoll.client;

import org.vectomatic.dom.svg.OMElement;
import org.vectomatic.dom.svg.OMNode;
import org.vectomatic.dom.svg.OMSVGAnimationElement;
import org.vectomatic.dom.svg.OMSVGPathElement;
import org.vectomatic.dom.svg.OMSVGSVGElement;
import org.vectomatic.dom.svg.utils.OMSVGParser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.uni.hs13.visupoll.datastructures.CantonData;
import com.uni.hs13.visupoll.datastructures.DistrictData;

public class GeographicViewWidget extends Composite {
	interface GeographicViewWidgetUiBinder extends
	UiBinder<Widget, GeographicViewWidget> {
	}
	
	private static GeographicViewWidgetUiBinder uiBinder = GWT
			.create(GeographicViewWidgetUiBinder.class);
	
	@UiField
	static HTMLPanel main;								// Main HTML panel
	
	static OMSVGSVGElement mapSVG;						// The SVG element of the complete map
	static OMSVGPathElement zoomedCanton = null;		// References path of the current zoomed in canton
	static Button zoomOutButton = new Button("Zoom Out"); // Button to zoom back out
	static Label toolTip = new Label("tooltip");
	
	// Constructor
	public GeographicViewWidget() {
		initWidget(uiBinder.createAndBindUi(this));

		// Loads map svg
		loadMap();
		
		// Initialize zoom out button
		main.getElement().getStyle().setPosition(Position.RELATIVE);		
		zoomOutButton.getElement().setAttribute("style", "position: absolute; top: 20px; left: 20px");
		zoomOutButton.setVisible(false);
		zoomOutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				zoomOut();
			}
		});
		main.add(zoomOutButton);
		
		// Tooltip
		toolTip.getElement().setAttribute("id", "tooltip");
		toolTip.setVisible(false);
		main.add(toolTip);
	}
	
	private static void loadMap() {
		String resourceUrl = "schweiz.svg";
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, resourceUrl);
			requestBuilder.setCallback(new RequestCallback() {
				public void onError(Request request, Throwable e) {
					GWT.log("Cannot fetch ", e);
				}

				private void onSuccess(Request request, Response response) {
					mapSVG = OMSVGParser.parse(response.getText());
					main.getElement().appendChild(mapSVG.getElement());
					mapSVG.setId("mapSVG");
					initCantonClickHandlers();
				}
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == Response.SC_OK) {
						onSuccess(request, response);
					}
				}

			});
			try {
				requestBuilder.send();
			} catch (RequestException e) {
				GWT.log("Cannot fetch ", e);
			}
	}
	
	private static void initCantonClickHandlers() {
		for (OMNode cantonNode : mapSVG.getElementById("kantone").getChildNodes()) {
			OMSVGPathElement canton = (OMSVGPathElement)cantonNode;
			// Add click handler
			canton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					OMSVGPathElement fromViewbox = zoomedCanton;
					zoomedCanton = ((OMSVGPathElement) event.getSource());
					zoomToCanton(Integer.parseInt(zoomedCanton.getId()), fromViewbox);
				}
			});
			// Add mouse over handler
			//TODO jQueryActivateTooltips();
		}
	}
	
	private static void zoomToCanton(int cantonID, OMSVGPathElement fromViewbox) {
		// Color districts
		for(final DistrictData district : Home.curPoll.getCanton(cantonID).districts) {
			
			if(DOM.getElementById(Integer.toString(district.districtID)) != null) {
				OMElement e = mapSVG.getElementById(Integer.toString(district.districtID));
				e.setAttribute("fill", getVoteColor(district.getYesPercent()/100));
			} else {
				//System.out.println("Couldn't find " + Integer.toString(district.districtID) + " "  + district.districtName);
			}
		}
		// Zoom
		//mapSVG.setViewBox(zoomedCanton.getBBox());
		OMSVGAnimationElement anim;
		anim = (OMSVGAnimationElement)mapSVG.getElementById("zoom");
		String sOldViewbox = "";
		if(fromViewbox == null)
			sOldViewbox = "0 0 800 509";
		else {
			sOldViewbox = fromViewbox.getBBox().getX() + " " +
					fromViewbox.getBBox().getY() + " " +
					fromViewbox.getBBox().getWidth() + " " +
					fromViewbox.getBBox().getHeight();
		}
		String newViewbox = zoomedCanton.getBBox().getX() + " " +			
							zoomedCanton.getBBox().getY() + " " +
							zoomedCanton.getBBox().getWidth() + " " +
							zoomedCanton.getBBox().getHeight();
		anim.setAttribute("from", sOldViewbox);
		anim.setAttribute("to", newViewbox);
		anim.beginElement();
	
		// Make canton transparent, so one can see through
		mapSVG.getElementById(Integer.toString(cantonID)).setAttribute("class", "selectedCanton");
		
		// Gray out other cantons
		for(int i=1;i<=26;i++) {
			if(i!=cantonID)
				mapSVG.getElementById(Integer.toString(i)).setAttribute("class", "notSelectedCanton");
		}
		
		// Show zoom out button
		zoomOutButton.setVisible(true);
	}
	
	private void zoomOut() {
		//mapSVG.setViewBox(0, 0, 800, 509);
		OMSVGAnimationElement anim;
		anim = (OMSVGAnimationElement)mapSVG.getElementById("zoom");
		String oldViewbox = zoomedCanton.getBBox().getX() + " " +
							zoomedCanton.getBBox().getY() + " " +
							zoomedCanton.getBBox().getWidth() + " " +
							zoomedCanton.getBBox().getHeight();
		anim.setAttribute("from", oldViewbox);
		anim.setAttribute("to", "0 0 800 509");
		anim.beginElement();
		
		zoomedCanton=null;
		
		for(int i=1;i<=26;i++) {
			mapSVG.getElementById(Integer.toString(i)).setAttribute("class", "");
		}
		
		zoomOutButton.setVisible(false);
	}
	
	public static void colorEverything() {
		for(final CantonData canton : Home.curPoll.cantons) {
			for(final DistrictData district : Home.curPoll.getCanton(canton.cantonID).districts) {
				// Check for districts
				if(DOM.getElementById(Integer.toString(district.districtID)) == null)
					System.out.println("Couldn't find District: " + Integer.toString(district.districtID) + " "  + district.districtName);
				else {
					
					mapSVG.getElementById(Integer.toString(district.districtID)).setAttribute("fill", getVoteColor(district.getYesPercent()/100));
					/*
					for(final TownshipData town : district.townships) {
						// Check for townships
						if(DOM.getElementById("T_" + Integer.toString(town.townshipID)) == null)
							System.out.println("Couldn't find Town: " + Integer.toString(town.townshipID) + " "  + town.townshipName);
							mapSVG.getElementById("T_" + Integer.toString(town.townshipID)).setAttribute("fill", getVoteColor(town.getYesPercent()/100));
					}
					*/
				}
			}
		}
	}
	
	
	public static void colorCanton(int cantonID, float value) {
		mapSVG.getElementById(Integer.toString(cantonID)).setAttribute("fill", getVoteColor(value));
	}
	
	public static native String getVoteColor(float v)/*-{
		scale = $wnd.chroma.scale(['red', 'white', 'green']).mode('lab');
		return scale(v).hex();
	}-*/;
	
	//TODO Try to get information out of the java functions.
	public static native void jQueryActivateTooltips()/*-{
		
		$wnd.jQuery("#schweiz path").hover(function(){
			
  			},function(){
  				$wnd.jQuery('#tooltip').hide();
  			}
		);
		
		$wnd.jQuery("#kantone path").hover(function(){
				$wnd.jQuery('#tooltip').text($wnd.jQuery(this).attr('id-cantonnameshort'));
    			$wnd.jQuery('#tooltip').position({at: 'left top', of: $wnd.jQuery(this), my: 'left top', collision: 'fit'})
				$wnd.jQuery('#tooltip').fadeIn(200);
  			},function(){

  			}
		);
		
	}-*/;

}
