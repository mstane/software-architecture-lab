package org.sm.lab.mybooks.server.action;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.log4j.Logger;
import org.sm.lab.mybooks.server.AppDatabase;
import org.sm.lab.mybooks.shared.AppConsts;
import org.sm.lab.mybooks.shared.action.UpdateNoteAction;
import org.sm.lab.mybooks.shared.action.UpdateNoteResult;
import org.sm.lab.mybooks.shared.dto.NoteDto;
import org.sm.lab.mybooks.shared.dto.ReaderDto;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpSession;

public class UpdateNoteHandler implements ActionHandler<UpdateNoteAction, UpdateNoteResult> {
	
	static final Logger logger = Logger.getLogger(UpdateNoteHandler.class);
	
	private final Provider<HttpSession> httpSession;
	
    public Class<UpdateNoteAction> getActionType() {
        return UpdateNoteAction.class;
    }
    
    @Inject
	public UpdateNoteHandler(final Provider<HttpSession> httpSession) {
		this.httpSession = httpSession;
	}

	@Override
	public UpdateNoteResult execute(UpdateNoteAction action, ExecutionContext context) throws DispatchException {
		
		logger.debug("Start execute.");
		
		NoteDto dto = action.getDto();
		
		AppDatabase.get().updateNote(dto);
		
		return new UpdateNoteResult(dto);
	}

	@Override
	public void rollback(UpdateNoteAction action, UpdateNoteResult result, ExecutionContext context) throws DispatchException {
		
	}
	
}