package com.esprow.stock.market.simulator.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class TabRowEditorSelectionListener extends SelectionAdapter {
	private final Table table;
	private TableCursor cursor;
	private ControlEditor editor;
	public TabRowEditorSelectionListener(Table table) {
		this.table = table;
		cursor = new TableCursor(table, SWT.NONE);
	    editor = new ControlEditor(cursor);
	    editor.grabHorizontal = true;
	    editor.grabVertical = true;
	    cursor.addSelectionListener(this);
	}
	
	
	 // This is called as the user navigates around the table
    public void widgetSelected(SelectionEvent event) {
      // Select the row in the table where the TableCursor is
      table.setSelection(new TableItem[] { cursor.getRow() });
    }
    // This is called when the user hits Enter
    public void widgetDefaultSelected(SelectionEvent event) {
      final Text text = new Text(cursor, SWT.NONE);
      text.setFocus();
      // Copy the text from the cell to the Text control
      text.setText(cursor.getRow().getText(cursor.getColumn()));
      text.setFocus();
      // Add a handler to detect key presses
      text.addKeyListener(new KeyAdapter() {
        public void keyPressed(KeyEvent event) {
          switch (event.keyCode) {
          case SWT.CR:
            cursor.getRow().setText(cursor.getColumn(), text.getText());
          case SWT.ESC:
            text.dispose();
            break;
          }
        }
      });
      text.addMouseListener(new MouseAdapter() {
    	  @Override
    	public void mouseDoubleClick(MouseEvent e) {
    		  cursor.getRow().setText(cursor.getColumn(), text.getText());
    		  System.out.println("mouse double click");
    	}
    	  @Override
    	public void mouseDown(MouseEvent e) {
    		  cursor.getRow().setText(cursor.getColumn(), text.getText());
    		  System.out.println("mouse down");
    	}
    	  @Override
    	public void mouseUp(MouseEvent e) {
    		  text.dispose();
    		  System.out.println("mouse up");
    	}
	});
      text.addMouseTrackListener(new MouseTrackAdapter() {
	@Override
	public void mouseEnter(MouseEvent e) {
		cursor.getRow().setText(cursor.getColumn(), text.getText());
		  System.out.println("mouse enter");
	}
	@Override
		public void mouseExit(MouseEvent e) {
		  text.dispose();
		  System.out.println("mouse exit");
		}
      });
      text.addMouseMoveListener(new MouseMoveListener() {
		
		@Override
		public void mouseMove(MouseEvent e) {
			System.out.println("mouse move");
		}
	});
      editor.setEditor(text);
    }
}
