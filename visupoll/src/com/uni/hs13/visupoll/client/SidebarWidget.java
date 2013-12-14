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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.uni.hs13.visupoll.client.rpc.PollDataService;
import com.uni.hs13.visupoll.client.rpc.PollDataServiceAsync;
import com.uni.hs13.visupoll.datastructures.Poll;

//This class builds the sidebar logic with four buttons.
public class SidebarWidget extends Composite {

	//for comment
	private static final String DEFAULT_COMMENT_BODY_AREA_TEXT = "Your comment";
	private static final String DEFAULT_COMMENT_EMAIL_ADDRESS_TEXT = "E-mail address";
	
	//for share
	private static final String DEFAULT_SHARE_EMAIL_ADDRESS_TEXT = "E-mail address";
	
	final DialogBox helpDialog;
	final DialogBox aboutDialog;
	final DialogBox commentDialog;
	
	static TextArea helpTextArea;
	static TextArea aboutTextArea;
	static TextArea commentEmailArea;
	static TextArea commentBodyArea;
	
	static Button helpCloseButton;
	static Button aboutCloseButton;
	static Button commentCloseButton;
	static Button commentSendButton;
	static Button commentCancelButton;
	
	static VerticalPanel help_vPanel;
	static VerticalPanel about_vPanel;
	static VerticalPanel comment_vPanel;
	
	static VerticalPanel mainSidebarPanel;
	
	static HorizontalPanel comment_hPanel;
	
	static Button helpButton;
	static Button aboutButton;
	static Button commentButton;

	
	@UiField
	HTMLPanel main;
	
	private static SidebarWidgetUiBinder uiBinder = GWT
			.create(SidebarWidgetUiBinder.class);

	interface SidebarWidgetUiBinder extends
			UiBinder<Widget, SidebarWidget> {
	}

	public SidebarWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		
		//define vPanel for correct structure
		
		mainSidebarPanel = new VerticalPanel();
		mainSidebarPanel.setSpacing(10);
		
		//--Start Help Button--
		
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
		
		//add the button to panel
		mainSidebarPanel.add(helpButton);
		
		//--End Help Button
		
		//--Start About Button
		
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
		
		//add the button to panel
		mainSidebarPanel.add(aboutButton);
		
		//--End About Button
		
		//--Start Comment Button
		
		commentDialog = new DialogBox();
		commentDialog.setText("Share");
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
		commentSendButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Home.setWaitCursor();
				if (GeographicViewWidget.zoomedCanton != null)
					GeographicViewWidget.mapSVG.setViewBox(GeographicViewWidget.zoomedCanton.getBBox());
				else
					GeographicViewWidget.mapSVG.setViewBox(0, 0, 800, 509);
				
				GeographicViewWidget.svgToCanvas();
				commentDialog.hide();
			}
		});
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
		
		commentButton = new Button("Share");
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
		
		//add the button to Panel
		mainSidebarPanel.add(commentButton);
		
		//--End Comment Button
		

		//add Panel to main
		main.add(mainSidebarPanel);

	}
	
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
		bodyText.append(new SafeHtmlBuilder().appendEscapedLines(commentBodyArea.getText()).toSafeHtml().asString());
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
							commentEmailArea.getText(),
							bodyText.toString(),
							GeographicViewWidget.canvas.toDataUrl(),
							emailSent);
		
	}

}
