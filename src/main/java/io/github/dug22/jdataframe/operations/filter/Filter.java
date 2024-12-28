package io.github.dug22.jdataframe.operations.filter;

import java.util.List;
import java.util.Map;

public interface Filter {

    /**
     * Filters a DataFrame (map of columns) based on a condition for a single column.
     *
     * @param dataFrameMap the dataframe containing the data
     * @param column The name of the column to filter by.
     * @param condition A predicate that defines the condition to apply to the rows.
     * @return A filtered map with the same structure but only containing rows that satisfy the condition.
     */
    Map<String, List<Object>> filter(Map<String, List<Object>> dataFrameMap, String column, FilterPredicate<?> condition);

    /**
     * Filters a DataFrame (map of columns) based on a condition for multiple columns.
     *
     * @param dataFrameMap the dataframe containing the data
     * @param columns A list of column names to filter by.
     * @param condition A predicate that defines the condition to apply to the rows.
     * @return A filtered map with the same structure but only containing rows that satisfy the condition.
     */
    Map<String, List<Object>> filter(Map<String, List<Object>> dataFrameMap, List<String> columns, FilterPredicate<?> condition);
}