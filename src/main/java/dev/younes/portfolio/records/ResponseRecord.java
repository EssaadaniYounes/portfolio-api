package dev.younes.portfolio.records;

public record ResponseRecord<T>(T data, boolean success, String message) {
    public ResponseRecord(T data) {
        this(data, true, "");
    }

    public ResponseRecord(T data, boolean success) {
        this(data, success, "");
    }

}
