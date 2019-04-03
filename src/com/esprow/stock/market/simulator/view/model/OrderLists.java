package com.esprow.stock.market.simulator.view.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.esprow.stock.market.simulator.util.OrdersIndexGenerator;

/**
 * Order lists (Buy and Sell) container
 */
public final class OrderLists {

	private final List<OrderItem> buyList;
	private final List<OrderItem> sellList;

	public OrderLists() {
		buyList = new ArrayList<>();
		sellList = new ArrayList<>();
	}

	public List<OrderItem> getBuyList() {
		return buyList;
	}

	public List<OrderItem> getSellList() {
		return sellList;
	}

	private static BuyItem createBuyItem(int id, LocalDateTime date, double price, long quality) {
		return new BuyItem(id, price, quality, date);
	}

	private static SellItem createSellItem(int id, LocalDateTime date, double price, long quality) {
		return new SellItem(id, price, quality, date);
	}

	public final OrderItem createBuyItem(double price, long quality) {
		final int id = OrdersIndexGenerator.createNewIndex();
		final LocalDateTime date = LocalDateTime.now();

		final OrderItem item = createBuyItem(id, date, price, quality);
		buyList.add(item);
		return item;

	}

	public final OrderItem createSellItem(double price, long quality) {
		final int id = OrdersIndexGenerator.createNewIndex();
		final LocalDateTime date = LocalDateTime.now();
	
		final OrderItem item = createSellItem(id, date, price, quality);
		sellList.add(item);
		return item;

	}

	public void removeSellItem(SellItem sellItemData) {
		sellList.remove(sellItemData);
		
	}

	public void removeBuyItem(BuyItem buyItemData) {
		buyList.remove(buyItemData);
		
	}

	

}
