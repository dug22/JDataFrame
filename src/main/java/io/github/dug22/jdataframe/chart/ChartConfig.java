package io.github.dug22.jdataframe.chart;

import io.github.dug22.jdataframe.chart.components.data.ChartData;

public class ChartConfig {

    private String type;
    private ChartData data;
    private ChartOptions options;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ChartData getData() {
        return data;
    }

    public void setData(ChartData data) {
        this.data = data;
    }

    public ChartOptions getOptions() {
        return options;
    }

    public void setOptions(ChartOptions options) {
        this.options = options;
    }
}
