package ru.syntez.orders.parser.entities.results;

import ru.syntez.orders.parser.entities.OrderFieldEnum;
/**
 * ParseResultValue for order field with some message
 *
 * @author Savoskin Dmitry
 * @date 05.11.2020
 */
public class ParseResultValue {

    private final OrderFieldEnum orderField;
    private final String resultMessage;
    private final Object resultValue;

    public ParseResultValue(OrderFieldEnum orderField, String resultMessage) {
        this.orderField = orderField;
        this.resultMessage = resultMessage;
        this.resultValue = null;
    }

    public ParseResultValue(OrderFieldEnum orderField, String resultMessage, Object resultValue) {
        this.orderField = orderField;
        this.resultMessage = resultMessage;
        this.resultValue = resultValue;
    }

    public OrderFieldEnum getOrderField() {
        return orderField;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public Object getResultValue() {
        return resultValue;
    }

}
