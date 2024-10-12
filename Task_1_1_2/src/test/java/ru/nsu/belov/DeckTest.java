package ru.nsu.belov;

import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Deck Test
 */
public class DeckTest {

    public Deck deck;

    /**
     * setup
     */
    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    /**
     * test deck has 52 cards
     */
    @Test
    void testDeckHas52Cards() {
        assertEquals(52, deck.cards.size());
    }

    /**
     * test deck contains all unique cards
     */
    @Test
    void testDeckContainsAllUniqueCards() {
        Set<Card> cardSet = new HashSet<>(deck.cards);
        assertEquals(52, cardSet.size());
    }

    /**
     * test get card from deck reduces size
     */
    @Test
    void testGetCardFromDeckReducesSize() {
        int initialSize = deck.cards.size();
        deck.getCardFromDeck();
        assertEquals(initialSize - 1, deck.cards.size());
    }

    /**
     * test desk is empty after all cards are dawn
     */
    @Test
    void testDeckIsEmptyAfterAllCardsAreDrawn() {
        for (int i = 0; i < 52; i++) {
            deck.getCardFromDeck();
        }
        assertEquals(0, deck.cards.size());
    }
}
