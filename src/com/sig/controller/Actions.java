
package com.sig.controller;

import com.sig.view.InvoiceWindow;
import com.sig.model.Invoice;
import com.sig.model.InvoiceLine;
import com.sig.model.InvoicesTable;
import com.sig.model.LinesTable;
import com.sig.view.InvoicesDialog;
import com.sig.view.ItemsDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Actions implements ActionListener, ListSelectionListener {

    private InvoiceWindow window;
    InvoicesDialog invDial;
    ItemsDialog itemDial;
    
    public Actions (InvoiceWindow window){
        this.window = window;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        
        switch ( actionCommand ){
            case "Load File":
                loadFile();
                break;
            
            case "Save File":
                saveFile();
                break;
            
            case "Create New Invoice":
                createNewInvoice();
                break;
            
            case "Delete Invoice":
                deleteInvoice();
                break;
                
            case "addInvBtn":
                addInvBtn();
                break;
                
            case "cancelInvBtn":
                cancelInvBtn();
                break;
            
            case "Create Item":
                createItem();
                break;
            
            case "Remove Item":
                RemoveItem();
                break;
                
            case "addItemBtn":
                addItemBtn();
                break;
                
            case "cancelItemBtn":
                cancelItemBtn();
                break;
            
        }
    }

    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        try {
        int rslt = fileChooser.showOpenDialog(window);
        if ( rslt == JFileChooser.APPROVE_OPTION ){
            File invoicesFile = fileChooser.getSelectedFile();
            Path invoicesPath = Paths.get(invoicesFile.getAbsolutePath());
            List<String> invoicesLines = Files.readAllLines(invoicesPath);
            
            ArrayList<Invoice> invoicesArray = new ArrayList<>();
            for ( String invoicesLine : invoicesLines ){
               // 1,22-11-2020,Ali
              //  2,13-10-2021,Saleh
              String[] invoicesValues = invoicesLine.split(",");
              int invNum = Integer.parseInt(invoicesValues[0]);
              String date = invoicesValues[1];
              String cstName = invoicesValues[2];
              
              Invoice invoice = new Invoice(invNum, date, cstName);
              invoicesArray.add(invoice);
            }
            
            rslt = fileChooser.showOpenDialog(window);
            if ( rslt == JFileChooser.APPROVE_OPTION ){
                File linesFile = fileChooser.getSelectedFile();
                Path linesPath = Paths.get(linesFile.getAbsolutePath());
                List<String> lineLines = Files.readAllLines(linesPath);
                
                for ( String lineLine : lineLines ){
                    // 1,22-11-2020,Ali
                    //  2,13-10-2021,Saleh
                    String[] lineValues = lineLine.split(",");
                    int invNum = Integer.parseInt(lineValues[0]);
                    String itemName = lineValues[1];
                    double itemPrice = Double.parseDouble(lineValues[2]);
                    int count = Integer.parseInt(lineValues[3]);
                    Invoice inv = null;
                    for ( Invoice invoice : invoicesArray ){
                        if ( invoice.getInvNum() == invNum ){
                            inv = invoice;
                            break;
                        }
                    }
                    InvoiceLine line = new InvoiceLine(itemName, itemPrice, count, inv);
                    inv.getLines().add(line);
                }
            }
            window.setInvoices(invoicesArray);
            InvoicesTable invTable = new InvoicesTable (invoicesArray);
            window.setInvTableModel(invTable);
            window.getInvoicesTable().setModel(invTable);
            window.getInvTableModel().fireTableDataChanged();
        }
        } catch (NumberFormatException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(window, "The number format is incorrect\n", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(window, "File Error\n", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(window, "Read Error\n", "Error", JOptionPane.ERROR_MESSAGE);
        }
        printInvoices();    //delete this 
    }

    private void saveFile() {
        ArrayList<Invoice> invs = window.getInvoices();
        String invoices = "";
        String items = "";
        for ( Invoice inv : invs ){
            String invoiceCSV = inv.getCSV();
            invoices += invoiceCSV;
            invoices += "\n";
            for ( InvoiceLine line : inv.getLines() ){
                String lineCSV = line.getCSV();
                items += lineCSV;
                items += "\n";
            }
        }
        System.out.println("check point");
        try {
            JFileChooser fileChooser = new JFileChooser();
            int rslt = fileChooser.showSaveDialog(window);
            if ( rslt == JFileChooser.APPROVE_OPTION ){
                File invoicesFile = fileChooser.getSelectedFile();
                FileWriter invFileWriter = new FileWriter(invoicesFile);
                invFileWriter.write(invoices);
                invFileWriter.flush();
                invFileWriter.close();
                rslt = fileChooser.showSaveDialog(window);
                if ( rslt == JFileChooser.APPROVE_OPTION ){
                    File linesFile = fileChooser.getSelectedFile();
                    FileWriter linesFileWriter = new FileWriter(linesFile);
                    linesFileWriter.write(items);
                    linesFileWriter.flush();
                    linesFileWriter.close();
                }
            }
        } catch (Exception e){ 
            JOptionPane.showMessageDialog(window, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        JOptionPane.showMessageDialog(window,"The file was saved Successfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void createNewInvoice() {
        invDial = new InvoicesDialog(window);
        invDial.setVisible(true);
    }

    private void deleteInvoice() {
        int selectedInv = window.getInvoicesTable().getSelectedRow();
        if ( selectedInv > -1 ){
            window.getInvoices().remove(selectedInv);
            window.getInvTableModel().fireTableDataChanged();
            JOptionPane.showMessageDialog(window,"The invoice was deleted Successfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
            printInvoices();
        }
    }

    private void createItem() {
        itemDial = new ItemsDialog(window);
        itemDial.setVisible(true);
    }

    private void RemoveItem() {
        int selectedItem = window.getItemsTable().getSelectedRow();
        int selectedInv = window.getInvoicesTable().getSelectedRow();
        if ( (selectedItem > -1) && (selectedInv > -1) ){
            Invoice inv = window.getInvoices().get(selectedInv);
            inv.getLines().remove(selectedItem);
            LinesTable itemsTableModel = new LinesTable(inv.getLines());
            window.getItemsTable().setModel(itemsTableModel);
            itemsTableModel.fireTableDataChanged();
            window.getInvTableModel().fireTableDataChanged();
            JOptionPane.showMessageDialog(window,"The item was removed Successfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
            printInvoices();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int index = window.getInvoicesTable().getSelectedRow();
        if ( index > -1 ){
            Invoice selectedInv = window.getInvoices().get(index);
            window.getInvNumLbl().setText("# "+ selectedInv.getInvNum());
            window.getInvDateLbl().setText(selectedInv.getDate());
            window.getCstNameLbl().setText(selectedInv.getCstName());
            window.getInvTotalLbl().setText(""+ selectedInv.getInvTotal());
            LinesTable linesTable = new LinesTable( selectedInv.getLines() );
            window.getItemsTable().setModel(linesTable);
            linesTable.fireTableDataChanged();
        }
    }

    private void addInvBtn() {
        String date = invDial.getInvDate().getText();
        String cst = invDial.getCstName().getText();
        String cstUpper = cst.substring(0, 1).toUpperCase() + cst.substring(1);
        Invoice inv = new Invoice(window.getNxtInvNum(), date, cstUpper);
        window.getInvoices().add(inv);
        window.getInvTableModel().fireTableDataChanged();
        invDial.setVisible(false);
        invDial.dispose();
        invDial = null;
        JOptionPane.showMessageDialog(window,"The invoice was added Successfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
        printInvoices();
        
    }

    private void cancelInvBtn() {
        invDial.setVisible(false);
        invDial.dispose();
        invDial = null;
    }

    private void addItemBtn() {
        String itemName = itemDial.getItemName().getText();
        String itemNameUpper = itemName.substring(0, 1).toUpperCase() + itemName.substring(1);
        double price = Double.parseDouble( itemDial.getItemPrice().getText() );
        int count = Integer.parseInt( itemDial.getItemCount().getText() );
        int selectedInv = window.getInvoicesTable().getSelectedRow();
        if ( selectedInv > -1 ){
            Invoice inv = window.getInvoices().get(selectedInv);
            InvoiceLine item = new InvoiceLine(itemNameUpper, price, count, inv);
            inv.getLines().add(item);
            LinesTable lineTable = (LinesTable) window.getItemsTable().getModel();
            lineTable.fireTableDataChanged();
            window.getInvTableModel().fireTableDataChanged();
        }
        itemDial.setVisible(false);
        itemDial.dispose();
        itemDial = null;
        JOptionPane.showMessageDialog(window,"The item was added Successfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
        printInvoices();
    }

    private void cancelItemBtn() {
        itemDial.setVisible(false);
        itemDial.dispose();
        itemDial = null;
    }
    
    //delete this
    private void printInvoices() {
        System.out.println("--------");
        for (Invoice inv : window.getInvoices()) {
            System.out.println(inv);
        }
        System.out.println("--------");
    }
}
