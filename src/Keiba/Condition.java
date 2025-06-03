package Keiba;
import java.util.Random;

enum Condition {
    EXCELLENT("☆ ", 1.20, 2.00),
    GOOD("〇", 1.10, 1.90),
    FAIR("▲ ", 1.00, 1.80),
    POOR("△ ", 0.90, 1.70),
    BAD("× ", 0.80, 1.60);

    private final String mark;
    private final double min;
    private final double max;
    private static final Random r = new Random();
    Condition(String mark, double min, double max) {
        this.mark = mark;
        this.min = min;
        this.max = max;
    }
    public String getMark() { return mark; }
    public double getMin() { return min; }
    public double getMax() { return max; }
    public double getMedian() { return (min + max) / 2.0; }
    public double getRandomFactor() { return min + r.nextDouble() * (max - min); }
    public static Condition getRandomCondition() {
        Condition[] values = Condition.values();
        return values[r.nextInt(values.length)];
    }
    public static Condition getWeightedRandomCondition() {
        int roll = r.nextInt(100);
        if (roll < 20) return EXCELLENT;
        else if (roll < 60) return GOOD;
        else if (roll < 80) return FAIR;
        else if (roll < 90) return POOR;
        else return BAD;
    }
}
