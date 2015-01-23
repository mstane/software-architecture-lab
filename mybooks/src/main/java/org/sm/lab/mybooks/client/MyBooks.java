package org.sm.lab.mybooks.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import org.sm.lab.mybooks.client.place.LoginPlace;
import org.sm.lab.mybooks.client.resources.MyBooksWebResources;

public class MyBooks implements EntryPoint {

	/**
	 * The static images used throughout the MyBooks.
	 */
	public static final MyBooksWebResources images = GWT.create(MyBooksWebResources.class);

	public void onModuleLoad() {
		Log.debug("App loaded on client.");
		
		Place defaultPlace = new LoginPlace();

		Window.setTitle("My Books");
		
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		
		MyBooksShell shell = clientFactory.getMyBooksShell();
		
        // Start ActivityManager for the main widget with our ActivityMapper
        ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
        ActivityManager activityManager = new ActivityManager(activityMapper, clientFactory.getEventBus());
        activityManager.setDisplay(shell.getContentPanel());
	
        // Start PlaceHistoryHandler with our PlaceHistoryMapper
        AppPlaceHistoryMapper historyMapper= GWT.create(AppPlaceHistoryMapper.class);
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(clientFactory.getPlaceController(), clientFactory.getEventBus(), defaultPlace);
		
		RootLayoutPanel.get().add(shell);
        historyHandler.handleCurrentHistory();
		

	}

}
