package org.sm.lab.mybooks.client.event;

import com.google.gwt.event.shared.GwtEvent;

import org.sm.lab.mybooks.shared.dto.ReaderDto;

public class ReaderChangedEvent extends GwtEvent<ReaderChangedEventHandler> {
	public static Type<ReaderChangedEventHandler> TYPE = new Type<ReaderChangedEventHandler>();

	public enum Action {
		CREATED, UPDATED, DELETED
	};

	private final ReaderDto updatedReader;
	private final Action action;

	public ReaderChangedEvent(Action action, ReaderDto updatedReader) {
		this.action = action;
		this.updatedReader = updatedReader;
	}

	public ReaderDto getChangedReader() {
		return updatedReader;
	}

	public Action getAction() {
		return action;
	}

	@Override
	public Type<ReaderChangedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ReaderChangedEventHandler handler) {
		handler.onReaderUpdated(this);
	}
}
