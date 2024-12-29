package io.github.dug22.jdataframe.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RestructureUtils {

    /**
     * Restructure is designed to transform a Map of Lists into a lists of maps, where each map represents a row with column names as keys and values from the lists.
     * @param dataFrameMap dataframeMap the dataframe containing the data
     * @return a new restructured list of maps representing rows from the Map of Lists.
     */
    public static List<Map<String, Object>> restructure(Map<String, List<Object>> dataFrameMap){
        List<Map<String, Object>> rows = new ArrayList<>();
        for(int i = 0; i < dataFrameMap.values().iterator().next().size(); i++){
            Map<String, Object> row = new LinkedHashMap<>();
            for(Map.Entry<String, List<Object>> entry : dataFrameMap.entrySet()){
                row.put(entry.getKey(), entry.getValue().get(i));
            }

            rows.add(row);
        }

        return rows;
    }
}
