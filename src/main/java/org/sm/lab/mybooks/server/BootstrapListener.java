package org.sm.lab.mybooks.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import net.customware.gwt.dispatch.server.guice.ServerDispatchModule;

import org.sm.lab.mybooks.server.action.ActionsModule;

public class BootstrapListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
        		new ServerDispatchModule(), 
        		new ActionsModule(), 
        		new DispatchServletModule(),
        		new AppInjector());
    }
}
