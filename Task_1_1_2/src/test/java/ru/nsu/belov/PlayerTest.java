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
        player.add_card(card);
        assertEquals(1, player.hand.size());
        assertEquals(card, player.hand.get(0));
    }

    @Test
    void testGetScoreWithoutAces() {
        player.add_card(new Card(2, 9)); // Hearts Ten
        player.add_card(new Card(1, 6)); // Diamonds Seven
        assertEquals(17, player.get_point_for_player());
    }

    @Test
    void testGetScoreWithAce() {
        player.add_card(new Card(0, 0)); // Spades Ace
        player.add_card(new Card(3, 8)); // Clubs Nine
        assertEquals(20, player.get_point_for_player());
    }

    @Test
    void testGetScoreWithAceAsOne() {
        player.add_card(new Card(0, 0)); // Spades Ace
        player.add_card(new Card(3, 9)); // Clubs Ten
        player.add_card(new Card(2, 9)); // Hearts Ten
        assertEquals(21, player.get_point_for_player());
    }

    @Test
    void testIsLoser() {
        player.add_card(new Card(2, 12)); // Hearts King
        player.add_card(new Card(3, 11)); // Clubs Queen
        player.add_card(new Card(0, 2));  // Spades Three
        assertTrue(player.is_loser());
    }

    @Test
    void testHasBlackjack() {
        player.add_card(new Card(2, 0));  // Hearts Ace
        player.add_card(new Card(1, 10)); // Diamonds Jack
        assertTrue(player.is_blackjack());
    }

    @Test
    void testNotBlackjackWithMoreThanTwoCards() {
        player.add_card(new Card(2, 0));  // Hearts Ace
        player.add_card(new Card(1, 8));  // Diamonds Nine
        player.add_card(new Card(3, 0));  // Clubs Ace
        assertFalse(player.is_blackjack());
    }
}
