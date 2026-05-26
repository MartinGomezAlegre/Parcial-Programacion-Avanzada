package edu.usal.exceptions;

public class ArchivoPersistenciaException extends Exception {

    public ArchivoPersistenciaException(String message) {
        super(message);
    }

    public ArchivoPersistenciaException(String message, Throwable cause) {
        super(message, cause);
    }
}
