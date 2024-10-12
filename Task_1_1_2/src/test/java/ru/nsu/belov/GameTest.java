package ru.nsu.belov;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game game;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        game = new Game();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testPlayerWinsWithBlackjack() {
        game.deck = new Deck() {
            public final List<Card> testDeck = new ArrayList<>(Arrays.asList(
                    new Card(Card.Suit.Spades.ordinal(), Card.Rank.Ace.ordinal()),
                    new Card(Card.Suit.Hearts.ordinal(), Card.Rank.Two.ordinal()),
                    new Card(Card.Suit.Diamonds.ordinal(), Card.Rank.Ten.ordinal()),
                    new Card(Card.Suit.Clubs.ordinal(), Card.Rank.Ace.ordinal())
            ));

            @Override
            public Card get_card_from_deck() {
                return testDeck.remove(testDeck.size() - 1);
            }
        };

        game.gameStart(0);

        String output = outputStream.toString();
        assertTrue(output.contains("You have Blackjack!"));
    }

    @Test
    void testPlayerLosesRound() {
        game.deck = new Deck() {
            public final List<Card> testDeck = new ArrayList<>(Arrays.asList(
                    new Card(Card.Suit.Spades.ordinal(), Card.Rank.Two.ordinal()),
                    new Card(Card.Suit.Spades.ordinal(), Card.Rank.Eight.ordinal()),
                    new Card(Card.Suit.Spades.ordinal(), Card.Rank.Ten.ordinal()),
                    new Card(Card.Suit.Hearts.ordinal(), Card.Rank.Ten.ordinal()),
                    new Card(Card.Suit.Clubs.ordinal(), Card.Rank.Ten.ordinal())
            ));

            @Override
            public Card get_card_from_deck() {
                return testDeck.remove(testDeck.size() - 1);
            }
        };

        System.setIn(new ByteArrayInputStream("1\n".getBytes()));

        game.gameStart(0);

        String output = outputStream.toString();
        assertTrue(output.contains("You've lost the round."));
    }

    @Test
    void testGameEndsInTie() {
        game.deck = new Deck() {
            public final List<Card> testDeck = new ArrayList<>(Arrays.asList(
                    new Card(Card.Suit.Spades.ordinal(), Card.Rank.Eight.ordinal()),
                    new Card(Card.Suit.Spades.ordinal(), Card.Rank.Ten.ordinal()),
                    new Card(Card.Suit.Hearts.ordinal(), Card.Rank.Eight.ordinal()),
                    new Card(Card.Suit.Clubs.ordinal(), Card.Rank.Ten.ordinal())
            ));

            @Override
            public Card get_card_from_deck() {
                return testDeck.remove(testDeck.size() - 1);
            }
        };

        System.setIn(new ByteArrayInputStream("0\n".getBytes()));

        game.gameStart(0);

        String output = outputStream.toString();
        assertTrue(output.contains("It's a tie."));
    }

    @Test
    void testDealerLosesRound() {

        // Настраиваем колоду
        game.deck = new Deck() {
            public final List<Card> testDeck = new ArrayList<>(Arrays.asList(
                    new Card(Card.Suit.Spades.ordinal(), Card.Rank.Eight.ordinal()),
                    new Card(Card.Suit.Spades.ordinal(), Card.Rank.Six.ordinal()),
                    new Card(Card.Suit.Spades.ordinal(), Card.Rank.Ten.ordinal()),
                    new Card(Card.Suit.Hearts.ordinal(), Card.Rank.Eight.ordinal()),
                    new Card(Card.Suit.Clubs.ordinal(), Card.Rank.Ten.ordinal())
            ));

            @Override
            public Card get_card_from_deck() {
                return testDeck.remove(testDeck.size() - 1);
            }
        };

        System.setIn(new ByteArrayInputStream("0\n".getBytes()));

        game.gameStart(0);

        String output = outputStream.toString();
        assertTrue(output.contains("You have won the round!"));
    }

    @Test
    void playerWinByScoreDefault() {
        game.deck = new Deck() {
            public final List<Card> testDeck = new ArrayList<>(Arrays.asList(
                    new Card(Card.Suit.Hearts.ordinal(), Card.Rank.Three.ordinal()),
                    new Card(Card.Suit.Spades.ordinal(), Card.Rank.Three.ordinal()),
                    new Card(Card.Suit.Spades.ordinal(), Card.Rank.Six.ordinal()),
                    new Card(Card.Suit.Spades.ordinal(), Card.Rank.Ten.ordinal()),
                    new Card(Card.Suit.Hearts.ordinal(), Card.Rank.Eight.ordinal()),
                    new Card(Card.Suit.Clubs.ordinal(), Card.Rank.Ten.ordinal())
            ));

            @Override
            public Card get_card_from_deck() {
                return testDeck.remove(testDeck.size() - 1);
            }
        };

        System.setIn(new ByteArrayInputStream("1\n0\n".getBytes()));

        game.gameStart(0);

        String output = outputStream.toString();
        assertTrue(output.contains("You have won the round!"));
    }

    @BeforeEach
    void getBack() {
        System.setOut(originalOut);
    }
}
