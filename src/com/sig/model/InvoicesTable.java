

package com.sig.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class InvoicesTable extends AbstractTableModel{
    
    private ArrayList<Invoice> invoices;
    String[] cols = {"No.", "Date", "Customer", "Total"};

    public InvoicesTable(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }
    
    @Override
    public int getRowCount() {
        return invoices.size();     //returns the no. of invoices
    }

    @Override
    public int getColumnCount() {
        return cols.length;         //returns the no. of columns in the invoice
    }

    @Override
    public String getColumnName(int index){
        return cols[index];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Invoice inv = invoices.get(rowIndex);
        switch (columnIndex){
            case 0:
                return inv.getInvNum();
            case 1:
                return inv.getDate();
            case 2:
                return inv.getCstName();
            case 3:
                return inv.getInvTotal();
            default:
                return "";
        }
    }
    
    
}
