package org.sm.lab.mybooks.client.event;

import com.google.gwt.event.shared.GwtEvent;

import org.sm.lab.mybooks.shared.dto.NoteDto;

public class NoteChangedEvent extends GwtEvent<NoteChangedEventHandler> {
	public static Type<NoteChangedEventHandler> TYPE = new Type<NoteChangedEventHandler>();

	public enum Action {
		CREATED, UPDATED, DELETED
	};

	private final NoteDto updatedNote;
	private final Action action;

	public NoteChangedEvent(Action action, NoteDto updatedNote) {
		this.action = action;
		this.updatedNote = updatedNote;
	}

	public NoteDto getChangedNote() {
		return updatedNote;
	}

	public Action getAction() {
		return action;
	}

	@Override
	public Type<NoteChangedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(NoteChangedEventHandler handler) {
		handler.onNoteUpdated(this);
	}
}
