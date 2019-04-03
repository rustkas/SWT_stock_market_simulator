package com.esprow.stock.market.simulator.view.model;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public final class MainViewContainer extends Composite {

	public MainViewContainer(Composite parent) {
		super(parent, SWT.DOUBLE_BUFFERED);
		setLayout(new FormLayout());
		
		Composite composite = new OrderBooks(this);
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0, 5);
		fd_composite.bottom = new FormAttachment(100, -5);
		fd_composite.left = new FormAttachment(0);
		composite.setLayoutData(fd_composite);
		
	}

}
