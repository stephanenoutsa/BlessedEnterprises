package com.blessedenterprises.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by stephnoutsa on 9/13/16.
 */
public class RandomGenerator {

    public RandomGenerator() {

    }

    public String generate() {
        List<Integer> digits = new ArrayList<>();
        String code = "";

        // Populate list of unique digits from 0 to 9
        for (int i = 0; i < 10; i++) {
            digits.add(i);
        }

        // Shuffle the list
        Collections.shuffle(digits);

        // Generate 4-digit code
        for (int i = 0; i < 4; i++) {
            code += digits.get(i).toString();
        }

        return code;
    }

}
