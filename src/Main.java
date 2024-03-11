import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static AtomicInteger countFor3 = new AtomicInteger(0);
    static AtomicInteger countFor4 = new AtomicInteger(0);
    static AtomicInteger countFor5 = new AtomicInteger(0);

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {            //генерируем строки в массиве
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        List<Thread> threads = new ArrayList<>();

            Runnable logic1 = () -> {            //Поток 1
                for (String str : texts) {
                    if (isPalindrome(str)) {
                        switch (str.length()) {
                            case 3:
                                countFor3.getAndIncrement();
                            case 4:
                                countFor4.getAndIncrement();
                            case 5:
                                countFor5.getAndIncrement();
                        }
                    }
                }
            };
            Thread thread1 = new Thread(logic1);
            thread1.start();
            threads.add(thread1);

            Runnable logic2 = () -> {           //Поток 2
                for (String str : texts) {
                    if (isSameChar(str)) {
                        switch (str.length()) {
                            case 3:
                                countFor3.getAndIncrement();
                                break;
                            case 4:
                                countFor4.getAndIncrement();
                                break;
                            case 5:
                                countFor5.getAndIncrement();
                                break;
                        }
                    }
                }
            };
            Thread thread2 = new Thread(logic2);
            thread2.start();
            threads.add(thread2);

            Runnable logic3 = () -> {           //Поток 3
                for (String str : texts) {
                    if (isSort(str)) {
                        switch (str.length()) {
                            case 3:
                                countFor3.getAndIncrement();
                                break;
                            case 4:
                                countFor4.getAndIncrement();
                                break;
                            case 5:
                                countFor5.getAndIncrement();
                                break;
                        }
                    }
                }
            };
            Thread thread3 = new Thread(logic3);
            thread3.start();
            threads.add(thread3);



        for (Thread thread : threads) {  // Ожидание завершения всех потоков
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Красивых слов с длиной 3: " + countFor3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + countFor4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + countFor5 + " шт");
    }

    public static boolean isPalindrome(String str) {  // Проверяем, палиндром строка или нет
        int left = 0;
        int right = str.length() - 1;

        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static boolean isSameChar(String str) {   // Проверяем, состоит ли строка из одного и того же символа
        char firstChar = str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) != firstChar) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSort(String str) { // Проверяем, состоит ли строка из сортированных символов
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        return str.equals(new String(chars));
    }

    public static String generateText(String letters, int length) { //  генерирует строки
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}

