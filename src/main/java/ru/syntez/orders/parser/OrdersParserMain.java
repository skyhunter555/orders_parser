package ru.syntez.orders.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.syntez.orders.parser.usecases.CreateOrdersFromFileUsecase;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static java.lang.System.exit;

/**
 * Main class for console running
 *
 * @author Savoskin Dmitry
 * @date 03.11.2020
 */
@SpringBootApplication
public class OrdersParserMain implements CommandLineRunner {

    private final String SYNC_LOCK = "FileProgressLock";

    private Set<String> filesInProgress = new HashSet<>();

    @Autowired
    private CreateOrdersFromFileUsecase createOrdersFromFile;

    private static Logger LOG = LogManager.getLogger(OrdersParserMain.class);

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(OrdersParserMain.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) {
        LOG.info("Start process parse files...");
        for (int i = 0; i < args.length; ++i) {

            synchronized (SYNC_LOCK) {
                String fileName = args[i];
                LOG.info("args[{}]: {}", i, fileName);

                if (!filesInProgress.contains(fileName)) {

                    CompletableFuture<String> promise = createOrdersFromFile.execute(fileName);
                    filesInProgress.add(fileName);
                    LOG.info(String.format("Added file %s to progress", fileName));
                    promise.handle((completedFile, ex) -> {
                        if (completedFile == null) {
                            LOG.warn("Problem", ex);
                            return "";
                        }
                        LOG.info(String.format("File %s was completed", completedFile));
                        synchronized(SYNC_LOCK) {
                            filesInProgress.remove(completedFile);
                            LOG.info(String.format("Removed file %s from lock", completedFile));
                            return completedFile;
                        }
                    });
                }
            }
        }
        exit(0);
    }
}