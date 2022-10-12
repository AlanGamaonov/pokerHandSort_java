package com.alan;

import java.util.ArrayList;
import java.util.Collections;

public class HandParser {

    public static ArrayList<PokerCard> parse(String strHand) {
        ArrayList<PokerCard> cards = new ArrayList<>();
        for (String strCard : strHand.split("\\s+")) {
            cards.add(parseCard(strCard));
        }
        if (cards.size() != 5)
            throw new IllegalArgumentException(cards.size() + " is not valid size of hand (must be 5)");
        Collections.sort(cards);
        return cards;
    }

    private static PokerCard parseCard(String strCard) {
        if (strCard.length() != 2)
            throw new IllegalArgumentException(strCard + "is not valid card");

        char charValue = strCard.charAt(0);
        Integer numValue = null;
        PokerCard.CardSuit suit = null;

        if (Character.isDigit(charValue))
            numValue = Character.getNumericValue(charValue);
        else switch (charValue) {
            case 'T':
                numValue = 10; break;
            case 'J':
                numValue = 11; break;
            case 'Q':
                numValue = 12; break;
            case 'K':
                numValue = 13; break;
            case 'A':
                numValue = 14; break;
        }
        if (numValue == null)
            throw new IllegalArgumentException(charValue + " is not valid card value");

        switch (strCard.charAt(1)) {
            case 'S':
                suit = PokerCard.CardSuit.SPADES; break;
            case 'H':
                suit = PokerCard.CardSuit.HEARTS; break;
            case 'C':
                suit = PokerCard.CardSuit.CLUBS; break;
            case 'D':
                suit = PokerCard.CardSuit.DIAMONDS; break;
        }
        if (suit == null)
            throw new IllegalArgumentException(strCard.charAt(1) + "is not valid card suit");

        return new PokerCard(numValue, suit);
    }
}
