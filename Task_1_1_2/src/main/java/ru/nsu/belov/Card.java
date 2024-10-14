package ru.nsu.belov;

/**
 * Card
 */
public class Card {
    /**
     * Suit
     */
    public enum Suit {
        Spades,
        Clubs,
        Diamonds,
        Hearts
    }

    /**
     * Rank
     */
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

    /**
     * Card
     * @param suit int
     * @param rank int
     */
    public Card(int suit, int rank) {
        this.suit = Suit.values()[suit % 4];
        this.rank = Rank.values()[rank % 13];
    }

    /**
     * Point System
     * @param moreThan21 boolean
     * @return int
     */
    public int pointSystem(boolean moreThan21) {
        int countPointsByCard = rank.ordinal()+1;

        if ((2 <= countPointsByCard) && (countPointsByCard <= 10)) {
            return countPointsByCard;
        } else if (countPointsByCard == 1) {
            if (moreThan21) {
                return 1;
            } else {
                return 11;
            }
        } else {
            return 10;
        }
    }

    /**
     * to String method
     */
    @Override
    public String toString() {
        int countPointsByCard = rank.ordinal()+1;
        return rank+" of "+suit+" "+"("+countPointsByCard+")";
    }
}
