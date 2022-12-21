
package com.sig.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class InvoicesDialog extends JDialog {
    private JLabel cstNameLbl;
    private JLabel invDateLbl;
    private JTextField cstName;
    private JTextField invDate;
    private JButton addInvBtn;
    private JButton cancelInvBtn;

    public InvoicesDialog( InvoiceWindow window ) {
        
        cstName = new JTextField(10);
        cstNameLbl = new JLabel("Customer Name:");
        
        invDate = new JTextField(10);
        invDateLbl = new JLabel("Date:");
        
        addInvBtn = new JButton("Add Invoice");
        cancelInvBtn = new JButton("Cancel");
        
        addInvBtn.setActionCommand("addInvBtn");
        addInvBtn.addActionListener(window.getAction());
        
        cancelInvBtn.setActionCommand("cancelInvBtn");
        cancelInvBtn.addActionListener(window.getAction());
        
        setLayout( new GridLayout(3,2) );
        
        add(cstNameLbl);
        add(cstName);
        cstName.setText("Welcome!");
        add(invDateLbl);
        add(invDate);
        invDate.setText("DD-MM-YYYY");
        add(addInvBtn);
        add(cancelInvBtn);
        
        pack();
    }

    public JTextField getCstName() {
        return cstName;
    }

    public JTextField getInvDate() {
        return invDate;
    }
      
}
