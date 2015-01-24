package org.sm.lab.mybooks.client.view;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.HasData;

import org.sm.lab.mybooks.shared.dto.NoteDto;


public interface BookFormView extends IsWidget {

	public interface Presenter {
		void onUpdateButtonClicked();
		void onDeleteButtonClicked();
		void onNewNoteButtonClicked();
		void onNoteItemClicked(NoteDto noteDto);
		void onBackButtonPressed();
	}

	HasValue<String> getBookTitle();
	HasValue<String> getAuthor();
	HasValue<String> getUrl();
	HasValue<String> getStartReadingDate();
	HasValue<String> getEndReadingDate();
	HasValue<String> getRating();
	
	HasData<NoteDto> getNoteTable();
	HasText getErrorLabel();
	
	void setSaveButtonEnable(boolean enable);
	void setDeleteButtonEnable(boolean enable);
	void setNewNoteButton(boolean enable);

	void clear();
	
	void setPresenter(Presenter presenter);

	void setVisible(boolean visibility);


}
