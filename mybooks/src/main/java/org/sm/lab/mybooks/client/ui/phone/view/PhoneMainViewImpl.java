package org.sm.lab.mybooks.client.ui.phone.view;

import org.sm.lab.mybooks.client.view.MainView;

import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.animation.AnimatableDisplay;

public class PhoneMainViewImpl implements MainView {

	public PhoneMainViewImpl() {

	}

	@Override
	public Widget asWidget() {
		return null;
	}

	@Override
	public HasSelectionHandlers<Integer> getSelectionHandlers() {
		return null;
	}

	@Override
	public AnimatableDisplay getAnimatableDisplay() {
		return null;
	}

    @Override
    public void setTabVisibility(boolean userLogged) {
    }

	@Override
	public AcceptsOneWidget getContentPanel() {
		return null;
	}
}
