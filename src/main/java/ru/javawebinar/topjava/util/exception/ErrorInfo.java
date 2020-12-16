package ru.javawebinar.topjava.util.exception;

import java.util.ArrayList;
import java.util.List;

public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private List<String> details = new ArrayList<>();
    private String typeMessage;

    public ErrorInfo(CharSequence url, ErrorType type, String detail) {
        this.url = url.toString();
        this.type = type;
        //this.detail = new String[]{detail};
        details.add(detail);
    }

    public ErrorInfo(CharSequence url, ErrorType type, List<String> details) {
        this.url = url.toString();
        this.type = type;
        this.details = details;
    }

    public ErrorInfo(CharSequence url, ErrorType type, String detail, String typeMessage) {
        this(url, type, detail);
        this.typeMessage = typeMessage;
    }

    public ErrorInfo(CharSequence url, ErrorType type,  List<String> details, String typeMessage) {
        this(url, type, details);
        this.typeMessage = typeMessage;
    }

    public List<String> getDetails() {
        return details;
    }

    public String getTypeMessage() {
        return typeMessage;
    }
}