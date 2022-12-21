
package com.sig.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ItemsDialog extends JDialog {
    
    private JLabel itemNameLbl;
    private JLabel itemPriceLbl;
    private JLabel itemCountLbl;
    private JTextField itemName;
    private JTextField itemPrice;
    private JTextField itemCount;
    private JButton addItemBtn;
    private JButton cancelItemBtn;

    public ItemsDialog( InvoiceWindow window ) {
        itemName = new JTextField(10);
        itemNameLbl = new JLabel("Item Name:");
        
        itemPrice = new JTextField(10);
        itemPriceLbl = new JLabel("Item Price:");
        
        itemCount = new JTextField(10);
        itemCountLbl = new JLabel("Count:");
        
        addItemBtn = new JButton("Add Item");
        cancelItemBtn = new JButton("Cancel");
        
        addItemBtn.setActionCommand("addItemBtn");
        addItemBtn.addActionListener(window.getAction());
        
        cancelItemBtn.setActionCommand("cancelItemBtn");
        cancelItemBtn.addActionListener(window.getAction());
        
        setLayout( new GridLayout(4,2) );
        
        add(itemNameLbl);
        add(itemName);
        add(itemPriceLbl);
        add(itemPrice);
        add(itemCountLbl);
        add(itemCount);
        add(addItemBtn);
        add(cancelItemBtn);
     
        pack();           
    }

    public JTextField getItemName() {
        return itemName;
    }

    public JTextField getItemPrice() {
        return itemPrice;
    }

    public JTextField getItemCount() {
        return itemCount;
    }
    
    
}
