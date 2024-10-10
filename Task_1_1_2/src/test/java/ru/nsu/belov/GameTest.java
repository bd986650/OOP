package ru.nsu.belov;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private static void notRepeatInDeck(List<Card> cards) {
        for (Card card : cards) {
            long cnt = cards.stream().filter(card::equals).count();
            assertEquals(cnt, 1);
        }
    }

    @Test
    void gameClassTest() {
        Game game = new Game();

        assertNotNull(game.player);
        assertNotNull(game.dealer);
        assertNotNull(game.deck);
        notRepeatInDeck(game.deck.cards);
    }

    @Test
    void dealCardTest() {
        Player player = new Player("You");
        Deck deck = new Deck();
        Game game = new Game();

        Card tmp = game.deck.get_card_from_deck();
        player.add_card(tmp);
        assertNotNull(tmp);
        assertEquals(-1, deck.cards.indexOf(tmp));

        Card tmp2 = game.deck.get_card_from_deck();
        player.add_card(tmp2);
        assertNotNull(tmp2);
        assertEquals(-1, deck.cards.indexOf(tmp2));
        assertNotEquals(tmp, tmp2);
    }

    @Test
    void playerBlackjackTest() {
        Game game = new Game();

        game.player.add_card(new Card(1, 0));  // Ace
        game.player.add_card(new Card(2, 12)); // King

        game.dealer.add_card(new Card(1, 5));  // 6

        assertTrue(game.player.is_blackjack());
        assertFalse(game.dealer.is_blackjack());
    }

    @Test
    void dealerBlackjackTest() {
        Game game = new Game();

        game.dealer.add_card(new Card(1, 0));  // Ace
        game.dealer.add_card(new Card(2, 12)); // King

        game.player.add_card(new Card(1, 5));  // 6

        assertTrue(game.dealer.is_blackjack());
        assertFalse(game.player.is_blackjack());
    }

    @Test
    void playerWinsTest() {
        Game game = new Game();

        game.player.add_card(new Card(1, 8));  // 9
        game.player.add_card(new Card(2, 5));  // 6

        game.dealer.add_card(new Card(2, 2));  // 3
        game.dealer.add_card(new Card(2, 3));  // 4

        assertTrue(game.player.get_point_for_player() > game.dealer.get_point_for_player());
    }

    @Test
    void dealerWinsTest() {
        Game game = new Game();

        game.player.add_card(new Card(1, 5));  // 6
        game.player.add_card(new Card(2, 2));  // 3

        game.dealer.add_card(new Card(1, 8));  // 9
        game.dealer.add_card(new Card(2, 11)); // 10

        assertTrue(game.dealer.get_point_for_player() > game.player.get_point_for_player());
    }

    @Test
    void tieGameTest() {
        Game game = new Game();

        game.player.add_card(new Card(1, 9));  // 10
        game.player.add_card(new Card(2, 9));  // 10

        game.dealer.add_card(new Card(1, 9));  // 10
        game.dealer.add_card(new Card(2, 9));  // 10

        assertEquals(game.player.get_point_for_player(), game.dealer.get_point_for_player());
    }
}
