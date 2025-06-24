package Keiba;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Keiba {

    private static final Random r = new Random();
    Scanner sc = new Scanner(System.in);

    private int bet;

    private int money;

    private int ticketType;

    private final int WEIGHT = 12;

    private final int ENTRY_COUNT = 16;

    public Keiba(int money){
        this.money = money;
    }

    private List<Horse> entryList = IntStream.rangeClosed(1, ENTRY_COUNT)
            .boxed()
            .map(Horse::new)
            .collect(Collectors.toList());

    public void generateOdds() {
        Map<Integer, Double> powerMap = entryList.stream()
                .collect(Collectors.toMap(
                        Horse::getNumber,
                        horse -> Math.pow(horse.getSpeed(), WEIGHT)));
        double total = powerMap.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        for (Horse horse : entryList) {
            double horsePower = powerMap.get(horse.getNumber());
            double winRate = horsePower / total;
            double odds = 1.0 / winRate;

            horse.setOdds(odds);
        }
    }

    public String generateRaceTitle() {
        String[] formats = { "第%s回", "春の", "秋の", "令和%s年", "" };
        String[] symbols = { "桜花", "紅葉", "天翔", "雷鳴", "宝珠", "黒龍", "鳳凰", "風雅", "武蔵", "富嶽" };
        String[] types = { "記念", "カップ", "ステークス", "特別", "賞", "チャレンジ", "ダービー" };

        String format = formats[r.nextInt(formats.length)];

        String prefix = format.contains("%s")
                ? String.format(format, r.nextInt(30) + 1)
                : format;

        String symbol = symbols[r.nextInt(symbols.length)];
        String type = types[r.nextInt(types.length)];

        return prefix + symbol + type;
    }

    public double speed(Horse horse) {
        Condition condition = horse.getCondition();
        return horse.getSpeed() * (r.nextDouble() * (condition.getMax() - condition.getMin()) + condition.getMin());
    }

    public void setAllPopularity() {
        entryList.sort((h1, h2) -> {
            return Double.compare(h1.getOdds(), h2.getOdds());
        });
        for (int i = 0; i < entryList.size(); i++) {
            entryList.get(i).setPopularity(i + 1);
        }
    }

    public String getRaceInfo(List<Horse> horseList) {
        entryList.sort((h1, h2) -> {
            return Integer.compare(h1.getNumber(), h2.getNumber());
        });
        String raceInfo = "";
        raceInfo += String.format("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n"
                + "%s 出走頭数：%d頭\n"
                + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n"
                + "【出走馬一覧】\n"
                + "[ 枠番 ]|馬名  \t|調子 |単勝オッズ\t|人気\n"
                + "----------------------------------------------------------\n", generateRaceTitle(), ENTRY_COUNT);

        raceInfo += IntStream.range(0, horseList.size())
                .mapToObj(i -> horseList.get(i).getHorseInfo())
                .collect(Collectors.joining("\n"));

        raceInfo += "\n----------------------------------------------------------";

        return raceInfo;
    }

    public String getRaceResult() {
        String raceResult = "";
        raceResult += String.format("【レース結果】\n");
        raceResult += entryList.get(0).getResult();
        raceResult += entryList.get(1).getResult();
        raceResult += entryList.get(2).getResult();
        return raceResult;
    }

    public int checkPayOut(int bet, int ticketType, Ticket ticket) {
        switch (ticketType) {
            case 1:
                return Integer.parseInt(String.format("%.0f", bet * ticket.win()));
            case 2:
                return Integer.parseInt(String.format("%.0f", bet * ticket.place()));
            case 3:
                return Integer.parseInt(String.format("%.0f", bet * ticket.trifecta()));
            case 4:
                return Integer.parseInt(String.format("%.0f", bet * ticket.trio()));
            default:
                return 0;
        }
    }

    public void ranking() {
        entryList.sort((h1, h2) -> {
            return Double.compare(speed(h2), speed(h1));
        });
        for (int i = 0; i < entryList.size(); i++) {
            entryList.get(i).setPlacement(i + 1);
        }
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void Race() {
        generateOdds();
        setAllPopularity();
        System.out.println(getRaceInfo(entryList));

        System.out.println(String.format("賭ける券種を番号で選択してください。\n"
                + "1：単勝\n"
                + "2：複勝\n"
                + "3：三連単\n"
                + "4：三連複"));

        ticketType = sc.nextInt();

        int first = 0;
        int second = 0;
        int third = 0;
        Ticket ticket = null;
        switch (ticketType) {
            case 1:
            case 2:
                System.out.print("賭ける馬の番号を入力してください：");
                first = sc.nextInt();
                while (first > ENTRY_COUNT) {
                    System.out.println("その番号の馬は登録されていません。再度入力して下さい。");
                    first = sc.nextInt();
                }
                ticket = new Ticket(entryList.get(first - 1));
                break;

            case 3:
                System.out.print("1着予想の馬の番号を入力して下さい：");
                first = sc.nextInt();
                while (first > ENTRY_COUNT) {
                    System.out.println("その番号の馬は登録されていません。再度入力して下さい。");
                    first = sc.nextInt();
                }
                System.out.print("2着予想の馬の番号を入力して下さい：");
                second = sc.nextInt();
                while (second > ENTRY_COUNT) {
                    System.out.println("その番号の馬は登録されていません。再度入力して下さい。");
                    second = sc.nextInt();
                }
                System.out.print("3着予想の馬の番号を入力して下さい：");
                third = sc.nextInt();
                while (third > ENTRY_COUNT) {
                    System.out.println("その番号の馬は登録されていません。再度入力して下さい。");
                    third = sc.nextInt();
                }
                ticket = new Ticket(
                        entryList.get(first - 1), entryList.get(second - 1), entryList.get(third - 1));
                break;
            case 4:
                System.out.print("3着までに入る馬の番号を1頭ずつ入力してください：");
                first = sc.nextInt();
                while (first > ENTRY_COUNT) {
                    System.out.println("その番号の馬は登録されていません。再度入力して下さい。");
                    first = sc.nextInt();
                }
                System.out.print("次の馬の番号を入力してください：");
                second = sc.nextInt();
                while (second > ENTRY_COUNT) {
                    System.out.println("その番号の馬は登録されていません。再度入力して下さい。");
                    second = sc.nextInt();
                }
                System.out.print("次の馬の番号を入力してください：");
                third = sc.nextInt();
                while (third > ENTRY_COUNT) {
                    System.out.println("その番号の馬は登録されていません。再度入力して下さい。");
                    third = sc.nextInt();
                }
                ticket = new Ticket(
                        entryList.get(first - 1), entryList.get(second - 1), entryList.get(third - 1));
                break;
            default:
                break;
        }
        System.out.println(String.format("現在の所持金：%d", getMoney()));
        System.out.print("賭ける金額を入力してください：");
        bet = sc.nextInt();
        while (getMoney() * 3 < bet) {
            System.out.println(String.format("所持金の2倍以上の借金はできません。\n"
                    + "入力は%d以下としてください", getMoney() * 3));
            bet = sc.nextInt();
        }
        setMoney(getMoney() - bet);

        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(800);
                System.out.println("…");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        ranking();
        System.out.println(getRaceResult());
        int ret = checkPayOut(bet, ticketType, ticket);
        setMoney(getMoney() + ret);
        System.out.println(String.format("払戻金：%d円", ret));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean play = true;
        int money = 100000;
        while (play) {
            Keiba keiba = new Keiba(money);
            System.out.println(String.format("あなたの軍資金は%d円です", money));
            keiba.Race();
            money = keiba.getMoney();
            if (money <= 0) {
                break;
            }
            System.out.println(String.format("あなたの軍資金は%d円です", money));
            System.out.println(String.format("続けますか?y/n"));
            String input = sc.nextLine();
            while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                System.out.println(String.format("入力はY,y,N,nのみ受け付けています"));
                input = sc.nextLine();
            }
            if (input.equalsIgnoreCase("n"))
                play = false;
        }
        if(money <= 0)
            System.out.println(String.format("あなたは破産しました。"));
        if (money < 0)
            System.out.println(String.format("借金額：%d", money * -1));
        if(money > 0)
            System.out.println(String.format("最終資金：%d", money));
        sc.close();
    }
}