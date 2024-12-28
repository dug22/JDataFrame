package io.github.dug22.jdataframe;

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

}