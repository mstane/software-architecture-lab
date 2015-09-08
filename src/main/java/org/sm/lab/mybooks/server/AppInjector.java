package org.sm.lab.mybooks.server;

import com.google.inject.AbstractModule;

import net.customware.gwt.dispatch.server.secure.SecureSessionValidator;

public class AppInjector extends AbstractModule {

	@Override
	protected void configure() {
		bind(SecureSessionValidator.class).to(AppSecureSessionValidator.class);

	}

}
