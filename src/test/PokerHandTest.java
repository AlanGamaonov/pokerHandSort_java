package test;

import com.alan.PokerHand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PokerHandTest {
    @Test
    public void validTest() {
        String first = "2C 3C 4C 5C 6C";
        String second = "2S 8H TD AD AC";
        check(first, second);
    }

    @Test
    public void straightFlashBeatsWeaker() {
        String first = "5S 6S 7S 8S 9S";
        String second = "2C 3C 4C 5C 6C";
        check(first, second);
    }

    @Test
    public void fourBeatsWeaker() {
        String first = "2C 2S 2D 2C 9S";
        String second = "2C 2S 2D 2C 5S";
        check(first, second);
    }

    @Test
    public void highCardBeatsWeaker() {
        String first = "KC QS 2D 2C 9S";
        String second = "KS JS 2D 2C 5S";
        check(first, second);
    }

    // Дальше надо было бы проверить ещё комбинации, но я поленился((

    private void check(String first, String second) {
        ArrayList<PokerHand> hands = new ArrayList<>();
        hands.add(new PokerHand(first));
        hands.add(new PokerHand(second));

        Collections.sort(hands);
        assertEquals(first, hands.get(1).getHandDefinition());
        assertEquals(second, hands.get(0).getHandDefinition());
        assertTrue(hands.get(1).getHandStrength() > hands.get(0).getHandStrength());
    }
}