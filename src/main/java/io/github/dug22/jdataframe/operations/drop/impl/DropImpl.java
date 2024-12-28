package io.github.dug22.jdataframe.operations.drop.impl;

import io.github.dug22.jdataframe.operations.drop.Drop;
import io.github.dug22.jdataframe.operations.drop.How;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DropImpl implements Drop {

    /**
     * Drops an entire column from a given data frame.
     * @param dataFrameMap the dataframe containing the data.
     * @param column the column we want to drop.
     */
    @Override
    public void drop(Map<String, List<Object>> dataFrameMap, String column) {
        dataFrameMap.remove(column);
    }

    /**
     * Drops multiple columns from a given data frame.
     * @param dataFrameMap the dataframe containing the data.
     * @param columns the columns we want to drop.
     */
    @Override
    public void drop(Map<String, List<Object>> dataFrameMap, List<String> columns) {
       columns.forEach(dataFrameMap::remove);
    }

    /**
     * Drops null values from a given column.
     * @param dataFrameMap the dataframe containing the data.
     * @param column the column we want to remove null values from.
     * @param how if how = 'all' it drops the entire column, if 'any' it just removes that value from the column if any null values are spotted.
     */
    @Override
    public void dropNA(Map<String, List<Object>> dataFrameMap, String column, How how) {
        dropNA(dataFrameMap, Collections.singletonList(column), how);
    }

    /**
     * Drops null values from multiple columns.
     * @param dataFrameMap the dataframe containing the data.
     * @param columns the columns we want to remove null value from.
     * @param how if how = 'all' it drops the entire column, if 'any' it just removes that value from the column if any null values are spotted.
     */
    @Override
    public void dropNA(Map<String, List<Object>> dataFrameMap, List<String> columns, How how) {
        columns.stream().filter(dataFrameMap::containsKey).forEach(column -> {
            List<Object> columnData = dataFrameMap.get(column);
            if (columnData != null) {
                switch (how) {
                    case ANY -> columnData.removeIf(Objects::isNull);
                    case ALL -> {
                        if (columnData.stream().allMatch(Objects::isNull)) {
                            drop(dataFrameMap, column);
                        }
                    }
                }
            }
        });
    }
}
