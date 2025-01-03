package io.github.dug22.jdataframe.util;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class CSVUtils {

    /**
     * Reads the given data of a csv file
     *
     * @param filepath the path of the destined csv file.
     * @param dataframeMap the dataframe containing the data
     * @throws IOException if the file does not exist
     */
    public static void readFromCSV(String filepath, Map<String, List<Object>> dataframeMap) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                throw new IllegalArgumentException("That header does not exist!");
            }

            List<String> columns = Arrays.asList(headerLine.split(","));
            for (String column : columns) {
                dataframeMap.put(column, new ArrayList<>());
            }

            String line;
            while ((line = reader.readLine()) != null) {
                List<Object> values = Arrays.asList(line.split(","));
                int valuesLength = values.size();
                int columnSize = columns.size();
                if (valuesLength != columnSize) {
                    throw new IllegalArgumentException("Row length does not match column size!");
                }

                IntStream.range(0, columnSize).forEach(i -> dataframeMap.get(columns.get(i)).add(values.get(i)));
            }
        }
    }

    /**
     * Exports a given dataframe to a CSV file.
     * @param filePath the path of the destined csv file.
     * @param dataFrameMap the dataframe containing the data
     */
    public static void writeToCSV(String filePath, Map<String, List<Object>> dataFrameMap) {
        if(!filePath.endsWith(".txt")){
            throw new RuntimeException("You need to save your file with a .csv extension.");
        }
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            String headers = String.join(",", dataFrameMap.keySet());
            fileWriter.append(headers).append("\n");
            for (int i = 0; i < dataFrameMap.values().iterator().next().size(); i++) {
                for (String key : dataFrameMap.keySet()) {
                    fileWriter.append(String.valueOf(dataFrameMap.get(key).get(i))).append(",");
                }

                fileWriter.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
