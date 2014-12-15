package org.sm.lab.mybooks.client.util.widget;

import com.google.gwt.user.client.ui.Anchor;

public class AnchorExt extends Anchor {
	
	public AnchorExt() {
		super();
	} 
	
	private String download;

	public String getDownload() {
		return download;
	}

	public void setDownload(String download) {
		this.download = (download != null ? download : "");;
		getElement().setPropertyString("download", download);
	}
	
}
