package com.esprow.stock.market.simulator.util;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.widgets.Display;

public final class TradingScheduler {

	private static Timer timer;

	private TradingScheduler() {

	}

	public static void startScheduling() {
		timer = new Timer(true);
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						// analyze order state
					}
				});
			}
		}, 1000);
	}

	public static void stopScheduling() {
		timer.cancel();
	}
}
