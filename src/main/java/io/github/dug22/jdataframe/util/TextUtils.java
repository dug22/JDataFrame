package io.github.dug22.jdataframe.util;

import java.io.FileWriter;
import java.util.List;
import java.util.Map;

public class TextUtils {


    /**
     * Exports a given dataframe to a Text file.
     * @param filePath the path of the destined csv file.
     * @param dataFrameMap the dataframe containing the data
     */
    public static void writeToTXTFile(String filePath, Map<String, List<Object>> dataFrameMap) {
        if(!filePath.endsWith(".txt")){
            throw new RuntimeException("You need to save your file with a .txt extension.");
        }
        List<Map<String, Object>> restructuredData = RestructureUtils.restructure(dataFrameMap);
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write("-------\n");
            for (Map<String, Object> data : restructuredData) {
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    fileWriter.write(entry.getKey() + ":" + entry.getValue() + "\n");
                }
                fileWriter.write("-------\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
