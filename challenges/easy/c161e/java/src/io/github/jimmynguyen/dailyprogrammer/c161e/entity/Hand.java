package io.github.jimmynguyen.dailyprogrammer.c161e.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Hand {
    private List<Card> hand;
    private List<Integer> values;

    public Hand() {
        hand = new ArrayList<>();
        values = new ArrayList<>();
    }

    public int size() {
        return hand.size();
    }

    public boolean isBlackJack() {
        return !hand.isEmpty() && values.stream().anyMatch(v -> v == 21);
    }

    public boolean isBusted() {
        return !hand.isEmpty() && values.stream().allMatch(v -> v > 21);
    }

    public boolean hasToHit() {
        return hand.isEmpty() || values.stream().allMatch(v -> v <= 11);
    }

    public void addCard(Card card) {
        hand.add(card);
        computeHandValues(card);
    }

    private int getValue() {
        return values.stream().filter(v -> v <= 21).max(Integer::compareTo).get();
    }

    private void computeHandValues(Card card) {
        List<Integer> cardValues = card.getValues();
        if (values.isEmpty()) {
            values = cardValues;
        } else {
            List<Integer> newValues = new ArrayList<>();
            for (int handValue : values) {
                for (int cardValue : cardValues) {
                    newValues.add(handValue + cardValue);
                }
            }
            values = newValues;
        }
    }

    public String toString() {
        return String.format("%s -> %d", hand.stream().map(Card::toString).collect(Collectors.joining(" ")), getValue());
    }
}
