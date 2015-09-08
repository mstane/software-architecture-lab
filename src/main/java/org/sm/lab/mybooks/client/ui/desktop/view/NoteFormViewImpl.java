package org.sm.lab.mybooks.client.ui.desktop.view;

import org.sm.lab.mybooks.client.view.NoteFormView;

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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * A form used for editing notes.
 */
public class NoteFormViewImpl extends Composite implements NoteFormView {

	@UiTemplate("NoteFormView.ui.xml")
	interface Binder extends UiBinder<Widget, NoteFormViewImpl> {
	}

	@UiField
	TextBox titleTextBox;
	@UiField
	TextArea contentTextArea;
    @UiField
    Label errorLabel;
	
	@UiField
	Button updateButton;
	@UiField
	Button deleteButton;
	
	private Presenter presenter;
	private Widget widget;

	public NoteFormViewImpl() {
		onInitialize();
	}
	
	public void onInitialize() {
		
		Binder uiBinder = GWT.create(Binder.class);
		widget = uiBinder.createAndBindUi(this);
		
		initWidget(widget);

				
	}

	@Override
	public HasValue<String> getNoteTitle() {
		return titleTextBox;
	}

	@Override
	public HasValue<String> getContent() {
		return contentTextArea;
	}
	
    @Override
    public HasText getErrorLabel() {
        return errorLabel;
    }
	
	@Override
	public void clear() {
	    titleTextBox.setValue("");
		contentTextArea.setValue("");
		
		errorLabel.setText("");
	}

	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
	}

	@UiHandler("updateButton")
	void onUpdateButtonClicked(ClickEvent event) {		
		if (presenter != null) {
			presenter.onUpdateButtonClicked();
		}
	}

	@UiHandler("deleteButton")
	void onDeleteButtonClicked(ClickEvent event) {
		if (Window.confirm("Are you sure to delete the note?")) {
			if (presenter != null) {
				presenter.onDeleteButtonClicked();
			}
		}
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}



}
