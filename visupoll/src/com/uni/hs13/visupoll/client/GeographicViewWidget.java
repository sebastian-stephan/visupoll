package com.uni.hs13.visupoll.client;

import org.vectomatic.dom.svg.OMNode;
import org.vectomatic.dom.svg.OMSVGAnimationElement;
import org.vectomatic.dom.svg.OMSVGPathElement;
import org.vectomatic.dom.svg.OMSVGSVGElement;
import org.vectomatic.dom.svg.utils.OMSVGParser;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
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
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.uni.hs13.visupoll.datastructures.CantonData;
import com.uni.hs13.visupoll.datastructures.DistrictData;
//canvas
import com.uni.hs13.visupoll.datastructures.TownshipData;

public class GeographicViewWidget extends Composite {
	interface GeographicViewWidgetUiBinder extends
	UiBinder<Widget, GeographicViewWidget> {
	}
	
	private static GeographicViewWidgetUiBinder uiBinder = GWT
			.create(GeographicViewWidgetUiBinder.class);
	
	@UiField
	static HTMLPanel main;								// Main HTML panel
	
	static SimplePanel svgWrapper;						// Div around SVG needed by canvg library.
	static Canvas canvas;								// Canvas for picture export
	static OMSVGSVGElement mapSVG;						// The SVG element of the complete map
	static OMSVGPathElement zoomedCanton = null;		// References path of the current zoomed in canton
	static OMSVGAnimationElement anim;
	static Button zoomOutButton = new Button("Zoom Out"); // Button to zoom back out
	static ToggleButton townToggleButton = new ToggleButton("Show townships"); // Button to show townships
	
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
		
		// Initialize town toggle button
		townToggleButton.getElement().setAttribute("style", "position: absolute; top: 50px; left: 20px");
		townToggleButton.setVisible(false);
		townToggleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!townToggleButton.isDown())
					changeCSSRule(3, 4, "visibility", "visible");					
				else
					changeCSSRule(3, 4, "visibility", "hidden");
			}
		});
		main.add(townToggleButton);
		
		// Tooltip
		toolTip.getElement().setAttribute("id", "tooltip");
		toolTip.setVisible(false);
		main.add(toolTip);
		
		svgWrapper = new SimplePanel();
		svgWrapper.getElement().setId("svgWrapper");
		main.add(svgWrapper);
		
		canvas = Canvas.createIfSupported();
		canvas.getElement().setAttribute("width", "800");
		canvas.getElement().setAttribute("height", "509");
		canvas.getElement().setAttribute("style", "visibility: hidden; position: absolute");
		canvas.getElement().setId("canvas");
		main.add(canvas);
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
					svgWrapper.getElement().appendChild(mapSVG.getElement());
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
		// Zoom
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
		
		// Show zoom out button and toggle button
		zoomOutButton.setVisible(true);
		if (!Home.curPoll.cantons.get(0).districts.get(0).townships.isEmpty())
			townToggleButton.setVisible(true);
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
		townToggleButton.setVisible(false);
	}
	
	// Colors all districts and all towns.
	public static void colorEverything() {
		for(final CantonData canton : Home.curPoll.cantons) {
			for(final DistrictData district : Home.curPoll.getCanton(canton.cantonID).districts) {
				// Check for districts
				if(DOM.getElementById(Integer.toString(district.districtID)) == null)
					System.out.println("Couldn't find District: " + Integer.toString(district.districtID) + " "  + district.districtName);
				else {
					// Color district
					mapSVG.getElementById(Integer.toString(district.districtID)).setAttribute("fill", getVoteColor(district.getYesPercent()/100));
					
					if (district.townships.isEmpty()) {
						townToggleButton.setDown(false);
						townToggleButton.setVisible(false);
						changeCSSRule(3, 4, "visibility", "visible"); // show districts
					}
					for(final TownshipData town : district.townships) {
						// Check for townships
						if(DOM.getElementById("T_" + Integer.toString(town.townshipID)) == null)
							System.out.println("Couldn't find Town: " + Integer.toString(town.townshipID) + " "  + town.townshipName);
						else // Color town
							mapSVG.getElementById("T_" + Integer.toString(town.townshipID)).setAttribute("fill", getVoteColor(town.getYesPercent()/100));
					}
					
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
	
	public static native void svgToCanvas()/*-{
		var finishedRendering = function() {
			@com.uni.hs13.visupoll.client.SidebarWidget::sendEmail()();
		};
		
		$wnd.canvg(null, null, { 
				renderCallback: finishedRendering,
				ignoreAnimation: false
			});
	}-*/;
	
	public static native void changeCSSRule(int styleSheet, int rule, String attribute, String value)/*-{
		// IE uses rules instead of cssRules
		var rules = $wnd.document.styleSheets[styleSheet].rules || $wnd.document.styleSheets[styleSheet].cssRules;
		rules[rule].style[attribute] = value;
	}-*/;

}
