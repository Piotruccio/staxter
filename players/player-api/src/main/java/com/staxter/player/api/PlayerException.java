package com.staxter.player.api;

/**
 * A common exception class for all cases regarding player error conditions,
 * e.g. invalid/non-existing player ID used.
 */
public class PlayerException extends Exception {

    /**
     * Constructs a new instance of PlayerException with the specified message.
     *
     * @param message the message to set
     */
    public PlayerException(String message) {
        super(message);
    }

    /**
     * Constructs a new instance of PlayerException with the specified message
     * and cause.
     *
     * @param message the message to set
     * @param cause the cause to set
     */
    public PlayerException(String message, Throwable cause) {
        super(message, cause);
    }
}
