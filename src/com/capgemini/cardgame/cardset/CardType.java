package com.capgemini.cardgame.cardset;

public enum CardType {
    HEARTS('♥', "of Hearts"), CLUBS('♠', "of Clubs"), CLOVER('♣', "of Clovers"), DIAMOND('♦', "of Diamonds");

    private char character;
    private String value;

    CardType(char character, String value) {
        this.character = character;
        this.value = value;
    }

    public char getCharacter() {
        return this.character;
    }

    public String getValue() {
        return this.value;
    }
}