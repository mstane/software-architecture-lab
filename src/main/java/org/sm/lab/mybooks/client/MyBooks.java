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
                            Window.alert("uncaught: " + e.getMessage());
                            String s = buildStackTrace(e, "RuntimeException:\n");
                            Window.alert(s);
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

    private String buildStackTrace(Throwable t, String log) {

            if (t != null) {
                    log += t.getClass().toString();
                    log += t.getMessage();
                    //
                    StackTraceElement[] stackTrace = t.getStackTrace();
                    if (stackTrace != null) {
                            StringBuffer trace = new StringBuffer();

                            for (int i = 0; i < stackTrace.length; i++) {
                                    trace.append(stackTrace[i].getClassName() + "." + stackTrace[i].getMethodName() + "(" + stackTrace[i].getFileName() + ":" + stackTrace[i].getLineNumber());
                            }

                            log += trace.toString();
                    }
                    //
                    Throwable cause = t.getCause();
                    if (cause != null && cause != t) {

                            log += buildStackTrace(cause, "CausedBy:\n");

                    }
            }
            return log;
    }

}
