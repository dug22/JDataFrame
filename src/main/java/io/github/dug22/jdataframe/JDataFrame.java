package io.github.dug22.jdataframe;

import io.github.dug22.jdataframe.operations.statistics.Statistics;
import io.github.dug22.jdataframe.util.CSVUtils;
import io.github.dug22.jdataframe.util.JSONUtils;
import io.github.dug22.jdataframe.util.TextUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class JDataFrame implements DataFrame {

    private final Map<String, List<Object>> dataFrameMap;

    /**
     * Constructor to create a DataFrame by reading data from a CSV file.
     *
     * @param filePath the path to the CSV file to read
     * @throws IOException if an error occurs while reading the file
     */
    public JDataFrame(String filePath) throws IOException {
        this.dataFrameMap = new LinkedHashMap<>();
        CSVUtils.readFromCSV(filePath, dataFrameMap);
    }

    /**
     * Constructor to create a DataFrame from a map of column names and their corresponding data.
     *
     * @param data a map where the key is the column name and the value is a list of data for that column
     */
    public JDataFrame(Map<String, List<Object>> data) {
        this.dataFrameMap = new LinkedHashMap<>(data);
    }

    /**
     * Static method to get a builder instance for constructing a DataFrame.
     *
     * @return a new instance of the DataFrame builder
     */
    public static JDataFrameBuilder builder() {
        return new JDataFrameBuilder();
    }

    /**
     * Displays all rows in the DataFrame.
     * This method is intended for showing the entire dataset.
     */
    @Override
    public void show() {
        displayRows(0, size());
    }

    /**
     * Displays the first 5 rows of the DataFrame.
     */
    @Override
    public void head() {
        displayRows(0, 5);
    }

    /**
     * Displays the first 'x' rows of the DataFrame.
     *
     * @param x the number of rows to display from the start of the DataFrame
     */
    @Override
    public void head(int x) {
        displayRows(0, x);
    }

    /**
     * Displays the last 5 rows of the DataFrame.
     */
    @Override
    public void tail() {
        int size = size();
        displayRows(size - 5, size);
    }

    /**
     * Displays the last 'x' rows of the DataFrame.
     *
     * @param x the number of rows to display from the end of the DataFrame
     */
    @Override
    public void tail(int x) {
        int size = size();
        displayRows(size - x, size);
    }

    /**
     * Returns the number of rows in the DataFrame.
     *
     * @return the number of rows, or 0 if the DataFrame is empty
     */
    @Override
    public int size() {
        if (dataFrameMap.isEmpty()) {
            return 0;
        }
        return dataFrameMap.values().iterator().next().size();
    }

    /**
     * Calculates a statistical value (e.g., sum, mean) for a given column.
     *
     * @param function the name of the statistical function to apply (e.g., "SUM", "MEAN")
     * @param column the column name on which the statistical function is applied
     * @return the result of the statistical calculation
     */
    @Override
    public double findStat(String function, String column) {
        return Statistics.valueOf(function).calculate(dataFrameMap, column);
    }

    /**
     * Calculates a statistical value for multiple columns.
     *
     * @param function the name of the statistical function to apply
     * @param columns a list of column names for which to apply the statistical function
     * @return a list of results for each column
     */
    @Override
    public List<Double> findStat(String function, List<String> columns) {
        return Statistics.valueOf(function).calculate(dataFrameMap, columns);
    }

    /**
     * Exports a dataframe to a TXT file.
     * @param filePath the path of the destined TXT file.
     */
    @Override
    public void exportToTXT(String filePath){
        TextUtils.writeToTXTFile(filePath, dataFrameMap);
    }

    /**
     * Exports a dataframe to a CSV file
     * @param filePath the path of the destined CSV file.
     */
    @Override
    public void exportToCSV(String filePath){
        CSVUtils.writeToCSV(filePath, dataFrameMap);
    }

    /**
     * Exports a dataframe to a JSON file
     * @param filePath the path of the destined JSON file.
     */
    @Override
    public void exportToJSON(String filePath){
        JSONUtils.writeToJSONFile(filePath, dataFrameMap);
    }
    /**
     * A private helper method that displays a range of rows from the DataFrame.
     *
     * @param start the starting index of the rows to display
     * @param end the ending index (exclusive) of the rows to display
     */
    private void displayRows(int start, int end) {
        List<String> columns = new ArrayList<>(dataFrameMap.keySet());
        int size = size();

        if (columns.isEmpty()) {
            System.out.println("No columns available.");
            return;
        }

        if (size == 0) {
            System.out.println("No data rows available.");
            return;
        }

        System.out.println(String.join(", ", columns));

        for (int i = Math.max(start, 0); i < end && i < size; i++) {
            int index = i;
            List<Object> row = columns.stream()
                    .map(col -> dataFrameMap.get(col).get(index))
                    .toList();
            System.out.println(row.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ")));
        }
    }
}