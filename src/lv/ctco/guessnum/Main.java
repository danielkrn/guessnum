package lv.ctco.guessnum;

import java.util.*;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static Random rand = new Random();
    static List<GameResult> results = new ArrayList<>();

    public static void main(String[] args) {
        do {
            System.out.println("What is your name?");
            String name = scan.next();
            System.out.println("Hello, " + name + "!");
            int myNum = rand.nextInt(100) + 1;
            System.out.println("Spoiler: " + myNum);
             long t1 = System.currentTimeMillis();

            for (int i = 1; i <= 10; i++) {
                System.out.println("Guess my number");
                int userNum = readUserNum();
                if (userNum == myNum) {
                    System.out.println("Yahoo! That's right!");
                    GameResult r = new GameResult();
                    r.name = name;
                    r.triesCount = i;
                    r.duration = System.currentTimeMillis() - t1;
                    results.add(r);
                    break;
                } else if (myNum > userNum) {
                    System.out.println("My num is greater than yours");
                } else {
                    System.out.println("My num is less than yours");
                }
            }
            System.out.println("Do you want to play one more time?");
        } while ("y".equals(scan.next()));
        for (GameResult r : results) {
            System.out.printf("Player %s has done %d tries and it took %.2f sec\n",
                    r.name,
                    r.triesCount,
                    r.duration / 1000.0);
        }
    }

    private static int readUserNum() {
        while (true) {
            try {
                int userNum = scan.nextInt();
                if (userNum < 1 || userNum > 100) {
                    System.out.println("Wrong number. Try again");
                    continue;
                }
                return userNum;
            } catch (InputMismatchException e) {
                scan.next();
                System.out.println("You are cheater ");
            }
        }
    }
}
