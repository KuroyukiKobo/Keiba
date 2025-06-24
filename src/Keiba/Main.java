package Keiba;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Color color = new Color();
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
        if (money <= 0)
            System.out.println(String.format("あなたは破産しました。"));
        if (money < 0)
            System.out.println(String.format("借金額：%s", color.serColorRed(String.format("%d", money * -1))));
        if (money > 0)
            System.out.println(String.format("最終資金：%d", money));
        sc.close();
    }
}
