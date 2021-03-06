package lv.ctco.guessnum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static final File RESULTS_FILE = new File("results.txt");
    static Scanner scan = new Scanner(System.in);
    static Random rand = new Random();
    static List<GameResult> results = new ArrayList<>();

    public static void main(String[] args) {
        loadResults();
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
        showResults();
        saveResults();
    }

    private static void showResults() {
        results.stream()
                .sorted(Comparator.<GameResult>comparingInt(r -> r.triesCount)
                                  .<GameResult>thenComparingLong(r -> r.duration))
                .limit(3)
                .forEach(r -> System.out.printf("Player %s has done %d tries and it took %.2f sec\n",
                        r.name,
                        r.triesCount,
                        r.duration / 1000.0));
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

    private static void saveResults() {
        try (PrintWriter fileOut = new PrintWriter(RESULTS_FILE)) {

            int skipCount = results.size() - 5;

            results.stream()
                    .skip(skipCount)
                    .forEach(r -> fileOut.printf("%s %d %d\n", r.name, r.triesCount, r.duration));

//            boolean have5 = results.stream()
//                    .filter(r -> r.name.equals("Dima"))
//                    .anyMatch(r -> r.triesCount == 5);

//            for (GameResult r : results) {
//                if (skipCount <= 0) {
//                    fileOut.printf("%s %d %d\n", r.name, r.triesCount, r.duration);
//                }
//                skipCount--;
//            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void loadResults() {
        try (Scanner in = new Scanner(RESULTS_FILE)) {

            while (in.hasNext()) {
                GameResult gr = new GameResult();
                gr.name = in.next();
                gr.triesCount = in.nextInt();
                gr.duration = in.nextLong();

                results.add(gr);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
