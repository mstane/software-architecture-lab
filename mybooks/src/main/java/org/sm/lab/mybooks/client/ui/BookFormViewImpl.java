package org.sm.lab.mybooks.client.ui;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.view.client.HasData;

import org.sm.lab.mybooks.shared.dto.NoteDto;

/**
 * A form used for editing books.
 */
public class BookFormViewImpl extends Composite implements BookFormView {

	@UiTemplate("BookFormView.ui.xml")
	interface Binder extends UiBinder<Widget, BookFormViewImpl> {
	}

	@UiField
	TextBox titleTextBox;
	@UiField
	TextBox authorTextBox;
	@UiField
	TextBox urlTextBox;
    @UiField
    TextBox startReadingDateTextBox;
    @UiField
    TextBox endReadingDateTextBox;
	@UiField
	TextBox ratingTextBox;
	@UiField
	CellTable<NoteDto> noteTable;
    @UiField
    Label errorLabel;

	@UiField
	Button saveButton;
	@UiField
	Button newNoteButton;
	@UiField
	Button deleteButton;

	private Presenter presenter;

	private Widget widget;

	public BookFormViewImpl() {
		onInitialize();
	}
	
	public void onInitialize() {
		
		Binder uiBinder = GWT.create(Binder.class);
		widget = uiBinder.createAndBindUi(this);
		
		initWidget(widget);
		
		TextColumn<NoteDto> nameColumn = new TextColumn<NoteDto>() {
			@Override
			public String getValue(NoteDto noteDto) {
				return noteDto.getTitle();
			}
		};
		
		noteTable.addColumn(nameColumn, "Notes");
		
		noteTable.addCellPreviewHandler(new Handler<NoteDto>() {
			@Override
			public void onCellPreview(CellPreviewEvent<NoteDto> event) {
				if ("click".equals(event.getNativeEvent().getType())) {
					presenter.onNoteItemClicked(event.getValue());
				}
			}
		});		
	}
	
    public Widget asWidget() {
        Log.debug("BookFormViewImpl.asWidget()");
        return widget;
    }

    @Override
    public HasValue<String> getBookTitle() {
        Log.debug("BookFormViewImpl.getBookTitle()");
        return titleTextBox;
    }
    
	@Override
	public HasValue<String> getAuthor() {
		return authorTextBox;
	}

	@Override
	public HasValue<String> getUrl() {
		return urlTextBox;
	}

    @Override
    public HasValue<String> getStartReadingDate() {
        return startReadingDateTextBox;
    }
    
    @Override
    public HasValue<String> getEndReadingDate() {
        return endReadingDateTextBox;
    }

    @Override
    public HasValue<String> getRating() {
        return ratingTextBox;
    }

	@Override
	public HasData<NoteDto> getNoteTable() {
		return noteTable;
	}
	
    @Override
    public HasText getErrorLabel() {
        return errorLabel;
    }
    
    @Override
    public void clear() {
        titleTextBox.setValue("");
        authorTextBox.setValue("");
        urlTextBox.setValue("");
        startReadingDateTextBox.setValue("");
        endReadingDateTextBox.setValue("");
        ratingTextBox.setValue("");
        errorLabel.setText("");        
    }

    @Override
    public void setSaveButtonEnable(boolean enabled) {
        saveButton.setEnabled(enabled);
        
    }

    @Override
    public void setDeleteButtonEnable(boolean enabled) {
        deleteButton.setEnabled(enabled);
    }

    @Override
    public void setNewNoteButton(boolean enabled) {
        newNoteButton.setEnabled(enabled);
    }
    
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
	}

	@UiHandler("saveButton")
	void onSaveButtonClicked(ClickEvent event) {		
		if (presenter != null) {
			presenter.onUpdateButtonClicked();
		}
	}

	@UiHandler("deleteButton")
	void onDeleteButtonClicked(ClickEvent event) {
		if (Window.confirm("Are you sure to delete the book?")) {
			if (presenter != null) {
				presenter.onDeleteButtonClicked();
			}
		}
	}
	
	@UiHandler("newNoteButton")
	void onNewNoteButtonClicked(ClickEvent event) {		
		if (presenter != null) {
			presenter.onNewNoteButtonClicked();
		}
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}


}
