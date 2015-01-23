package org.sm.lab.mybooks.client.view;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

public interface NoteFormView extends IsWidget {

	public interface Presenter {
		void onUpdateButtonClicked();
		void onDeleteButtonClicked();
	}

	HasValue<String> getNoteTitle();
	HasValue<String> getContent();
	HasText getErrorLabel();

	void clear();
	
	void setPresenter(Presenter presenter);

	void setVisible(boolean visibility);


}
