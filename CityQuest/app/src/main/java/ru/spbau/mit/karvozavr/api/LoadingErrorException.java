package ru.spbau.mit.karvozavr.api;

public class LoadingErrorException extends Exception {

    public LoadingErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
