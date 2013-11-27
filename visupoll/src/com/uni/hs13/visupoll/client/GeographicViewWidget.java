package com.uni.hs13.visupoll.client;

import org.vectomatic.dom.svg.OMSVGSVGElement;
import org.vectomatic.dom.svg.utils.OMSVGParser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class GeographicViewWidget extends Composite {

	OMSVGSVGElement mapSVG;
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
	
	public void addMap() {
		String resourceUrl = "schweiz.svg";
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, resourceUrl);
			requestBuilder.setCallback(new RequestCallback() {
				public void onError(Request request, Throwable e) {
					GWT.log("Cannot fetch ", e);
				}

				private void onSuccess(Request request, Response response) {
					mapSVG = OMSVGParser.parse(response.getText());
					//main.getElement().appendChild(mapSVG.getElement());
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
	
	public static native String getVoteColor(float v)/*-{
		scale = $wnd.chroma.scale(['red', 'white', 'green']);
		return scale(v).hex();
	}-*/;
	
	// Color cantons (Poll Loaded): mapSVG.getElementById(Integer.toString(canton.cantonID)).setAttribute("fill", getVoteColor(canton.getYesPercent()/100));
	// Color district (Canton Loaded): mapSVG.getElementById(Integer.toString(district.districtID)).setAttribute("fill", getVoteColor(district.getYesPercent()/100));

}
