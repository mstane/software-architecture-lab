package org.sm.lab.mybooks.client;

import net.customware.gwt.dispatch.client.DispatchAsync;

import org.sm.lab.mybooks.client.activity.BookFormActivity;
import org.sm.lab.mybooks.client.activity.BookListActivity;
import org.sm.lab.mybooks.client.activity.LoginActivity;
import org.sm.lab.mybooks.client.activity.NoteFormActivity;
import org.sm.lab.mybooks.client.activity.ProfileFormActivity;
import org.sm.lab.mybooks.client.ui.BookListView;
import org.sm.lab.mybooks.client.ui.BookListViewImpl;
import org.sm.lab.mybooks.client.ui.LoginView;
import org.sm.lab.mybooks.client.ui.LoginViewImpl;
import org.sm.lab.mybooks.client.ui.ProfileFormView;
import org.sm.lab.mybooks.client.ui.ProfileFormViewImpl;
import org.sm.lab.mybooks.client.util.AppDialogBox;
import org.sm.lab.mybooks.client.util.IAppDialogBox;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;



public class ClientFactoryPhoneImpl implements ClientFactory {

    private DispatchAsync dispatchAsync;
	private EventBus eventBus;
	private PlaceController placeController;
	
	private MyBooksShell myBooksShell;
	private IAppDialogBox appDialogBox;
	
	private LoginView loginView;
	private BookListView bookListView;
	private ProfileFormView profileFormView;
	
	private LoginActivity loginActivity;
	private BookListActivity bookListActivity;
	private BookFormActivity bookFormActivity;
	private NoteFormActivity noteFormActivity;
	private ProfileFormActivity profileFormActivity;


	public ClientFactoryPhoneImpl() {
		dispatchAsync = new DefaultSecureDispatchAsync();
		eventBus = new SimpleEventBus();
		placeController = new DefaultPlaceController(eventBus);
		myBooksShell = new MyBooksShell(eventBus);
		
		appDialogBox = new AppDialogBox();
	}
	
	@Override
    public DispatchAsync getDispatchAsync() {
        return dispatchAsync;
    }
	
	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public MyBooksShell getMyBooksShell() {
		return myBooksShell;
	}
	
	@Override
	public IAppDialogBox getAppDialogBox() {
		return appDialogBox;
	}
	
	@Override
	public LoginView getLoginView() {
		if (loginView == null) {
			loginView = new LoginViewImpl();
		}
		return loginView;
	}
	
	@Override
	public BookListView getBookListView() {
		if (bookListView == null) {
			bookListView = new BookListViewImpl();
		}
		return bookListView;
	}
	
	@Override
	public ProfileFormView getProfileFormView() {
		if (profileFormView == null) {
			profileFormView = new ProfileFormViewImpl();
		}
		return profileFormView;
	}

	@Override
	public LoginActivity getLoginActivity() {
		if (loginActivity == null) {
			loginActivity = new LoginActivity(this);
		}
		return loginActivity;
	}



	@Override
	public BookListActivity getBookListActivity() {
		if (bookListActivity == null) {
			bookListActivity = new BookListActivity(this);
		}
		return bookListActivity;
	}

	@Override
	public BookFormActivity getBookFormActivity() {
		if (bookFormActivity == null) {
			bookFormActivity = new BookFormActivity(this);
		}
		return bookFormActivity;	
	}

	@Override
	public NoteFormActivity getNoteFormActivity() {
		if (noteFormActivity == null) {
			noteFormActivity = new NoteFormActivity(this);
		}
		return noteFormActivity;	
	}

	@Override
	public ProfileFormActivity getProfileFormActivity() {
		if (profileFormActivity == null) {
			profileFormActivity = new ProfileFormActivity(this);
		}
		return profileFormActivity;
	}

}
