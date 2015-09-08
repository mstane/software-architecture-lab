package org.sm.lab.mybooks.client.view;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

public interface ProfileFormView extends IsWidget {

	public interface Presenter {
		void onSaveButtonClicked();
		void onDeleteButtonClicked();
	}
	
	HasValue<String> getUsername();
	HasValue<String> getPassword();
	HasValue<String> getFirstName();
	HasValue<String> getLastName();
	HasValue<String> getEmail();
	HasText getErrorLabel();

	void setPresenter(Presenter presenter);
	
	void clear();	

}
