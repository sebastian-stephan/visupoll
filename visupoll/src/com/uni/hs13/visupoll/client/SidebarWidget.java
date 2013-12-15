package com.uni.hs13.visupoll.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.uni.hs13.visupoll.client.rpc.PollDataService;
import com.uni.hs13.visupoll.client.rpc.PollDataServiceAsync;
import com.uni.hs13.visupoll.datastructures.Poll;

//This class builds the sidebar logic with four buttons.
public class SidebarWidget extends Composite {
	
	// Main side bar with buttons
	static VerticalPanel mainSidebarPanel;
	static Button shareButton;
	static Button helpButton;
	static Button aboutButton;
	static Button optionButton;
	
	// Share dialog
	static final String DEFAULT_SHARE_BODY_AREA_TEXT = "Your comment";
	static final String DEFAULT_SHARE_EMAIL_ADDRESS_TEXT = "E-mail address";
	static DialogBox shareDialog;
	static TextArea shareEmailArea;
	static TextArea shareBodyArea;
	static Button shareCloseButton;
	static Button shareSendButton;
	static Button shareCancelButton;
	static VerticalPanel share_vPanel;
	static HorizontalPanel share_hPanel;
	
	// Help dialog
	static DialogBox helpDialog;
	static TextArea helpTextArea;
	static Button helpCloseButton;
	static VerticalPanel help_vPanel;
	
	// About dialog
	static DialogBox aboutDialog;
	static TextArea aboutTextArea;
	static Button aboutCloseButton;
	static VerticalPanel about_vPanel;
	
	// Option dialog
	static DialogBox optionDialog;
	static TextBox optionColor1;
	static TextBox optionColor2;
	static TextBox optionColor3;
	static Button optionSaveButton;
	static Button optionCancelButton;
	static VerticalPanel option_vPanel;
	static HorizontalPanel option_hPanel;
	
	@UiField
	HTMLPanel main;
	
	private static SidebarWidgetUiBinder uiBinder = GWT
			.create(SidebarWidgetUiBinder.class);

	interface SidebarWidgetUiBinder extends
			UiBinder<Widget, SidebarWidget> {
	}

	public SidebarWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		
		// Main layout (V panel which holds all buttons)
		mainSidebarPanel = new VerticalPanel();
		mainSidebarPanel.setSpacing(10);
		
		// Help Dialog
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
		mainSidebarPanel.add(helpButton);
		
		// About Dialog
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
		mainSidebarPanel.add(aboutButton);
		
		// Share Dialog
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
		shareEmailArea.addFocusHandler(new FocusHandler() {			// Removes sample text ("E-mail address") after the first click
			@Override
			public void onFocus(FocusEvent event) {
				if(shareEmailArea.getText().equals(DEFAULT_SHARE_EMAIL_ADDRESS_TEXT)) shareEmailArea.setText("");
			}
		});
		shareEmailArea.addBlurHandler(new BlurHandler() {			// ... and put's it back when user didn't enter anything
			@Override
			public void onBlur(BlurEvent event) {
				if(shareEmailArea.getText().equals("")) shareEmailArea.setText(DEFAULT_SHARE_EMAIL_ADDRESS_TEXT);
			}
			
		});
		shareBodyArea = new TextArea();
		shareBodyArea.setWidth("200px");
		shareBodyArea.setHeight("150px");
		shareBodyArea.addFocusHandler(new FocusHandler() {			// Removes sample text ("Your comment") after the first click
			@Override
			public void onFocus(FocusEvent event) {
				if (shareBodyArea.getText().equals(DEFAULT_SHARE_BODY_AREA_TEXT)) shareBodyArea.setText("");
			}			
		});
		shareBodyArea.addBlurHandler(new BlurHandler() {			// ... and put's it back when user didn't enter anything
			@Override
			public void onBlur(BlurEvent event) {						
				if (shareBodyArea.getText().equals("")) shareBodyArea.setText(DEFAULT_SHARE_BODY_AREA_TEXT);
			}
		});
		share_hPanel = new HorizontalPanel();
		share_hPanel.setSpacing(10);
		shareSendButton = new Button("Send");
		shareSendButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Home.setWaitCursor();
				if (GeographicViewWidget.zoomedCanton != null)
					GeographicViewWidget.mapSVG.setViewBox(GeographicViewWidget.zoomedCanton.getBBox());
				else
					GeographicViewWidget.mapSVG.setViewBox(0, 0, 800, 509);
				
