package com.alan;

import java.util.*;

public class PokerHand implements Comparable<PokerHand> {

    private final String handDefinition;
    private final ArrayList<PokerCard> hand;
    private final HashMap<Integer, Integer> cardValues;
    private final boolean isStraight;
    private final boolean isFlush;
    private final int differentValuesCount;
    private final int maxOfSameValue;
    private final long handStrength;

    public PokerHand(String handDefinition) {
        // Все карты отсортированы по возрастанию
        hand = HandParser.parse(handDefinition);
        this.handDefinition = handDefinition;
        // Подсчёт кол-ва карт каждого номинала
        cardValues = countValues();

        isStraight = checkIfStraight();
        isFlush = checkIfFlush();
        differentValuesCount = cardValues.size();
        maxOfSameValue = Collections.max(cardValues.values());
        handStrength = calculateHandStrength();
    }

    public long getHandStrength() {
        return handStrength;
    }

    public String getHandDefinition() {
        return handDefinition;
    }

    private long calculateHandStrength() {
        if (isStraight && isFlush)
            return 80000000000L + getHighestCardValue();
        if (isFour())
            return 70000000000L + getKey(cardValues, 4) * 100L + getKey(cardValues, 1);
        if (isFullHouse())
            return 60000000000L + getKey(cardValues, 3) * 100L + getKey(cardValues, 2);
        if (isFlush)
            return 50000000000L + handRawStrength();
        if (isStraight)
            return 40000000000L + getHighestCardValue();
        if (isThree())
            return 30000000000L + threeStrength();
        if (isTwoPairs())
            return 20000000000L + twoPairsStrength();
        if (isPair())
            return 10000000000L + pairStrength();
        return handRawStrength();
    }

    private boolean checkIfStraight() {
        return hand.get(4).getValue() - hand.get(0).getValue() == 4;
    }

    private boolean checkIfFlush() {
        PokerCard.CardSuit suit = hand.get(0).getSuit();
        for (int i = 1; i <= 4; i++) {
            if (hand.get(i).getSuit() != suit)
                return false;
        }
        return true;
    }

    private boolean isFour() {
        return maxOfSameValue == 4;
    }

    private boolean isThree() {
        return maxOfSameValue == 3;
    }

    private boolean isPair() {
        return maxOfSameValue == 2;
    }

    private boolean isFullHouse() {
        return differentValuesCount == 2 && isThree();
    }

    private boolean isTwoPairs() {
        return isPair() && hand.size() == 3;
    }

    private long handRawStrength() {
        return hand.get(4).getValue() * 100000000L
                + hand.get(3).getValue() * 1000000L
                + hand.get(2).getValue() * 10000L
                + hand.get(1).getValue() * 100L
                + hand.get(0).getValue();
    }

    private long threeStrength() {
        int threeValue = getKey(cardValues, 3);
        ArrayList<PokerCard> noThree = (ArrayList<PokerCard>) hand.clone();
        // Избавляемся от тройки карт одинакового номинала. Порядок возрастания сохраняется
        noThree.removeIf(e -> e.getValue() == threeValue);

        return threeValue * 10000L + noThree.get(1).getValue() * 100L + noThree.get(0).getValue();
    }

    private long twoPairsStrength() {
        int lonelyValue = getKey(cardValues, 1);
        ArrayList<PokerCard> noThree = (ArrayList<PokerCard>) hand.clone();
        // Избавляемся от единственной карты без пары. Порядок возрастания сохраняется
        noThree.removeIf(e -> e.getValue() == lonelyValue);

        return noThree.get(3).getValue() * 10000L + noThree.get(1).getValue() * 100L + lonelyValue;
    }

    private long pairStrength() {
        int pairValue = getKey(cardValues, 1);
        ArrayList<PokerCard> noThree = (ArrayList<PokerCard>) hand.clone();
        // Избавляемся от пары карт одинакового номинала. Порядок возрастания сохраняется
        noThree.removeIf(e -> e.getValue() == pairValue);

        return pairValue * 1000000L + noThree.get(2).getValue() * 10000L + noThree.get(1).getValue() * 100L + noThree.get(0).getValue();
    }

    private HashMap<Integer, Integer> countValues() {
        //Карт 13 для каждой масти, но начинаются они с 2, а кончаются 14 (туз)
        int[] values = new int[15];
        for (PokerCard card : hand) {
            values[card.getValue()]++;
        }

        HashMap<Integer, Integer> result = new HashMap<>();
        for (int i = 0; i < 15; i++)
             result.put(i, values[i]);

        result.values().removeIf(e -> e == 0);
        return result;
    }

    private int getHighestCardValue() {
        return hand.get(4).getValue();
    }

    private Integer getKey(Map<Integer, Integer> map, Integer value) {
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    @Override
    public int compareTo(PokerHand o) {
        return Long.compare(this.handStrength, o.getHandStrength());
    }
}
