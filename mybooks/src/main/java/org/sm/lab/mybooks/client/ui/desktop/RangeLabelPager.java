package org.sm.lab.mybooks.client.ui.desktop;

import com.google.gwt.i18n.client.HasDirection;
import com.google.gwt.user.cellview.client.AbstractPager;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.view.client.HasRows;
import com.google.gwt.view.client.Range;

/**
 * A pager that displays the current range without any controls to change the range.
 */
public class RangeLabelPager extends AbstractPager {

	/**
	 * The label that shows the current range.
	 */
	private final HTML label = new HTML();

	public RangeLabelPager() {
		initWidget(label);
	}

	@Override
	protected void onRangeOrRowCountChanged() {
		HasRows display = getDisplay();
		Range range = display.getVisibleRange();
		int start = range.getStart();
		int end = start + range.getLength();
		label.setText(start + " - " + end + " : " + display.getRowCount(), HasDirection.Direction.LTR);
	}
}
