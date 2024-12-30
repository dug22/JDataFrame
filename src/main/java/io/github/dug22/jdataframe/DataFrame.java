package io.github.dug22.jdataframe;

import java.util.List;
import java.util.Map;

public interface DataFrame {

    /**
     * Displays the entire content of the DataFrame in a readable format.
     * This method should provide a summary or full view of the data.
     */
    void show();

    /**
     * Displays the first few rows of the DataFrame.
     * This method is typically used for previewing the data in a compact format.
     */
    void head();

    /**
     * Displays the first 'x' rows of the DataFrame.
     * This method allows the user to specify how many rows to display from the beginning.
     *
     * @param x the number of rows to display from the start of the DataFrame
     */
    void head(int x);

    /**
     * Displays the last few rows of the DataFrame.
     * This method is used for previewing the data from the end.
     */
    void tail();

    /**
     * Displays the last 'x' rows of the DataFrame.
     * This method allows the user to specify how many rows to display from the end.
     *
     * @param x the number of rows to display from the end of the DataFrame
     */
    void tail(int x);

    /**
     * Returns the number of rows in the DataFrame.
     * This method provides a way to determine the size of the DataFrame,
     * which is useful for understanding the dataset's dimensions.
     *
     * @return the number of rows in the DataFrame
     */
    int size();


    /**
     * Calculates a statistical value (e.g., sum, mean) for a given column.
     *
     * @param function the name of the statistical function to apply (e.g., "SUM", "MEAN")
     * @param column the column name on which the statistical function is applied
     * @return the result of the statistical calculation
     */
    double findStat(String function, String column);

    /**
     * Calculates a statistical value for multiple columns.
     *
     * @param function the name of the statistical function to apply
     * @param columns a list of column names for which to apply the statistical function
     * @return a list of results for each column
     */
    List<Double> findStat(String function, List<String> columns);

    /**
     * Generates summary statistics (sum, mean, mode, median, max, min, range, and std) for a numerical column in a DataFrame
     * @param column the name of the column to calculate the statistic for
     * @return the general summary statistics for a numerical column in a dataframe.
     */
    Map<String, Object> describeStats(String column);

    /**
     * Generates summary statistics (sum, mean, mode, median, max, min, range, and std) for multiple numerical columns in a DataFrame
     * @param columns the name of the columns to calculate the statistic for
     * @return the general summary statistics for multiple numerical columns in a dataframe.
     */
    Map<String, Map<String, Object>> describeStats(List<String> columns);

    /**
     * Exports a dataframe to a TXT file.
     * @param filePath the path of the destined TXT file.
     */
    void exportToTXT(String filePath);

    /**
     * Exports a dataframe to a CSV file
     * @param filePath the path of the destined CSV file.
     */
    void exportToCSV(String filePath);

    /**
     * Exports a dataframe to a JSON file
     * @param filePath the path of the destined JSON file.
     */
    void exportToJSON(String filePath);
}