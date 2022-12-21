
package com.sig.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class LinesTable extends AbstractTableModel{

    private ArrayList<InvoiceLine> items;
    String[] cols = {"No.", "Item Name", "Item Price", "Count", "Item Total"};

    public LinesTable(ArrayList<InvoiceLine> items) {
        this.items = items;
    }

    public ArrayList<InvoiceLine> getItems() {
        return items;
    }
    
    
    @Override
    public int getRowCount() {
        return items.size();     //returns the no. of lines, AKA items' lines in the selected invoice
    }

    @Override
    public int getColumnCount() {
        return cols.length;         //returns the no. of columns in the item's line
    }
    
    @Override
    public String getColumnName(int index){
        return cols[index];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceLine item = items.get(rowIndex);
        switch (columnIndex){
            case 0:
                return item.getInvoice().getInvNum();
            case 1:
                return item.getItemName();
            case 2:
                return item.getItemPrice();
            case 3:
                return item.getCount();
            case 4:
                return item.getTotal();
            default:
                return "";
        }
    }
    
    
}
