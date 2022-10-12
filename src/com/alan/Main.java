package com.alan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> uuu = new ArrayList<>();
        uuu.add(1);
        uuu.add(2);
        uuu.add(2);
        uuu.add(3);
        for (int i = 0; i < uuu.size(); i++) {
            System.out.println(i + "  " + uuu.get(i));
        }
    }
}
