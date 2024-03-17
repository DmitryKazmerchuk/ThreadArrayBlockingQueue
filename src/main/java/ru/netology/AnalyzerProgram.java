package ru.netology;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnalyzerProgram {

    public static ArrayBlockingQueue<String> queueA = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> queueB = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> queueC = new ArrayBlockingQueue<>(100);
    public static CopyOnWriteArrayList<ArrayBlockingQueue<String>> queueList = new CopyOnWriteArrayList<>();


    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {

                try {
                    queueA.put(generateText("abc", 100_000));
                    queueB.put(generateText("abc", 100_000));
                    queueC.put(generateText("abc", 100_000));

                } catch (InterruptedException e) {
                    return;
                }
            }
            queueList.add(queueA);
            queueList.add(queueB);
            queueList.add(queueC);
        }
        );


        Thread threadA = new Thread(() -> {
            try {
                Thread.sleep(450);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String strMaxA = comparisonSymbol(queueList,'a');

            System.out.println("A: " + strMaxA);
        }
        );

        Thread threadB = new Thread(() -> {
            try {
                Thread.sleep(900);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String strMaxB = comparisonSymbol(queueList,'b');
            System.out.println("B: " + strMaxB);
        }
        );

        Thread threadC = new Thread(() -> {
            try {
                Thread.sleep(1_350);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String strMaxC = comparisonSymbol(queueList,'c');
            System.out.println("C: " + strMaxC);
        }
        );

        thread.start();
        threadA.start();
        threadB.start();
        threadC.start();

        threadC.join();
        threadB.join();
        threadA.join();
        thread.join();

    }
    public static String comparisonSymbol(CopyOnWriteArrayList<ArrayBlockingQueue<String>> queueList, char symbol) {
        int count = 0;
        int countMax = 0;
        String strMax = null;
        for(ArrayBlockingQueue<String> queue : queueList) {
            for (String str : queue) {
                for (int i = 0; i < str.length(); i++) {
                    if (str.charAt(i) == symbol) {
                        count++;
                    }
                }
                if (count > countMax) {
                    countMax = count;
                    strMax = str;
                }
            }
        }
        return strMax;
    }
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}