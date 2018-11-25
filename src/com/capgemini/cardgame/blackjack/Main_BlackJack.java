package com.capgemini.cardgame.blackjack;

import com.capgemini.calendar.CalendarFormatting;
import com.capgemini.cardgame.cardset.Card;
import com.capgemini.cardgame.cardset.CardIndex;
import com.capgemini.cardgame.cardset.CardType;
import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main_BlackJack {

    public static List<Card> blackJackDeck;


    public static void main(String[] args) {
        blackJackDeck = new ArrayList<>();
        for(CardType type : CardType.values())
        {
            blackJackDeck.add(new Card(type, new CardIndex("Ace", 1, 11)));
            blackJackDeck.add(new Card(type, new CardIndex("King", 10)));
            blackJackDeck.add(new Card(type, new CardIndex("Queen", 10)));
            blackJackDeck.add(new Card(type, new CardIndex("Bischop", 10)));
            for(int pip = 0; pip < 10; pip++)
            {
                blackJackDeck.add(new Card(type, new CardIndex(pip)));
            }
        }

        //Data collection from io stream
        Scanner sc = new Scanner(System.in);
        //Boolean to create a looping cycle
        boolean isRunning = true;
        while(isRunning)
        {
            //print question
            System.out.println("enter a year to get the calendar for that year, enter exit to close the application: ");
            //get answer loop internally when failed
            failedReturn:
            for(;;) {
                String data = sc.next();
                //expected result is either exit or a number so remove all spaces.
                data = StringUtils.trimWhitespace(data);

                //try to parse to a number, if it fails check if it's equal to exit else return.
                try {
                    int year = Integer.parseInt(data);
                    System.out.println(CalendarFormatting.CalendarOfYear(year, 2));
                    break failedReturn;
                } catch (NumberFormatException nfe) {
                    //check for exit else return
                    if (data.equals("exit")) {
                        isRunning = false;
                        break failedReturn;
                    }
                    else {
                        System.out.println("Please enter a number or type exit: ");
                        continue failedReturn;
                    }
                }
            }
        }
    }
}
