package org.sm.lab.mybooks.server.action;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.log4j.Logger;
import org.sm.lab.mybooks.server.AppDatabase;
import org.sm.lab.mybooks.shared.action.LoadAllNotesAction;
import org.sm.lab.mybooks.shared.action.LoadAllNotesResult;
import org.sm.lab.mybooks.shared.dto.NoteDto;

import java.util.ArrayList;
import java.util.Arrays;

public class LoadAllNotesHandler implements ActionHandler<LoadAllNotesAction, LoadAllNotesResult> {
	
	static final Logger logger = Logger.getLogger(LoadAllNotesHandler.class);

    public Class<LoadAllNotesAction> getActionType() {
        return LoadAllNotesAction.class;
    }

	@Override
	public LoadAllNotesResult execute(LoadAllNotesAction action, ExecutionContext context) throws DispatchException {
		logger.debug("Start execute.");
		
		NoteDto[] dtos = AppDatabase.get().loadBookNotes(action.getBookId()); 
		return new LoadAllNotesResult(new ArrayList<NoteDto>(Arrays.asList(dtos)));
	}

	@Override
	public void rollback(LoadAllNotesAction action, LoadAllNotesResult result, ExecutionContext context) throws DispatchException {
		
	}
}