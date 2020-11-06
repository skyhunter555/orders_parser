package ru.syntez.orders.parser.utils.validator;

import ru.syntez.orders.parser.entities.OrderFieldEnum;
import ru.syntez.orders.parser.entities.results.ParseResultMessageEnum;
import ru.syntez.orders.parser.entities.results.ValidatedResult;
import java.util.Optional;

public class OrderValidator {

    /**
     * Parse String object value
     * @param orderField
     * @param recordValue
     * @return result with String
     */
    public static ValidatedResult validateResultString(final OrderFieldEnum orderField, final String recordValue) {
        if (recordValue == null || recordValue.length() == 0) {
            return new ValidatedResult(orderField, String.format(ParseResultMessageEnum.EMPTY_FIELD.getDescription(), orderField.getFieldCode()));
        }
        return new ValidatedResult(orderField, ParseResultMessageEnum.OK.getDescription(), recordValue);
    }
    /**
     * Parse Integer object value
     * @param orderField
     * @param recordValue
     * @return result with Integer
     */
    public static ValidatedResult validateResultInteger(final OrderFieldEnum orderField, final String recordValue) {
        Optional<Integer> result = Optional.ofNullable(recordValue)
                .filter(OrderValidator::isDigit)
                .map(Integer::parseInt);
        if (result.isPresent()) {
            return new ValidatedResult(orderField, ParseResultMessageEnum.OK.getDescription(), result.get());
        }
        return new ValidatedResult(orderField, String.format(ParseResultMessageEnum.NOT_DIGIT.getDescription(), orderField.getFieldCode(), recordValue));
    }
    /**
     * Check is digit
     * @param str
     * @return
     */
    public static boolean isDigit(final String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return str.chars().allMatch(Character::isDigit);
    }
}
