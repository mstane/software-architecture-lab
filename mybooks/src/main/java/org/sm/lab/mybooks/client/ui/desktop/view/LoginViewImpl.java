package org.sm.lab.mybooks.client.ui.desktop.view;

import org.sm.lab.mybooks.client.view.LoginView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginViewImpl extends Composite implements LoginView {

	@UiTemplate("LoginView.ui.xml")
	interface Binder extends UiBinder<Widget, LoginViewImpl> {
	}

	@UiField
	TextBox usernameTextBox;
	@UiField
	PasswordTextBox passwordTextBox;
	@UiField
	CheckBox rememberMeCheckBox;
	@UiField
	Button loginButton;
	
	private Presenter presenter;
	
	private Widget widget;

	public LoginViewImpl() {
		widget = onInitialize();
	}

	public Widget onInitialize() {

		Binder uiBinder = GWT.create(Binder.class);
		Widget widget = uiBinder.createAndBindUi(this);

		return widget;

	}
	
	@UiHandler("loginButton")
	void onLoginButtonClicked(ClickEvent event) {		
		if (presenter != null) {
			if (usernameTextBox.getText().length() == 0 || passwordTextBox.getText().length() == 0) {
				Window.alert("Username or password is empty.");
			} else {
				presenter.onLogInButtonClicked();
			}
		}
	}

	@Override
	public HasValue<String> getUsername() {
		return usernameTextBox;
	}

	@Override
	public HasValue<String> getPassword() {
		return passwordTextBox;
	}

	@Override
	public HasValue<Boolean> getRemeberMe() {
		return rememberMeCheckBox;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		
	}
	
	public Widget asWidget() {
		return widget;
	}
	
	

}
