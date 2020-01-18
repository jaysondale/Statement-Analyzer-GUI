package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Transaction {
    private final SimpleStringProperty ID;
    private final SimpleStringProperty date;
    private final SimpleDoubleProperty debit;
    private final SimpleDoubleProperty credit;
    private final SimpleStringProperty category;

    public Transaction(String date, String ID, double debit, double credit) {
        this.ID = new SimpleStringProperty(ID);
        this.date = new SimpleStringProperty(date);
        this.debit = new SimpleDoubleProperty(debit);
        this.credit = new SimpleDoubleProperty(credit);
        this.category = new SimpleStringProperty("");
    }

    public String getID(){
        return ID.get();
    }

    public String getDate(){
        return date.get();
    }

    public Double getDebit(){
        return debit.get();
    }

    public Double getCredit(){
        return credit.get();
    }

    public String getCategory() { return category.get(); }

    public void setCategory(String cat) { category.set(cat); }
}
