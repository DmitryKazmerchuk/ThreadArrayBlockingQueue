package ru.netology;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class AnalyzerProgram {

    public static ArrayBlockingQueue<String> queueA = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> queueB = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> queueC = new ArrayBlockingQueue<>(100);


    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            String[] texts = new String[100];
            for (int i = 0; i < texts.length; i++) {
                texts[i] = generateText("abc", 100_000);
                try {
                    queueA.put(texts[i]);
                    queueB.put(texts[i]);
                    queueC.put(texts[i]);

                } catch (InterruptedException e) {
                    return;
                }
            }
        }
        );

        Thread threadA = new Thread(() -> {
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String strMaxA = comparisonSymbol(queueA, 'a');
            int count = 0;
            for (int i = 0; i < strMaxA.length(); i++) {
                if (strMaxA.charAt(i) == 'a') {
                    count++;
                }
            }
            System.out.println();
            System.out.println("Текст с символом 'a'. Количество символов 'a' -> " + count + "." + "\n" + strMaxA);
        }
        );

        Thread threadB = new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String strMaxB = comparisonSymbol(queueB, 'b');
            int count = 0;
            for (int i = 0; i < strMaxB.length(); i++) {
                if (strMaxB.charAt(i) == 'b') {
                    count++;
                }
            }
            System.out.println();
            System.out.println("Текст с символом 'b'. Количество символов 'b' -> " + count + "." + "\n" + strMaxB);
        }
        );

        Thread threadC = new Thread(() -> {
            try {
                Thread.sleep(1_350);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String strMaxC = comparisonSymbol(queueC, 'c');
            int count = 0;
            for (int i = 0; i < strMaxC.length(); i++) {
                if (strMaxC.charAt(i) == 'b') {
                    count++;
                }
            }
            System.out.println();
            System.out.println("Текст с символом 'c'. Количество символов 'c' -> " + count + "." + "\n" + strMaxC);
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

    public static String comparisonSymbol(ArrayBlockingQueue<String> queue, char symbol) {
        int countMax = 0;
        String strMax = null;
        for (String str : queue) {
            int count = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == symbol) {
                    count++;
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