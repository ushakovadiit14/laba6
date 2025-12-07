import java.util.Scanner;

/**
 * Главный класс для демонстрации работы аннотаций.
 */
public class Main {

    /**
     * Основной метод для выбора и запуска заданий.
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== ДЕМОНСТРАЦИЯ АННОТАЦИЙ ===");
            System.out.println("1. Аннотация @Invoke");
            System.out.println("2. Аннотация @Default");
            System.out.println("3. Аннотация @ToString");
            System.out.println("4. Аннотация @Validate");
            System.out.println("5. Аннотация @Two");
            System.out.println("6. Аннотация @Cache");
            System.out.println("7. Запуск всех тестов");
            System.out.println("0. Выход");

            int choice = InputValidation.inputInt("Выберите задание (0-7): ", 0, 7);

            switch (choice) {
                case 0:
                    System.out.println("Выход из программы.");
                    scanner.close();
                    return;

                case 1: {
                    System.out.println("\n--- Демонстрация @Invoke ---");
                    TestForInvoke testObject = new TestForInvoke();
                    System.out.println("Вызов методов с аннотацией @Invoke:");
                    AnnotationProcessor.processForInvoke(testObject);
                    break;
                }

                case 2: {
                    System.out.println("\n--- Демонстрация @Default ---");
                    System.out.println("1. TestForDefault (класс)");
                    System.out.println("2. TestClassWithStringDefault (класс + поле)");
                    int subChoice = InputValidation.inputInt("Выберите вариант: ", 1, 2);

                    if (subChoice == 1) {
                        AnnotationProcessor.processForDefault(TestForDefault.class);
                    } else {
                        AnnotationProcessor.processForDefault(TestClassWithStringDefault.class);
                    }
                    break;
                }

                case 3: {
                    System.out.println("\n--- Демонстрация @ToString ---");
                    TestForToString testObject = new TestForToString();
                    System.out.println("Объект: " + testObject);
                    System.out.println("Метод toString(): " + testObject.toString());

                    // Дополнительная демонстрация
                    class HiddenClass {
                        private String secret = "скрыто";
                        private int number = 42;
                    }

                    HiddenClass hidden = new HiddenClass();
                    System.out.println("Класс с @ToString(Mode.NO): " +
                            ToStringFormatter.format(hidden));
                    break;
                }

                case 4: {
                    System.out.println("\n--- Демонстрация @Validate ---");
                    try {
                        AnnotationProcessor.processForValidate(TestForValidate.class);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }

                    // Демонстрация исключения
                    @Validate({})
                    class EmptyValidateClass {}

                    System.out.println("\nПопытка обработки класса с пустым массивом:");
                    try {
                        AnnotationProcessor.processForValidate(EmptyValidateClass.class);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Ожидаемая ошибка: " + e.getMessage());
                    }
                    break;
                }

                case 5: {
                    System.out.println("\n--- Демонстрация @Two ---");
                    AnnotationProcessor.processForTwo(TestForTwo.class);
                    break;
                }

                case 6: {
                    System.out.println("\n--- Демонстрация @Cache ---");
                    System.out.println("1. TestForCache (3 области)");
                    System.out.println("2. EmptyCacheService (пустой кеш)");
                    int subChoice = InputValidation.inputInt("Выберите вариант: ", 1, 2);

                    if (subChoice == 1) {
                        AnnotationProcessor.processForCache(TestForCache.class);
                    } else {
                        AnnotationProcessor.processForCache(EmptyCacheService.class);
                    }
                    break;
                }

                case 7: {
                    System.out.println("\n--- Запуск тестов ---");
                    runTests();
                    break;
                }
            }

            System.out.println("\nНажмите Enter для продолжения...");
            scanner.nextLine();
        }
    }

    /**
     * Запускает демонстрационные тесты.
     */
    private static void runTests() {
        System.out.println("Запуск тестов...");

        // Демонстрация работы тестов
        ToStringTest toStringTest = new ToStringTest();
        ValidateTest validateTest = new ValidateTest();

        try {
            System.out.println("\n1. Тест ToStringTest.testToStringIncludesCorrectFields()");
            toStringTest.testToStringIncludesCorrectFields();
            System.out.println("✓ Тест пройден");

            System.out.println("\n2. Тест ValidateTest.testValidateAnnotationExtractsClasses()");
            validateTest.testValidateAnnotationExtractsClasses();
            System.out.println("✓ Тест пройден");

            System.out.println("\n3. Тест ValidateTest.testEmptyValidationArrayThrowsException()");
            validateTest.testEmptyValidationArrayThrowsException();
            System.out.println("✓ Тест пройден (исключение было выброшено как и ожидалось)");

            System.out.println("\nВсе тесты успешно пройдены!");

        } catch (AssertionError e) {
            System.out.println("✗ Тест не пройден: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Ошибка при выполнении теста: " + e.getMessage());
        }
    }
}