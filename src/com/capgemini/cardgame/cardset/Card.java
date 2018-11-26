package com.capgemini.cardgame.cardset;

public class Card {
    CardType cardType;
    CardIndex cardIndex;
    boolean visible;

    public Card(CardType cardType, CardIndex cardIndex) {
        this.cardType = cardType;
        this.cardIndex = cardIndex;
        this.visible = true;
    }

    public Card(CardType cardType, CardIndex cardIndex, boolean visible) {
        this.cardType = cardType;
        this.cardIndex = cardIndex;
        this.visible = visible;
    }

    public CardType getCardType() {
        return cardType;
    }

    public CardIndex getCardIndex() {
        return cardIndex;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return String.format("%s %s(%c)", cardIndex.name, cardType.getValue(), cardType.getCharacter());
    }
}
