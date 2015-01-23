package org.sm.lab.mybooks.client;

import org.sm.lab.mybooks.client.resources.MyBooksWebResources;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

public class MyBooks implements EntryPoint {

	/**
	 * The static images used throughout the MyBooks.
	 */
	public static final MyBooksWebResources images = GWT.create(MyBooksWebResources.class);

	private void start() {
		Log.debug("App loaded on client.");
		Window.setTitle("My Books");

		GWT.create(ClientFactory.class);
	}

	@Override
	public void onModuleLoad() {

		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void onUncaughtException(Throwable e) {
				// TODO put a meaninful handler
				Window.alert("uncaught: " + e.getMessage());
				e.printStackTrace();

			}
		});

		new Timer() {
			@Override
			public void run() {
				start();

			}
		}.schedule(1);

	}

}
