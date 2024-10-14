package ru.nsu.belov;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Deck
 */
public class Deck {
    ArrayList<Card> cards = new ArrayList<>();

    /**
     * Deck
     */
    public Deck() {
        createDeck();
    }

    /**
     * create deck
     */
    private void createDeck() {
        cards.clear();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit.ordinal(), rank.ordinal()));
            }
        }
        shuffle();
    }

    /**
     * shuffle
     */
    private void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * get card from deck
     *
     * @return Card
     */
    public Card getCardFromDeck() {
        if (cards.isEmpty()) {
            createDeck();
        }
        return cards.remove(cards.size() - 1);
    }
}
