package com.capgemini.cardgame.cardset;

public class CardIndex {
    int number;
    int altNumber; //used for Ace which can be 1 or 11;
    String name;

    public CardIndex(String name, int number)
    {
        this.name = name;
        this.number = number;
        this.altNumber = number;
    }

    public CardIndex(String name, int number, int altNumber)
    {
        this.name = name;
        this.number = number;
        this.altNumber = altNumber;
    }
}