				GeographicViewWidget.svgToCanvas();
				shareDialog.hide();
			}
		});
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
		share_vPanel.add(shareBodyArea);
		share_vPanel.add(share_hPanel);
		share_vPanel.setCellHorizontalAlignment(shareEmailArea, HasHorizontalAlignment.ALIGN_CENTER);
		share_vPanel.setCellHorizontalAlignment(shareBodyArea, HasHorizontalAlignment.ALIGN_CENTER);
		share_vPanel.setCellHorizontalAlignment(share_hPanel, HasHorizontalAlignment.ALIGN_CENTER);
		shareButton = new Button("Share");
		shareButton.setWidth("100px");
		shareButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				shareDialog.center();
				shareDialog.show();
				shareEmailArea.setText("E-mail address");
				shareBodyArea.setText("Your comment");
			}
		});
		mainSidebarPanel.add(shareButton);
		
		// Option dialog
		optionDialog = new DialogBox();
		optionDialog.setModal(false);
		optionDialog.setAnimationEnabled(true);
		optionDialog.setGlassEnabled(true);
		optionDialog.setText("Options");
		option_vPanel = new VerticalPanel();
		option_vPanel.setSpacing(10);
		optionColor1 = new TextBox();
		optionColor1.setWidth("70px");
		optionColor1.getElement().setId("colorBox1");
		optionColor2 = new TextBox();
		optionColor2.setWidth("70px");
		optionColor2.getElement().setId("colorBox2");
		optionColor3 = new TextBox();
		optionColor3.setWidth("70px");
		optionColor3.getElement().setId("colorBox3");
		option_vPanel.add(new Label("Colors"));
		option_vPanel.add(new Label("Color for 0%"));
		option_vPanel.add(optionColor1);
		option_vPanel.add(new Label("Color for 50% (optional)"));
		option_vPanel.add(optionColor2);
		option_vPanel.add(new Label("Color for 100%"));
		option_vPanel.add(optionColor3);
		option_hPanel = new HorizontalPanel();
		option_hPanel.setSpacing(10);
		optionSaveButton = new Button("Save");
		optionSaveButton.setWidth("100px");
		optionSaveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				GeographicViewWidget.colorScale1 = optionColor1.getText();
				GeographicViewWidget.colorScale2 = optionColor2.getText();
				GeographicViewWidget.colorScale3 = optionColor3.getText();
				optionDialog.hide();
			}
		});
		option_hPanel.add(optionSaveButton);
		optionCancelButton = new Button("Cancel");
		optionCancelButton.setWidth("100px");
		optionCancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				optionDialog.hide();
			}
		});
		option_hPanel.add(optionCancelButton);
		option_vPanel.add(option_hPanel);
		optionDialog.add(option_vPanel);
		optionButton = new Button("Options");
		optionButton.setWidth("100px");
		optionButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				optionColor1.setText(GeographicViewWidget.colorScale1);
				optionColor2.setText(GeographicViewWidget.colorScale2);
				optionColor3.setText(GeographicViewWidget.colorScale3);
				optionDialog.center();
				optionDialog.show();
				addColorPickers();
			}
		});
		mainSidebarPanel.add(optionButton);
		
		// Add main layout panel
		main.add(mainSidebarPanel);

	}
	
	public static native void addColorPickers() /*-{
			$wnd.jQuery('#colorBox1').colpick({
				layout:'hex',
				submit:0,
				onChange:function(hsb,hex,rgb,fromSetColor) {
					$wnd.jQuery('#colorBox1').val(hex);
				},
				onShow:function() {
					$wnd.jQuery('#colorBox1').colpickSetColor(this.value);
				}
			});
			
			$wnd.jQuery('#colorBox2').colpick({
				layout:'hex',
				submit:0,
				onChange:function(hsb,hex,rgb,fromSetColor) {
					$wnd.jQuery('#colorBox2').val(hex);
				},
				onShow:function() {
					$wnd.jQuery('#colorBox2').colpickSetColor(this.value);
				}
			})
			.keyup(function(){
				$wnd.jQuery('#colorBox2').colpickSetColor(this.value);
			});
			
			$wnd.jQuery('#colorBox3').colpick({
				layout:'hex',
				submit:0,
				onChange:function(hsb,hex,rgb,fromSetColor) {
					$wnd.jQuery('#colorBox3').val(hex);
				},
				onShow:function() {
					$wnd.jQuery('#colorBox3').colpickSetColor(this.value);
				}
			})
			.keyup(function(){
				$wnd.jQuery('#colorBox3').colpickSetColor(this.value);
			});
		
		
	}-*/;
	
	public static void sendEmail() {
		AsyncCallback<Void> emailSent = new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Could not send email");
				Home.setDefaultCursor();								
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Email sent");
				Home.setDefaultCursor();				
			}

		};
		StringBuilder bodyText = new StringBuilder();
		bodyText.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head><body>");
		bodyText.append(new SafeHtmlBuilder().appendEscapedLines(shareBodyArea.getText()).toSafeHtml().asString());
		bodyText.append("<h2>").append(TabellaricViewWidget.pollList.getItemText(TabellaricViewWidget.pollList.getSelectedIndex()));
		bodyText.append("</h2><table>");
		bodyText.append(TabellaricViewWidget.dataTable.getElement().getInnerHTML());
		bodyText.append("</table><table>");
		bodyText.append(TabellaricViewWidget.demographicDataTable.getElement().getInnerHTML());
		bodyText.append("</table><br>______________________________________<br>");
		bodyText.append("Email created by Visupoll (visupoll.appspot.com)");
		bodyText.append("</body></html>");
		
		System.out.println(bodyText.toString());
		
		TabellaricViewWidget.pollDataService.sendEmail(
							shareEmailArea.getText(),
							bodyText.toString(),
							GeographicViewWidget.canvas.toDataUrl(),
							emailSent);
		
	}

}
