package ru.nsu.belov;

/**
 * dealer class from player class.
 */
class Dealer extends Player {
    /**
     * dealer.
     */
    public Dealer() {
        super("Dealer");
    }

    /**
     * show start hand.
     */
    public void showStartHand() {
        System.out.println("    Dealer's cards: " + "[" + hand.get(0) + ", <closed card>" + "]");
    }

    /**
     * print hand for dealer.
     */
    public void printHand() {
        System.out.println("    Dealer's cards: " + hand + " => " + getPointForPlayer());
    }
}
