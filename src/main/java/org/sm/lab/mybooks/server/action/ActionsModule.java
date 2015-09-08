package org.sm.lab.mybooks.server.action;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import org.sm.lab.mybooks.shared.action.CreateBookAction;
import org.sm.lab.mybooks.shared.action.CreateNoteAction;
import org.sm.lab.mybooks.shared.action.CreateReaderAction;
import org.sm.lab.mybooks.shared.action.DeleteBookAction;
import org.sm.lab.mybooks.shared.action.DeleteNoteAction;
import org.sm.lab.mybooks.shared.action.DeleteReaderAction;
import org.sm.lab.mybooks.shared.action.GetNoteAction;
import org.sm.lab.mybooks.shared.action.GetReaderAction;
import org.sm.lab.mybooks.shared.action.LoadAllBooksAction;
import org.sm.lab.mybooks.shared.action.LoadAllNotesAction;
import org.sm.lab.mybooks.shared.action.LoginAction;
import org.sm.lab.mybooks.shared.action.LogoutAction;
import org.sm.lab.mybooks.shared.action.UpdateBookAction;
import org.sm.lab.mybooks.shared.action.UpdateNoteAction;
import org.sm.lab.mybooks.shared.action.UpdateReaderAction;

public class ActionsModule extends ActionHandlerModule {

    @Override
    protected void configureHandlers() {
    	
        bindHandler(LoginAction.class, LoginHandler.class);
        bindHandler(LogoutAction.class, LogoutHandler.class);
        
        bindHandler(LoadAllBooksAction.class, LoadAllBooksHandler.class);
        bindHandler(CreateBookAction.class, CreateBookHandler.class);
        bindHandler(UpdateBookAction.class, UpdateBookHandler.class);
        bindHandler(DeleteBookAction.class, DeleteBookHandler.class);
        
        bindHandler(LoadAllNotesAction.class, LoadAllNotesHandler.class);
        bindHandler(CreateNoteAction.class, CreateNoteHandler.class);
        bindHandler(GetNoteAction.class, GetNoteHandler.class);
        bindHandler(UpdateNoteAction.class, UpdateNoteHandler.class);
        bindHandler(DeleteNoteAction.class, DeleteNoteHandler.class);
                
        bindHandler(CreateReaderAction.class, CreateReaderHandler.class);
        bindHandler(GetReaderAction.class, GetReaderHandler.class);
        bindHandler(UpdateReaderAction.class, UpdateReaderHandler.class);
        bindHandler(DeleteReaderAction.class, DeleteReaderHandler.class);

    }
    

}
