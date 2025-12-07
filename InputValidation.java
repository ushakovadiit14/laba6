

import java.util.Scanner;
/**
 * Класс для валидации пользовательского ввода.
 */
public class InputValidation {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Ввод целого числа в заданном диапазоне.
     * @param prompt приглашение для ввода
     * @param min минимальное значение
     * @param max максимальное значение
     * @return валидное целое число
     */
    public static int inputInt(String prompt, int min, int max) {
        System.out.print(prompt);
        while (true) {
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                scanner.nextLine();
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("Число должно быть в диапазоне от " + min + " до " + max + "!");
                    System.out.print(prompt);
                }
            } else {
                System.out.println("Ошибка ввода! Пожалуйста, введите целое число.");
                scanner.nextLine();
                System.out.print(prompt);
            }
        }
    }

    /**
     * Проверка целого числа на соответствие диапазону.
     * @param test проверяемое число
     * @param min минимальное значение
     * @param max максимальное значение
     * @return валидное целое число
     */
    public static int validInt(int test, int min, int max) {
        int input = test;
        while (true) {
            if (input >= min && input <= max) {
                return input;
            } else {
                System.out.println("Число должно быть в диапазоне от " + min + " до " + max + "!");
                if (scanner.hasNextInt()) {
                    input = scanner.nextInt();
                } else {
                    System.out.println("Ошибка ввода! Пожалуйста, введите целое число.");
                    scanner.nextLine();
                }
            }
        }
    }
    /**
     * Ввод дробного числа в заданном диапазоне.
     * @param prompt приглашение для ввода
     * @param min минимальное значение
     * @param max максимальное значение
     * @return валидное дробное число
     */
    public static double inputDouble(String prompt, double min, double max) {
        System.out.print(prompt);
        while (true) {
            if (scanner.hasNextDouble()) {
                double input = scanner.nextDouble();
                scanner.nextLine();
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("Число должно быть в диапазоне от " + min + " до " + max + "!");
                    System.out.print(prompt);
                }
            } else {
                System.out.println("Ошибка ввода! Пожалуйста, введите число.");
                scanner.nextLine();
                System.out.print(prompt);
            }
        }
    }
    /**
     * Ввод строки с проверкой на буквы и пробелы.
     * @param prompt приглашение для ввода
     * @return валидная строка
     */
    public static String inputString(String prompt) {
        System.out.print(prompt);
        while (true) {
            String input = scanner.nextLine().trim();
            boolean isValid = true;

            if (input.isEmpty()) {
                System.out.println("Ошибка: строка не может быть пустой!");
                System.out.print(prompt);
                continue;
            }

            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                boolean isEnglishLetter = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
                boolean isRussianLetter =
                        (c >= 'а' && c <= 'я') || (c >= 'А' && c <= 'Я') || c == 'ё' || c == 'Ё';
                boolean isSpace = c == ' ';

                if (!isEnglishLetter && !isRussianLetter && !isSpace) {
                    isValid = false;
                    break;
                }
            }

            if (isValid) {
                return input;
            } else {
                System.out.println("Ошибка: разрешены только английские и русские буквы и пробелы!");
                System.out.print(prompt);
            }
        }
    }
    /**
     * Проверка строки на содержание только букв и пробелов.
     * @param test проверяемая строка
     * @return валидная строка
     */
    public static String inputLetters(String test) {
        String input = test.trim();
        while (true) {
            boolean isValid = true;
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                boolean isEnglishLetter = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
                boolean isRussianLetter =
                        (c >= 'а' && c <= 'я') || (c >= 'А' && c <= 'Я') || c == 'ё' || c == 'Ё';
                boolean isSpace = c == ' ';

                if (!isEnglishLetter && !isRussianLetter && !isSpace) {
                    isValid = false;
                    break;
                }
            }

            if (isValid) {
                return input;
            } else {
                System.out.println("Ошибка: разрешены только английские и русские буквы и пробелы!");
                input = scanner.nextLine().trim();
            }
        }
    }
}