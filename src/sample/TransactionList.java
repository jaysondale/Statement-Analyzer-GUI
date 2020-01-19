package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.*;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransactionList {

    private SimpleStringProperty name;
    private SimpleDoubleProperty debitTotal;
    private SimpleDoubleProperty creditTotal;
    private SimpleDoubleProperty netTotal;
    private ObservableList<Transaction> transactionList;

    public TransactionList(String name){
        this.name = new SimpleStringProperty(name);
        this.debitTotal = new SimpleDoubleProperty(0.0);
        this.creditTotal = new SimpleDoubleProperty(0.0);
        this.transactionList = FXCollections.observableArrayList();
    }

    public void AddTransaction(Transaction newTransaction) {
        InsertTransaction(newTransaction);
        debitTotal = new SimpleDoubleProperty(debitTotal.get() + newTransaction.getDebit());
        creditTotal = new SimpleDoubleProperty(creditTotal.get() + newTransaction.getCredit());
    }

    private void InsertTransaction(Transaction newTransaction) {

        SimpleDateFormat d_format = new SimpleDateFormat("MM/dd/yyyy");
        Date newTransDate = d_format.parse(newTransaction.getDate(), new ParsePosition(0));
        boolean added = false;
        for (int t = 0; t < transactionList.size(); t++) {
            Date curTransDate = d_format.parse(transactionList.get(t).getDate(), new ParsePosition(0));
            if (curTransDate.after(newTransDate)) {
                transactionList.add(t, newTransaction);
                added = true;
                break;
            }
        }
        if (!added) { transactionList.add(newTransaction); }
    }

    public void removeTransaction(Transaction t) {
        debitTotal = new SimpleDoubleProperty(debitTotal.get() - t.getDebit());
        creditTotal = new SimpleDoubleProperty(creditTotal.get() - t.getCredit());
        transactionList.remove(t);
    }

    public Double getDebitTotal() {
        return debitTotal.get();
    }

    public Double getCreditTotal() {
        return creditTotal.get();
    }

    public Double getNetTotal() {
        netTotal = new SimpleDoubleProperty(getDebitTotal()- getCreditTotal());
        return netTotal.get();
    }

    public Integer getSize() {
        return transactionList.size();
    }

    public String getName() {
        return name.get();
    }

    public void setName(String newName) {
        name = new SimpleStringProperty(newName);
    }
}
