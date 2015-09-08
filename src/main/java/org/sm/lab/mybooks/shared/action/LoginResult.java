package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Result;

import org.sm.lab.mybooks.shared.dto.ReaderDto;

public class LoginResult implements Result {
    private ReaderDto dto;
    private String sessionId;
    private int duration;

    LoginResult() {
    }

    public LoginResult(ReaderDto dto, String sessionId, int duration) {
        this.dto = dto;
        this.sessionId = sessionId;
        this.duration = duration;
    }

    public ReaderDto getDto() {
        return dto;
    }
    
    public String getSessionId() {
    	return this.sessionId;
    }
    
    public int getDuration() {
        return this.duration;
    }

}