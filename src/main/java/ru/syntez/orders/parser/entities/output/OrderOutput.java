package ru.syntez.orders.parser.entities.output;

/**
 * Output order data
 * {“id”:1, ”amount”:100, ”comment”:”оплата заказа”, ”filename”:”orders.csv”, ”line”:1, ”result”:”OK” }
 * @author Savoskin Dmitry
 * @date 05.11.2020
 */
public class OrderOutput {

    private Integer id;
    private Integer amount;
    private String comment;

    private final String filename;
    private final Integer line;
    private final String result;

    public OrderOutput(String filename, Integer line, String result) {
        this.filename = filename;
        this.line = line;
        this.result = result;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }

    public String getFilename() {
        return filename;
    }

    public Integer getLine() {
        return line;
    }

    public String getResult() {
        return result;
    }
}
