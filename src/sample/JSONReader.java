package sample;

import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.google.gson.Gson;

public class JSONReader {

    public Map<String, String> readIDMAp(String fName) {
        Map<String, String> idMap = new HashMap<>();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(fName));
            JSONObject json = (JSONObject) obj;

            for (Map.Entry entry : (Set<Map.Entry>) json.entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();

                String keyString = String.valueOf(key);
                String valueString = String.valueOf(value);

                idMap.put(keyString, valueString);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return idMap;
    }

    public void saveIDMap(String fName, Map<String, String> idMap) throws IOException {
        Gson gson = new Gson();
        Type gsonType = new TypeToken<HashMap>(){}.getType();
        String gsonString = gson.toJson(idMap, gsonType);

        FileWriter fw = new FileWriter(fName);
        PrintWriter pw = new PrintWriter(fw);
        pw.print(gsonString);
        pw.close();
    }
}
