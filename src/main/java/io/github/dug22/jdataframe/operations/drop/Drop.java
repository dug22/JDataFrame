package io.github.dug22.jdataframe.operations.drop;

import java.util.List;
import java.util.Map;

public interface Drop {

    /**
     * Drops an entire column from a given data frame.
     * @param dataFrameMap the dataframe containing the data.
     * @param column the column we want to drop.
     */
    void drop(Map<String, List<Object>> dataFrameMap, String column);


    /**
     * Drops multiple columns from a given data frame.
     * @param dataFrameMap the dataframe containing the data.
     * @param columns the columns we want to drop.
     */
    void drop(Map<String, List<Object>> dataFrameMap, List<String> columns);

    /**
     * Drops null values from a given column.
     * @param dataFrameMap the dataframe containing the data.
     * @param column the column we want to remove null values from.
     * @param how if how = 'all' it drops the entire column, if 'any' it just removes that value from the column if any null values are spotted.
     */
    void dropNA(Map<String, List<Object>> dataFrameMap, String column, How how);

    /**
     * Drops null values from multiple columns.
     * @param dataFrameMap the dataframe containing the data.
     * @param columns the columns we want to remove null value from.
     * @param how if how = 'all' it drops the entire column, if 'any' it just removes that value from the column if any null values are spotted.
     */
    void dropNA(Map<String, List<Object>> dataFrameMap, List<String> columns, How how);
}
