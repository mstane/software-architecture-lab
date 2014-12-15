package org.sm.lab.mybooks.server.action;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.log4j.Logger;
import org.sm.lab.mybooks.server.AppDatabase;
import org.sm.lab.mybooks.shared.AppConsts;
import org.sm.lab.mybooks.shared.action.DeleteNoteAction;
import org.sm.lab.mybooks.shared.action.DeleteNoteResult;
import org.sm.lab.mybooks.shared.dto.NoteDto;
import org.sm.lab.mybooks.shared.dto.ReaderDto;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpSession;

public class DeleteNoteHandler implements ActionHandler<DeleteNoteAction, DeleteNoteResult> {
	
	static final Logger logger = Logger.getLogger(DeleteNoteHandler.class);
	
	private final Provider<HttpSession> httpSession;
	
    public Class<DeleteNoteAction> getActionType() {
        return DeleteNoteAction.class;
    }
    
    @Inject
	public DeleteNoteHandler(final Provider<HttpSession> httpSession) {
		this.httpSession = httpSession;
	}

	@Override
	public DeleteNoteResult execute(DeleteNoteAction action, ExecutionContext context) throws DispatchException {
		logger.debug("Start execute.");
		
		NoteDto dto = action.getDto();
		AppDatabase.get().deleteNote(dto);
		return new DeleteNoteResult();
	}

	@Override
	public void rollback(DeleteNoteAction action, DeleteNoteResult result, ExecutionContext context) throws DispatchException {
		
	}

	
}