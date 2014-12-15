package org.sm.lab.mybooks.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

@Singleton
public class DefaultStandardDispatchAsync extends StandardDispatchAsync {
    @Inject
    public DefaultStandardDispatchAsync() {
        super(new DefaultExceptionHandler());
    }
}
