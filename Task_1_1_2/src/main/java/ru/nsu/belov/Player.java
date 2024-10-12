package ru.nsu.belov;

import java.util.*;

public class Player {
    protected List<Card> hand;
    protected String name;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<Card>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

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

    public void printHand() {
        System.out.println("    Your cards: " + hand + " => " + getPointForPlayer());
    }

    public boolean isLoser() {
        return getPointForPlayer() > 21;
    }

    public boolean isBlackjack() {
        return getPointForPlayer() == 21 && hand.size() == 2;
    }
}

class Dealer extends Player {
    public Dealer() {
        super("Dealer");
    }

    public void showStartHand() {
        System.out.println("    Dealer's cards: " + "["+hand.get(0) + ", <closed card>"+"]");
    }

    public void printHand(){
        System.out.println("    Dealer's cards: " + hand + " => " + getPointForPlayer());
    }
}
