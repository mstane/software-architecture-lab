package org.sm.lab.mybooks.client;

import org.sm.lab.mybooks.client.event.LoginEvent;
import org.sm.lab.mybooks.client.event.LoginEventHandler;
import org.sm.lab.mybooks.client.place.BookFormPlace;
import org.sm.lab.mybooks.client.place.BookListPlace;
import org.sm.lab.mybooks.client.place.LoginPlace;
import org.sm.lab.mybooks.client.place.NoteFormPlace;
import org.sm.lab.mybooks.client.place.ProfileFormPlace;
import org.sm.lab.mybooks.shared.AppConsts;
import org.sm.lab.mybooks.shared.dto.ReaderDto;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;

public class AppActivityMapper implements ActivityMapper {
    
    private AppGinjector ginjector;
    private EventBus eventBus;
    private PlaceController placeController;
    
    private int sessionDuration = 0;
    
    @Inject
    public AppActivityMapper(AppGinjector ginjector) {
        super();
        this.ginjector = ginjector;
        this.eventBus = ginjector.getEventBus();
        this.placeController = ginjector.getPlaceController();
        
        eventBus.addHandler(LoginEvent.TYPE,
                new LoginEventHandler() {
                    public void onLogin(LoginEvent event) {
                        doLogin(event.getDto(), event.getDuration());
                    }
                });
        
    }
    
    @Override
    public Activity getActivity(Place place) {
        
        Log.debug("AppActivityMapper.getActivity() place=" + place);
        
        resetSessionTimer(place);
        
        if (place instanceof LoginPlace) return ginjector.getLoginActivity();
        else if (place instanceof BookFormPlace) return ginjector.getBookFormActivity();
        else if (place instanceof BookListPlace) return ginjector.getBookListActivity();
        else if (place instanceof NoteFormPlace) return ginjector.getNoteFormActivity();
        else if (place instanceof ProfileFormPlace) return ginjector.getProfileFormActivity();
        
        return null;
    }
    
    private void doLogin(ReaderDto dto, int duration) {
        if (dto != null) {
            sessionDuration = duration;
            placeController.goTo(new BookListPlace());
        } else {
            logoutTimer.cancel();
            placeController.goTo(new LoginPlace());
        }
    }
    
    
    Timer logoutTimer = new Timer() {
        @Override
        public void run() {
            Log.debug("Logout Timer");
            Cookies.removeCookie(AppConsts.COOKIE_NAME);
            eventBus.fireEvent(new LoginEvent());
        }
    };
    
    public void resetSessionTimer(Place place) {
        if (!(place instanceof LoginPlace)) {
            logoutTimer.cancel();
            logoutTimer.schedule(sessionDuration);
        }
    }
    
    
    

}
