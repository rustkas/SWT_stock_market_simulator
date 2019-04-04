package com.esprow.stock.market.simulator.model;

import java.time.LocalDateTime;

import com.esprow.stock.market.simulator.view.enums.Operations;

public abstract class OrderItem{

	private final int id;
	private double price;
	private  long  quality;
	private  LocalDateTime dateTime;
	private Operations operation;
	
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

	public void setPrice(double price) {
		this.price = price;
	}

	public void setQuality(long quality) {
		this.quality = quality;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public void setOperation(Operations operation) {
		this.operation = operation;
	}
	

}
