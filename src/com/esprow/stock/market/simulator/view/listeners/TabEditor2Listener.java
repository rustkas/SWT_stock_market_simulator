package com.esprow.stock.market.simulator.view.listeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class TabEditor2Listener implements Listener {
	private final Table table;
	private TableEditor editor;
	public TabEditor2Listener(Table table) {
		this.table = table;
		editor = new TableEditor(table);
		editor.grabHorizontal = true;
		
		
	}
	
	public void handleEvent(Event event) {
        Rectangle clientArea = table.getClientArea();
        Point pt = new Point(event.x, event.y);
        int index = table.getTopIndex();
        while (index < table.getItemCount()) {
          boolean visible = false;
          final TableItem item = table.getItem(index);
          for (int i = 0; i < table.getColumnCount(); i++) {
            Rectangle rect = item.getBounds(i);
            if (rect.contains(pt)) {
              final int column = i;
              final Text text = new Text(table, SWT.NONE);
              Listener textListener = new Listener() {
                public void handleEvent(final Event e) {
                  switch (e.type) {
                  case SWT.FocusOut:
                    item.setText(column, text.getText());
                    text.dispose();
                    break;
                  case SWT.Traverse:
                    switch (e.detail) {
                    case SWT.TRAVERSE_RETURN:
                      item
                          .setText(column, text
                              .getText());
                    //FALL THROUGH
                    case SWT.TRAVERSE_ESCAPE:
                      text.dispose();
                      e.doit = false;
                    }
                    break;
                  }
                }
              };
              text.addListener(SWT.FocusOut, textListener);
              text.addListener(SWT.Traverse, textListener);
              editor.setEditor(text, item, i);
              text.setText(item.getText(i));
              text.selectAll();
              text.setFocus();
              return;
            }
            if (!visible && rect.intersects(clientArea)) {
              visible = true;
            }
          }
          if (!visible)
            return;
          index++;
        }
      }
    }

