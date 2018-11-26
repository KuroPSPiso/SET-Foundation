package com.capgemini.cardgame.blackjack;

import com.capgemini.calendar.CalendarFormatting;
import com.capgemini.cardgame.cardset.Card;
import com.capgemini.cardgame.cardset.CardIndex;
import com.capgemini.cardgame.cardset.CardType;
import com.capgemini.cardgame.cardset.Player;
import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main_BlackJack {

    public static List<Card> blackJackDeck;

    public static void shuffleDeck()
    {
        blackJackDeck.clear();
        for(CardType type : CardType.values())
        {
            blackJackDeck.add(new Card(type, new CardIndex("Ace", 1, 11)));
            blackJackDeck.add(new Card(type, new CardIndex("King", 10)));
            blackJackDeck.add(new Card(type, new CardIndex("Queen", 10)));
            blackJackDeck.add(new Card(type, new CardIndex("Bischop", 10)));
            for(int pip = 2; pip <= 10; pip++) //2-10
            {
                blackJackDeck.add(new Card(type, new CardIndex(pip)));
            }
        }
    }

    public static Card getRandomCardFromDeck()
    {
        Random random = new Random();
        Card card = blackJackDeck.get(random.nextInt(blackJackDeck.size()));
        blackJackDeck.remove(card);
        return card;
    }

    public static void main(String[] args) {
        blackJackDeck = new ArrayList<>();
        shuffleDeck();
        Player player = new Player("Player 1", getRandomCardFromDeck(), getRandomCardFromDeck());
        Player dealer = new Player("Dealer", getRandomCardFromDeck(), getRandomCardFromDeck());

        //Data collection from io stream
        Scanner sc = new Scanner(System.in);
        //Boolean to create a looping cycle
        boolean isRunning = true;
        while(isRunning)
        {
            //print score
            printScoreBoard(player, dealer);

            if(player.getAltScore() == 21 || player.getScore() == 21)
            {
                System.out.println("you won");
                isRunning = false;
                break;
            }
            if(dealer.getAltScore() == 21 || dealer.getScore() == 21)
            {
                System.out.println("you won");
                isRunning = false;
                break;
            }

            //get answer loop internally when failed
            failedReturn:
            for(;;) {
                System.out.println("hit or keep:");
                String data = sc.nextLine();
                //expected result is either exit or a number so remove all spaces.
                if(data.toLowerCase().equals("hit"))
                {
                    player.hit(getRandomCardFromDeck());
                    if(player.isBust())
                    {
                        System.out.println("Bust");
                        isRunning = false; //exit program if bust (can change to play again or something)
                    }
                }
                else if(data.toLowerCase().equals("keep"))
                {
                    //check if lost by not looking at the score
                    if(
                        player.getScore() < dealer.getScore() &&
                        player.getScore() < dealer.getAltScore() &&
                        player.getAltScore() < dealer.getScore() &&
                        player.getAltScore() < dealer.getAltScore()
                    )
                    {
                        System.out.println("You lost");
                        isRunning = false;
                        break failedReturn;
                    }

                    while(
                        (dealer.getScore() < 17 || dealer.getAltScore() < 17) ||
                        (player.getScore() <= 21 && (dealer.getScore() < player.getScore()) || dealer.getAltScore() < player.getScore())||
                        (player.getAltScore() <= 21 && (dealer.getScore() < player.getAltScore()) || dealer.getAltScore() < player.getAltScore())
                    )
                    {
                        dealer.hit(getRandomCardFromDeck());
                        printScoreBoard(player, dealer);
                        if(dealer.isBust())
                        {
                            System.out.println("You won");
                            isRunning = false;
                            break failedReturn;
                        }
                    }
                    //check if draw
                    if(
                            player.getScore() == dealer.getScore() ||
                            player.getScore() == dealer.getAltScore() ||
                            player.getAltScore() == dealer.getScore() ||
                            player.getAltScore() == dealer.getAltScore()
                    )
                    {
                        System.out.println("Draw house lost.");
                    }
                    //check if lost
                    if(
                            player.getScore() < dealer.getScore() &&
                                    player.getScore() < dealer.getAltScore() &&
                                    player.getAltScore() < dealer.getScore() &&
                                    player.getAltScore() < dealer.getAltScore()
                    )
                    {
                        System.out.println("You lost");
                        isRunning = false;
                        break failedReturn;
                    }
                }
                //check for exit else return
                else if (data.equals("exit")) {
                    isRunning = false;
                    break failedReturn;
                }
                else
                {
                    continue failedReturn;
                }
                break failedReturn;
            }
        }
    }

    private static void printScoreBoard(Player player, Player dealer) {
        System.out.println("-------------------------");
        System.out.println(dealer.toString());
        System.out.println();
        System.out.println(player.toString());
        System.out.println();
    }
}
