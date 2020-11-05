package ru.syntez.orders.parser.utils;

import ru.syntez.orders.parser.entities.output.OrderOutput;
import java.io.IOException;
import java.util.List;

/**
 * Methods to parse some orders from file
 *
 * @author Savoskin Dmitry
 * @date 05.11.2020
 */
public interface IOrdersParser {

    List<OrderOutput> parseFromFile(String fileName) throws IOException;

}
