package io.github.jimmynguyen.dailyprogrammer.c161e.enums;

public enum CardValue {
    ACE("A"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN("10"), JACK("J"), QUEEN("Q"), KING("K");

    private final String symbol;

    CardValue(String symbol) {
        this.symbol = symbol;
    }

    public int getMinValue() {
        switch (this) {
            case JACK:
            case QUEEN:
            case KING:
                return 10;
            default:
                return this.ordinal() + 1;
        }
    }

    public int getMaxValue() {
        if (this == ACE) {
            return 11;
        }
        return getMinValue();
    }

    public String toString() {
        return this.symbol;
    }
}
