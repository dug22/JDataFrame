package io.github.dug22.jdataframe.operations.group.impl;

import io.github.dug22.jdataframe.operations.group.GroupBy;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupByImpl implements GroupBy {


    /**
     * Groups a DataFrame (map of columns) by a single column.
     *
     * @param dataFrameMap A map where each key is a column name and the value is a list of objects representing the column data.
     * @param column The name of the column to group by.
     * @return A map where each key is a distinct value from the grouping column, and the value is another map representing the columns with their respective values for that group.
     */
    @Override
    public Map<Object, Map<String, List<Object>>> groupBy(Map<String, List<Object>> dataFrameMap, String column) {
        return groupByInternal(dataFrameMap, Collections.singletonList(column));
    }

    /**
     * Groups a DataFrame (map of columns) by multiple columns.
     *
     * @param dataFrameMap A map where each key is a column name and the value is a list of objects representing the column data.
     * @param columns A list of column names to group by.
     * @return A map where each key is a distinct combination of values from the grouping columns, and the value is another map representing the columns with their respective values for that group.
     */
    @Override
    public Map<Object, Map<String, List<Object>>> groupBy(Map<String, List<Object>> dataFrameMap, List<String> columns) {
        return groupByInternal(dataFrameMap, columns);
    }

    /**
     * Internal method that handles the grouping logic for both single and multiple column groupings.
     *
     * @param dataFrameMap A map where each key is a column name and the value is a list of objects representing the column data.
     * @param columns A list of column names to group by.
     * @return A map where each key is a distinct combination of values from the grouping columns, and the value is another map representing the columns with their respective values for that group.
     */
    private Map<Object, Map<String, List<Object>>> groupByInternal(Map<String, List<Object>> dataFrameMap, List<String> columns) {
        int rowCount = dataFrameMap.values().stream().findFirst().orElse(Collections.emptyList()).size();

        Map<Object, List<Integer>> groupedIndexes = IntStream.range(0, rowCount).boxed().collect(Collectors.groupingBy(index -> columns.size() == 1 ? dataFrameMap.get(columns.getFirst()).get(index) : columns.stream().map(col -> dataFrameMap.get(col).get(index)).collect(Collectors.toList()), LinkedHashMap::new, Collectors.toList()));
        return groupedIndexes.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> {
                    Map<String, List<Object>> groupedData = new LinkedHashMap<>();
                    for (String column : columns) {
                        groupedData.put(column, entry.getValue().stream().map(index -> dataFrameMap.get(column).get(index)).collect(Collectors.toList()));
                    }
                    dataFrameMap.keySet().stream().filter(col -> !columns.contains(col)).forEach(col -> groupedData.put(col, entry.getValue().stream().map(index -> dataFrameMap.get(col).get(index)).collect(Collectors.toList())));
                    return groupedData;
                },
                (a, b) -> a, LinkedHashMap::new));
    }
}