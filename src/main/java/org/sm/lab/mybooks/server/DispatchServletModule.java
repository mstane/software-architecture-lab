package org.sm.lab.mybooks.server;

import com.google.inject.servlet.ServletModule;

import org.sm.lab.mybooks.shared.AppConsts;

public class DispatchServletModule extends ServletModule {

    @Override
    protected void configureServlets() {    
        serve(AppConsts.DISPATCH_PATH).with(AppDispatchServlet.class);
    }
}
