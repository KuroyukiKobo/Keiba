package Keiba;
import java.util.Random;

class Horse {
    private static final Random r = new Random();
    private String name;
    private int speed;
    private Condition condition;
    private double odds;
    private int placement;
    private int popularity;
    private int number;
    private final int MAX_SPEED = 150;
    private final int MIN_SPEED = 100;

    public Horse(int number) {
        name = generateName();
        speed = generateSpeed();
        condition = Condition.getWeightedRandomCondition();
        this.number = number;
    }

    public String generateName() {
        String[] prefixes = { "サトノ", "ヒシ", "ミホノ", "トウカイ", "シンボリ", "メジロ", "ナリタ", "テイエム", "マイネル", "エア" };
        String[] start = { "ア", "キ", "サ", "ナ", "ミ", "リ", "ト", "ハ", "ユ", "レ", "エ", "カ", "モ", "ズ", "シ", "フ", "オ", "グ", "ディ", "ジャ", "ヴ" };
        String[] mid = { "ノ", "リュ", "ラ", "ン", "ツ", "カ", "ネ", "ト", "ズ", "ウィ", "ディ", "シ", "ブ", "オ" };
        String[] end = { "ト", "ン", "ア", "オ", "カ", "ド", "ム", "ラ", "ーン", "ル", "シ", "リア", "フィ", "ス", "ン", "ズ" };
        String name;
        do {
            String prefix = r.nextInt(10) > 8 ? prefixes[r.nextInt(prefixes.length)] : "";
            String core = start[r.nextInt(start.length)];
            if (r.nextInt(10) > 3)
                core += mid[r.nextInt(mid.length)];
            core += end[r.nextInt(end.length)];
            if (r.nextInt(10) > 7)
                core += "ー";
            name = prefix + core;
        } while (name.length() < 5);
        return name;
    }

    public int generateSpeed() {
        return r.nextInt(MAX_SPEED - MIN_SPEED + 1) + MIN_SPEED;
    }

    public String getName() { return name; }
    public void setPlacement(int placement) { this.placement = placement; }
    public int getPlacement() { return placement; }
    public void setOdds(double odds) { this.odds = odds; }
    public double getOdds() { return odds; }
    public void setPopularity(int popularity) { this.popularity = popularity; }
    public int getPopularity() { return popularity; }
    public int getSpeed() { return speed; }
    public Condition getCondition() { return condition; }
    public int getNumber() { return number; }
    public String getHorseInfo() {
        if (name.length() > 8)
            return String.format("[ %d ]|%s| %s |%.1f倍\t|%s番人気", number, name, condition.getMark(), odds, popularity);
        return String.format("[ %d ]|%s\t| %s |%.1f倍\t|%s番人気", number, name, condition.getMark(), odds, popularity);
    }
    public String getResult() {
        return String.format("%d着：[ %d ]%s %d番人気\n", placement, number, name, popularity);
    }
}
