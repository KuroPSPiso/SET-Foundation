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
            for(int pip = 1; pip < 10; pip++)
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
            //print question
            System.out.println(dealer.toString());
            System.out.println(player.toString());
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
                        isRunning = false;
                    }
                }
                else if(data.toLowerCase().equals("keep"))
                {
                }
                //check for exit else return
                else if (data.equals("exit")) {
                    isRunning = false;
                }
                else
                {
                    continue failedReturn;
                }
                break failedReturn;
            }
        }
    }
}
