package io.github.dug22.jdataframe.operations.drop.impl;

import io.github.dug22.jdataframe.operations.drop.Drop;
import io.github.dug22.jdataframe.operations.drop.How;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DropImpl implements Drop {

    /**
     * Drops an entire column from a given data frame.
     *
     * @param dataFrameMap the dataframe containing the data.
     * @param column the column we want to drop.
     */
    @Override
    public void drop(Map<String, List<Object>> dataFrameMap, String column) {
        dataFrameMap.remove(column);
    }

    /**
     * Drops multiple columns from a given data frame.
     *
     * @param dataFrameMap the dataframe containing the data.
     * @param columns the columns we want to drop.
     */
    @Override
    public void drop(Map<String, List<Object>> dataFrameMap, List<String> columns) {
        columns.forEach(dataFrameMap::remove);
    }

    /**
     * Drops null values from a given column.
     *
     * @param dataFrameMap the dataframe containing the data.
     * @param column the column we want to remove null values from.
     * @param how Specifies how to handle null values:
     *                     - If 'ALL', drops rows where all elements are missing across all columns.
     *                     - If 'ANY', drops rows containing any null values in the specified columns will have those null values removed.
     */
    @Override
    public void dropNA(Map<String, List<Object>> dataFrameMap, String column, How how) {
        dropNA(dataFrameMap, Collections.singletonList(column), how);
    }

    /**
     * Drops null values from multiple columns.
     *
     * @param dataFrameMap the dataframe containing the data.
     * @param columns the columns we want to remove null value from.
     * @param how Specifies how to handle null values:
     *                     - If 'ALL', drops rows where all elements are missing across all columns.
     *                     - If 'ANY', drops rows containing any null values in the specified columns will have those null values removed.
     */
    @Override
    public void dropNA(Map<String, List<Object>> dataFrameMap, List<String> columns, How how) {
        switch (how) {
            case ANY -> {
                columns.stream().filter(dataFrameMap::containsKey).forEach(column -> {
                    List<Object> columnData = dataFrameMap.get(column);
                    if (columnData != null) {
                        columnData.removeIf(Objects::isNull);
                    }
                });
            }
            case ALL -> {
                int rowCount = dataFrameMap.values().stream().mapToInt(List::size).min().orElse(0);
                List<Integer> rowsToRemove = new ArrayList<>();

                for (int i = 0; i < rowCount; i++) {
                    final int row = i;
                    boolean allNull = columns.stream()
                            .filter(dataFrameMap::containsKey)
                            .allMatch(column -> {
                                List<Object> columnData = dataFrameMap.get(column);
                                return columnData == null || columnData.size() <= row || columnData.get(row) == null;
                            });
                    if (allNull) {
                        rowsToRemove.add(row);
                    }
                }

                for (int i = rowsToRemove.size() - 1; i >= 0; i--) {
                    int rowToRemove = rowsToRemove.get(i);
                    dataFrameMap.values().forEach(columnData -> {
                        if (columnData.size() > rowToRemove) {
                            columnData.remove(rowToRemove);
                        }
                    });
                }
            }
        }

        int minSize = dataFrameMap.values().stream()
                .mapToInt(List::size)
                .min()
                .orElse(0);

        dataFrameMap.values().forEach(columnData -> {
            if (columnData.size() > minSize) {
                columnData.subList(minSize, columnData.size()).clear();
            }
        });
    }
}