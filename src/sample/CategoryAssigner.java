package sample;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class CategoryAssigner {

    @FXML private Label idLabel;
    @FXML private ComboBox catDropdown;
    @FXML private TextField newCatTextField;
    @FXML private Button setButton;

    private Transaction editTransaction;
    private ObservableList<TransactionList> catList;
    private ObservableList<String> catLabels;
    private TableView transTable;
    private TableView catTable;

    public void setTransaction(Transaction t) {
        editTransaction = t;
        idLabel.setText(t.getID());
    }

    public void setCatList(ObservableList<TransactionList> cList, ObservableList<String> cLabels, TableView tTable, TableView cTable) {
        catDropdown.setItems(cLabels);
        catList = cList;
        catLabels = cLabels;
        transTable = tTable;
        catTable = cTable;

    }

    public void setCategory() {
        String newCat = newCatTextField.getText();
        String currentCat = editTransaction.getCategory();



        if (newCat.isBlank()) {
            newCat = (String) catDropdown.getSelectionModel().getSelectedItem();
        }
        for (TransactionList tl : catList) {
            if (tl.getName().equals(currentCat)) {
                tl.removeTransaction(editTransaction);
                break;
            }
        }
        boolean added = false;
        for (TransactionList tl : catList) {
            if (tl.getName().equals(newCat)) {
                tl.AddTransaction(editTransaction);
                added = true;
                break;
            }
        }
        if (!added) {
            TransactionList newList = new TransactionList(newCat);
            newList.AddTransaction(editTransaction);
            catList.add(newList);
            catLabels.add(newCat);
        }

        editTransaction.setCategory(newCat);
        transTable.refresh();
        catTable.refresh();
        Stage currentStage = (Stage) setButton.getScene().getWindow();
        currentStage.close();
    }
}
