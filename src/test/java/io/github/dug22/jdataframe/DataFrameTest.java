package io.github.dug22.jdataframe;

import io.github.dug22.jdataframe.operations.filter.FilterPredicate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

class DataFrameTest {

    //JDataFrameTest
    public static void main(String[] args) {
        System.out.println("JDataFrame Example #1");
        System.out.println("-".repeat(100));
        Map<String, List<Object>> dataframeMap = Map.of(
                "Weight", List.of(150, 200, 170, 195, 205, 185),
                "Age", List.of(28, 29, 24, 31, 26, 27),
                "Name", List.of("Abby", "Bob", "Carlos", "Daniel", "Evan", "Finn")
        );

        JDataFrame dataFrame = JDataFrame.builder().fromData(dataframeMap).filter("Weight", (FilterPredicate<Map<String, Object>>) row -> {
            Object weight = row.get("Weight");
            return weight instanceof Integer && (Integer) weight > 180;
        }).groupBy("Name").build();

        System.out.println("Dataframe Representation:");
        dataFrame.show();
        System.out.println("The max weight from this dataframe is :" + dataFrame.findStat("MAX", "Weight"));
        System.out.println("The mean weight from this data frame is: " + dataFrame.findStat("MEAN", "Weight"));
        System.out.println("-".repeat(100));
        System.out.println("JDataFrame Example #2");
        System.out.println("-".repeat(100));
        try {

            JDataFrame employeeData = JDataFrame.builder().fromCSV("D:\\JDataFrame\\src\\main\\resources\\employee_data.csv").filter(List.of("Age", "Department"), (FilterPredicate<Map<String, Object>>) row -> {
                String ageString = (String) row.get("Age");
                int age = Integer.parseInt(ageString);
                String department = (String) row.get("Department");
                return (age > 30 && (department.equals("HR") || department.equals("Engineering")));
            }).groupBy("Name").build();
            employeeData.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("-".repeat(100));
    }
}