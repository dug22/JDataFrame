package io.github.dug22.jdataframe.operations.filter.impl;

import io.github.dug22.jdataframe.operations.filter.Filter;
import io.github.dug22.jdataframe.operations.filter.FilterPredicate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FilterImpl implements Filter {

    /**
     * Filters a DataFrame (map of columns) based on a condition for a single column.
     *
     * @param dataFrameMap A map where each key is a column name and the value is a list of objects representing the column data.
     * @param column The name of the column to filter by.
     * @param condition A predicate that defines the condition to apply to the rows.
     * @return A filtered map with the same structure but only containing rows that satisfy the condition.
     */
    @Override
    public Map<String, List<Object>> filter(Map<String, List<Object>> dataFrameMap, String column, FilterPredicate<?> condition) {
        return filterOperation(dataFrameMap, Collections.singletonList(column), condition);
    }


    /**
     * Filters a DataFrame (map of columns) based on a condition for multiple columns.
     *
     * @param dataFrameMap A map where each key is a column name and the value is a list of objects representing the column data.
     * @param columns A list of column names to filter by.
     * @param condition A predicate that defines the condition to apply to the rows.
     * @return A filtered map with the same structure but only containing rows that satisfy the condition.
     */
    @Override
    public Map<String, List<Object>> filter(Map<String, List<Object>> dataFrameMap, List<String> columns, FilterPredicate<?> condition) {
        return filterOperation(dataFrameMap, columns, condition);
    }


    /**
     * Performs the filtering operation by checking each row in the DataFrame against the provided condition.
     *
     * @param dataFrameMap A map where each key is a column name and the value is a list of objects representing the column data.
     * @param columns A list of column names to filter by.
     * @param condition A predicate that defines the condition to apply to the rows.
     * @param <T> Passed predicate element.
     * @return A filtered map with the same structure but only containing rows that satisfy the condition.
     */
    private <T> Map<String, List<Object>> filterOperation(Map<String, List<Object>> dataFrameMap, List<String> columns, FilterPredicate<T> condition) {
        int numberOfRows = dataFrameMap.get(columns.getFirst()).size();
        List<Integer> matchingIndexes = IntStream.range(0, numberOfRows).filter(index -> {
                    Map<String, Object> rowData = columns.stream().collect(Collectors.toMap(col -> col, col -> dataFrameMap.get(col).get(index)));
                    return condition.test((T) rowData);
                })
                .boxed()
                .toList();

        return dataFrameMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> matchingIndexes.stream().map(entry.getValue()::get).collect(Collectors.toList())));
    }
}