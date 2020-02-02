package com.staxter.player.impl;

import com.staxter.player.api.PlayerID;

import java.io.Serializable;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * A simple player ID implementation that uses player name (string).
 */
public class PlayerIDImpl implements PlayerID, Serializable {

    private final String name; // Player name is immutable

    /**
     * Constructs a new player ID instance with given name.
     *
     * @param name the player name ID
     */
    public PlayerIDImpl(String name) {
        requireNonNull(name, "name cannot be null");

        this.name = name;
    }

    @Override
    public String getStringValue() { return getName(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerIDImpl that = (PlayerIDImpl) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() { return Objects.hash(name); }

    @Override
    public String toString() { return getName(); }

    /**
     * Returns the name of the player represented by this player ID.
     *
     * @return the player name ID
     */
    public String getName() { return name; }
}
