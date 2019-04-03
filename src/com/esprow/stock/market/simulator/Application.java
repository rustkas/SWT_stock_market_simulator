package com.esprow.stock.market.simulator;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.esprow.stock.market.simulator.view.model.MainViewContainer;

public final class Application {
	private static final String APP_NAME = "Stock market simulator";

	public static void run() {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		shell.setText(APP_NAME);
		 new MainViewContainer(shell);
		
		shell.pack();
		shell.setSize(850, 600);
		shell.open();
		// Set up the event loop.
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				// If no more entries in the event queue
				display.sleep();
			}
		}
		display.dispose();
		
	}
}
