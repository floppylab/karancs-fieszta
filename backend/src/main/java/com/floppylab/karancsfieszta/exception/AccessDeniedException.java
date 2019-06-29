package com.floppylab.karancsfieszta.exception;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {
        super("You cannot modify this animation.");
    }

}
