package ru.nsu.belov;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
    public Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Danil");
    }

    @Test
    void testAddCard() {
        Card card = new Card(2, 9); // Hearts Ten
        player.addCard(card);
        assertEquals(1, player.hand.size());
        assertEquals(card, player.hand.getFirst());
    }

    @Test
    void testGetScoreWithoutAces() {
        player.addCard(new Card(2, 9)); // Hearts Ten
        player.addCard(new Card(1, 6)); // Diamonds Seven
        assertEquals(17, player.getPointForPlayer());
    }

    @Test
    void testGetScoreWithAce() {
        player.addCard(new Card(0, 0)); // Spades Ace
        player.addCard(new Card(3, 8)); // Clubs Nine
        assertEquals(20, player.getPointForPlayer());
    }

    @Test
    void testGetScoreWithAceAsOne() {
        player.addCard(new Card(0, 0)); // Spades Ace
        player.addCard(new Card(3, 9)); // Clubs Ten
        player.addCard(new Card(2, 9)); // Hearts Ten
        assertEquals(21, player.getPointForPlayer());
    }

    @Test
    void testIsLoser() {
        player.addCard(new Card(2, 12)); // Hearts King
        player.addCard(new Card(3, 11)); // Clubs Queen
        player.addCard(new Card(0, 2));  // Spades Three
        assertTrue(player.isLoser());
    }

    @Test
    void testHasBlackjack() {
        player.addCard(new Card(2, 0));  // Hearts Ace
        player.addCard(new Card(1, 10)); // Diamonds Jack
        assertTrue(player.isBlackjack());
    }

    @Test
    void testNotBlackjackWithMoreThanTwoCards() {
        player.addCard(new Card(2, 0));  // Hearts Ace
        player.addCard(new Card(1, 8));  // Diamonds Nine
        player.addCard(new Card(3, 0));  // Clubs Ace
        assertFalse(player.isBlackjack());
    }
}
