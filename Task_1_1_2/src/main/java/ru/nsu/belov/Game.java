package ru.nsu.belov;

import java.util.Scanner;

/**
 * Game
 */
public class Game {
    public Deck deck;
    public Player player;
    public Dealer dealer;

    /**
     * Game
     */
    public Game() {
        deck = new Deck();
        player = new Player("You");
        dealer = new Dealer();
    }

    /**
     * game start (main game function)
     * @param numOfRoundsFlag
     */
    public void gameStart(int numOfRoundsFlag ) {
        int round = 1;
        int playerWins = 0;
        int dealerWins = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Blackjack!");

        while (true) {
            player.hand.clear();
            dealer.hand.clear();

            System.out.println("Round " + round);
            round++;

            System.out.println("The dealer dealt the cards");

            player.addCard(deck.getCardFromDeck());
            player.addCard(deck.getCardFromDeck());
            dealer.addCard(deck.getCardFromDeck());
            dealer.addCard(deck.getCardFromDeck());

            player.printHand();
            dealer.showStartHand();

            if (player.isBlackjack() && dealer.isBlackjack()) {
                dealerWins++;
                playerWins++;

                System.out.println("It's a tie. Score " + playerWins + ":" + dealerWins);

                if (numOfRoundsFlag==0) {
                    break;
                }

                continue;
            } else if (player.isBlackjack()) {
                playerWins++;

                System.out.println("You have Blackjack! You have won the round. Score " + playerWins + ":" + dealerWins);

                if (numOfRoundsFlag==0) {
                    break;
                }

                continue;
            } else if (dealer.isBlackjack()) {
                dealerWins++;

                System.out.println("Dealer has Blackjack! Dealer has won the round. Score " + playerWins + ":" + dealerWins);

                if (numOfRoundsFlag==0){
                    break;
                }

                continue;
            }

            System.out.println("Your move");
            System.out.println("-------");

            int flag = 0;

            while (true) {
                System.out.print("Enter '1' to take the card, and '0' to stop...");
                String decision = scanner.nextLine();

                if (decision.equalsIgnoreCase("1")) {
                    player.addCard(deck.getCardFromDeck());

                    System.out.println("You have opened the card "+ player.hand.get(player.hand.size()-1));

                    player.printHand();
                    dealer.showStartHand();

                    if (player.isLoser()) {
                        flag = 1;
                        dealerWins++;

                        System.out.println("You've lost the round. Score " + playerWins + ":" + dealerWins);

                        break;
                    }
                } else {
                    break;
                }
            }
            if (flag == 1) {
                if (numOfRoundsFlag==0) {
                    break;
                }
                continue;
            }

            System.out.println("Dealer's move");
            System.out.println("-------");
            System.out.println("Dealer is opening closed card " + dealer.hand.get(1));

            player.printHand();
            dealer.printHand();

            while (dealer.getPointForPlayer() < 17) {
                dealer.addCard(deck.getCardFromDeck());

                System.out.println("Dealer is opening card " + dealer.hand.get(player.hand.size()-1));

                player.printHand();
                dealer.printHand();
            }

            if (dealer.isLoser()) {
                playerWins++;

                System.out.println("You have won the round! Score " + playerWins + ":" + dealerWins );

                if (numOfRoundsFlag==0) {
                    break;
                }
            } else if (player.getPointForPlayer() > dealer.getPointForPlayer()) {
                playerWins++;

                System.out.println("You have won the round! Score " + playerWins + ":" + dealerWins );

                if (numOfRoundsFlag==0) {
                    break;
                }
            } else if (player.getPointForPlayer() < dealer.getPointForPlayer()) {
                dealerWins++;

                System.out.println("Dealer has won the round. Score " + playerWins + ":" + dealerWins);

                if (numOfRoundsFlag==0) {
                    break;
                }
            } else {
                playerWins++;
                dealerWins++;

                System.out.println("It's a tie. Score " + playerWins + ":" + dealerWins);

                if (numOfRoundsFlag==0) {
                    break;
                }
            }
        }
    }
}