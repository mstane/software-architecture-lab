package org.sm.lab.mybooks.server.action;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.log4j.Logger;
import org.sm.lab.mybooks.server.AppDatabase;
import org.sm.lab.mybooks.shared.action.GetNoteAction;
import org.sm.lab.mybooks.shared.action.GetNoteResult;
import org.sm.lab.mybooks.shared.dto.NoteDto;

public class GetNoteHandler implements ActionHandler<GetNoteAction, GetNoteResult> {
	
	static final Logger logger = Logger.getLogger(GetNoteHandler.class);

    public Class<GetNoteAction> getActionType() {
        return GetNoteAction.class;
    }

	@Override
	public GetNoteResult execute(GetNoteAction action, ExecutionContext context) throws DispatchException {
		logger.debug("Start execute.");
		
		NoteDto dto = action.getDto();
		dto = AppDatabase.get().getNote(dto.getId());
		return new GetNoteResult(dto);
	}

	@Override
	public void rollback(GetNoteAction action, GetNoteResult result, ExecutionContext context) throws DispatchException {
		
	}
	
}