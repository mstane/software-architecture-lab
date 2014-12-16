package org.sm.lab.mybooks.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Singleton;

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

public class AppGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(DispatchAsync.class).to(DefaultSecureDispatchAsync.class).in(Singleton.class);
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(IAppDialogBox.class).to(AppDialogBox.class).in(Singleton.class);
		bind(PlaceController.class).to(DefaultPlaceController.class).in(Singleton.class);
            
        bind(LoginView.class).to(LoginViewImpl.class).in(Singleton.class);
        bind(BookListView.class).to(BookListViewImpl.class).in(Singleton.class);
        bind(ProfileFormView.class).to(ProfileFormViewImpl.class).in(Singleton.class);
        
        bind(LoginActivity.class).in(Singleton.class);
        bind(BookListActivity.class).in(Singleton.class);
        bind(BookFormActivity.class).in(Singleton.class);
        bind(NoteFormActivity.class).in(Singleton.class);
        bind(ProfileFormActivity.class).in(Singleton.class);

	}

}
