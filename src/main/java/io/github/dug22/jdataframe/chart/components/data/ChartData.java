package io.github.dug22.jdataframe.chart.components.data;

import java.util.List;

public class ChartData {

    private List<Object> labels;
    private List<Dataset> datasets;

    public List<Object> getLabels() {
        return labels;
    }

    public void setLabels(List<Object> labels) {
        this.labels = labels;
    }

    public List<Dataset> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<Dataset> datasets) {
        this.datasets = datasets;
    }
}
