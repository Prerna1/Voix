package com.example.sunejas.voix.Utilities;

import java.util.Random;

/**
 * Created by Suneja's on 25-06-2018.
 */

public class GenerateRandomString {

    public static final String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static Random RANDOM = new Random();

    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < len; i++) {
            sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
        }

        return sb.toString();
    }

}
