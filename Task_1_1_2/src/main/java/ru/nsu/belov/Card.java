package ru.nsu.belov;

public class Card {
    public enum Suit {
        Spades,
        Clubs,
        Diamonds,
        Hearts
    }

    public enum Rank {
        Ace,
        Two,
        Three,
        Four,
        Five,
        Six,
        Seven,
        Eight,
        Nine,
        Ten,
        Jack,
        Queen,
        King
    }

    Suit suit;
    Rank rank;

    public Card(int suit, int rank) {
        this.suit = Suit.values()[suit % 4];
        this.rank = Rank.values()[rank % 13];
    }

    public int point_system(boolean more_than_21) {
        int count_points_by_card = rank.ordinal() + 1;

        if ((2 <= count_points_by_card) && (count_points_by_card <= 10)) {
            return count_points_by_card;
        } else if (count_points_by_card == 1) {
            if (more_than_21) {
                return 1;
            } else {
                return 11;
            }
        } else {
            return 10;
        }
    }

    @Override
    public String toString() {
        int count_points_by_card = rank.ordinal() + 1;
        return rank + " of " + suit + " " + "("+ count_points_by_card+ ")";
    }
}
