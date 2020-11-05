# Service for parsing orders from file.
Library name: orders-parser

Today implemented only CSV and JSON parsers.
Parsers can parse orders from file by line and output result to console.
System events output to log file.
Simple implementation.

## Example
java -jar orders-parser-1.0.0.jar order_20201105.csv orders_20201105.json

## Build
mvn clean install
