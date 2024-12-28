package io.github.dug22.jdataframe.operations.statistics;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Statistics {

    /**
     * Finds the given sum of a column within the dataframe.
     */
    SUM {
        @Override
        public double calculate(Map<String, List<Object>> dataFrameMap, String column) {
            return dataFrameMap.get(column).stream().filter(value -> value instanceof Number).mapToDouble(value -> ((Number) value).doubleValue()).sum();
        }
    },

    /**
     * Finds the given mean of a column within the dataframe.
     */
    MEAN {
        @Override
        public double calculate(Map<String, List<Object>> dataFrameMap, String column) {
            return dataFrameMap.get(column).stream().filter(value -> value instanceof Number).mapToDouble(value -> ((Number) value).doubleValue()).average().orElse(0.0);
        }
    },

    /**
     * Finds the mode of a specified column within the dataframe.
     */
    MODE {
        @Override
        public double calculate(Map<String, List<Object>> dataFrameMap, String column) {
            return dataFrameMap.get(column)
                    .stream()
                    .filter(value -> value instanceof Number)
                    .mapToDouble(value -> ((Number) value).doubleValue())
                    .boxed()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() > 1)
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElseThrow(() -> new IllegalArgumentException("No mode was found for column: " + column));
        }
    },

    /**
     * Finds the given median of a specified column within the dataframe.
     */
    MEDIAN {
        @Override
        public double calculate(Map<String, List<Object>> dataFrameMap, String column) {
            List<Double> sortedNumbers = dataFrameMap.get(column).stream().filter(value -> value instanceof Number).map(value -> ((Number) value).doubleValue()).sorted().toList();
            int size = sortedNumbers.size();
            if (size == 0) {
                return 0.0;
            }

            return size % 2 == 0 ? (sortedNumbers.get(size / 2 - 1) + sortedNumbers.get(size / 2)) / 2.0 : sortedNumbers.get(size / 2);
        }
    },

    /**
     * Finds the maximum value of a given column within the dataframe.
     */
    MAX {
        @Override
        public double calculate(Map<String, List<Object>> dataFrameMap, String column) {
            OptionalDouble max = dataFrameMap.get(column).stream().filter(value -> value instanceof Number).mapToDouble(value -> ((Number) value).doubleValue()).max();
            return max.isPresent() ? max.getAsDouble() : 0.0;
        }

    },

    /**
     * Finds the minimum value of a given column within the dataframe.
     */
    MIN {
        @Override
        public double calculate(Map<String, List<Object>> dataFrameMap, String column) {
            OptionalDouble min = dataFrameMap.get(column).stream().filter(value -> value instanceof Number).mapToDouble(value -> ((Number) value).doubleValue()).min();
            return min.isPresent() ? min.getAsDouble() : 0.0;
        }
    },

    /**
     * Calculates the range (max - min) of a specified column within the dataframe
     */
    RANGE {
        @Override
        public double calculate(Map<String, List<Object>> dataFrameMap, String column) {
            return MAX.calculate(dataFrameMap, column) - MIN.calculate(dataFrameMap, column);
        }
    };


    /**
     * Abstract method for calculating specific statistical metrics.
     * @param dataFrameMap the dataframe containing the data
     * @param column the name of the column to calculate the statistic for
     * @return the calculated value for the specified statistic
     */
    public abstract double calculate(Map<String, List<Object>> dataFrameMap, String column);

    /**
     * Calculates a specific statistical metric for multiple columns in the dataframe.
     * This method applies the abstract `calculate` method to each column in the provided list.
     * @param dataFrameMap the dataframe containing the data
     * @param columns a list of column names for which the statistic is to be calculated
     * @return a list of calculated statistic values for each specified column
     */
    public List<Double> calculate(Map<String, List<Object>> dataFrameMap, List<String> columns) {
        return columns.stream().map(column -> calculate(dataFrameMap, column)).collect(Collectors.toList());
    }
}
