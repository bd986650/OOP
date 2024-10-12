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

    public int pointSystem(boolean more_than_21) {
        int countPointsByCard = rank.ordinal() + 1;

        if ((2 <= countPointsByCard) && (countPointsByCard <= 10)) {
            return countPointsByCard;
        } else if (countPointsByCard == 1) {
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
        int countPointsByCard = rank.ordinal() + 1;
        return rank + " of " + suit + " " + "("+ countPointsByCard+ ")";
    }
}
