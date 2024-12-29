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
     *
     * @param dataFrameMap the dataframe containing the data.
     * @param column the column we want to remove null value from.
     * @param how Specifies how to handle null values:
     *                     - If 'ALL', drops rows where all elements are missing across all columns.
     *                     - If 'ANY', drops rows containing any null values in the specified columns will have those null values removed.
     */
    void dropNA(Map<String, List<Object>> dataFrameMap, String column, How how);

    /**
     * Drops null values from multiple columns.
     *
     * @param dataFrameMap the dataframe containing the data.
     * @param columns the columns we want to remove null value from.
     * @param how Specifies how to handle null values:
     *                     - If 'ALL', drops rows where all elements are missing across all columns.
     *                     - If 'ANY', drops rows containing any null values in the specified columns will have those null values removed.
     */
    void dropNA(Map<String, List<Object>> dataFrameMap, List<String> columns, How how);
}
