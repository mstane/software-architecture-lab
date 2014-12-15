package org.sm.lab.mybooks.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import org.sm.lab.mybooks.client.event.LoginEvent;
import org.sm.lab.mybooks.client.event.LoginEventHandler;
import org.sm.lab.mybooks.shared.dto.ReaderDto;


/**
 * Application shell for MyBooks project.
 */
public class MyBooksShell extends ResizeComposite implements LoginEventHandler {

	interface MyBooksShellUiBinder extends
			UiBinder<Widget, MyBooksShell> {
	}

	private static MyBooksShellUiBinder uiBinder = GWT.create(MyBooksShellUiBinder.class);

	/**
	 * The panel that holds the content.
	 */
	@UiField
	ScrollPanel contentPanel;

	@UiField
	Anchor menuBooksAnchor;
	
	@UiField
	Anchor menuProfileAnchor;
	
	@UiField
	Anchor menuLogoutAnchor;

	@Inject
	public MyBooksShell(EventBus eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		contentPanel.ensureDebugId("contentPanel");
		
		eventBus.addHandler(LoginEvent.TYPE, this);

		showMenuItems(null);
		
	}

	public ScrollPanel getContentPanel() {
		return contentPanel;
	}
	
	@Override
	public void onLogin(LoginEvent event) {
		showMenuItems(event.getDto());
	}
	
	public void showMenuItems(ReaderDto user) {
		menuBooksAnchor.setVisible(user != null);
		menuProfileAnchor.setVisible(user != null);
		menuLogoutAnchor.setVisible(user != null);
	}

}
