package ru.javawebinar.topjava.util.exception;

public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final String detail;
    private String typeMessage;

    public ErrorInfo(CharSequence url, ErrorType type, String detail) {
        this.url = url.toString();
        this.type = type;
        this.detail = detail;
    }

    public ErrorInfo(CharSequence url, ErrorType type, String detail, String typeMessage) {
        this(url, type, detail);
        this.typeMessage = typeMessage;
    }

    public String getDetail() {
        return detail;
    }

    public String getTypeMessage() {
        return typeMessage;
    }
}