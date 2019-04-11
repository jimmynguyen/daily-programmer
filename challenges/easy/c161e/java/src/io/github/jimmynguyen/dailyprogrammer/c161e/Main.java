package io.github.jimmynguyen.dailyprogrammer.c161e;

import io.github.jimmynguyen.dailyprogrammer.c161e.entity.Deck;
import io.github.jimmynguyen.dailyprogrammer.c161e.entity.Hand;

public class Main {

    public static void main(String[] args) {
        boolean debug = true;
        int numDecks = 1;
        if (args.length > 0) {
            numDecks = Integer.parseInt(args[0]);
        }
        Deck deck = new Deck(numDecks);
        int numHands = 0, numBlackJack = 0, numBusted = 0, numCharlie = 0;
        while (deck.size() >= 2) {
            Hand hand = new Hand();
            hand.addCard(deck.draw());
            hand.addCard(deck.draw());
            while (!hand.isBusted() && hand.hasToHit()) {
                if (deck.isEmpty()) {
                    break;
                }
                hand.addCard(deck.draw());
            }
            if (hand.isBlackJack()) {
                numBlackJack++;
            } else if (hand.isBusted()) {
                numBusted++;
            } else if (hand.size() >= 5) {
                numCharlie++;
            }
            numHands++;
            if (debug) {
                System.out.println(hand);
            }
        }
        printStatistic("blackjacks", numBlackJack, numHands);
        printStatistic("busted", numBusted, numHands);
        printStatistic("charlie", numCharlie, numHands);
    }

    private static void printStatistic(String statistic, int numStatistic, int numTotal) {
        System.out.println(String.format("%d %s at %.2f%% after %d hands", numStatistic, statistic, (double)numStatistic/numTotal*100, numTotal));
    }
}
