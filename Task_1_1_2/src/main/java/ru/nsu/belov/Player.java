package ru.nsu.belov;

import java.util.ArrayList;
import java.util.List;

/**
 * Player
 */
public class Player {
    protected List<Card> hand;
    protected String name;

    /**
     * Player
     * @param name
     */
    public Player(String name) {
        this.name = name;
        hand = new ArrayList<Card>();
    }

    /**
     * add card
     * @param card
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * get point for player
     * @return
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
     * print card from hand
     */
    public void printHand() {
        System.out.println("    Your cards: " + hand + " => " + getPointForPlayer());
    }

    /**
     * is loser
     * @return
     */
    public boolean isLoser() {
        return getPointForPlayer() > 21;
    }

    /**
     * is blackjack
     * @return
     */
    public boolean isBlackjack() {
        return getPointForPlayer() == 21 && hand.size() == 2;
    }
}

/**
 * dealer class from player class
 */
class Dealer extends Player {
    /**
     * dealer
     */
    public Dealer() {
        super("Dealer");
    }

    /**
     * show start hand
     */
    public void showStartHand() {
        System.out.println("    Dealer's cards: " + "["+hand.get(0) + ", <closed card>"+"]");
    }

    /**
     * print hand for dealer
     */
    public void printHand(){
        System.out.println("    Dealer's cards: " + hand + " => " + getPointForPlayer());
    }
}
