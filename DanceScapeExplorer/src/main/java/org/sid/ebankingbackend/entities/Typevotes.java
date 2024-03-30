package org.sid.ebankingbackend.entities;

public enum Typevotes {
    VOTE_20(20),
    VOTE_30(30),
    VOTE_50(50);

    private final int value;

    Typevotes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }}