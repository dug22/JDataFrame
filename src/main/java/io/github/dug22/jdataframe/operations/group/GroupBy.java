package io.github.dug22.jdataframe.operations.group;

import java.util.List;
import java.util.Map;

public interface GroupBy {

    /**
     * Groups a DataFrame (map of columns) by a single column.
     *
     * @param dataFrameMap A map where each key is a column name and the value is a list of objects representing the column data.
     * @param column The name of the column to group by.
     * @return A map where each key is a distinct value from the grouping column, and the value is another map representing the columns with their respective values for that group.
     */
    Map<Object, Map<String, List<Object>>> groupBy(Map<String, List<Object>> dataFrameMap, String column);


    /**
     * Groups a DataFrame (map of columns) by multiple columns.
     *
     * @param dataFrameMap A map where each key is a column name and the value is a list of objects representing the column data.
     * @param columns A list of column names to group by.
     * @return A map where each key is a distinct combination of values from the grouping columns, and the value is another map representing the columns with their respective values for that group.
     */
    Map<Object, Map<String, List<Object>>> groupBy(Map<String, List<Object>> dataFrameMap, List<String> columns);
}
