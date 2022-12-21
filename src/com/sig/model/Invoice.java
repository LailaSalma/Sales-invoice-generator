
package com.sig.model;

import java.util.ArrayList;


public class Invoice {
    private int invNum;
    private String date;
    private String cstName;
    private ArrayList<InvoiceLine> lines;
    double invTotal;

    public Invoice() {
    }

    public Invoice(int invNum, String date, String cstName) {
        this.invNum = invNum;
        this.date = date;
        this.cstName = cstName;
    }
    
    public double getInvTotal(){
        invTotal = 0.0;
        for ( InvoiceLine item : getLines() ){
            invTotal += item.getTotal();
        }
        return invTotal ;
    }

    public String getCstName() {
        return cstName;
    }

    public void setCstName(String cstName) {
        this.cstName = cstName;
    }

    public int getInvNum() {
        return invNum;
    }

    public void setInvNum(int invNum) {
        this.invNum = invNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<InvoiceLine> getLines() {
        // lazy loading
        if ( lines == null ){
            lines = new ArrayList<>();
        }
        return lines;
    }
    
    public String getCSV(){
        return invNum +", "+ date +", "+ cstName ;
    }

    @Override
    public String toString() {
        return "Invoice{" + "Invoice no.: #" + invNum + ", date: " + date + ", Customer name: " + cstName + '}';
    }
    
    
}
