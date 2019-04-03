package com.esprow.stock.market.simulator.view.model;

import java.time.LocalDateTime;

import com.esprow.stock.market.simulator.view.enums.Operations;

public final class BuyItem extends OrderItem{

	public BuyItem(int id, double price, long quality, LocalDateTime date) {
		super(id, price, quality, date, Operations.Buy);
	}

}
