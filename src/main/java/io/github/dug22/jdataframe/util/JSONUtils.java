package io.github.dug22.jdataframe.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class JSONUtils {

    private static final Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();


    /**
     * Exports a given dataframe to a JSON file.
     * @param filePath the path of the destined csv file.
     * @param dataFrameMap the dataframe containing the data
     */
    public static void writeToJSONFile(String filePath, Map<String, List<Object>> dataFrameMap){
        if(!filePath.endsWith(".json")){
            throw new RuntimeException("You need to save your file with a .json extension.");
        }
        List<Map<String, Object>> restructuredData = RestructureUtils.restructure(dataFrameMap);
        String jsonData = gson.toJson(restructuredData);
        try (FileWriter fileWriter = new FileWriter(filePath)){
            fileWriter.write(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
