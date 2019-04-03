package com.esprow.stock.market.simulator.util;

public final class OrdersIndexGenerator {

	private static int INDEX=1;
	private OrdersIndexGenerator() {
		
	}
	/**
	 * return new index
	 * */
	public static int createNewIndex() {
		return INDEX++;
	}
}
