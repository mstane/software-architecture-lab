package org.sm.lab.mybooks.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DefaultPlaceController extends PlaceController {

    @Inject
    public DefaultPlaceController(EventBus eventBus) {
        super(eventBus);
    }

}
