package ru.syntez.orders.parser.entities;

import ru.syntez.orders.parser.exceptions.OrderParserException;

/**
 * File extensions
 *
 * @author Skyhunter
 * @date 05.11.2020
 */
public enum FileExtensionEnum {
    
    CSV("csv"),
    JSON("json"),
    XLSX("xlsx"), //Not implemented yet
    XLS("xls"),   //Not implemented yet
    XLTX("xltx"); //Not implemented yet

    private final String code;

    FileExtensionEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static FileExtensionEnum parseCode(String code) {
        for (FileExtensionEnum each : values()) {
            if (each.code.equals(code)) {
                return each;
            }
        }
        throw new OrderParserException(String.format("Extension %s not found.", code));
    }
}
