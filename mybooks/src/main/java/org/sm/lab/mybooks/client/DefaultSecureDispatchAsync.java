package org.sm.lab.mybooks.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.secure.CookieSecureSessionAccessor;
import net.customware.gwt.dispatch.client.secure.SecureDispatchAsync;

import org.sm.lab.mybooks.shared.AppConsts;

@Singleton
public class DefaultSecureDispatchAsync extends SecureDispatchAsync {
    @Inject
    public DefaultSecureDispatchAsync() {
        super(new DefaultExceptionHandler(), new CookieSecureSessionAccessor(AppConsts.COOKIE_NAME));
    }
}
