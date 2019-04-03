package com.esprow.stock.market.simulator.util;

public final class TradeIndexGenerator {

	private static int INDEX=1;
	private TradeIndexGenerator() {
		
	}
	/**
	 * return new index
	 * */
	public static int createNewIndex() {
		return INDEX++;
	}
}
