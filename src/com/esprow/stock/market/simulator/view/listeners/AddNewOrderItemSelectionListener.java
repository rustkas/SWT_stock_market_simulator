package com.esprow.stock.market.simulator.view.listeners;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.esprow.stock.market.simulator.model.BuyItem;
import com.esprow.stock.market.simulator.model.OrderItem;
import com.esprow.stock.market.simulator.model.OrderLists;
import com.esprow.stock.market.simulator.model.SellItem;
import com.esprow.stock.market.simulator.util.TimeFormatter;
import com.esprow.stock.market.simulator.util.TradeIndexGenerator;
import com.esprow.stock.market.simulator.view.enums.LedgerOperations;
import com.esprow.stock.market.simulator.view.enums.Operations;

public class AddNewOrderItemSelectionListener extends SelectionAdapter {

	private static final Color GRAY = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
	private Map<Double, TableItem> buyMap;
	private Map<Double, TableItem> sellMap;
	private final OrderLists orderList;

	private Text txtPrice;
	private Text txtQuality;
	private Table orderTable;
	private Table ledgerTable;
	private Combo comboOperation;

	public AddNewOrderItemSelectionListener(Text txtPrice, Text txtQuality, Table orderTable, Combo comboOperation, Table ledgerTable) {
		this.txtPrice = txtPrice;
		this.txtQuality = txtQuality;
		this.orderTable = orderTable;
		this.comboOperation = comboOperation;
		this.ledgerTable = ledgerTable;
		
		buyMap = new TreeMap<Double, TableItem>();
		sellMap = new TreeMap<Double, TableItem>();
		orderList = new OrderLists();
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		String strPrice = txtPrice.getText();
		String strQuality = txtQuality.getText();
		strPrice = strPrice.trim();
		strQuality = strQuality.trim();
		if ("".equals(strPrice)) {
			return;
		}

		if ("".equals(strQuality)) {
			return;
		}
		Double priceValue = null;

		try {
			priceValue = Double.parseDouble(strPrice);
		} catch (NumberFormatException e2) {
			System.err.println(strPrice);
		} catch (NullPointerException e2) {
			System.err.println("price = null");
		}

		if (null == priceValue) {
			return;
		}

		Long qualityValue = null;

		try {
			qualityValue = Long.parseLong(strQuality, 10);
		} catch (NumberFormatException e1) {
			System.err.println(strQuality);
		}

		if (null == qualityValue) {
			return;
		}

		addNewOrder(priceValue.doubleValue(), qualityValue.longValue());

	}

	private void addNewOrder(double price, long quality) {

		OrderItem orderItem;

		final TableItem newItem = new TableItem(orderTable, SWT.NONE);

		
		
		if (Operations.Buy.name().equals(comboOperation.getText())) {
			orderItem = orderList.createBuyItem(price, quality);
			buyMap.put(price, newItem);
		} else {
			orderItem = orderList.createSellItem(price, quality);
			sellMap.put(price, newItem);
			newItem.setBackground(GRAY);
		}
		newItem.setData(orderItem);

		final String[] strings = new String[] {

				String.valueOf(orderItem.getId()), String.valueOf(orderItem.getPrice()),
				String.valueOf(orderItem.getQuality()), TimeFormatter.getString(orderItem.getDateTime()),
				String.valueOf(orderItem.getOperation()), };
		newItem.setText(strings);

		Double maxBuy = null;
		Double minSell = null;
		try {
			final Set<Double> buySet = buyMap.keySet();
			final Set<Double> sellSet = sellMap.keySet();
			if (0 == buySet.size() || 0 == sellSet.size()) {
				return;
			}
			maxBuy = buySet.stream().mapToDouble(v -> v).max().orElseThrow(NoSuchElementException::new);
			minSell = sellSet.stream().mapToDouble(v -> v).min().orElseThrow(NoSuchElementException::new);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// check value
		if (null != maxBuy && null != minSell) {
			final int result = minSell.compareTo(maxBuy);

			if (0 >= result) {
				// do action
				trade(maxBuy, minSell);

			}
		}

	}

	private void trade(Double maxBuy, Double minSell) {
		final TableItem buyItem = buyMap.get(maxBuy);
		final TableItem sellItem = sellMap.get(minSell);
		final SellItem sellItemData = (SellItem) sellItem.getData();
		final BuyItem buyItemData = (BuyItem) buyItem.getData();

		String buyIdItemString = buyItem.getText(0);
		String buypriceItemString = buyItem.getText(1);
		String buyqualityItemString = buyItem.getText(2);
		// String buyTimeItemString = buyItem.getText(3);
		String buyoperationItemString = buyItem.getText(4);

		String sellIdItemString = sellItem.getText(0);
		String sellpriceItemString = sellItem.getText(1);
		String sellqualityItemString = sellItem.getText(2);
		// String sellTimeItemString = sellItem.getText(3);
		String selloperationItemString = sellItem.getText(4);

		LedgerOperations legerSellType = LedgerOperations.Full;

		long buyqualityItem = Long.valueOf(buyqualityItemString);
		long sellqualityItem = Long.valueOf(sellqualityItemString);
		long result = sellqualityItem - buyqualityItem;
		if (result < 0) {
			result = 0;
		}

		if (result > 0) {
			legerSellType = LedgerOperations.Partial;
		}

		// Edit order table

		sellItem.setText(2, String.valueOf(result));

		if (0 == result) {
			final Table table = sellItem.getParent();

			sellMap.remove(minSell);
			orderList.removeSellItem(sellItemData);
			table.remove(table.indexOf(sellItem));
		}

		final Table table = buyItem.getParent();

		buyMap.remove(maxBuy);
		orderList.removeBuyItem(buyItemData);
		table.remove(table.indexOf(buyItem));

		// add new sell tab item

		final TableItem sellItemLedger = new TableItem(ledgerTable, SWT.NONE);

		final String tradeTimeString = TimeFormatter.getString(LocalDateTime.now());

		final String[] sellLedgerStrings = new String[] {

				String.valueOf(TradeIndexGenerator.createNewIndex()), String.valueOf(sellIdItemString),
				String.valueOf(sellpriceItemString), String.valueOf(sellqualityItemString),
				String.valueOf(tradeTimeString), String.valueOf(selloperationItemString), legerSellType.name()

		};
		sellItemLedger.setText(sellLedgerStrings);

		final TableItem buyItemLedger = new TableItem(ledgerTable, SWT.NONE);
		final String[] buyLedgerStrings = new String[] {

				String.valueOf(TradeIndexGenerator.createNewIndex()), String.valueOf(buyIdItemString),
				String.valueOf(buypriceItemString), String.valueOf(buyqualityItemString),
				String.valueOf(tradeTimeString), String.valueOf(buyoperationItemString), LedgerOperations.Full.name()

		};
		buyItemLedger.setText(buyLedgerStrings);

		orderTable.layout(true);
		ledgerTable.layout(true);
	}
}
