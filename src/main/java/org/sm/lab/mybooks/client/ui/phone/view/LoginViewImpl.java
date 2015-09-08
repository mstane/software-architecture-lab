package org.sm.lab.mybooks.client.ui.phone.view;

import org.sm.lab.mybooks.client.view.LoginView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.widget.button.ButtonBase;
import com.googlecode.mgwt.ui.client.widget.input.MTextBox;

public class LoginViewImpl extends Composite implements LoginView {

	private static LoginViewImplUiBinder uiBinder = GWT.create(LoginViewImplUiBinder.class);
	private Presenter presenter;

	interface LoginViewImplUiBinder extends UiBinder<Widget, LoginViewImpl> {
	}

	public LoginViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@UiField
	ButtonBase login;
	
	@UiField
	MTextBox username;
	
	@UiField
	MTextBox password;

	@UiHandler("login")
	public void onLogInButtonClicked(TapEvent event) {
		if (presenter != null) {
			presenter.onLogInButtonClicked();
		}
	}
	
	@Override
	public HasValue<String> getUsername() {
		return username;
	}

	@Override
	public HasValue<String> getPassword() {
		return password;
	}

	@Override
	public HasValue<Boolean> getRemeberMe() {
		// TODO Auto-generated method stub
		return null;
	}

}
