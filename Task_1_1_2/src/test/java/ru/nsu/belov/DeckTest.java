package ru.nsu.belov;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class DeckTest {

    public Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    void testDeckHas52Cards() {
        assertEquals(52, deck.cards.size());
    }

    @Test
    void testDeckContainsAllUniqueCards() {
        Set<Card> cardSet = new HashSet<>(deck.cards);
        assertEquals(52, cardSet.size());
    }

    @Test
    void testGetCardFromDeckReducesSize() {
        int initialSize = deck.cards.size();
        deck.get_card_from_deck();
        assertEquals(initialSize - 1, deck.cards.size());
    }

    @Test
    void testDeckIsEmptyAfterAllCardsAreDrawn() {
        for (int i = 0; i < 52; i++) {
            deck.get_card_from_deck();
        }
        assertEquals(0, deck.cards.size());
    }
}