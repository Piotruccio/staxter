package com.staxter.player.api;

/**
 * A wrapper interface for player ID (whatever this can be, e.g. name)
 * that can be easily extended in future as needed.
 */
public interface PlayerID {

    /**
     * Returns the string representation of this player ID.
     *
     * @return string representation of this player ID
     */
    String getStringValue();
}
