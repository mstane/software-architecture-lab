package org.sm.lab.mybooks.client.event;

import com.google.gwt.event.shared.GwtEvent;

import org.sm.lab.mybooks.shared.dto.ReaderDto;

public class LoginEvent extends GwtEvent<LoginEventHandler> {
	public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();
	private final ReaderDto readerDto;
	private final int duration;

	public LoginEvent() {
	    this.readerDto = null;
	    this.duration = 0;
	}
	
	public LoginEvent(ReaderDto readerDto, int duration) {
		this.readerDto = readerDto;
		this.duration = duration;
	}

	public ReaderDto getDto() {
		return this.readerDto;
	}

	public int getDuration() {
	    return duration;
	}
	
	@Override
	public Type<LoginEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LoginEventHandler handler) {
		handler.onLogin(this);
	}
}
