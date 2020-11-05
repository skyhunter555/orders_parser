package ru.syntez.orders.parser.utils;

import ru.syntez.orders.parser.entities.FileExtensionEnum;
import ru.syntez.orders.parser.exceptions.OrderParserException;
import java.util.Optional;

/**
 * Create order parser by file extensions
 *
 * @author Savoskin Dmitry
 * @date 05.11.2020
 */
public class OrdersParserFactory {

    public IOrdersParser buildParser(String fileName) throws OrderParserException {

        Optional<String> extensionOptional = getExtensionFile(fileName);

        if (!extensionOptional.isPresent()) {
            throw new OrderParserException(String.format("Extension undefined for file %s", fileName));
        }

        FileExtensionEnum extension = FileExtensionEnum.parseCode(extensionOptional.get().toLowerCase());
        switch (extension) {
            case CSV:
                return new OrdersParserCSV();
            case JSON:
                return new OrdersParserJSON();
            case XLSX:
                return new OrdersParserXLSX();
        }

        throw new OrderParserException(String.format("Parsing not yet implemented for file with extension %s", extension.getCode()));
    }

    private Optional<String> getExtensionFile(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
