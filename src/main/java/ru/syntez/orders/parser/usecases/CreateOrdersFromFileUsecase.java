package ru.syntez.orders.parser.usecases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.syntez.orders.parser.entities.output.OrderOutput;
import ru.syntez.orders.parser.utils.IOrdersParser;
import ru.syntez.orders.parser.utils.OrdersParserFactory;

import java.io.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CreateOrdersFromFileUsecase {

    private static Logger LOG = LogManager.getLogger(CreateOrdersFromFileUsecase.class);

    @Async
    public CompletableFuture<String> execute(String fileName) {

        File csvFile = new File(fileName);
        if (!csvFile.isFile()) {
            LOG.error(String.format("This is not a file %s", fileName));
            return CompletableFuture.completedFuture(null);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        OrdersParserFactory factory = new OrdersParserFactory();
        IOrdersParser parser = factory.buildParser(fileName);

        try {
            List<OrderOutput> outputList = parser.parseFromFile(fileName);
            for (OrderOutput output: outputList) {
                printOut(output, objectMapper);
            }
            return CompletableFuture.completedFuture(fileName);
        } catch (IOException e) {
            LOG.error(String.format("Error read file %s", fileName));
            return CompletableFuture.completedFuture(null);
        }
    }

    private void printOut(OrderOutput order, ObjectMapper objectMapper) {
        try {
            String orderPayload = objectMapper.writeValueAsString(order);
            System.out.println(orderPayload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
