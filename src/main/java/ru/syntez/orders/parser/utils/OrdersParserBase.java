package ru.syntez.orders.parser.utils;

import ru.syntez.orders.parser.entities.output.OrderOutput;
import ru.syntez.orders.parser.entities.results.ParseResultMessageEnum;
import ru.syntez.orders.parser.entities.results.ValidatedResult;
import java.util.List;
/**
 * Base parser methods
 *
 * createOrderOutput
 * createOrderOutputWithError
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
        final String fileName,
        final Integer lineNumber,
        final List<ValidatedResult> orderValues
    ) {
        OrderOutput order = new OrderOutput(fileName, lineNumber, getTotalValidateMessage(orderValues));
        for (ValidatedResult result : orderValues) {
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
    private String getTotalValidateMessage(final List<ValidatedResult> results) {
        StringBuilder totalResult = new StringBuilder();
        for (ValidatedResult result : results) {
            if (!result.getResultMessage().equals(ParseResultMessageEnum.OK.getDescription())) {
                totalResult.append(result.getResultMessage());
            }
        }
        if (totalResult.length() == 0) {
            totalResult.append(ParseResultMessageEnum.OK.getDescription());
        }
        return totalResult.toString();
    }

}
