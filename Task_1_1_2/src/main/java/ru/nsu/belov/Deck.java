
package ru.nsu.belov;

import java.util.*;

public class Deck {
    ArrayList<Card> cards = new ArrayList<>();

    public Deck() {
        createDeck();
    }

    private void createDeck() {
        cards.clear();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit.ordinal(), rank.ordinal()));
            }
        }
        shuffle();
    }

    private void shuffle() {
        Collections.shuffle(cards);
    }

    public Card get_card_from_deck() {
        if (cards.isEmpty()) {
            createDeck();
        }
        return cards.remove(cards.size() - 1);
    }
}
