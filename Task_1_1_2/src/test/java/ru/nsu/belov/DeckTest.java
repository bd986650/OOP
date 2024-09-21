package ru.nsu.belov;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeckTest {
    private ArrayList<Card> new_cards_for_test = new ArrayList<>();

//    @Test
//    void create_deck_test() {
//        Deck deck = new Deck();
//        for (int rank = 0; rank < 4; rank++) {
//            for (int suit = 0; suit < 13; suit++) {
//                new_cards_for_test.add(new Card(suit, rank));
//            }
//        }
//        Collections.shuffle(new_cards_for_test);
//        assertEquals(new_cards_for_test, deck.cards);
//    }

    @Test
    void shuffle_test() {
        ArrayList<Card> new_cards_for_test = new ArrayList<>();
    }

    @Test
    void get_card_from_deck_test() {

    }
}