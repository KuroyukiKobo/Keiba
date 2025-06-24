package Keiba;

import java.util.Random;

public class Color {

    private static final Random r = new Random();

    private String red = "\u001b[00;31m";
    private String green = "\u001b[00;32m";
    private String yellow = "\u001b[00;33m";
    private String purple = "\u001b[00;34m";
    private String pink = "\u001b[00;35m";
    private String cyan = "\u001b[00;36m";
    private String end = "\u001b[00m";

    public String serColorRed(String str) {
        return String.format("%s%s%s", red, str, end);
    }

    public String randomColor(String str) {
        String[] names = new String[] { red, green, yellow, purple, pink, cyan };
        return String.format("%s%s%s", names[r.nextInt(names.length)], str, end);
    }
}
