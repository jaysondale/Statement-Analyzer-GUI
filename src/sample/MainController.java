package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    // Transaction Table
    @FXML private TableView<Transaction> tTable;
    @FXML private TableColumn<Transaction, String> dateCol;
    @FXML private TableColumn<Transaction, String> idCol;
    @FXML private TableColumn<Transaction, Double> debitCol;
    @FXML private TableColumn<Transaction, Double> creditCol;
    @FXML private TableColumn<Transaction, String> transCatCol;

    // Category Table
    @FXML private TableView<TransactionList> catTable;
    @FXML private TableColumn<TransactionList, String> catCol;
    @FXML private TableColumn<TransactionList, Double> debBalCol;
    @FXML private TableColumn<TransactionList, Double> credBalCol;
    @FXML private TableColumn<TransactionList, Double> netBalCol;

    private ObservableList<TransactionList> catList;
    private ObservableList<String> catLabels;


    public void updateTransactions() {

    }

    public void assignCategory() throws Exception{
        Transaction selected = tTable.getSelectionModel().getSelectedItem();
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("categoryAssigner.fxml"));
        Region root = loader.load();

        CategoryAssigner catAssigner = loader.getController();
        catAssigner.setTransaction(selected);
        catAssigner.setCatList(catList, catLabels, tTable, catTable);

        Scene scene = new Scene(root);
        newStage.setScene(scene);

        newStage.show();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        catList = FXCollections.observableArrayList();
        catLabels = FXCollections.observableArrayList();

        // Load in statement data
        URL csvFile = getClass().getResource("statement.csv");

        // Load in id mapping data
        JSONReader jsonReader = new JSONReader();
        Map<String, String> idMap = jsonReader.readIDMAp("/Users/jaysondale/Google Drive/AI/Statement-Analyzer-GUI/src/sample/id_map.json");

        CSVReader reader = new CSVReader();
        ObservableList<Transaction> rawTransactions = reader.readStatement("/Users/jaysondale/Google Drive/AI/Statement-Analyzer-GUI/src/sample/statement.csv");

        // Classify transactions from existing dataset
        for (Transaction t : rawTransactions) {
            if (idMap.containsKey(t.getID())){
                // Set category in transaction object
                String cat = idMap.get(t.getID());
                t.setCategory(cat);

                // Add transaction to category's transaction list
                if (catLabels.contains(cat)) {
                    for (TransactionList tl : catList) {
                        if (tl.getName().equals(cat)) {
                            tl.AddTransaction(t);
                            break;
                        }
                    }
                } else {
                    TransactionList newList = new TransactionList(cat);
                    newList.AddTransaction(t);
                    catList.add(newList);
                    catLabels.add(cat);
                }
            }
        }

        // Prep transaction table columns
        dateCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("date"));
        idCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("ID"));
        debitCol.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("debit"));
        creditCol.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("credit"));
        transCatCol.setCellValueFactory(new PropertyValueFactory<Transaction, String>("category"));

        // Prep category table columns
        catCol.setCellValueFactory(new PropertyValueFactory<TransactionList, String>("name"));
        debBalCol.setCellValueFactory(new PropertyValueFactory<TransactionList, Double>("debitTotal"));
        credBalCol.setCellValueFactory(new PropertyValueFactory<TransactionList, Double>("creditTotal"));
        netBalCol.setCellValueFactory(new PropertyValueFactory<TransactionList, Double>("netTotal"));

        // Assign rawTransactions to tTable
        tTable.setItems(rawTransactions);

        // Assign catList to catTable
        catTable.setItems(catList);

    }
}
