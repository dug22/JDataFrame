package io.github.dug22.jdataframe.chart;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.dug22.jdataframe.chart.components.axis.Axis;
import io.github.dug22.jdataframe.chart.components.axis.AxisTitle;
import io.github.dug22.jdataframe.chart.components.data.ChartData;
import io.github.dug22.jdataframe.chart.components.data.Dataset;
import io.github.dug22.jdataframe.chart.components.plugins.ChartPlugins;
import io.github.dug22.jdataframe.chart.components.plugins.PluginLegend;
import io.github.dug22.jdataframe.chart.components.plugins.PluginTitle;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class ChartGenerator {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Generates a chart and writes it to an HTML file.
     *
     * @param filePath The file path where the HTML will be saved.
     * @param chartType The type of chart (e.g., "bar", "line").
     * @param title The chart's title.
     * @param dataFrame The data for the chart (column -> values map).
     * @param xColumn  The column used for the x-axis labels.
     * @param yColumns The list of columns used for the y-axis datasets.
     * @param xAxisLabel The label for the x-axis.
     * @param yAxisLabel The label for the y-axis.
     * @param colors A list of color strings for the datasets.
     * @throws Exception If an error occurs during file writing.
     */
    public static void generateChart(
            String filePath,
            String chartType,
            String title,
            Map<String, List<Object>> dataFrame,
            String xColumn,
            List<String> yColumns,
            String xAxisLabel,
            String yAxisLabel,
            List<String> colors) throws Exception {

        ChartConfig chartConfig = buildChartConfig(chartType, title, dataFrame, xColumn, yColumns, xAxisLabel, yAxisLabel, colors);

        String chartConfigJson = gson.toJson(chartConfig);

        String htmlTemplate = """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Chart</title>
                    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                </head>
                <body>
                    <canvas id="myChart" width="800" height="600"></canvas>
                    <script>
                        const ctx = document.getElementById('myChart').getContext('2d');
                        const chartConfig = {{CHART_CONFIG}};
                        new Chart(ctx, chartConfig);
                    </script>
                </body>
                </html>
                """;

        String finalHtml = htmlTemplate.replace("{{CHART_CONFIG}}", chartConfigJson);
        Files.write(Path.of(filePath), finalHtml.getBytes());
    }

    private static ChartConfig buildChartConfig(
            String chartType,
            String title,
            Map<String, List<Object>> dataFrame,
            String xColumn,
            List<String> yColumns,
            String xAxisLabel,
            String yAxisLabel,
            List<String> colors) {

        List<Object> xData = dataFrame.getOrDefault(xColumn, Collections.emptyList());
        if (xData.isEmpty()) {
            throw new IllegalArgumentException("xColumn data is empty or not found.");
        }

        List<Dataset> datasets = new ArrayList<>();
        int colorIndex = 0;

        for (String yColumn : yColumns) {
            List<Object> yData = dataFrame.getOrDefault(yColumn, Collections.emptyList());
            if (yData.isEmpty() || yData.stream().anyMatch(value -> !(value instanceof Number))) {
                continue;
            }

            Dataset dataset = new Dataset();
            dataset.setLabel(yColumn);
            dataset.setData(yData.stream().map(v -> ((Number) v).doubleValue()).collect(Collectors.toList()));
            String backgroundColor = (colorIndex < colors.size()) ? colors.get(colorIndex) : generateRandomColor();
            String borderColor = (colorIndex < colors.size()) ? colors.get(colorIndex) : generateRandomColor();

            dataset.setBackgroundColor(backgroundColor);
            dataset.setBorderColor(borderColor);
            dataset.setBorderWidth(1);

            datasets.add(dataset);
            colorIndex++;
        }

        ChartOptions options = new ChartOptions();
        options.setResponsive(true);

        PluginTitle pluginTitle = new PluginTitle();
        pluginTitle.setDisplay(true);
        pluginTitle.setText(title);

        PluginLegend pluginLegend = new PluginLegend();
        pluginLegend.setDisplay(true);
        pluginLegend.setPosition("top");

        ChartPlugins plugins = new ChartPlugins();
        plugins.setTitle(pluginTitle);
        plugins.setLegend(pluginLegend);
        options.setPlugins(plugins);

        Axis xAxis = new Axis();
        xAxis.setTitle(new AxisTitle());
        xAxis.getTitle().setDisplay(true);
        xAxis.getTitle().setText(xAxisLabel);

        Axis yAxis = new Axis();
        yAxis.setTitle(new AxisTitle());
        yAxis.getTitle().setDisplay(true);
        yAxis.getTitle().setText(yAxisLabel);

        options.setScales(Map.of("x", xAxis, "y", yAxis));
        ChartData chartData = new ChartData();
        chartData.setLabels(xData);
        chartData.setDatasets(datasets);
        ChartConfig chartConfig = new ChartConfig();
        chartConfig.setType(chartType);
        chartConfig.setData(chartData);
        chartConfig.setOptions(options);

        return chartConfig;
    }

    private static String generateRandomColor() {
        Random rand = new Random();
        return String.format("rgba(%d, %d, %d, 0.2)", rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }
}
