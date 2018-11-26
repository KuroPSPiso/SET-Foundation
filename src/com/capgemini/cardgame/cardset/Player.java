package com.capgemini.cardgame.cardset;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> cards;
    private boolean active;

    public Player(String name, Card... cards)
    {
        this.name = name;
        this.cards = new ArrayList<>();
        for(Card card : cards)
        {
            this.cards.add(card);
        }
    }

    public int getScore()
    {
        int score = 0;
        for (Card card : this.cards)
        {
            score += card.cardIndex.number;
        }
        return score;
    }

    public int getAltScore()
    {
        int altScore = 0;
        for (Card card : this.cards)
        {
            altScore += card.cardIndex.altNumber;
        }
        return altScore;
    }

    public boolean isBust()
    {
        if(getScore() > 21 && getAltScore() > 21)
        {
            return true;
        }
        return false;
    }

    public boolean isActive()
    {
        return this.active;
    }

    public void hit(Card card) {
        this.cards.add(card);
        if(isBust()) active = false;
    }

    @Override
    public String toString() {
        String s = null;
        String score = null;
        for(Card card : this.cards)
        {
            if(s == null) s = card.toString();
            else s += ", " + card.toString();
        }
        //when a player has a ace the score can be seen as 1 or 11.
        if(getAltScore() != getScore())
            score = String.format("%d or %d", getScore(), getAltScore());
        else
            score = Integer.toString(getScore());
        return String.format("%s has:\n%s\npoints:%s", this.name, s, score);
    }
}
