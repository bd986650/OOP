package ru.nsu.belov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Card Test.
 */
class CardTest {
    @Test
    /**
     * void points system test.
     */
    void pointsSystemTest() {
        Card card;

        boolean moreThan21 = true;

        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                card = new Card(suit.ordinal(), rank.ordinal());

                int expectedPoints = calculateExpectedPoints(rank, moreThan21);
                assertEquals(expectedPoints, card.pointSystem(moreThan21));
            }
        }

        moreThan21 = false;
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                card = new Card(suit.ordinal(), rank.ordinal());

                int expectedPoints = calculateExpectedPoints(rank, moreThan21);
                assertEquals(expectedPoints, card.pointSystem(moreThan21));
            }
        }
    }

    /**
     * calculate points.
     *
     * @param rank card.
     * @param moreThan21 boolean.
     * @return int.
     */
    private int calculateExpectedPoints(Card.Rank rank, boolean moreThan21) {
        int rankValue = rank.ordinal() + 1;

        if (rankValue >= 2 && rankValue <= 10) {
            return rankValue;
        } else if (rankValue == 1) {
            return moreThan21 ? 1 : 11;
        } else {
            return 10;
        }
    }
}
