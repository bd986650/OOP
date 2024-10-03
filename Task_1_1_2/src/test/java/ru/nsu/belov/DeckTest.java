package ru.nsu.belov;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeckTest {
    @Test
    void points_system_test() {
        Card card;

        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                card = new Card(suit, rank);
                assertEquals(card.getValue(),card.rank.getValue());
            }
        }
    }
}
