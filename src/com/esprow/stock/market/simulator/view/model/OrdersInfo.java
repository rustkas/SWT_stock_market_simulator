package com.esprow.stock.market.simulator.view.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.esprow.stock.market.simulator.util.TimeFormatter;
import com.esprow.stock.market.simulator.util.TradeIndexGenerator;
import com.esprow.stock.market.simulator.view.enums.LedgerOperations;
import com.esprow.stock.market.simulator.view.enums.Operations;

public class OrdersInfo extends Composite {
	private Table buyTable;
	private Table ledgerTable;
	private Text txtPrice;
	private Text txtQuality;
	private Combo comboOperation;
	private Button btnAdd;

	private final OrderLists orderList;


	public OrdersInfo(Composite parent) {
		super(parent, SWT.None);

		orderList = new OrderLists();
	

		buyMap = new TreeMap<Double, TableItem>();
		sellMap = new TreeMap<Double, TableItem>();

		setLayout(new FormLayout());

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new FormLayout());

		{
			FormData fd = new FormData();
			fd.top = new FormAttachment(0, 5);
			fd.left = new FormAttachment(0, 5);
			composite.setLayoutData(fd);
		}

		Composite compositePrice = new Composite(composite, SWT.NONE);
		compositePrice.setLayout(new FormLayout());
		FormData fd_lblPrice = new FormData();
		fd_lblPrice.top = new FormAttachment(0, 5);
		fd_lblPrice.left = new FormAttachment(0, 5);
		compositePrice.setLayoutData(fd_lblPrice);

		Composite compositeQuality = new Composite(composite, SWT.NONE);
		compositeQuality.setLayout(new FormLayout());
		FormData fd_lblQuality = new FormData();
		fd_lblQuality.top = new FormAttachment(0, 5);
		fd_lblQuality.left = new FormAttachment(compositePrice, 5, SWT.RIGHT);
		compositeQuality.setLayoutData(fd_lblQuality);

		Composite compositeOperation = new Composite(composite, SWT.NONE);
		compositeOperation.setLayout(new FormLayout());
		FormData fd_lblOperation = new FormData();
		fd_lblOperation.top = new FormAttachment(0, 5);
		fd_lblOperation.left = new FormAttachment(compositeQuality, 5, SWT.RIGHT);
		compositeOperation.setLayoutData(fd_lblOperation);

		Label lblPrice = new Label(compositePrice, SWT.NONE);
		txtPrice = new Text(compositePrice, SWT.BORDER);

		
		txtPrice.addVerifyListener(new VerifyListener() {
			
			@Override
			public void verifyText(VerifyEvent e) {
				
				Double value = null;
				try {
					value = Double.parseDouble(e.text);
				} catch (NumberFormatException e1) {
					
					System.out.println("" + e.text + " is not a number");
				}catch (NullPointerException e2) {
					System.out.println("number can not be a null");
				}
				
				if(null == value) {
					e.doit = false;
					e.text="";
					return;
				}
				if(0 < Double.compare(0, value.doubleValue())) {
					e.text = String.valueOf(0);
				}
			}
		});
		
		Label lblQuality = new Label(compositeQuality, SWT.NONE);
		txtQuality = new Text(compositeQuality, SWT.BORDER);

		txtQuality.addVerifyListener(new VerifyListener() {
			
			@Override
			public void verifyText(VerifyEvent e) {
				
				Integer value = null;
				try {
					
					value = Integer.parseInt(e.text, 10);
				} catch (NumberFormatException e1) {
					
					System.out.println("" + e.text + " is not a number");
					e.text="";
				}catch (NullPointerException e2) {
					System.out.println("number can not be a null");
				}
				if(null == value) {
					e.doit = false;
					e.text="";
					return;
				}
				
				if(0 < value.intValue()) {
					e.text = String.valueOf(0);
				}
				
			}
		});
		
		Label lblOperation = new Label(compositeOperation, SWT.NONE);
		comboOperation = new Combo(compositeOperation, SWT.DROP_DOWN | SWT.READ_ONLY | SWT.SIMPLE);
		comboOperation.add(Operations.Buy.name());
		comboOperation.add(Operations.Sell.name());
		comboOperation.select(0);
		btnAdd = new Button(composite, SWT.None);

		lblPrice.setText("Price:");
		lblQuality.setText("Quality:");
		lblOperation.setText("Operation:");
		btnAdd.setText("Add");
		btnAdd.setToolTipText("Add Order book");

		{
			FormData fd = new FormData();
			fd.top = new FormAttachment(0, 5);
			fd.left = new FormAttachment(0, 5);
			lblPrice.setLayoutData(fd);
		}
		{
			FormData fd = new FormData();
			fd.top = new FormAttachment(lblPrice, 0, SWT.CENTER);
			fd.left = new FormAttachment(lblPrice, 5, SWT.RIGHT);
			txtPrice.setLayoutData(fd);
		}

		{
			FormData fd = new FormData();
			fd.top = new FormAttachment(0, 5);
			fd.left = new FormAttachment(0, 5);
			lblQuality.setLayoutData(fd);
		}
		{
			FormData fd = new FormData();
			fd.top = new FormAttachment(lblQuality, 0, SWT.CENTER);
			fd.left = new FormAttachment(lblQuality, 5, SWT.RIGHT);
			txtQuality.setLayoutData(fd);
		}

