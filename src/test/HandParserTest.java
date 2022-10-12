package test;

import com.alan.HandParser;
import com.alan.PokerCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HandParserTest {
    @Test
    public void validTest() {
        ArrayList<PokerCard> cards = HandParser.parse("KS 2H 5C JD TD");

        assertEquals(2, cards.get(0).getValue());
        assertEquals(PokerCard.CardSuit.HEARTS, cards.get(0).getSuit());

        assertEquals(5, cards.get(1).getValue());
        assertEquals(PokerCard.CardSuit.CLUBS, cards.get(1).getSuit());

        assertEquals(10, cards.get(2).getValue());
        assertEquals(PokerCard.CardSuit.DIAMONDS, cards.get(2).getSuit());

        assertEquals(11, cards.get(3).getValue());
        assertEquals(PokerCard.CardSuit.DIAMONDS, cards.get(3).getSuit());

        assertEquals(13, cards.get(4).getValue());
        assertEquals(PokerCard.CardSuit.SPADES, cards.get(4).getSuit());
    }

    @Test
    public void emptyHandTest() {
        assertThrows(IllegalArgumentException.class, () -> HandParser.parse(""));
    }

    @Test
    public void invalidHandSizeTest() {
        assertThrows(IllegalArgumentException.class, () -> HandParser.parse("KS 2H 5C JD"));

        assertThrows(IllegalArgumentException.class, () -> HandParser.parse("KS 2H 5C JD TD AS"));
    }
}