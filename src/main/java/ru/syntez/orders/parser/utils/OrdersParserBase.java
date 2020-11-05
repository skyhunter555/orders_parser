package ru.syntez.orders.parser.utils;

import ru.syntez.orders.parser.entities.OrderFieldEnum;
import ru.syntez.orders.parser.entities.output.OrderOutput;
import ru.syntez.orders.parser.entities.results.ParseResultMessageEnum;
import ru.syntez.orders.parser.entities.results.ParseResultValue;

import java.util.List;
import java.util.Optional;

/**
 * Base parser methods
 *
 * createOrderOutput
 * createOrderOutputWithError
 * parseResultString
 * parseResultInteger
 * isDigit
 *
 * @author Savoskin Dmitry
 * @date 05.11.2020
 */
abstract class OrdersParserBase {
    /**
     * Create order output from results
     * @param fileName - "C:\orders.csv"
     * @param lineNumber - 123
     * @param orderValues - result order values
     * @return OrderOutput
     */
    protected OrderOutput createOrderOutput(
            String fileName,
            final Integer lineNumber,
            final List<ParseResultValue> orderValues
    ) {
        OrderOutput order = new OrderOutput(fileName, lineNumber, getTotalResultMessage(orderValues));
        for (ParseResultValue result : orderValues) {
            switch (result.getOrderField()) {
                case ID:
                    order.setId((Integer) result.getResultValue());
                    break;
                case AMOUNT:
                    order.setAmount((Integer) result.getResultValue());
                    break;
                case COMMENT:
                    order.setComment((String) result.getResultValue());
                    break;
            }
        }
        return order;
    }
    /**
     * If catch parse error on all line
     * @param fileName   - "C:\orders.csv"
     * @param lineNumber - 123
     * @param error      - "Parsing error"
     * @return OrderOutput
     */
    protected OrderOutput createOrderOutputWithError(final String fileName, final Integer lineNumber, final String error) {
        return new OrderOutput(fileName, lineNumber, error);
    }
    /**
     * Get total result message from parse result
     * @param results result
     * @return OK
     */
    private String getTotalResultMessage(final List<ParseResultValue> results) {
        StringBuilder totalResult = new StringBuilder();
        for (ParseResultValue result : results) {
            if (!result.getResultMessage().equals(ParseResultMessageEnum.OK.getDescription())) {
                totalResult.append(result.getResultMessage());
            }
        }
        if (totalResult.length() == 0) {
            totalResult.append(ParseResultMessageEnum.OK.getDescription());
        }
        return totalResult.toString();
    }
    /**
     * Parse String object value
     * @param orderField
     * @param recordValue
     * @return result with String
     */
    protected ParseResultValue parseResultString(final OrderFieldEnum orderField, final String recordValue) {
        if (recordValue == null || recordValue.length() == 0) {
            return new ParseResultValue(orderField, String.format(ParseResultMessageEnum.EMPTY_FIELD.getDescription(), orderField.getFieldCode()));
        }
        return new ParseResultValue(orderField, ParseResultMessageEnum.OK.getDescription(), recordValue);
    }
    /**
     * Parse Integer object value
     * @param orderField
     * @param recordValue
     * @return result with Integer
     */
    protected ParseResultValue parseResultInteger(final OrderFieldEnum orderField, final String recordValue) {
        Optional<Integer> result = Optional.ofNullable(recordValue)
                .filter(this::isDigit)
                .map(Integer::parseInt);
        if (result.isPresent()) {
            return new ParseResultValue(orderField, ParseResultMessageEnum.OK.getDescription(), result.get());
        }
        return new ParseResultValue(orderField, String.format(ParseResultMessageEnum.NOT_DIGIT.getDescription(), orderField.getFieldCode(), recordValue));
    }
    /**
     * Check is digit
     * @param str
     * @return
     */
    protected boolean isDigit(final String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return str.chars().allMatch(Character::isDigit);
    }

}
