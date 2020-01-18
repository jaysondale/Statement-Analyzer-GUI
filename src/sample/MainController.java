package sample;

import com.sun.jdi.connect.TransportTimeoutException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

    public ObservableList<TransactionList> catList = FXCollections.observableArrayList();


    public void updateTransactions() {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load in data
        URL csvFile = getClass().getResource("statement.csv");

        CSVReader reader = new CSVReader();
        ObservableList<Transaction> rawTransactions = reader.readStatement("/Users/jaysondale/Google Drive/AI/Statement-Analyzer-GUI/src/sample/statement.csv");

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
