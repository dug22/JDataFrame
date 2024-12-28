package io.github.dug22.jdataframe;

import io.github.dug22.jdataframe.operations.filter.FilterPredicate;
import io.github.dug22.jdataframe.operations.filter.impl.FilterImpl;
import io.github.dug22.jdataframe.operations.group.impl.GroupByImpl;
import io.github.dug22.jdataframe.util.CSVUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JDataFrameBuilder {

    private Map<String, List<Object>> dataFrameMap;

    public JDataFrameBuilder() {
        this.dataFrameMap = new LinkedHashMap<>();
    }

    // Method to load data from a CSV file
    public JDataFrameBuilder fromCSV(String filePath) throws IOException {
        CSVUtils.readFromCSV(filePath, dataFrameMap);
        return this;
    }

    // Method to set an initial data frame map (optional)
    public JDataFrameBuilder fromData(Map<String, List<Object>> data) {
        this.dataFrameMap = new LinkedHashMap<>(data);
        return this;
    }

    /**
     * Applies a filter condition on a specific column of the DataFrame.
     * The method filters the rows based on the given column and condition and
     * returns the updated builder instance to allow method chaining.
     *
     * @param column the name of the column on which the condition is applied
     * @param condition a predicate that defines the filter condition to be applied to the column
     * @return the updated builder instance with the filtered DataFrame
     */
    public JDataFrameBuilder filter(String column, FilterPredicate<?> condition) {
        this.dataFrameMap = new FilterImpl().filter(dataFrameMap, column, condition);
        return this;
    }

    /**
     * Applies a filter condition across multiple columns of the DataFrame.
     * The method filters the rows based on the given list of columns and condition,
     * then returns the updated builder instance to allow method chaining.
     *
     * @param columns a list of column names on which the condition is applied
     * @param condition a predicate that defines the filter condition to be applied to the columns
     * @return the updated builder instance with the filtered DataFrame
     */
    public JDataFrameBuilder filter(List<String> columns, FilterPredicate<?> condition) {
        this.dataFrameMap = new FilterImpl().filter(dataFrameMap, columns, condition);
        return this;
    }

    /**
     * Groups the DataFrame by a specified column.
     * This method aggregates rows by the unique values in the given column,
     * and restructures the DataFrame accordingly. It returns the updated builder instance
     * for method chaining.
     *
     * @param column the name of the column by which to group the DataFrame
     * @return the updated builder instance with the grouped DataFrame
     */
    public JDataFrameBuilder groupBy(String column) {
        Map<Object, Map<String, List<Object>>> groupedMap = new GroupByImpl().groupBy(dataFrameMap, column);
        this.dataFrameMap = convertGroupedMapToDataFrame(groupedMap);
        return this;
    }

    /**
     * Groups the DataFrame by a list of columns.
     * This method aggregates rows based on the unique combinations of values
     * in the specified columns, and restructures the DataFrame accordingly.
     * It returns the updated builder instance for method chaining.
     *
     * @param columns a list of column names by which to group the DataFrame
     * @return the updated builder instance with the grouped DataFrame
     */
    public JDataFrameBuilder groupBy(List<String> columns) {
        Map<Object, Map<String, List<Object>>> groupedMap = new GroupByImpl().groupBy(dataFrameMap, columns);
        this.dataFrameMap = convertGroupedMapToDataFrame(groupedMap);
        return this;
    }

    /**
     * Converts a grouped map structure back into a DataFrame format.
     * This method takes the result of a group-by operation (a nested map) and
     * flattens it into the standard DataFrame structure (a map of column names to lists of data).
     *
     * @param groupedMap the grouped map result from the group-by operation
     * @return a map representing the DataFrame with columns and their respective grouped data
     */
    private Map<String, List<Object>> convertGroupedMapToDataFrame(Map<Object, Map<String, List<Object>>> groupedMap) {
        Map<String, List<Object>> newDataFrameMap = new LinkedHashMap<>();
        // Iterate over the grouped map and flatten it back into a DataFrame structure
        for (Map.Entry<Object, Map<String, List<Object>>> entry : groupedMap.entrySet()) {
            for (Map.Entry<String, List<Object>> innerEntry : entry.getValue().entrySet()) {
                newDataFrameMap.computeIfAbsent(innerEntry.getKey(), k -> new ArrayList<>()).addAll(innerEntry.getValue());
            }
        }
        return newDataFrameMap;
    }


    /**
     * Builds the given dataframe
     * @return the built dataframe
     */
    public JDataFrame build() {
        return new JDataFrame(dataFrameMap);
    }
}

