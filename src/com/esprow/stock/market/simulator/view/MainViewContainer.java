package com.esprow.stock.market.simulator.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import com.esprow.stock.market.simulator.util.TradingScheduler;

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

		// Create the bar menu
		Shell shell = getShell();
		Menu menuBar = new Menu(shell, SWT.BAR);

		// Create the File item's dropdown menu
		Menu traidingMenu = new Menu(menuBar);

		MenuItem traidingItem = new MenuItem(menuBar, SWT.CASCADE);
		traidingItem.setText("Traiding");
		traidingItem.setMenu(traidingMenu);

		// Create all the items in the File dropdown menu
		MenuItem startTraidingItem = new MenuItem(traidingMenu, SWT.NONE);
		startTraidingItem.setText("Start traiding");
		MenuItem stopTraidingItem = new MenuItem(traidingMenu, SWT.NONE);
		stopTraidingItem.setText("Stop traiding");

		shell.setMenuBar(menuBar);

		startTraidingItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TradingScheduler.startScheduling();
			}
		});

		startTraidingItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TradingScheduler.stopScheduling();
			}
		});

//		Menu mainMenu = new Menu(getShell());
//		MenuItem newItem = new MenuItem(mainMenu, SWT.CASCADE);

//		Menu menuBar = new Menu(getShell(), SWT.BAR);
//        MenuItem cascadeTraidingMenu = new MenuItem(menuBar, SWT.CASCADE);
//        cascadeTraidingMenu.setText("&Traiding");
//		
//        Menu cascadeTraidingMenuItem = new Menu(menuBar, SWT.DROP_DOWN);
//        cascadeFileMenu.setMenu(fileMenu);
//        
//        MenuItem exitItem = new MenuItem(cascadeFileMenu, SWT.PUSH);
//        exitItem.setText("&Exit");
//        
//        Menu startTraiding = new Menu(getShell(), SWT.DROP_DOWN);
//        cascadeFileMenu.setMenu(startTraiding);
//        
//        Menu stopTraiding = new Menu(getShell(), SWT.DROP_DOWN);
//        cascadeFileMenu.setMenu(stopTraiding);

	}

}
