JDataFrame
=======
![MIT](https://img.shields.io/badge/License-MIT-grey?style=flat-square&color=green)
![Stars](https://img.shields.io/github/stars/dug22/jdataframe.svg)
### Overview
<p align="justify">
JDataFrame short for Java DataFrame is a simple and easy-to-use dataframe library for Java. JDataFrame can be used to manipulate, filter, and extract statistics from specific columns within large tabular datasets, including both hard-coded data and CSV files. 
</p>

### Requirements
* You must have Java 21 installed.
* You must have Maven installed. JDataFrame uses Maven to build the project.

### Dependencies JDataFrame Uses
* JDataFrame v1.0.3+ uses the latest version of [Gson's](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.11.0) library.
* JDataFrame v1.0.4+ uses the latest version of [chart.js](https://github.com/chartjs/Chart.js)
  
### JDataFrame Features
**Data manipulation & transformation**
* Import data from hard-coded data and CSV files.
* Group, filter, drop, replace, and display results for one or multiple columns.
* Extract statistics from one or multiple columns.
  * Statistic functions supported: sum, mean, mode, median, min, max, and range.
* Export DataFrames to a TXT, CSV, or JSON file.

**Visualization**
JDataFrame supports basic data visualization by sending your preconfigured conditions in JSON format, with Chart.js handling the rest.

### Getting started:
If you're using Maven, add JDataFrame to your given project's pom.xml. Copy and paste the given dependency into your pom.xml.
~~~xml
<dependency>
  <groupId>io.github.dug22</groupId>
  <artifactId>jdataframe</artifactId>
  <version>LATEST</version>
</dependency>
~~~


### Documentation
[User guide](https://github.com/dug22/JDataFrame/blob/master/UserGuide.md): This guide contains examples on how to use JDataFrame.

### Support & Contributions
If you'd like to contribute to this repository, feel free to open a ticket with your suggestions, bug fixes, or enhancements [here](https://github.com/dug22/JDataFrame/issues). Contributions are always welcome! 

### License
JDataFrame is released under the [MIT license](https://github.com/dug22/JDataFrame/blob/master/LICENSE).

```
MIT License

Copyright (c) 2024 dug22

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files(the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
of the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies
or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```

### Currently Working On:
* More Documentation.
* Implement basic plotting concepts.
* Add more statistic functions. 
