package org.sm.lab.mybooks.client.ui.phone;

import org.sm.lab.mybooks.client.place.BookFormPlace;
import org.sm.lab.mybooks.client.place.BookListPlace;
import org.sm.lab.mybooks.client.place.LoginPlace;
import org.sm.lab.mybooks.client.place.NoteFormPlace;

import com.google.gwt.place.shared.Place;
import com.googlecode.mgwt.mvp.client.AnimationMapper;
import com.googlecode.mgwt.ui.client.widget.animation.Animation;
import com.googlecode.mgwt.ui.client.widget.animation.Animations;

public class AppAnimationMapper implements AnimationMapper {

	@Override
	public Animation getAnimation(Place oldPlace, Place newPlace) {

        if (oldPlace instanceof LoginPlace) {
            return Animations.SLIDE;
        }

        if (oldPlace instanceof BookListPlace && newPlace instanceof BookFormPlace) {
            return Animations.SLIDE;
        }
        
		if (oldPlace instanceof BookFormPlace && newPlace instanceof BookListPlace) {
			return Animations.SLIDE_REVERSE;
		}

        if (oldPlace instanceof BookFormPlace && newPlace instanceof NoteFormPlace) {
            return Animations.SLIDE;
        }
        
		if (oldPlace instanceof NoteFormPlace && newPlace instanceof BookFormPlace) {
			return Animations.SLIDE_REVERSE;
		}


		return null;
	}

}
