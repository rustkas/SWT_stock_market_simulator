package com.esprow.stock.market.simulator.view.model;

import java.time.LocalDateTime;

import com.esprow.stock.market.simulator.view.enums.Operations;

public final class SellItem extends OrderItem{

	public SellItem(int id, double price, long quality, LocalDateTime date) {
		super(id, price, quality, date, Operations.Sell);
		 
	}

	
	
}
