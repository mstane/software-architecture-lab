package org.sm.lab.mybooks.client.util;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AppDialogBox extends DialogBox implements IAppDialogBox {
	
	private HTML label = new HTML();

	public AppDialogBox() {
		setText("My Books");

        setAnimationEnabled(true);
        setGlassEnabled(true);

        Button ok = new Button("OK");
        ok.addClickHandler(new ClickHandler() {
           public void onClick(ClickEvent event) {
        	   AppDialogBox.this.hide();
           }
        });

        VerticalPanel panel = new VerticalPanel();
        panel.setHeight("100");
        panel.setWidth("300");
        panel.setSpacing(10);
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.add(label);
        panel.add(ok);

        setWidget(panel);
	}
	
	@Override
	public void display(String message) {
		this.label.setText(message);
		center();
		show();
		
	}

}
