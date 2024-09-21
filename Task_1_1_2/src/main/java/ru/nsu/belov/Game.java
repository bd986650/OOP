package ru.nsu.belov;

import java.util.*;

public class Game {
    private Deck deck;
    private Player player;
    private Dealer dealer;

    public Game() {
        deck = new Deck();
        player = new Player("You");
        dealer = new Dealer();
    }

    public void start_game() {
        int count_round = 1;
        int count_player_wins = 0;
        int count_dealer_wins = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Blackjack!");

        while(true) {
            player.hand.clear();
            dealer.hand.clear();

            System.out.println("=====================");
            System.out.println("Round " + count_round);
            count_round++;

            System.out.println("The dealer dealt the cards");

            player.add_card(deck.get_card_from_deck());
            player.add_card(deck.get_card_from_deck());

            dealer.add_card(deck.get_card_from_deck());
            dealer.add_card(deck.get_card_from_deck());

            player.print_hand();
            dealer.show_start_hand();

            if (player.is_blackjack() && dealer.is_blackjack()) {
                count_player_wins++;
                count_dealer_wins++;

                System.out.println("It's a tie. Score " + count_player_wins + ":" + count_dealer_wins);

                continue;
            } else if (player.is_blackjack()) {
                count_player_wins++;

                System.out.println("You have Blackjack! You have won the round. Score " + count_player_wins + ":" + count_dealer_wins);

                continue;
            } else if (dealer.is_blackjack()) {
                count_dealer_wins++;

                System.out.println("Dealer has Blackjack! Dealer has won the round. Score " + count_player_wins + ":" + count_dealer_wins);

                continue;
            }

            System.out.println("Your move");
            System.out.println("-------");

            int flag = 0;

            while(true) {
                System.out.print("Enter '1' to take the card, and '0' to stop...");

                String user_choice = scanner.nextLine();
                if(user_choice.equals("1")) {
                    player.add_card(deck.get_card_from_deck());
                    int index_for_get_opened_card = player.hand.size()-1;
                    System.out.println("You have opened the card "+ player.hand.get(index_for_get_opened_card));

                    player.print_hand();
                    dealer.show_start_hand();

                    if (player.is_loser()) {
                        flag = 1;
                        count_dealer_wins++;

                        System.out.println("You've lost the round. Score " + count_player_wins + ":" + count_dealer_wins);

                        break;
                    }
                } else {
                    break;
                }
            }

            if (flag == 1) {
                continue;
            }

            System.out.println("Dealer's move");
            System.out.println("-------");
            System.out.println("Dealer is opening closed card " + dealer.hand.get(1));

            player.print_hand();
            dealer.print_hand();

            while(dealer.get_point_for_player() < 17) {
                dealer.add_card(deck.get_card_from_deck());

                int index = dealer.hand.size() - 1;
                System.out.println("Dealer is opening card " + dealer.hand.get(index));

                player.print_hand();
                dealer.print_hand();
            }

            if (dealer.is_loser()) {
                count_player_wins++;

                System.out.println("You have won the round! Score " + count_player_wins + ":" + count_dealer_wins );
            } else if (player.get_point_for_player() > dealer.get_point_for_player()) {
                count_player_wins++;

                System.out.println("You have won the round! Score " + count_player_wins + ":" + count_dealer_wins );
            } else if (player.get_point_for_player() < dealer.get_point_for_player()) {
                count_dealer_wins++;

                System.out.println("Dealer has won the round. Score " + count_player_wins + ":" + count_dealer_wins);
            } else {
                count_player_wins++;
                count_dealer_wins++;

                System.out.println("It's a tie. Score " + count_player_wins + ":" + count_dealer_wins);
            }
        }
    }
}
