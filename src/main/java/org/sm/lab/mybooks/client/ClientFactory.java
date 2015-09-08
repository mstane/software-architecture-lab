package org.sm.lab.mybooks.client;

import net.customware.gwt.dispatch.client.DispatchAsync;

import org.sm.lab.mybooks.client.activity.BookFormActivity;
import org.sm.lab.mybooks.client.activity.BookListActivity;
import org.sm.lab.mybooks.client.activity.LoginActivity;
import org.sm.lab.mybooks.client.activity.NoteFormActivity;
import org.sm.lab.mybooks.client.activity.ProfileFormActivity;
import org.sm.lab.mybooks.client.util.IAppDialogBox;
import org.sm.lab.mybooks.client.view.BookFormView;
import org.sm.lab.mybooks.client.view.BookListView;
import org.sm.lab.mybooks.client.view.LoginView;
import org.sm.lab.mybooks.client.view.MainView;
import org.sm.lab.mybooks.client.view.NoteFormView;
import org.sm.lab.mybooks.client.view.ProfileFormView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

public interface ClientFactory {

    DispatchAsync getDispatchAsync();
    MainView getMainView();
	EventBus getEventBus();
	PlaceController getPlaceController();
	IAppDialogBox getAppDialogBox();
   
    LoginView getLoginView();
    BookListView getBookListView();
    BookFormView getBookFormView();
    NoteFormView getNoteFormView();
    ProfileFormView getProfileFormView();
    
    LoginActivity getLoginActivity();
    BookListActivity getBookListActivity();
    BookFormActivity getBookFormActivity();
    NoteFormActivity getNoteFormActivity();
    ProfileFormActivity getProfileFormActivity();
	
}
