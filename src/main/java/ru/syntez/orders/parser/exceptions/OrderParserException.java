package ru.syntez.orders.parser.exceptions;
/**
 * Wrapper over RuntimeException. Includes additional options for formatting message text.
 *
 * @author Savoskin Dmitry
 * @date 05.11.2020
 */
public class OrderParserException extends RuntimeException {

    public OrderParserException(String message) {
	super(message);
    }

    public OrderParserException(String messageFormat, Object... messageArgs) {
	    super(String.format(messageFormat, messageArgs));
    }

    public OrderParserException(Throwable throwable) {
	super(throwable);
    }    
   
}
