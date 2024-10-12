package ru.nsu.belov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class DealerTest {

    private Dealer dealer;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        dealer = new Dealer();
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testShowStartHand() {
        dealer.addCard(new Card(3, 9)); // Hearts Ten
        dealer.addCard(new Card(1, 0)); // Clubs Ace

        dealer.showStartHand();

        String expectedOutput = "    Dealer's cards: [Ten of Hearts (10), <closed card>]";
        assertTrue(outputStream.toString().contains(expectedOutput));
    }

    @Test
    void testDealerScore() {
        dealer.addCard(new Card(3, 12)); // Hearts King
        dealer.addCard(new Card(2, 0));  // Diamonds Ace

        assertEquals(21, dealer.getPointForPlayer());
        assertTrue(dealer.isBlackjack());
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }
}
