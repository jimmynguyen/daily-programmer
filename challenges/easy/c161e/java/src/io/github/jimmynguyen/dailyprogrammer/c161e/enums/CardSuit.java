package io.github.jimmynguyen.dailyprogrammer.c161e.enums;

public enum CardSuit {
    SPADES("♠"),
    CLUBS("♣"),
    DIAMONDS("♦"),
    HEARTS("♥");

    private final String symbol;

    CardSuit(String symbol) {
        this.symbol = symbol;
    }

    public String toString() {
        return symbol;
    }
}
