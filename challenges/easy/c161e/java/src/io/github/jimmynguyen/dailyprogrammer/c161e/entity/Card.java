package io.github.jimmynguyen.dailyprogrammer.c161e.entity;

import io.github.jimmynguyen.dailyprogrammer.c161e.enums.CardSuit;
import io.github.jimmynguyen.dailyprogrammer.c161e.enums.CardValue;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private final CardValue value;
    private final CardSuit suit;

    public Card(CardValue value, CardSuit suit) {
        this.value = value;
        this.suit = suit;
    }

    public List<Integer> getValues() {
        List<Integer> values = new ArrayList<>();
        int min = value.getMinValue();
        int max = value.getMaxValue();
        if (min != max) {
            values.add(min);
        }
        values.add(max);
        return values;
    }

    public String toString() {
        return value.toString() + suit.toString();
    }
}
