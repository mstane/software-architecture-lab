package org.sm.lab.mybooks.client.ui;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ProfileFormViewImpl extends Composite implements ProfileFormView {

	@UiTemplate("ProfileFormView.ui.xml")
	interface Binder extends UiBinder<Widget, ProfileFormViewImpl> {
	}

	@UiField
	TextBox usernameTextBox;
	@UiField
	TextBox firstNameTextBox;
	@UiField
	TextBox lastNameTextBox;
	@UiField
	TextBox emailTextBox;
	@UiField
	PasswordTextBox passwordTextBox;
	@UiField
	PasswordTextBox password2TextBox;
    @UiField
    Label errorLabel;
	
	@UiField
	Button saveButton;
	@UiField
	Button deleteButton;
	
	private Presenter presenter;
	
	private Widget widget;

	public ProfileFormViewImpl() {
		Log.debug("ProfileFormViewImpl.ProfileFormViewImpl()");
		widget = onInitialize();
	}

	public Widget onInitialize() {

		Binder uiBinder = GWT.create(Binder.class);
		Widget widget = uiBinder.createAndBindUi(this);

		return widget;

	}
	
	@UiHandler("saveButton")
	void onSaveButtonClicked(ClickEvent event) {		
		if (presenter != null) {
			if (usernameTextBox.getText().length() == 0 || passwordTextBox.getText().length() == 0) {
				Window.alert("Username or password is empty.");
			} else {
				presenter.onSaveButtonClicked();
			}
		}
	}
	
	@UiHandler("deleteButton")
	void onDeleteButtonClicked(ClickEvent event) {		
		if (Window.confirm("Are you sure to delete yourself?")) {
			if (presenter != null) {
				presenter.onDeleteButtonClicked();
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
	public HasValue<String> getFirstName() {
		return firstNameTextBox;
	}

	@Override
	public HasValue<String> getLastName() {
		return lastNameTextBox;
	}
	
	@Override
	public HasValue<String> getEmail() {
		return emailTextBox;
	}

	@Override
	public HasText getErrorLabel() {
		return errorLabel;
	}	
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		
	}
	
	public Widget asWidget() {
		Log.debug("ProfileFormViewImpl.asWidget()");
		return widget;
	}

	@Override
	public void clear() {
		usernameTextBox.setValue("");			
		passwordTextBox.setValue("");
		password2TextBox.setValue("");
		firstNameTextBox.setValue("");
		lastNameTextBox.setValue("");
		emailTextBox.setValue("");
		errorLabel.setText("");
		
	}
	

}
