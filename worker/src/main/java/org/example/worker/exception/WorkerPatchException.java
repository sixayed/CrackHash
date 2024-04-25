package org.example.worker.exception;

public class WorkerPatchException extends RuntimeException{
    public WorkerPatchException(String message) {
        super(message);
    }

    public WorkerPatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
