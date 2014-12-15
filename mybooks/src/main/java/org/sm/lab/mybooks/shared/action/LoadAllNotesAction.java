package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Action;

public class LoadAllNotesAction implements Action<LoadAllNotesResult> {

	private Long bookId;

	LoadAllNotesAction() {
	}

	public LoadAllNotesAction(Long bookId) {
		this.bookId = bookId;
	}

	public Long getBookId() {
		return this.bookId;
	}
}