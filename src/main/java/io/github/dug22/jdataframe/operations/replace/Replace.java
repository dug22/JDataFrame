package io.github.dug22.jdataframe.operations.replace;

import java.util.List;
import java.util.Map;

public interface Replace {

    /**
     * Updates the value of a specific row in a specified column with the provided new data value.
     *
     * @param dataFrameMap the dataframe containing the data.
     * @param column the column of the dataset to update.
     * @param index the row index within the column to be updated.
     * @param value the new value to replace the existing data at the specified row and column.
     * @throws NullPointerException if the specified column does not exist in the dataframe.
     * @throws IndexOutOfBoundsException if the specified index is out of bounds for the column.
     */
    void replace(Map<String, List<Object>> dataFrameMap, String column, int index, Object value);

    /**
     * Updates multiple values in a dataframe based on specified columns, row indexes, and new values.
     *
     * @param dataFrameMap the dataframe containing the data.
     * @param columns the list of column names where the updates should be applied.
     * @param indexes the list of row indexes corresponding to the updates.
     * @param values the list of new values to replace the existing data at the specified rows and columns.
     * @throws IllegalArgumentException if the sizes of the columns, indexes, and values lists do not match.
     * @throws NullPointerException if any specified column does not exist in the dataframe.
     * @throws IndexOutOfBoundsException if any specified index is out of bounds for its corresponding column.
     */
    void replace(Map<String, List<Object>> dataFrameMap, List<String> columns, List<Integer> indexes, List<Object> values);
}
