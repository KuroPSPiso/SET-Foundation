package com.capgemini.cardgame.cardset;

public class Card {
    CardType cardType;
    CardIndex cardIndex;

    public Card(CardType cardType, CardIndex cardIndex) {
        this.cardType = cardType;
        this.cardIndex = cardIndex;
    }

    public CardType getCardType() {
        return cardType;
    }

    public CardIndex getCardIndex() {
        return cardIndex;
    }
}
