# Fx Report Generator

## Installation
- Clone this repository, import as `maven` project if importing using IDE.
```
git clone https://github.com/prasadkothavale/fx-reporting-tool.git
```
- Install maven dependencies and build target jar
```
mvn install
```

## Execution
- Execute the `.jar` created in `target` folder. Specify settlement date as argument (data is prepared for dates between 2018-09-01 and 2018-09-08)
```
cd target
java -jar fxreport-0.0.1-SNAPSHOT.jar 2018-09-06
```
The report will be printed on console. In case report is not visible properly on console due to limited width use below command to save report in file
```
java -jar fxreport-0.0.1-SNAPSHOT.jar 2018-09-06 > report.txt
```
