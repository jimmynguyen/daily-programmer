package io.github.jimmynguyen.dailyprogrammer.c161e.entity;

import io.github.jimmynguyen.dailyprogrammer.c161e.enums.CardSuit;
import io.github.jimmynguyen.dailyprogrammer.c161e.enums.CardValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.stream.IntStream;

public class Deck {
    private Stack<Card> deck;
    private Random random;

    public Deck(int numStandardDecks) {
        if (numStandardDecks <= 0) {
            throw new IllegalArgumentException(String.format("Invalid number of decks: %d", numStandardDecks));
        }
        deck = new Stack<>();
        random = new Random();
        IntStream.range(0, numStandardDecks).forEach(i -> deck.addAll(getNewStandardDeck()));
        shuffle();
    }

    public Card draw() {
        return deck.pop();
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }

    public int size() {
        return deck.size();
    }

    private void shuffle() {
        Stack<Card> newDeck = new Stack<>();
        random.ints(deck.size(), 0, deck.size()).forEach(i -> newDeck.push(deck.get(i)));
        deck = newDeck;
    }

    private List<Card> getNewStandardDeck() {
        List<Card> deck = new ArrayList<>();
        for (CardSuit cardSuit : CardSuit.values()) {
            for (CardValue cardValue : CardValue.values()) {
                deck.add(new Card(cardValue, cardSuit));
            }
        }
        return deck;
    }
}
