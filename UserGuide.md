# JDataFrame Userguide

1. [Getting Started with JDataFrame](#overview)
2. [Setup](#setup)
3. [Using JDataFrame](#using-jdataframe)
   * [Creating a Simple DataFrame](#creating-a-simple-dataframe)
   * [Creating a DataFrame From a CSV File](#creating-a-dataframe-from-a-csv-file)
   * [JDataFrame Filtering](#jdataframe-filtering) 
      * [Filtering a Column Within a DataFrame](#filtering-a-column-within-a-dataframe)
      * [Filtering Multiple Columns Within a DataFrame](#filtering-multiple-columns-within-a-dataframe)
   * [JDataFrame Dropping](#jdataframe-dropping)
      * [Dropping a Column From a DataFrame](#dropping-a-column-from-a-dataframe)
      * [Dropping Multiple Columns From a DataFrame](#dropping-multiple-columns-from-a-dataframe)
      * [Dropping Null Values From a DataFrame](#dropping-null-values-from-a-dataframe)
   * [Grouping Columns Within a DataFrame](#grouping-columns-within-a-dataframe)
   * [JDataFrame Replace](#jdataframe-replace)
     * [Replacing a Column's Value and Updating it](#replacing-a-column's-value-and-updating-it)
     * [Replacing Multiple Columns Values and Updating it](#replacing-multiple-columns-values-and-updating-it)
   * [Statistic Functions](#statistic-functions)
   * [Exporting a DataFrame to a Specific File](#exporting-a-dataframe-to-a-specific-file)
   * [Visualizing Data with JDataFrame](#visualizing-data-with-jdataframe)
4. [Reporting Bugs](#reporting-bugs)
5. [Contributions](#contributions)

## Overview

<p align="justify">
 JDataFrame short for Java DataFrame is a simple and easy-to-use dataframe library for Java. JDataFrame can be used to manipulate, filter, and extract statistics from specific columns within large tabular datasets, including both hard-coded data and CSV files. 
</p>

## Setup
First, JDataFrame requires Java 21 or newer.

Second, you need to add the following dependency to your pom.xml file. 

~~~xml
<dependency>
  <groupId>io.github.dug22</groupId>
  <artifactId>jdataframe</artifactId>
  <version>LATEST</version>
</dependency>
~~~

The setup is really that simple. Now its time to go over on how to use JDataFrame.

## Using JDataFrame

I've simplified the process of defining a dataframe by using a builder-style class, allowing you to customize your dataframe setup with options to filter, drop, or group specific data—all in one place.

## Creating a Simple Dataframe

It is really simple creating a basic DataFrame.

~~~java
//First define your data
 Map<String, List<Object>> dataframeMap = Map.of(
                "Weight", List.of(150, 200, 170, 195, 205, 185),
                "Age", List.of(28, 29, 24, 31, 26, 27),
                "Name", List.of("Abby", "Bob", "Carlos", "Daniel", "Evan", "Finn")
        );
//Creating the actual dataframe.
JDataFrame dataFrame = JDataFrame.builder().fromData(dataframeMap).build();

//Displaying the given dataframe:
dataFrame.show();
~~~

~~~markdown
Age, Weight, Name
28, 150, Abby
29, 200, Bob
24, 170, Carlos
31, 195, Daniel
26, 205, Evan
27, 185, Finn
~~~

## Creating a DataFrame From a CSV File

You can easily create a dataframe From a CSV File.

~~~java
//Define the filepath (Replace with the actual file path)
String filePath = "path/to/file/employee_data.csv";

//Create your dataframe object.
JDataFrame dataFrame = null;

  try {

    //Initialize dataframe within try and catch block
    dataFrame = JDataFrame.builder().fromCSV(filePath).build();

    //Display the first ten results (By default it is 5)
    dataFrame.head(10);

  } catch (IOException e) {
      e.printStackTrace();
  }
}
~~~

~~~markdown
Name, Age, Department, Salary, Joining Date
Alice, 25, HR, 50000, 2020-01-15
Bob, 30, Engineering, 70000, 2018-03-22
Charlie, 35, Marketing, 60000, 2019-07-10
David, 40, Finance, 80000, 2017-11-30
Eve, 28, HR, 52000, 2021-06-18
Frank, 32, Engineering, 75000, 2020-09-01
Grace, 27, Marketing, 55000, 2021-03-15
Hannah, 38, Finance, 85000, 2016-02-10
Ivy, 29, HR, 53000, 2022-05-20
Jack, 33, Engineering, 78000, 2019-12-01
~~~

## JDataFrame Filtering

JDataFrame's filter methods allows you to return a dataframe based on a condition applied to specific columns, resulting in a new dataframe that retains the same structure but only includes rows that meet the specified condition.

## Filtering a Column Within a DataFrame

Let's say we want to return a dataframe that displays people's weights that are greater than 180. We can simply do that using JDataFrame's filter method.

~~~java
//Defined data
Map<String, List<Object>> dataframeMap = Map.of(
                "Weight", List.of(150, 200, 170, 195, 205, 185),
                "Age", List.of(28, 29, 24, 31, 26, 27),
                "Name", List.of("Abby", "Bob", "Carlos", "Daniel", "Evan", "Finn")
        );
//Defined dataframe with the applied filter method
JDataFrame dataFrame = JDataFrame.builder().fromData(dataframeMap).filter("Weight", (FilterPredicate<Map<String, Object>>) row -> {
            Object weight = row.get("Weight");
            return weight instanceof Integer && (Integer) weight > 180;
        }).build();

//Displaying the given dataframe
dataframe.show();
~~~

~~~markdown
Age, Weight, Name
29, 200, Bob
31, 195, Daniel
26, 205, Evan
27, 185, Finn
~~~

## Filtering Multiple Columns Within a DataFrame

JDataFrame provides the ability to filter multiple columns simultaneously using various conditions. For example, you can create a dataframe that includes only people whose weights exceed 180 and whose ages are greater than 27, as shown below.

~~~java
//Defined data
Map<String, List<Object>> dataframeMap = Map.of(
                "Weight", List.of(150, 200, 170, 195, 205, 185),
                "Age", List.of(28, 29, 24, 31, 26, 27),
                "Name", List.of("Abby", "Bob", "Carlos", "Daniel", "Evan", "Finn")
        );

//Defined dataframe with the applied filter method
JDataFrame dataFrame = JDataFrame.builder().fromData(dataframeMap).filter(List.of("Weight", "Age"), (FilterPredicate<Map<String, Object>>) row -> {
            Object weight = row.get("Weight");
            Object age = row.get("Age");
            return weight instanceof Integer && (Integer) weight > 180 && age instanceof Integer && (Integer) age > 27;
        }).build();

//Displaying the given dataframe
dataframe.show();
~~~

~~~markdown
Age, Weight, Name
29, 200, Bob
31, 195, Daniel
~~~

## JDataFrame Dropping
JDataFrame's drop methods enable you to remove specific columns from a dataframe and provide the functionality to eliminate null values from a given column within a dataframe.

## Dropping a Column From a DataFrame
Let's say we want to drop a column that doesn't seem important to us at all, we can easily do that using JDataFrame's drop method.

~~~java
//Define the filepath (Replace with the actual file path)
String filePath = "path/to/file/employee_data.csv";

//Create your dataframe object.
JDataFrame dataFrame = null;

  try {

    //Initialize dataframe with applied drop function within try and catch block
    dataFrame = JDataFrame.builder().fromCSV(filePath).drop("Department").build();

    //Display the first ten results (By default it is 5)
    dataFrame.head(10);

  } catch (IOException e) {
      e.printStackTrace();
  }
}
~~~

~~~markdown
Name, Age, Salary, Joining Date
Alice, 25, 50000, 2020-01-15
Bob, 30, 70000, 2018-03-22
Charlie, 35, 60000, 2019-07-10
David, 40, 80000, 2017-11-30
Eve, 28, 52000, 2021-06-18
Frank, 32, 75000, 2020-09-01
Grace, 27, 55000, 2021-03-15
Hannah, 38, 85000, 2016-02-10
Ivy, 29, 53000, 2022-05-20
Jack, 33, 78000, 2019-12-01
~~~

## Dropping Multiple Columns From a DataFrame
We can even drop multiple columns too.

~~~java
//Define the filepath (Replace with the actual file path)
String filePath = "path/to/file/employee_data.csv";

//Create your dataframe object.
JDataFrame dataFrame = null;

  try {

    //Initialize dataframe with applied drop function within try and catch block
    dataFrame = JDataFrame.builder().fromCSV(filePath).drop(List.of("Age", "Department")).build();

    //Display the first ten results (By default it is 5)
    dataFrame.head(10);

  } catch (IOException e) {
      e.printStackTrace();
  }
}
~~~

~~~markdown
Name, Salary, Joining Date
Alice, 50000, 2020-01-15
Bob, 70000, 2018-03-22
Charlie, 60000, 2019-07-10
David, 80000, 2017-11-30
Eve, 52000, 2021-06-18
Frank, 75000, 2020-09-01
Grace, 55000, 2021-03-15
Hannah, 85000, 2016-02-10
Ivy, 53000, 2022-05-20
Jack, 78000, 2019-12-01
~~~

## Dropping Null Values From a DataFrame
JDataFrame also allows you to drop null values from a dataframe, depending on how the "how" parameter is defined. If how = 'all', it drops rows where all elements are missing across all columns.
If how = 'any', it drops rows containing any null values in the specified columns.

**Dropping Null Values From a DataFrame Using HOW.ALL**
~~~java
 // We must use mutable lists to add null values into our dataset.
        Map<String, List<Object>> dataframeMap = Map.of(
                "Weight", new ArrayList<>(Arrays.asList(220, null, 170, 195, 200, null)),
                "Age", new ArrayList<>(Arrays.asList(28, 33, 24, 31, 26, null)),
                "Name", new ArrayList<>(Arrays.asList("Ben", "Jenny", null, "Daniel", "Evan", null))
        );
//Defined dataframe with the applied dropNA method
JDataFrame dataFrame = JDataFrame.builder()
                .fromData(dataframeMap)
                .dropNA(Arrays.asList("Age", "Weight", "Name"), How.ALL)
                .build();
//Displaying the given dataframe
dataFrame.show();
~~~

~~~markdown
Name, Weight, Age
Ben, 220, 28
Jenny, null, 33
null, 170, 24
Daniel, 195, 31
Evan, 200, 26
~~~

**Dropping Null Values From a DataFrame Using HOW.ANY**
~~~java
 // We must use mutable lists to add null values into our dataset.
Map<String, List<Object>> dataframeMap = Map.of(
                "Weight", new ArrayList<>(Arrays.asList(220, null, 170, 195, 200, null)),
                "Age", new ArrayList<>(Arrays.asList(28, 33, 24, 31, 26, null)),
                "Name", new ArrayList<>(Arrays.asList("Ben", "Jenny", null, "Daniel", "Evan", null))
        );
//Defined dataframe with the applied dropNA method
JDataFrame dataFrame = JDataFrame.builder()
                .fromData(dataframeMap)
                .dropNA(Arrays.asList("Age", "Weight", "Name"), How.ANY)
                .build();
//Displaying the given dataframe
dataFrame.show();
~~~

~~~markdown
Weight, Name, Age
220, Ben, 28
170, Jenny, 33
195, Daniel, 24
200, Evan, 31
~~~

## Grouping Columns Within a DataFrame
Grouping organizes the DataFrame by a specified column or columns, enabling operations to be applied separately to each group

Here is an example of grouping a dataframe based on a single column with an applied filter chained to it.
~~~java
//Defined Data
Map<String, List<Object>> dataframeMap = new LinkedHashMap<>(Map.of(
                "Weight", List.of(125, 120, 130, 180, 150, 185),
                "Age", List.of(28, 33, 24, 31, 26, 32),
                "Name", List.of("Ben", "Jenny", "Mark", "Daniel", "Evan", "Jacob")
        ));
//Defined dataframe with the applied filter and groupBy method
JDataFrame dataFrame = JDataFrame.builder()
                .fromData(dataframeMap)
                .filter("Age", (FilterPredicate<Map<String, Object>>) row -> {
                    Object age = row.get("Age");
                    return age instanceof Integer && (Integer) age > 30;
                })
                .groupBy("Name")
                .build();

//Displaying the given dataframe
dataFrame.show();
~~~

~~~markdown
Name, Age, Weight
Jenny, 33, 120
Daniel, 31, 180
Jacob, 32, 185
~~~

## JDataFrame Replace
JDataFrame's replace function updates the value of specific rows in one or more specified columns with the provided index and new data. We need to specify the target column, its index, and the new data value when using the JDataFrame's replace function.

## Replacing a Column's Value and Updating it
Here's an example of replacing and updating data in a specific row of a column.
~~~
//Defined Data
Map<String, List<Object>> dataframeMap = Map.of(
                "Weight", new ArrayList<>(Arrays.asList(150, 200, 170, 195, 205, 185)),
                "Age", new ArrayList<>(Arrays.asList(28, 29, 24, 31, 26, 27)),
                "Name", new ArrayList<>(Arrays.asList("Abby", "Bob", "Carlos", "Daniel", "Evan", "Finn"))
        );
//Column
String column = "Age";
//Index
int index = 3;
//New row data
Object newValue = 26;

//Defined dataframe with the applied replace function
JDataFrame dataFrame = JDataFrame.builder().fromData(dataframeMap).replace(column, index, newValue).build();

//Displaying the given dataframe
dataFrame.show();
~~~

Above we are replacing Daniel's Age value of 24 with a new data value of 26. 

~~~markdown
Name, Age, Weight
Abby, 28, 150
Bob, 29, 200
Carlos, 24, 170
Daniel, 26, 195
Evan, 26, 205
Finn, 27, 185
~~~

It is really that simple. Now I'll go over how we can update multiple values in a dataframe based on specified columns, row indexes, and new values.

## Replacing Multiple Columns Values and Updating it

Here's an example of replacing and updating data in multiple specific rows of multiple columns column.
~~~java
//Defined Data
Map<String, List<Object>> dataframeMap = Map.of(
                "Weight", new ArrayList<>(Arrays.asList(150, 200, 170, 195, 205, 185)),
                "Age", new ArrayList<>(Arrays.asList(28, 29, 24, 31, 26, 27)),
                "Name", new ArrayList<>(Arrays.asList("Abby", "Bob", "Carlos", "Daniel", "Evan", "Finn"))
        );
//Columns
List<String> columns = new ArrayList<>(Arrays.asList("Age", "Weight"));
//Indexes
List<Integer> indexes = new ArrayList<>(Arrays.asList(0, 0));
//New row data
List<Object> newValues = new ArrayList<>(Arrays.asList(27, 135));

//Defined dataframe with the applied replace function
JDataFrame dataFrame = JDataFrame.builder().fromData(dataframeMap).replace(columns, indexes, newValues).build();

//Displaying the given dataframe
dataFrame.show();
~~~

Above we are replacing Abby's Age value of 28 with a new data value of 27. We are also replacing Abby's Weight value of 150 with a new data value of 135.

~~~markdown
Age, Weight, Name
27, 135, Abby
29, 200, Bob
24, 170, Carlos
31, 195, Daniel
26, 205, Evan
27, 185, Finn
~~~

Replacing really comes in handy if you want to update specifc column row data, and with JDataFrame its really that simple!

## Visualizing Data with JDataFrame

JDataFrame supports basic data visualization by sending your preconfigured conditions in JSON format, with Chart.js handling the rest. JDataFrame currently supports creating line, bar, radar, pie, and doughnut charts. Anytime you create a chart it will be saved as an HTML file, and from there you can open up that file and visualize what your charts look like. Below I will show you an example on how we can create a line, bar, radar, pie, and doughnut charts using JDataFrame.

**Creating a Line Chart**
~~~java
 public static void main(String[] args) throws Exception {
        // Adding data for Employees and their sales for 9 months
        Map<String, List<Object>> dataframeMap = Map.ofEntries(
                Map.entry("Employee", Arrays.asList("Employee A", "Employee B", "Employee C", "Employee D", "Employee E", "Employee F", "Employee G", "Employee H", "Employee I")),
                Map.entry("January", Arrays.asList(1200, 1100, 1300, 1400, 1250, 1050, 950, 1600, 1700)),
                Map.entry("February", Arrays.asList(1250, 1150, 1350, 1450, 1300, 1080, 980, 1650, 1750)),
                Map.entry("March", Arrays.asList(1300, 1200, 1400, 1500, 1350, 1100, 1000, 1700, 1800)),
                Map.entry("April", Arrays.asList(1350, 1250, 1450, 1550, 1400, 1120, 1020, 1750, 1850)),
                Map.entry("May", Arrays.asList(1400, 1300, 1500, 1600, 1450, 1140, 1040, 1800, 1900)),
                Map.entry("June", Arrays.asList(1450, 1350, 1550, 1650, 1500, 1160, 1060, 1850, 1950)),
                Map.entry("July", Arrays.asList(1500, 1400, 1600, 1700, 1550, 1180, 1080, 1900, 2000)),
                Map.entry("August", Arrays.asList(1550, 1450, 1650, 1750, 1600, 1200, 1100, 1950, 2050)),
                Map.entry("September", Arrays.asList(1600, 1500, 1700, 1800, 1650, 1220, 1120, 2000, 2100))
        );

        //Defined colors for our Y Data.
        List<String> colors = Arrays.asList(
                "rgba(75, 192, 92, 0.9)",  // Green for Employee A
                "rgba(255, 99, 132, 0.9)", // Red for Employee B
                "rgba(54, 162, 235, 0.9)", // Blue for Employee C
                "rgba(153, 102, 255, 0.9)", // Purple for Employee D
                "rgba(255, 159, 64, 0.9)", // Orange for Employee E
                "rgba(255, 205, 86, 0.9)", // Yellow for Employee F
                "rgba(75, 192, 192, 0.9)", // Teal for Employee G
                "rgba(255, 159, 164, 0.9)", // Pink for Employee H
                "rgba(153, 255, 51, 0.9)"); // Lime for Employee I

         //Line chart generator method
         ChartGenerator.generateLineChart(
                "chart.html", //Generated HTML File
                "Employee Sales Performance (January - September)", //chart title
                "right", //Legend position
                dataframeMap,  //Our given dataframe map (we do not need a dataframe object. No manipulation is taking place.)
                "Employee", //X Column
                Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September"), //Y Columns
                "Employees", //X-Axis Label
                "Sales ($)", //Y-Axis Label
                colors); //Predefined colors
    }
}
~~~

![Image](https://i.imgur.com/wEgKfhy.png)

**Creating a Bar Chart**
~~~java
public static void main(String[] args) throws Exception {
        // Adding data for Employees and their sales for 9 months
        Map<String, List<Object>> dataframeMap = Map.ofEntries(
                Map.entry("Employee", Arrays.asList("Employee A", "Employee B", "Employee C", "Employee D", "Employee E", "Employee F", "Employee G", "Employee H", "Employee I")),
                Map.entry("January", Arrays.asList(1200, 1100, 1300, 1400, 1250, 1050, 950, 1600, 1700)),
                Map.entry("February", Arrays.asList(1250, 1150, 1350, 1450, 1300, 1080, 980, 1650, 1750)),
                Map.entry("March", Arrays.asList(1300, 1200, 1400, 1500, 1350, 1100, 1000, 1700, 1800)),
                Map.entry("April", Arrays.asList(1350, 1250, 1450, 1550, 1400, 1120, 1020, 1750, 1850)),
                Map.entry("May", Arrays.asList(1400, 1300, 1500, 1600, 1450, 1140, 1040, 1800, 1900)),
                Map.entry("June", Arrays.asList(1450, 1350, 1550, 1650, 1500, 1160, 1060, 1850, 1950)),
                Map.entry("July", Arrays.asList(1500, 1400, 1600, 1700, 1550, 1180, 1080, 1900, 2000)),
                Map.entry("August", Arrays.asList(1550, 1450, 1650, 1750, 1600, 1200, 1100, 1950, 2050)),
                Map.entry("September", Arrays.asList(1600, 1500, 1700, 1800, 1650, 1220, 1120, 2000, 2100))
        );



        //Defined colors for our Y Data.
        List<String> colors = Arrays.asList(
                "rgba(75, 192, 92, 0.9)",  // Green for Employee A
                "rgba(255, 99, 132, 0.9)", // Red for Employee B
                "rgba(54, 162, 235, 0.9)", // Blue for Employee C
                "rgba(153, 102, 255, 0.9)", // Purple for Employee D
                "rgba(255, 159, 64, 0.9)", // Orange for Employee E
                "rgba(255, 205, 86, 0.9)", // Yellow for Employee F
                "rgba(75, 192, 192, 0.9)", // Teal for Employee G
                "rgba(255, 159, 164, 0.9)", // Pink for Employee H
                "rgba(153, 255, 51, 0.9)"); // Lime for Employee I
        //Bar chart generator method
        ChartGenerator.generateBarChart(
                "chart.html", //Generated HTML File
                "Employee Sales Performance (January - September)", //chart title
                "top", //Legend position
                dataframeMap,  //Our given dataframe map (we do not need a dataframe object. No manipulation is taking place.)
                "Employee", //X Column
                Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September"), //Y Columns
                "Employees", //X-Axis Label
                "Sales ($)", //Y-Axis Label
                colors); //Predefined colors
    }
}
~~~

![Image](https://i.imgur.com/cMi92jU.png) 

**Creating a Radar Chart**
~~~java
public static void main(String[] args) throws Exception {
        // Adding data for Employees and their sales for 9 months
        Map<String, List<Object>> dataframeMap = Map.ofEntries(
                Map.entry("Employee", Arrays.asList("Employee A", "Employee B", "Employee C", "Employee D", "Employee E", "Employee F", "Employee G", "Employee H", "Employee I")),
                Map.entry("January", Arrays.asList(1200, 1100, 1300, 1400, 1250, 1050, 950, 1600, 1700)),
                Map.entry("February", Arrays.asList(1250, 1150, 1350, 1450, 1300, 1080, 980, 1650, 1750)),
                Map.entry("March", Arrays.asList(1300, 1200, 1400, 1500, 1350, 1100, 1000, 1700, 1800)),
                Map.entry("April", Arrays.asList(1350, 1250, 1450, 1550, 1400, 1120, 1020, 1750, 1850)),
                Map.entry("May", Arrays.asList(1400, 1300, 1500, 1600, 1450, 1140, 1040, 1800, 1900)),
                Map.entry("June", Arrays.asList(1450, 1350, 1550, 1650, 1500, 1160, 1060, 1850, 1950)),
                Map.entry("July", Arrays.asList(1500, 1400, 1600, 1700, 1550, 1180, 1080, 1900, 2000)),
                Map.entry("August", Arrays.asList(1550, 1450, 1650, 1750, 1600, 1200, 1100, 1950, 2050)),
                Map.entry("September", Arrays.asList(1600, 1500, 1700, 1800, 1650, 1220, 1120, 2000, 2100))
        );



        //Defined colors for our Y Data.
        List<String> colors = Arrays.asList(
                "rgba(75, 192, 92, 0.9)",  // Green for Employee A
                "rgba(255, 99, 132, 0.9)", // Red for Employee B
                "rgba(54, 162, 235, 0.9)", // Blue for Employee C
                "rgba(153, 102, 255, 0.9)", // Purple for Employee D
                "rgba(255, 159, 64, 0.9)", // Orange for Employee E
                "rgba(255, 205, 86, 0.9)", // Yellow for Employee F
                "rgba(75, 192, 192, 0.9)", // Teal for Employee G
                "rgba(255, 159, 164, 0.9)", // Pink for Employee H
                "rgba(153, 255, 51, 0.9)"); // Lime for Employee I
        //Radar chart generator method
        ChartGenerator.generateRadarChart(
                "chart.html", //Generated HTML File
                "Employee Sales Performance (January - September)", //chart title
                "top", //Legend position
                dataframeMap,  //Our given dataframe map (we do not need a dataframe object. No manipulation is taking place.)
                "Employee", //X Column
                Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September"), //Y Columns
                "Employees", //X-Axis Label
                "Sales ($)", //Y-Axis Label
                colors); //Predefined colors
    }
}
~~~

![Image](https://i.imgur.com/UPWBZcg.png)

**Creating a Pie Chart**
~~~java
public static void main(String[] args) throws Exception {
        //Predefined data of favorite sports
        Map<String, List<Object>> dataFrameMap = Map.ofEntries(
                Map.entry("Category", Arrays.asList("Baseball", "Football", "Basketball", "Other")),
                Map.entry("Percentage", Arrays.asList(30, 40, 25, 5))
        );

        //Defined colors for our categories.
        List<String> colors = Arrays.asList(
                "rgba(75, 192, 92, 0.9)",
                "rgba(255, 99, 132, 0.9)",
                "rgba(54, 162, 235, 0.9)",
                "rgba(91, 100, 230, 0.9)"
        );

        //Pie chart generator.
        ChartGenerator.generatePieChart(
                "chart.html", // Generated HTML File
                "Favorite Sports", // Title
                "right", // Legend Position
                dataFrameMap, //Our given dataframe map (we do not need a dataframe object. No manipulation is taking place.)
                "Category", //X Column
                "Percentage", //Y Column
                colors // Predefined colors
        );
    }
}

~~~

![Image](https://i.imgur.com/v43q1eg.png)

**Creating a Doughnut Chart**
~~~java
public static void main(String[] args) throws Exception {
        //Predefined data of favorite sports
        Map<String, List<Object>> dataFrameMap = Map.ofEntries(
                Map.entry("Category", Arrays.asList("Baseball", "Football", "Basketball", "Other")),
                Map.entry("Percentage", Arrays.asList(30, 40, 25, 5))
        );

        //Defined colors for our categories.
        List<String> colors = Arrays.asList(
                "rgba(75, 192, 92, 0.9)",
                "rgba(255, 99, 132, 0.9)",
                "rgba(54, 162, 235, 0.9)",
                "rgba(91, 100, 230, 0.9)"
        );

        //Donught chart generator.
        ChartGenerator.generateDoughnutChart(
                "chart.html", // Generated HTML File
                "Favorite Sports", // Title
                "right", // Legend Position
                dataFrameMap, //Our given dataframe map (we do not need a dataframe object. No manipulation is taking place.)
                "Category", //X Column
                "Percentage", //Y Column
                colors // Predefined colors
        );
    }
}

~~~

![Image](https://i.imgur.com/OXbbsM3.png)
## Statistic Functions
JDataFrame provides a rich set of statistical functions to analyze DataFrames. 
  * Statistic functions supported: sum, mean, mode, median, min, max, and range. (More to come in newer updates)
    
Here are a couple examples on how we can use JDataFrame's statistic methods:

**Finding Statistic Answers for One Column**

~~~java
//Defined Data
 Map<String, List<Object>> dataframeMap = new LinkedHashMap<>(Map.of(
                "Weight", List.of(125, 120, 130, 180, 150, 185),
                "Age", List.of(28, 33, 24, 31, 26, 32),
                "Name", List.of("Ben", "Jenny", "Mark", "Daniel", "Evan", "Jacob")
        ));

//Defined Dataframe
JDataFrame dataFrame = JDataFrame.builder().fromData(dataframeMap).build();

//Results:
System.out.println("Data Frame Results:");
String resultsToString = "Mean Age=" + dataFrame.findStat("MEAN", "Age") + "\n"+
                "Maximum Age=" + dataFrame.findStat("MAX", "Age") + "\n"
                + "Minimum Age=" + dataFrame.findStat("MIN", "Age");
System.out.println(resultsToString);
~~~

~~~markdown
Data Frame Results:
Mean Age=29.0
Maximum Age=33.0
Minimum Age=24.0
~~~

**Finding Statistic Answers for Multiple Columns**

~~~java
//Defined Data
 Map<String, List<Object>> dataframeMap = new LinkedHashMap<>(Map.of(
                "Weight", List.of(125, 120, 130, 180, 150, 185),
                "Age", List.of(28, 33, 24, 31, 26, 32),
                "Name", List.of("Ben", "Jenny", "Mark", "Daniel", "Evan", "Jacob")
        ));
//Defined Dataframe
JDataFrame dataFrame = JDataFrame.builder().fromData(dataframeMap).build();

//Results
System.out.println("Data Frame Results:");
List<String> columnHeaders = List.of( "Age", "Weight");
String resultsToString = "Mean Age & Weight=" + dataFrame.findStat("MEAN", columnHeaders) + "\n"+
                "Maximum Age & Weight=" + dataFrame.findStat("MAX", columnHeaders) + "\n"
                + "Minimum Age & Weight=" + dataFrame.findStat("MIN", columnHeaders);
System.out.println(resultsToString);
~~~

~~~markdown
Data Frame Results:
Mean Age & Weight=[29.0, 148.33333333333334]
Maximum Age & Weight=[33.0, 185.0]
Minimum Age & Weight=[24.0, 120.0]
~~~

## Exporting a DataFrame to a Specific File

You can easily export your given dataframe to a Text, CSV, or JSON file. Remember, any data processing (filtering, grouping, dropping, etc) you applied to your given dataframe will be saved as how it was manipulated.

**Exporting to a Text File**
~~~java
//Defined Data
 Map<String, List<Object>> dataframeMap = new LinkedHashMap<>(Map.of(
                "Weight", List.of(125, 120, 130, 180, 150, 185),
                "Age", List.of(28, 33, 24, 31, 26, 32),
                "Name", List.of("Ben", "Jenny", "Mark", "Daniel", "Evan", "Jacob")));
//Defined DataFrame
JDataFrame dataFrame = JDataFrame.builder().fromData(dataframeMap).build();

//Exporting our dataframe to a text file
dataFrame.exportToTXT('myfile.txt');
~~~

Saved Text File:
~~~markdown
-------
Name:Ben
Age:28
Weight:125
-------
Name:Jenny
Age:33
Weight:120
-------
Name:Mark
Age:24
Weight:130
-------
Name:Daniel
Age:31
Weight:180
-------
Name:Evan
Age:26
Weight:150
-------
Name:Jacob
Age:32
Weight:185
-------
~~~

**Exporting to a CSV File**

~~~java
//Defined Data
 Map<String, List<Object>> dataframeMap = new LinkedHashMap<>(Map.of(
                "Weight", List.of(125, 120, 130, 180, 150, 185),
                "Age", List.of(28, 33, 24, 31, 26, 32),
                "Name", List.of("Ben", "Jenny", "Mark", "Daniel", "Evan", "Jacob")));
//Defined DataFrame
JDataFrame dataFrame = JDataFrame.builder().fromData(dataframeMap).build();

//Exporting our dataframe to a csv file
dataFrame.exportToCSV('myfile.csv');
~~~

Saved CSV File:
~~~markdown
Name,Age,Weight
Ben,28,125,
Jenny,33,120,
Mark,24,130,
Daniel,31,180,
Evan,26,150,
Jacob,32,185,
~~~

**Exporting to a JSON File**

~~~java
//Defined Data
 Map<String, List<Object>> dataframeMap = new LinkedHashMap<>(Map.of(
                "Weight", List.of(125, 120, 130, 180, 150, 185),
                "Age", List.of(28, 33, 24, 31, 26, 32),
                "Name", List.of("Ben", "Jenny", "Mark", "Daniel", "Evan", "Jacob")));
//Defined DataFrame
JDataFrame dataFrame = JDataFrame.builder().fromData(dataframeMap).build();

//Exporting our dataframe to a json file
dataFrame.exportToJSON('myfile.json');
~~~

Saved JSON File:
~~~markdown
[
  {
    "Weight": 125,
    "Name": "Ben",
    "Age": 28
  },
  {
    "Weight": 120,
    "Name": "Jenny",
    "Age": 33
  },
  {
    "Weight": 130,
    "Name": "Mark",
    "Age": 24
  },
  {
    "Weight": 180,
    "Name": "Daniel",
    "Age": 31
  },
  {
    "Weight": 150,
    "Name": "Evan",
    "Age": 26
  },
  {
    "Weight": 185,
    "Name": "Jacob",
    "Age": 32
  }
]
~~~


## Reporting Bugs
If you happen to find a bug using JDataFrame please report them [here](https://github.com/dug22/JDataFrame/issues).

## Contributions
I intend to continuously enhance JDataFrame by incorporating new features, including:

- Enabling users to export data in CSV, JSON, and TXT files.
- Expanding the range of statistical functions available.
- Potentially introducing basic plotting capabilities.
  
If you have any ideas of your own please share them by posting them [here](https://github.com/dug22/JDataFrame/issues). Any contributions to this project are always welcomed!
Be aware to check recent issues or pull requests to see if a given feature was added or not. 
