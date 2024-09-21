package ru.nsu.belov;

import java.util.*;

public class Player {
    protected List<Card> hand;
    protected String name;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<Card>();
    }

    public void add_card(Card card) {
        hand.add(card);
    }

    public int get_point_for_player() {
        int score = 0;

        for (Card card : hand) {
            score += card.point_system(false);
        }

        if (score > 21) {
            score = 0;
            for (Card card : hand) {
                score += card.point_system(true);
            }
        }

        return score;
    }

    public void print_hand() {
        System.out.println("    Your cards: " + hand + " => " + get_point_for_player());
    }

    public boolean is_loser() {
        return get_point_for_player() > 21;
    }

    public boolean is_blackjack() {
        return get_point_for_player() == 21 && hand.size() == 2;
    }
}

class Dealer extends Player {
    public Dealer() {
        super("Dealer");
    }

    public void show_start_hand() {
        System.out.println("    Dealer's cards: " + "["+hand.get(0) + ", <closed card>"+"]");
    }

    public void print_hand(){
        System.out.println("    Dealer's cards: " + hand + " => " + get_point_for_player());
    }
}

