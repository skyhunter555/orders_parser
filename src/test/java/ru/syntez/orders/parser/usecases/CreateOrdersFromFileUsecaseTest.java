package ru.syntez.orders.parser.usecases;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CreateOrdersFromFileUsecaseTest {

    @Test
    public void createFromCSVTest() {

        CreateOrdersFromFileUsecase createOrdersFromFileUsecase = new CreateOrdersFromFileUsecase();
        try {
            File file = ResourceUtils.getFile(this.getClass().getResource("/order_20201105.csv"));
            String fileName = file.getAbsolutePath();
            CompletableFuture<String> result = createOrdersFromFileUsecase.execute(fileName);
            Assert.assertEquals(result.get(), fileName);
        } catch (FileNotFoundException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createFromJSONTest() {

        CreateOrdersFromFileUsecase createOrdersFromFileUsecase = new CreateOrdersFromFileUsecase();
        try {
            File file = ResourceUtils.getFile(this.getClass().getResource("/order_20201105.json"));
            String fileName = file.getAbsolutePath();
            CompletableFuture<String> result = createOrdersFromFileUsecase.execute(fileName);
            Assert.assertEquals(result.get(), fileName);
        } catch (FileNotFoundException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
