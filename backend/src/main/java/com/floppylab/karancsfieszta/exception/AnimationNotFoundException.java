package com.floppylab.karancsfieszta.exception;

public class AnimationNotFoundException extends RuntimeException {

    public AnimationNotFoundException() {
        super("There is no animation with this identifier.");
    }

}
