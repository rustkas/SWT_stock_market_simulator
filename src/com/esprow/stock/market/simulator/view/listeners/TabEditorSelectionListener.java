package com.esprow.stock.market.simulator.view.listeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class TabEditorSelectionListener extends SelectionAdapter {
	private static final Color YELLOW = Display.getDefault().getSystemColor(SWT.COLOR_YELLOW);
	
	private final Table table;
	private TableEditor editor;

	public TabEditorSelectionListener(Table table) {
		this.table = table;
		editor = new TableEditor(table);
		editor.grabHorizontal = true;
		
		
		table.addSelectionListener(this);
	}

	// This is called as the user navigates around the table
	public void widgetSelected(SelectionEvent event) {


		// Clean up any previous editor control
		Control oldEditor = editor.getEditor();
		if (oldEditor != null)
			oldEditor.dispose();

		// Identify the selected row

        TableItem item = (TableItem)event.item;
		if (item == null) {
			return;
		}

		// The control that will be the editor must be a child of the Table
		final Text newEditor = new Text(table, SWT.BORDER);
		newEditor.setBackground(YELLOW);
		newEditor.setForeground(YELLOW);
		
		final String currentText = item.getText(2);
		newEditor.setText(currentText);
		newEditor.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				final Text text = (Text) editor.getEditor();
				final String string = text.getText();
				
				editor.getItem().setText(2, string);
			}
		});

		newEditor.setFocus();
		editor.setEditor(newEditor, item, 2);
		editor.layout();

	}
}
