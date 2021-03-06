package com.esprow.stock.market.simulator.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
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

import com.esprow.stock.market.simulator.view.enums.Operations;
import com.esprow.stock.market.simulator.view.listeners.AddNewOrderItemSelectionListener;
import com.esprow.stock.market.simulator.view.listeners.SpecialTableEditorMouseAdapter;
import com.esprow.stock.market.simulator.view.listeners.TabEditorSelectionListener;

public class OrdersInfo extends Composite {
	private Table orderTable;
	private Table ledgerTable;
	private Text txtPrice;
	private Text txtQuality;
	private Combo comboOperation;
	private Button btnAdd;

	public OrdersInfo(Composite parent) {
		super(parent, SWT.None);

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

		Label lblQuality = new Label(compositeQuality, SWT.NONE);
		txtQuality = new Text(compositeQuality, SWT.BORDER);

		Label lblOperation = new Label(compositeOperation, SWT.NONE);
		comboOperation = new Combo(compositeOperation, SWT.READ_ONLY | SWT.BORDER);
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

		orderTable = new Table(tabFolder, SWT.BORDER | SWT.FULL_SELECTION);
		tbtmOrders.setControl(orderTable);

		orderTable.setHeaderVisible(true);
		orderTable.setLinesVisible(true);

		List<TableColumn> ordersColumnList = new ArrayList<TableColumn>();

		{
			TableColumn tableColumn = new TableColumn(orderTable, SWT.NONE);
			tableColumn.setWidth(100);
			tableColumn.setText("Id Order");
			ordersColumnList.add(tableColumn);
		}
		{
			TableColumn tblclmnNewColumn = new TableColumn(orderTable, SWT.NONE);
			tblclmnNewColumn.setWidth(100);
			tblclmnNewColumn.setText("Price");

		}
		{
			TableColumn tableColumn = new TableColumn(orderTable, SWT.NONE);
			tableColumn.setWidth(100);
			tableColumn.setText("Quality");
			ordersColumnList.add(tableColumn);
		}
		{
			TableColumn tableColumn = new TableColumn(orderTable, SWT.NONE);
			tableColumn.setWidth(100);
			tableColumn.setText("Time");
			ordersColumnList.add(tableColumn);
		}
		{
			TableColumn tableColumn = new TableColumn(orderTable, SWT.NONE);
			tableColumn.setWidth(100);
			tableColumn.setText("Buy/Sell");
			ordersColumnList.add(tableColumn);
		}

		orderTable.addMouseListener(new SpecialTableEditorMouseAdapter(orderTable));

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


		new TabEditorSelectionListener(ledgerTable);
		btnAdd.addSelectionListener(
				new AddNewOrderItemSelectionListener(txtPrice, txtQuality, orderTable, comboOperation, ledgerTable));

		tabFolder.setSelection(0);

		orderTable.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.DEL) {

					int selectionIndex = orderTable.getSelectionIndex();
					if (0 <= selectionIndex) {
						TableItem item = orderTable.getItem(selectionIndex);
						Object date = item.getData();

						// remove data from order list
						Table parent2 = item.getParent();
						item.dispose();
						parent2.layout(true);

					}

				}
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});
	}

}
