package org.sm.lab.mybooks.client.activity;

import java.util.Date;

import net.customware.gwt.dispatch.client.DispatchAsync;

import org.sm.lab.mybooks.client.ClientFactory;
import org.sm.lab.mybooks.client.event.LoginEvent;
import org.sm.lab.mybooks.client.util.AppAsyncCallback;
import org.sm.lab.mybooks.client.util.IAppDialogBox;
import org.sm.lab.mybooks.client.view.LoginView;
import org.sm.lab.mybooks.shared.AppConsts;
import org.sm.lab.mybooks.shared.action.LoginAction;
import org.sm.lab.mybooks.shared.action.LoginResult;
import org.sm.lab.mybooks.shared.action.LogoutAction;
import org.sm.lab.mybooks.shared.action.LogoutResult;
import org.sm.lab.mybooks.shared.dto.ReaderDto;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

public class LoginActivity extends AbstractActivity implements LoginView.Presenter {

	private final DispatchAsync dispatchRpcService;
	private final EventBus eventBus;
	private final LoginView view;
	private final IAppDialogBox appDialogBox;

	private ReaderDto dto;

	@Inject
	public LoginActivity(ClientFactory injector) {
	    Log.debug("LoginActivity:LoginActivity()");
	    
        this.dispatchRpcService = injector.getDispatchAsync();
        this.eventBus = injector.getEventBus();
        this.appDialogBox = injector.getAppDialogBox();
		
		this.view = injector.getLoginView();
		this.view.setPresenter(this);
	}

    @Override
    public void start(AcceptsOneWidget container, EventBus eventBus) {	    
	    Log.debug("LoginActivity.start()");
	    
		container.setWidget(view.asWidget());

		if (dto != null) { // do logout
			dispatchRpcService.execute(new LogoutAction(),
					new AppAsyncCallback<LogoutResult>(LoginActivity.this.eventBus, appDialogBox) {
						public void onSuccess(LogoutResult result) {
					        Log.debug("LogoutAction - start() -- onSuccess()");
					        dto = null;
					        Cookies.removeCookie(AppConsts.COOKIE_NAME);
					        LoginActivity.this.eventBus.fireEvent(new LoginEvent());
						}
					});
		} else {
			String sessionId = Cookies.getCookie(AppConsts.COOKIE_NAME);
			if (sessionId != null) {
				dispatchRpcService.execute(new LoginAction(sessionId),
						new AppAsyncCallback<LoginResult>(LoginActivity.this.eventBus, appDialogBox) {
							public void onSuccess(LoginResult result) {
								Log.debug("LoginAction - start() -- onSuccess()");
								dto = result.getDto();
								if (dto != null) {
								    LoginActivity.this.eventBus.fireEvent(new LoginEvent(dto, result.getDuration()));
								} else {
									appDialogBox.display("Access Denied. Check your username and password.");
								}
							}
						});
			}
		}
	}

	@Override
	public void onLogInButtonClicked() {
	    
	    Log.debug("LoginActivity:onLogInButtonClicked()");
	    
	    final boolean rememberMe = view.getRemeberMe() == null ? false : view.getRemeberMe().getValue();
	    
		dispatchRpcService.execute(new LoginAction(view.getUsername()
				.getValue(), view.getPassword().getValue(), rememberMe),
				new AppAsyncCallback<LoginResult>(eventBus, appDialogBox) {
					public void onSuccess(LoginResult result) {
						Log.debug("LoginAction - onLogInButtonClicked() -- onSuccess()");
						dto = result.getDto();
						if (dto != null) {
							String sessionID = result.getSessionId();
							int durationInt = result.getDuration() * AppConsts.SECOND;
							Long durationLong = new Long(durationInt);
							
							Date expires = new Date(System.currentTimeMillis() + durationLong);
							Cookies.setCookie(AppConsts.COOKIE_NAME, sessionID, expires, null, "/", false);

							eventBus.fireEvent(new LoginEvent(dto, durationInt));
						} else {
							appDialogBox.display("Access Denied. Check your username and password.");
						}
					}
				});
		
	}
 


	
}
