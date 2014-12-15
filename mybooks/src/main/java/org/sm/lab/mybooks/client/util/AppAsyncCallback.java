package org.sm.lab.mybooks.client.util;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;

import net.customware.gwt.dispatch.shared.secure.InvalidSessionException;

import org.sm.lab.mybooks.client.event.LoginEvent;
import org.sm.lab.mybooks.shared.AppConsts;
import org.sm.lab.mybooks.shared.exception.MyBooksException;

/**
 * 
 * @author Stanislav Markov
 *
 * @param <T>
 */
public abstract class AppAsyncCallback<T> implements AsyncCallback<T>
{
	public static final String PROCESSING_ERROR_PREFIX = "Processing error : ";
	public static final String INVALID_SESSION = "Invalid Session!";
	
	private IAppDialogBox appDialogBox;
	private EventBus eventBus;
	
	public AppAsyncCallback(EventBus eventBus, IAppDialogBox appDialogBox) {
		this.appDialogBox = appDialogBox;
		this.eventBus = eventBus;
	}
	
	@Override
	public void onFailure(Throwable caught)
	{
		String message = "";
		
		if (caught instanceof InvalidSessionException) {
		    doLogout();
            return;
		} else if (caught instanceof MyBooksException)
		{
		    MyBooksException exception = (MyBooksException)caught;
			
			switch(exception.getErrorCode())
			{
			case UNAUTHORIZED:
			    doLogout();
				return;				
			default:
				break;
			}

			
			if (exception.hasMessage())
			{
			    message += caught.getMessage();
			}
		}
		else
		{
		    message += caught.getMessage();
		}

		appDialogBox.display(PROCESSING_ERROR_PREFIX + message);
		
		Log.info(message);
	}
	
	private void doLogout() {
	    Log.debug(INVALID_SESSION);
	    appDialogBox.display(INVALID_SESSION);
        Cookies.removeCookie(AppConsts.COOKIE_NAME);
        eventBus.fireEvent(new LoginEvent());
	}
}
