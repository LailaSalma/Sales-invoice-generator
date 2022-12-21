
package com.sig.model;


public class InvoiceLine {
    private Invoice invoice;
    private String itemName;
    private double itemPrice;
    private int count;
    

    public InvoiceLine() {
    }

    public InvoiceLine(String itemName, double itemPrice, int count, Invoice invoice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
        this.invoice = invoice;
    }

    public Invoice getInvoice() {
        return invoice;
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
    
    public double getTotal(){
        return itemPrice*count ;
    }
    
    public String getCSV(){
        return invoice.getInvNum() +", "+ itemName +", "+ itemPrice +", "+  count;
    }

    @Override
    public String toString() {
        return "Item{" + "Invoice No.: #" + invoice.getInvNum() + ", item name: " + itemName + ", item price: " + itemPrice + ", count: " + count + '}';
    }
    
    
    
}
