package com.esprow.stock.market.simulator.view.model;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class OrderBooks extends Composite {

	private Tree orderBookTree;

	public OrderBooks(Composite parent) {
		super(parent, SWT.None);
		setLayout(new FormLayout());

		Composite composite = new Composite(this, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0, 5);
		fd_composite.left = new FormAttachment(0, 5);
		composite.setLayoutData(fd_composite);
		composite.setBounds(0, 0, 64, 64);

		Button btnAdd = new Button(composite, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(0 < orderBookTree.getItemCount()) {
					return;
				}
				final TreeItem item = new TreeItem(orderBookTree, SWT.NONE);
				item.setText("Test");
			}
		});
		btnAdd.setToolTipText("Add order book");
		btnAdd.setBounds(0, 0, 68, 23);
		btnAdd.setText("Add");

		Composite composite_1 = new Composite(this, SWT.NONE);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.top = new FormAttachment(composite, 5, SWT.BOTTOM);
		fd_composite_1.left = new FormAttachment(0, 5);
		fd_composite_1.right = new FormAttachment(100, -5);
		fd_composite_1.bottom = new FormAttachment(100, 5);
		composite_1.setLayoutData(fd_composite_1);
		composite_1.setBounds(0, 0, 64, 64);
		composite_1.setLayout(new FillLayout(SWT.VERTICAL));

		orderBookTree = new Tree(composite_1, SWT.BORDER);

	}
}
