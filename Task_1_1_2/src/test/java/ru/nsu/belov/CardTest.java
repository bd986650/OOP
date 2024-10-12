package ru.nsu.belov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    @Test
    void pointsSystemTest() {
        Card card;

        boolean more_than_21 = true;

        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                card = new Card(suit.ordinal(), rank.ordinal());

                int expectedPoints = calculateExpectedPoints(rank, more_than_21);
                assertEquals(expectedPoints, card.pointSystem(more_than_21));
            }
        }

        more_than_21 = false;
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                card = new Card(suit.ordinal(), rank.ordinal());

                int expectedPoints = calculateExpectedPoints(rank, more_than_21);
                assertEquals(expectedPoints, card.pointSystem(more_than_21));
            }
        }
    }

    private int calculateExpectedPoints(Card.Rank rank, boolean more_than_21) {
        int rankValue = rank.ordinal() + 1;

        if (rankValue >= 2 && rankValue <= 10) {
            return rankValue;
        } else if (rankValue == 1) {
            return more_than_21 ? 1 : 11;
        } else {
            return 10;
        }
    }
}
