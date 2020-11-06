package ru.syntez.orders.parser.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.syntez.orders.parser.entities.OrderFieldEnum;
import ru.syntez.orders.parser.entities.output.OrderOutput;
import ru.syntez.orders.parser.entities.results.ParseResultMessageEnum;
import ru.syntez.orders.parser.entities.results.ValidatedResult;
import ru.syntez.orders.parser.exceptions.OrderParserException;
import ru.syntez.orders.parser.utils.validator.OrderValidator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Parser for CSV file from lines
 *
 * @author Savoskin Dmitry
 * @date 05.11.2020
 */
public class OrdersParserCSV extends OrdersParserBase implements IOrdersParser {

    private static Logger LOG = LogManager.getLogger(OrdersParserCSV.class);

    @Override
    public List<OrderOutput> parseFromFile(String fileName) throws IOException {

        List<OrderOutput> orderOutputList = new ArrayList<>();
        int lineNumber = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                lineNumber++;
                try {
                    List<ValidatedResult> orderValues = parseFromLine(currentLine);
                    orderOutputList.add(createOrderOutput(fileName, lineNumber, orderValues));
                } catch (OrderParserException pe) {
                    LOG.error(String.format("Error parse file line %s", fileName));
                    orderOutputList.add(createOrderOutputWithError(fileName, lineNumber, pe.getMessage()));
                }
            }
            LOG.info(String.format("File parsed %s rows.", lineNumber));
            return orderOutputList;
        }
    }

    private List<ValidatedResult> parseFromLine(String line) throws IOException, OrderParserException {

        Reader reader = new StringReader(line);
        LOG.info("Start parse line of CSV file.");
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';').withTrim());
        CSVRecord csvRecord = csvParser.getRecords().get(0);
        //Check fields count
        if (csvRecord.size() != OrderFieldEnum.values().length) {
            throw new OrderParserException(String.format(ParseResultMessageEnum.INVALID_SCHEMA.getDescription(), csvRecord.size(), OrderFieldEnum.values().length));
        }
        return parseByFieldIndexes(csvRecord);
    }

    private List<ValidatedResult> parseByFieldIndexes(CSVRecord csvRecord) {
        List<ValidatedResult> resultList = new ArrayList<>();
        for (OrderFieldEnum orderField : OrderFieldEnum.values()) {
            String recordValue = csvRecord.get(orderField.getFieldIndex());
            if (orderField.getIsInteger()) {
                resultList.add(OrderValidator.validateResultInteger(orderField, recordValue));
            } else {
                resultList.add(OrderValidator.validateResultString(orderField, recordValue));
            }
        }
        LOG.info("Line of CSV file was successfully parsed by field indexes.");
        return resultList;
    }

}
