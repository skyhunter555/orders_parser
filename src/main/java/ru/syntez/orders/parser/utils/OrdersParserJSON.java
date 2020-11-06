package ru.syntez.orders.parser.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.syntez.orders.parser.entities.OrderFieldEnum;
import ru.syntez.orders.parser.entities.output.OrderOutput;
import ru.syntez.orders.parser.entities.results.ValidatedResult;
import ru.syntez.orders.parser.exceptions.OrderParserException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.syntez.orders.parser.utils.validator.OrderValidator.validateResultInteger;
import static ru.syntez.orders.parser.utils.validator.OrderValidator.validateResultString;

/**
 * Parser for JSON file from lines
 * {“orderId”:1,”amount”:100,”currency”:”USD”,”comment”:”оплата заказа”}
 * @author Savoskin Dmitry
 * @date 05.11.2020
 */
public class OrdersParserJSON extends OrdersParserBase implements IOrdersParser {

    private static Logger LOG = LogManager.getLogger(OrdersParserJSON.class);

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

    private List<ValidatedResult> parseFromLine(String line) throws OrderParserException {
        List<ValidatedResult> resultList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            JSONObject json = (JSONObject) parser.parse(line);
            for (OrderFieldEnum orderField : OrderFieldEnum.values()) {
                String recordValue = json.get(orderField.getFieldCode()).toString();
                if (orderField.getIsInteger()) {
                    resultList.add(validateResultInteger(orderField, recordValue));
                } else {
                    resultList.add(validateResultString(orderField, recordValue));
                }
            }
        } catch (ParseException e) {
            throw new OrderParserException(e.toString());
        }
        LOG.info("Line of JSON file was successfully parsed by field name.");
        return resultList;
    }

}
