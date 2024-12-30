package io.github.dug22.jdataframe.chart;

import io.github.dug22.jdataframe.chart.components.axis.Axis;
import io.github.dug22.jdataframe.chart.components.plugins.ChartPlugins;

import java.util.Map;

public class ChartOptions {

    private boolean responsive;
    private ChartPlugins plugins;
    private Map<String, Axis> scales;

    public boolean isResponsive() {
        return responsive;
    }

    public void setResponsive(boolean responsive) {
        this.responsive = responsive;
    }

    public ChartPlugins getPlugins() {
        return plugins;
    }

    public void setPlugins(ChartPlugins plugins) {
        this.plugins = plugins;
    }

    public Map<String, Axis> getScales() {
        return scales;
    }

    public void setScales(Map<String, Axis> scales) {
        this.scales = scales;
    }
}
