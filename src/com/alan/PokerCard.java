package com.alan;


public class PokerCard implements Comparable<PokerCard> {

    private final int value;
    private final CardSuit suit;

    public PokerCard(int value, CardSuit suit) {
        this.value = value;
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public CardSuit getSuit() {
        return suit;
    }

    @Override
    public int compareTo(PokerCard o) {
        return Integer.compare(value, o.getValue());
    }

    public enum CardSuit {
        SPADES,
        HEARTS,
        DIAMONDS,
        CLUBS,
    }
}
