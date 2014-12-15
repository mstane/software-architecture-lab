package org.sm.lab.mybooks.client.event;

import com.google.gwt.event.shared.GwtEvent;

import org.sm.lab.mybooks.shared.dto.BookDto;

public class BookChangedEvent extends GwtEvent<BookChangedEventHandler> {
	public static Type<BookChangedEventHandler> TYPE = new Type<BookChangedEventHandler>();

	public enum Action {
		CREATED, UPDATED, DELETED
	};

	private final BookDto updatedBook;
	private final Action action;

	public BookChangedEvent(Action action, BookDto updatedBook) {
		this.action = action;
		this.updatedBook = updatedBook;
	}

	public BookDto getChangedBook() {
		return updatedBook;
	}

	public Action getAction() {
		return action;
	}

	@Override
	public Type<BookChangedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BookChangedEventHandler handler) {
		handler.onBookUpdated(this);
	}
}
