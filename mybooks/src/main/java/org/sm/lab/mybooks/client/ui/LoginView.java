package org.sm.lab.mybooks.client.ui;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

public interface LoginView extends IsWidget {

	public interface Presenter {
		void onLogInButtonClicked();
	}
	
	HasValue<String> getUsername();
	HasValue<String> getPassword();
	HasValue<Boolean> getRemeberMe();

	void setPresenter(Presenter presenter);	

}
