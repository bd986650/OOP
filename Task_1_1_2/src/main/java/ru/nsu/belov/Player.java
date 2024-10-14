package ru.nsu.belov;

import java.util.ArrayList;
import java.util.List;

/**
 * Player.
 */
public class Player {
    protected List<Card> hand;
    protected String name;

    /**
     * Player.
     *
     * @param name string.
     */
    public Player(String name) {
        this.name = name;
        hand = new ArrayList<Card>();
    }

    /**
     * add card.
     *
     * @param card card.
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * get point for player.
     *
     * @return int.
     */
    public int getPointForPlayer() {
        int score = 0;

        for (Card card : hand) {
            score += card.pointSystem(false);
        }

        if (score > 21) {
            score = 0;
            for (Card card : hand) {
                score += card.pointSystem(true);
            }
        }

        return score;
    }

    /**
     * print card from hand.
     */
    public void printHand() {
        System.out.println("    Your cards: " + hand + " => " + getPointForPlayer());
    }

    /**
     * is loser.
     *
     * @return boolean.
     */
    public boolean isLoser() {
        return getPointForPlayer() > 21;
    }

    /**
     * is blackjack.
     *
     * @return boolean.
     */
    public boolean isBlackjack() {
        return getPointForPlayer() == 21 && hand.size() == 2;
    }
}