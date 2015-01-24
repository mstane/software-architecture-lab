package org.sm.lab.mybooks.client.ui.desktop.view;

import org.sm.lab.mybooks.client.event.LoginEvent;
import org.sm.lab.mybooks.client.event.LoginEventHandler;
import org.sm.lab.mybooks.client.view.MainView;
import org.sm.lab.mybooks.shared.dto.ReaderDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.googlecode.mgwt.ui.client.widget.animation.AnimatableDisplay;


/**
 * Application shell for MyBooks project.
 */
public class DesktopMainViewImpl extends ResizeComposite implements LoginEventHandler, MainView {

	@UiTemplate("DesktopMainView.ui.xml")
	interface MyBooksShellUiBinder extends
			UiBinder<Widget, DesktopMainViewImpl> {
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
	public DesktopMainViewImpl(EventBus eventBus) {
		initWidget(uiBinder.createAndBindUi(this));
		contentPanel.ensureDebugId("contentPanel");
		
		eventBus.addHandler(LoginEvent.TYPE, this);

		showMenuItems(null);
		
	}

	@Override
	public AcceptsOneWidget getContentPanel() {
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

	@Override
	public HasSelectionHandlers<Integer> getSelectionHandlers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AnimatableDisplay getAnimatableDisplay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTabVisibility(boolean userLogged) {
		// TODO Auto-generated method stub
		
	}

}
