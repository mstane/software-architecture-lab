package org.sm.lab.mybooks.client.ui.phone.view;

import org.sm.lab.mybooks.client.view.MainView;

import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.animation.AnimatableDisplay;
import com.googlecode.mgwt.ui.client.widget.animation.AnimationWidget;
import com.googlecode.mgwt.ui.client.widget.panel.flex.RootFlexPanel;


public class PhoneMainViewImpl extends Composite implements MainView {

	private RootFlexPanel container;
	
	private SimpleLayoutPanel animatableDisplayContainer;
	private AnimatableDisplay animatableDisplay;
	
	public PhoneMainViewImpl() {
		container = new RootFlexPanel();
	    initWidget(container);
		
		animatableDisplayContainer = new SimpleLayoutPanel();
		
		animatableDisplay = new AnimationWidget();
		
		animatableDisplayContainer.add(animatableDisplay);
		
		container.add(animatableDisplayContainer, 1);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasSelectionHandlers<Integer> getSelectionHandlers() {
		return null;
	}

	@Override
	public AnimatableDisplay getAnimatableDisplay() {
		return animatableDisplay;
	}

    @Override
    public void setTabVisibility(boolean userLogged) {
    }

	@Override
	public AcceptsOneWidget getContentPanel() {
		return animatableDisplayContainer;
	}
}