		{
			FormData fd = new FormData();
			fd.top = new FormAttachment(0, 5);
			fd.left = new FormAttachment(0, 5);
			lblOperation.setLayoutData(fd);
		}
		{
			FormData fd = new FormData();
			fd.top = new FormAttachment(lblOperation, 0, SWT.CENTER);
			fd.left = new FormAttachment(lblOperation, 5, SWT.RIGHT);
			comboOperation.setLayoutData(fd);
		}

		{
			FormData fd = new FormData();
			fd.top = new FormAttachment(compositeOperation, 0, SWT.CENTER);
			fd.left = new FormAttachment(compositeOperation, 5, SWT.RIGHT);
			btnAdd.setLayoutData(fd);
		}
		btnAdd.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				addNewOrder();

			}

		});

		CTabFolder tabFolder = new CTabFolder(this, SWT.BORDER | SWT.BOTTOM);

		{
			FormData fd = new FormData();
			fd.top = new FormAttachment(composite, 0, SWT.BOTTOM);
			fd.left = new FormAttachment(0, 5);
			fd.right = new FormAttachment(100, -5);
			fd.bottom = new FormAttachment(100, -5);
			tabFolder.setLayoutData(fd);
		}
		tabFolder.setSelectionBackground(
				Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		CTabItem tbtmOrders = new CTabItem(tabFolder, SWT.NONE);
		tbtmOrders.setText("Orders");

		buyTable = new Table(tabFolder, SWT.BORDER | SWT.FULL_SELECTION);
		tbtmOrders.setControl(buyTable);

		buyTable.setHeaderVisible(true);
		buyTable.setLinesVisible(true);

		TableColumn tblclmnIdOrder = new TableColumn(buyTable, SWT.NONE);
		tblclmnIdOrder.setWidth(100);
		tblclmnIdOrder.setText("Id Order");

		TableColumn tblclmnNewColumn = new TableColumn(buyTable, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("Price");

		TableColumn tblclmnNewColumn_1 = new TableColumn(buyTable, SWT.NONE);
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("Quality");

		TableColumn tblclmnTime = new TableColumn(buyTable, SWT.NONE);
		tblclmnTime.setWidth(100);
		tblclmnTime.setText("Time");

		TableColumn tblclmnBuysell = new TableColumn(buyTable, SWT.NONE);
		tblclmnBuysell.setWidth(100);
		tblclmnBuysell.setText("Buy/Sell");

		CTabItem tbtmLegers = new CTabItem(tabFolder, SWT.NONE);
		tbtmLegers.setText("Legers");

		ledgerTable = new Table(tabFolder, SWT.BORDER | SWT.FULL_SELECTION);
		ledgerTable.setLinesVisible(true);
		ledgerTable.setHeaderVisible(true);
		tbtmLegers.setControl(ledgerTable);

		TableColumn tableColumn_0 = new TableColumn(ledgerTable, SWT.NONE);
		tableColumn_0.setWidth(100);
		tableColumn_0.setText("Id Trade");

		TableColumn tableColumn = new TableColumn(ledgerTable, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("Id Order");

		TableColumn tableColumn_1 = new TableColumn(ledgerTable, SWT.NONE);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("Price");

		TableColumn tableColumn_2 = new TableColumn(ledgerTable, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("Quality");

		TableColumn tblclmnTimeTrade = new TableColumn(ledgerTable, SWT.NONE);
		tblclmnTimeTrade.setWidth(100);
		tblclmnTimeTrade.setText("Time Trade");

		TableColumn tableColumn_4 = new TableColumn(ledgerTable, SWT.NONE);
		tableColumn_4.setWidth(100);
		tableColumn_4.setText("Buy/Sell");

		TableColumn tblclmnNewColumn_2 = new TableColumn(ledgerTable, SWT.NONE);
		tblclmnNewColumn_2.setWidth(100);
		tblclmnNewColumn_2.setText("Partial/Full");

		tabFolder.setSelection(0);
	}

	private static final Color GRAY = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
	private Map<Double, TableItem> buyMap;
	private Map<Double, TableItem> sellMap;

	private void addNewOrder() {
		Double priceValue = Double.valueOf(txtPrice.getText());
		double price = priceValue.doubleValue();
		long quality = Long.valueOf(txtQuality.getText()).longValue();
		OrderItem orderItem;

		final TableItem newItem = new TableItem(buyTable, SWT.NONE);

		if (Operations.Buy.name().equals(comboOperation.getText())) {
			orderItem = orderList.createBuyItem(price, quality);
			buyMap.put(priceValue, newItem);
		} else {
			orderItem = orderList.createSellItem(price, quality);
			sellMap.put(priceValue, newItem);
			newItem.setBackground(GRAY);
		}
		newItem.setData(orderItem);

		final String[] strings = new String[] {

				String.valueOf(orderItem.getId()), 
				String.valueOf(orderItem.getPrice()),
				String.valueOf(orderItem.getQuality()), 
				TimeFormatter.getString(orderItem.getDateTime()),
				String.valueOf(orderItem.getOperation()), };
		newItem.setText(strings);

		Double maxBuy = null;
		Double minSell = null;
		try {
			final Set<Double> buySet = buyMap.keySet();
			final Set<Double> sellSet = sellMap.keySet();
			if(0 == buySet.size() || 0 == sellSet.size()) {
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

		if(0 == result) {
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
		
		buyTable.layout(true);
		ledgerTable.layout(true);
	}

}
