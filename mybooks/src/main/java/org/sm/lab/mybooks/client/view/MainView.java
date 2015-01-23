package org.sm.lab.mybooks.client.view;

import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.mgwt.ui.client.widget.animation.AnimatableDisplay;

public interface MainView extends IsWidget {
	public HasSelectionHandlers<Integer> getSelectionHandlers();

	public AnimatableDisplay getAnimatableDisplay();

    public void setTabVisibility(boolean userLogged);

	public AcceptsOneWidget getContentPanel();

}
