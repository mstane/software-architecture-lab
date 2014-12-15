package org.sm.lab.mybooks.server.action;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.log4j.Logger;
import org.sm.lab.mybooks.server.AppDatabase;
import org.sm.lab.mybooks.shared.AppConsts;
import org.sm.lab.mybooks.shared.action.CreateNoteAction;
import org.sm.lab.mybooks.shared.action.CreateNoteResult;
import org.sm.lab.mybooks.shared.dto.NoteDto;
import org.sm.lab.mybooks.shared.dto.ReaderDto;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpSession;

public class CreateNoteHandler implements ActionHandler<CreateNoteAction, CreateNoteResult> {
	
	static final Logger logger = Logger.getLogger(CreateNoteHandler.class);
	
	private final Provider<HttpSession> httpSession;

    public Class<CreateNoteAction> getActionType() {
        return CreateNoteAction.class;
    }
    
    @Inject
	public CreateNoteHandler(final Provider<HttpSession> httpSession) {
		this.httpSession = httpSession;
	}


	@Override
	public CreateNoteResult execute(CreateNoteAction action, ExecutionContext context) throws DispatchException {
		logger.debug("Start execute.");
		
		NoteDto dto = action.getDto();
		
		dto = AppDatabase.get().createNote(dto);

		return new CreateNoteResult(dto);
	}

	@Override
	public void rollback(CreateNoteAction action, CreateNoteResult result, ExecutionContext context) throws DispatchException {
		
	}
	
}