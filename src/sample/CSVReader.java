package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {


    public ObservableList readStatement(String fName) {
        ObservableList tList = FXCollections.observableArrayList();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(fName));
            while ((line = br.readLine()) != null) {

                String[] rawTransaction = line.split(cvsSplitBy);
                Transaction newTransaction = new Transaction(
                        rawTransaction[0],
                        rawTransaction[1],
                        (rawTransaction[2].isBlank()) ? 0 : Double.valueOf(rawTransaction[2]),
                        (rawTransaction[3].isBlank()) ? 0 : Double.valueOf(rawTransaction[3]));

                tList.add(newTransaction);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return tList;
    }
}
