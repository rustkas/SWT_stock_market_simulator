package com.esprow.stock.market.simulator.view.model;

import java.time.LocalDateTime;

import com.esprow.stock.market.simulator.view.enums.Operations;

public abstract class OrderItem{

	private final int id;
	private final double price;
	private final long  quality;
	private final LocalDateTime dateTime;
	private final Operations operation;
	
	public OrderItem(
			int id,
			double price,
			long  quality,
			LocalDateTime dateTime,
			Operations operation
			) {
		this.id = id;
		this.price = price;
		this.quality = quality;
		this.dateTime = dateTime;
		this.operation = operation;
	}

	public int getId() {
		return id;
	}

	public double getPrice() {
		return price;
	}

	public long getQuality() {
		return quality;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public Operations getOperation() {
		return operation;
	}
	

}
