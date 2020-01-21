package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;



import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Map;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private final String ID_MAP_JSON = "id_map.json";

    @FXML RadioMenuItem jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec;

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

    // Anchor pane
    @FXML private AnchorPane anchorPane;

    private ObservableList<TransactionList> catList;
    private TransactionList rawTransactions;
    private ObservableList<String> catLabels;
    private Map<String, String> idMap;
    private String month;


    private void loadStatement(String csvPath) throws UnsupportedEncodingException {
        // Load in id mapping data
        JSONReader jsonReader = new JSONReader();
        idMap = jsonReader.readIDMAp(URLDecoder.decode(getClass().getResource(ID_MAP_JSON).getPath(), java.nio.charset.StandardCharsets.UTF_8.toString()));

        CSVReader reader = new CSVReader();
        ObservableList<Transaction> newTransactions = reader.readStatement(URLDecoder.decode(csvPath, java.nio.charset.StandardCharsets.UTF_8.toString()));

        // Classify transactions from existing dataset
        for (Transaction t : newTransactions) {
            if (t.getDate().substring(0, 2).equals(month)) {
                if (idMap.containsKey(t.getID())) {
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
                rawTransactions.AddTransaction(t);
            }
        }

    }

    public void setMonth() {
        if (jan.isSelected()) {
            month = "01";
        } else if (feb.isSelected()) {
            month = "02";
        } else if (mar.isSelected()) {
            month = "03";
        } else if (apr.isSelected()) {
            month = "04";
        } else if (may.isSelected()) {
            month = "05";
        } else if (jun.isSelected()) {
            month = "06";
        } else if (jul.isSelected()) {
            month = "07";
        } else if (aug.isSelected()) {
            month = "08";
        } else if (sep.isSelected()) {
            month = "09";
        } else if (oct.isSelected()) {
            month = "10";
        } else if (nov.isSelected()) {
            month = "11";
        } else if (dec.isSelected()) {
            month = "12";
        }
    }

    public void setStatementDirectory() {
        DirectoryChooser dirChoose = new DirectoryChooser();
        dirChoose.setTitle("Select the directory where all statements are stored");
        File newDir = dirChoose.showDialog(anchorPane.getScene().getWindow());
        for (File file : newDir.listFiles()) {
            if (file.getName().contains(".csv")) {
                try {
                    loadStatement(file.getPath());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void assignCategory() throws Exception{
        Transaction selected = tTable.getSelectionModel().getSelectedItem();
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("categoryAssigner.fxml"));
        Region root = loader.load();

        CategoryAssigner catAssigner = loader.getController();
        catAssigner.setTransaction(selected);
        catAssigner.setCatList(catList, catLabels, tTable, catTable, idMap);

        Scene scene = new Scene(root);
        newStage.setScene(scene);

        newStage.show();

    }

    public void saveMap() {
        JSONReader json = new JSONReader();
        try {
            json.saveIDMap(URLDecoder.decode(getClass().getResource(ID_MAP_JSON).getPath(), java.nio.charset.StandardCharsets.UTF_8.toString()), idMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        catList = FXCollections.observableArrayList();
        catLabels = FXCollections.observableArrayList();
        rawTransactions = new TransactionList("rawTransactions");

        month = "01";

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
        tTable.setItems(rawTransactions.getTransactionList());

        // Assign catList to catTable
        catTable.setItems(catList);

    }
}
