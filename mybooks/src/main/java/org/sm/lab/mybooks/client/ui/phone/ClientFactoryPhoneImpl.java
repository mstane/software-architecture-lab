package org.sm.lab.mybooks.client.ui.phone;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.secure.CookieSecureSessionAccessor;
import net.customware.gwt.dispatch.client.secure.SecureDispatchAsync;

import org.sm.lab.mybooks.client.AppActivityMapper;
import org.sm.lab.mybooks.client.AppPlaceHistoryMapper;
import org.sm.lab.mybooks.client.ClientFactory;
import org.sm.lab.mybooks.client.activity.BookFormActivity;
import org.sm.lab.mybooks.client.activity.BookListActivity;
import org.sm.lab.mybooks.client.activity.LoginActivity;
import org.sm.lab.mybooks.client.activity.NoteFormActivity;
import org.sm.lab.mybooks.client.activity.ProfileFormActivity;
import org.sm.lab.mybooks.client.place.LoginPlace;

import org.sm.lab.mybooks.client.ui.phone.view.PhoneMainViewImpl;
import org.sm.lab.mybooks.client.util.IAppDialogBox;
import org.sm.lab.mybooks.client.view.BookListView;
import org.sm.lab.mybooks.client.view.LoginView;
import org.sm.lab.mybooks.client.view.MainView;
import org.sm.lab.mybooks.client.view.ProfileFormView;
import org.sm.lab.mybooks.shared.AppConsts;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.mgwt.mvp.client.AnimatingActivityManager;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;



public class ClientFactoryPhoneImpl implements ClientFactory {

    private DispatchAsync dispatchAsync;
	private EventBus eventBus;
	private PlaceController placeController;
	
	private MainView mainView;
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
		dispatchAsync = new SecureDispatchAsync(new DefaultExceptionHandler(), new CookieSecureSessionAccessor(AppConsts.COOKIE_NAME));
		eventBus = new SimpleEventBus();
		placeController = new PlaceController(eventBus);
		mainView = new PhoneMainViewImpl();
		
//		appDialogBox = new AppDialogBox();

	
		MGWT.applySettings(MGWTSettings.getAppSetting());

		
		Place defaultPlace = new LoginPlace();
		
        // Start PlaceHistoryHandler with our PlaceHistoryMapper
        AppPlaceHistoryMapper historyMapper= GWT.create(AppPlaceHistoryMapper.class);
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(this.getPlaceController(), this.getEventBus(), defaultPlace);


		AppActivityMapper activityMapper = new AppActivityMapper(this);
		AppAnimationMapper appAnimationMapper = new AppAnimationMapper();
		AnimatingActivityManager activityManager = new AnimatingActivityManager(activityMapper, appAnimationMapper, this.getEventBus());
		activityManager.setDisplay(this.getMainView().getAnimatableDisplay());
		
		RootPanel.get().add(this.getMainView());		
		historyHandler.handleCurrentHistory();

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
	public MainView getMainView() {
		return mainView;
	}
	
	@Override
	public IAppDialogBox getAppDialogBox() {
		return appDialogBox;
	}
	
	@Override
	public LoginView getLoginView() {
		if (loginView == null) {
//			loginView = new LoginViewImpl();
		}
		return loginView;
	}
	
	@Override
	public BookListView getBookListView() {
		if (bookListView == null) {
//			bookListView = new BookListViewImpl();
		}
		return bookListView;
	}
	
	@Override
	public ProfileFormView getProfileFormView() {
		if (profileFormView == null) {
//			profileFormView = new ProfileFormViewImpl();
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
