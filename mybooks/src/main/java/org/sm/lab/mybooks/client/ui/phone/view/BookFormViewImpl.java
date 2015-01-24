package org.sm.lab.mybooks.client.ui.phone.view;

import org.sm.lab.mybooks.client.view.BookFormView;
import org.sm.lab.mybooks.shared.dto.NoteDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasData;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.widget.button.ButtonBase;
import com.googlecode.mgwt.ui.client.widget.input.MTextBox;

public class BookFormViewImpl extends Composite implements BookFormView {

	private static BookFormViewImplGwtImplUiBinder uiBinder = GWT.create(BookFormViewImplGwtImplUiBinder.class);
	private Presenter presenter;

	interface BookFormViewImplGwtImplUiBinder extends UiBinder<Widget, BookFormViewImpl> {
	}

	@UiField
	ButtonBase backButton;
	
    @UiField
    MTextBox bookTitle;
	
    @UiField
    MTextBox author;
    
    @UiField
    MTextBox url;
    
    @UiField
    MTextBox startReadingDate;

    @UiField
    MTextBox endReadingDate;

    @UiField
    MTextBox rating;
    
	public BookFormViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@UiHandler("backButton")
	protected void onBackButtonPressed(TapEvent event) {
		if (presenter != null) {
			presenter.onBackButtonPressed();
		}
	}

	@Override
	public HasValue<String> getBookTitle() {
		return bookTitle;
	}

	@Override
	public HasValue<String> getAuthor() {
		return author;
	}

	@Override
	public HasValue<String> getUrl() {
		return url;
	}

	@Override
	public HasValue<String> getStartReadingDate() {
		return startReadingDate;
	}

	@Override
	public HasValue<String> getEndReadingDate() {
		return endReadingDate;
	}

	@Override
	public HasValue<String> getRating() {
		return rating;
	}

	@Override
	public HasData<NoteDto> getNoteTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasText getErrorLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSaveButtonEnable(boolean enable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDeleteButtonEnable(boolean enable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNewNoteButton(boolean enable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
    
    
}
