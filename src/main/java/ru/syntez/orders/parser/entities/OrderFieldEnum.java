package ru.syntez.orders.parser.entities;
/**
 * Universal schema of order file
 *
 * @author Savoskin Dmitry
 * @date 05.11.2020
 */
public enum OrderFieldEnum {

    ID(0, "orderId", true),
    AMOUNT(1, "amount", true),
    CURRENCY(2, "currency", false),
    COMMENT(3, "comment", false);

    private final int fieldIndex;
    private final String fieldCode;
    private final Boolean isInteger;

    OrderFieldEnum(int fieldIndex, String fieldCode, Boolean isInteger) {
        this.fieldCode = fieldCode;
        this.fieldIndex = fieldIndex;
        this.isInteger = isInteger;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public int getFieldIndex() {
        return fieldIndex;
    }

    public Boolean getIsInteger() {
        return isInteger;
    }
}
