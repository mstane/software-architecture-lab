package org.sm.lab.mybooks.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;

import net.customware.gwt.dispatch.client.DispatchAsync;

import org.sm.lab.mybooks.client.activity.BookFormActivity;
import org.sm.lab.mybooks.client.activity.BookListActivity;
import org.sm.lab.mybooks.client.activity.LoginActivity;
import org.sm.lab.mybooks.client.activity.NoteFormActivity;
import org.sm.lab.mybooks.client.ui.BookListView;
import org.sm.lab.mybooks.client.ui.LoginView;
import org.sm.lab.mybooks.client.util.IAppDialogBox;

@GinModules(AppGinModule.class)
public interface AppGinjector extends Ginjector {

    DispatchAsync getDispatchAsync();
	MyBooksShell getMyBooksShell();
	EventBus getEventBus();
	PlaceController getPlaceController();
	IAppDialogBox getAppDialogBox();
   
    LoginView getLoginView();
    LoginActivity getLoginActivity();

    BookListView getBookListView();
    BookListActivity getBookListActivity();
    BookFormActivity getBookFormActivity();

    NoteFormActivity getNoteFormActivity();
	
}
