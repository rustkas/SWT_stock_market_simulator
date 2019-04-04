package com.esprow.stock.market.simulator.view.listeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class TabCursorSelectionListener extends SelectionAdapter {
	private final TableCursor cursor;
	private final Table table;
	private final ControlEditor editor;

	public TabCursorSelectionListener(Table table) {
		this.table = table;
		this.cursor = new TableCursor(table, SWT.NONE);

		// Create the editor
		// Use a ControlEditor, not a TableEditor, because the cursor is the parent
		this.editor = new ControlEditor(cursor);
		editor.grabHorizontal = true;
		editor.grabVertical = true;
		cursor.addSelectionListener(this);
	}

	public void widgetSelected(SelectionEvent event) {
		// Select the row in the table where the TableCursor is
		table.setSelection(new TableItem[] { cursor.getRow() });
//	}
//
//	// This is called when the user hits Enter
//	public void widgetDefaultSelected(SelectionEvent event) {
		// Begin an editing session
		// Notice that the parent of the Text is the TableCursor, not the Table
		final Text text = new Text(cursor, SWT.NONE);
		text.setFocus();

		// Copy the text from the cell to the Text
		text.setText(cursor.getRow().getText(cursor.getColumn()));
		text.setFocus();

		// Add a handler to detect key presses
		text.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				// End the editing and save the text if the user presses Enter
				// End the editing and throw away the text if the user presses Esc
				switch (event.keyCode) {
				case SWT.CR:
					cursor.getRow().setText(cursor.getColumn(), text.getText());
				case SWT.ESC:
					text.dispose();
					break;
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				System.out.println("mouseDoubleClick");
			}

			@Override
			public void mouseDown(MouseEvent e) {
				System.out.println("mouseDown"); 
//				Table table = (Table) e.widget;
//				int selectionIndex = table.getSelectionIndex();
//				TableItem[] selection = table.getSelection();
//				TableItem item = selection[selectionIndex];
				
			}

			@Override
			public void mouseUp(MouseEvent e) {
				System.out.println("mouseUp");
			}
		});

		editor.setEditor(text);
	}
}
