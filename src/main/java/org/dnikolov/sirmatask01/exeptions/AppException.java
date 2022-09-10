package org.dnikolov.sirmatask01.exeptions;

import java.util.TreeMap;

public class AppException extends RuntimeException {
    private String message;
    private TreeMap<String, String> details;

    public AppException(String message, TreeMap<String, String> details) {
        super(message);
        this.message = message;
        this.details = details;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public TreeMap<String, String> getDetails(){
        return details;
    }
}
