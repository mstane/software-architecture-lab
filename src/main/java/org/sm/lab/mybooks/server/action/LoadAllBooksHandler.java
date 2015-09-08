package org.sm.lab.mybooks.server.action;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.log4j.Logger;
import org.sm.lab.mybooks.server.AppDatabase;
import org.sm.lab.mybooks.shared.action.LoadAllBooksAction;
import org.sm.lab.mybooks.shared.action.LoadAllBooksResult;
import org.sm.lab.mybooks.shared.dto.BookDto;
import org.sm.lab.mybooks.shared.dto.BookDtoComparator;

import java.util.ArrayList;
import java.util.Arrays;

public class LoadAllBooksHandler implements ActionHandler<LoadAllBooksAction, LoadAllBooksResult> {
	
	static final Logger logger = Logger.getLogger(LoadAllBooksHandler.class);

    public Class<LoadAllBooksAction> getActionType() {
        return LoadAllBooksAction.class;
    }

	@Override
	public LoadAllBooksResult execute(LoadAllBooksAction action, ExecutionContext context) throws DispatchException {
		logger.debug("Start execute.");
		
		BookDto[] dtos = AppDatabase.get().loadAllBooks();
		Arrays.sort(dtos, new BookDtoComparator());
		
		return new LoadAllBooksResult(new ArrayList<BookDto>(Arrays.asList(dtos)));
	}

	@Override
	public void rollback(LoadAllBooksAction action, LoadAllBooksResult result, ExecutionContext context) throws DispatchException {
		
	}
}