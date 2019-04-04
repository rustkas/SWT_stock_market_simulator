package com.esprow.stock.market.simulator.view.listeners;

import java.time.LocalDateTime;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.esprow.stock.market.simulator.model.OrderItem;
import com.esprow.stock.market.simulator.util.TimeFormatter;
import com.esprow.stock.market.simulator.view.enums.Operations;

public class SpecialTableEditorMouseAdapter extends MouseAdapter {

	private Table table;
	private TableEditor editor;

	public SpecialTableEditorMouseAdapter(Table orderTable) {
		this.table = orderTable;
		editor = new TableEditor(orderTable);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
	}

	@Override
	public void mouseDown(MouseEvent event) {

		// Dispose any existing editor
		Control old = editor.getEditor();
		if (old != null)
			old.dispose();

		// Determine where the mouse was clicked
		Point pt = new Point(event.x, event.y);

		// Determine which row was selected
		final TableItem item = table.getItem(pt);
		if (item != null) {
			// Determine which column was selected
			int column = -1;
			for (int i = 0, n = table.getColumnCount(); i < n; i++) {
				Rectangle rect = item.getBounds(i);
				if (rect.contains(pt)) {
					// This is the selected column
					column = i;
					break;
				}
			}

			final OrderItem orderItem = (OrderItem) item.getData();
			if (1 == column) {
				// Price
				// Create the Text object for our editor
				final Text text = new Text(table, SWT.NONE);
				text.setForeground(item.getForeground());
				// Transfer any text from the cell to the Text control,
				// set the color to match this row, select the text,
				// and set focus to the control
				text.setText(item.getText(column));
				text.setForeground(item.getForeground());
				text.selectAll();
				text.setFocus();

				// Recalculate the minimum width for the editor
				editor.minimumWidth = text.getBounds().width;

				// Set the control into the editor
				editor.setEditor(text, item, column);

				// Add a handler to transfer the text back to the cell
				// any time it's modified
				final int col = column;
				text.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent event) {

						Double newValue = null;
						String newValueText = text.getText();
						try {
							newValue = Double.parseDouble(newValueText);
						} catch (NumberFormatException e) {
							System.out.println("not a number");
						}
						if (null == newValue) {
							return;
						}

						// Set the text of the editor's control back into the cell
						item.setText(col, newValue.toString());
						orderItem.setPrice(newValue.doubleValue());
						updateOrderDate(item, orderItem);
					}

					
				});
			}

			if (2 == column) {
				// Quality

				// Create the Text object for our editor
				final Text text = new Text(table, SWT.NONE);
				text.setForeground(item.getForeground());
				// Transfer any text from the cell to the Text control,
				// set the color to match this row, select the text,
				// and set focus to the control
				text.setText(item.getText(column));
				text.setForeground(item.getForeground());
				text.selectAll();
				text.setFocus();

				// Recalculate the minimum width for the editor
				editor.minimumWidth = text.getBounds().width;

				// Set the control into the editor
				editor.setEditor(text, item, column);

				// Add a handler to transfer the text back to the cell
				// any time it's modified
				final int col = column;
				text.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent event) {

						Integer newValue = null;
						String newValueText = text.getText();
						try {
							newValue = Integer.parseInt(newValueText);
						} catch (NumberFormatException e) {
							System.out.println("not a number");
						}
						if (null == newValue) {
							return;
						}

						// Set the text of the editor's control back into the cell
						item.setText(col, newValue.toString());
						orderItem.setQuality(newValue.longValue());
						updateOrderDate(item, orderItem);
					}
				});
			}

			if (column == 4) {
				// Buy / Sell

				final Combo combo = new Combo(table, SWT.READ_ONLY);
				combo.add(Operations.Buy.name());
				combo.add(Operations.Sell.name());
				String comboText = item.getText(4);

				// Select the previously selected item from the cell
				if (comboText.equals(Operations.Buy.name())) {
					combo.select(0);
				} else if (comboText.equals(Operations.Sell.name())) {
					combo.select(1);
				}

				// Compute the width for the editor
				// Also, compute the column width, so that the dropdown fits
				editor.minimumWidth = combo.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
				table.getColumn(column).setWidth(editor.minimumWidth);

				// Set the focus on the dropdown and set into the editor
				combo.setFocus();
				editor.setEditor(combo, item, column);

				// Add a listener to set the selected item back into the cell
				final int col = column;
				combo.addSelectionListener(new SelectionAdapter() {

					public void widgetSelected(SelectionEvent event) {
						final String comboText = combo.getText();
						if (comboText.equals(Operations.Buy.name())) {
							orderItem.setOperation(Operations.Buy);
						} else if (comboText.equals(Operations.Sell.name())) {
							orderItem.setOperation(Operations.Sell);
						}
						item.setText(col, comboText);
						updateOrderDate(item, orderItem);
						// They selected an item; end the editing session
						combo.dispose();
					}

				});

			} // if

		} // if
	}// mouseDown
	
	private void updateOrderDate(final TableItem item, final OrderItem orderItem) {
		orderItem.setDateTime(LocalDateTime.now());
		item.setText(3, TimeFormatter.getString(orderItem.getDateTime()));
	}

}