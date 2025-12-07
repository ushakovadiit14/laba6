import java.lang.reflect.Method;
import java.lang.reflect.Field;

/**
 * Обработчик аннотаций через рефлексию.
 */
public class AnnotationProcessor {

    /**
     * Вызывает методы с аннотацией @Invoke.
     * @param obj объект для обработки
     * @throws IllegalArgumentException если передан null
     */
    public static void processForInvoke(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Передан null объект");
        }

        Class<?> testClass = obj.getClass();
        Method[] methods = testClass.getDeclaredMethods();

        int invokedCount = 0;

        for (Method method : methods) {
            if (method.isAnnotationPresent(Invoke.class)) {
                try {
                    if (method.getParameterCount() == 0) {
                        // Делаем приватные методы доступными
                        if (!method.canAccess(obj)) {
                            method.setAccessible(true);
                        }
                        method.invoke(obj);
                        invokedCount++;
                        System.out.println("Вызван метод: " + method.getName());
                    } else {
                        System.out.println(
                                "Метод " + method.getName() + " имеет параметры и пропущен");
                    }
                } catch (Exception e) {
                    System.out.println(
                            "Ошибка при вызове метода " + method.getName() + ": " + e.getMessage());
                }
            }
        }

        if (invokedCount == 0) {
            System.out.println("Не найдено методов с аннотацией @Invoke для вызова");
        }
    }

    /**
     * Читает значение аннотации @Default.
     * @param targetClass класс для анализа
     * @throws IllegalArgumentException если передан null или класс не аннотирован
     */
    public static void processForDefault(Class<?> targetClass) {
        if (targetClass == null) {
            throw new IllegalArgumentException("Передан null класс");
        }

        // Проверяем аннотацию на классе
        if (targetClass.isAnnotationPresent(Default.class)) {
            Default defaultAnnotation = targetClass.getAnnotation(Default.class);
            Class<?> defaultClass = defaultAnnotation.value();
            System.out.println("Класс по умолчанию: " + defaultClass.getName());
        }

        // Также проверяем поля
        Field[] fields = targetClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Default.class)) {
                Default fieldAnnotation = field.getAnnotation(Default.class);
                System.out.println("Поле " + field.getName() +
                        " имеет класс по умолчанию: " +
                        fieldAnnotation.value().getName());
            }
        }

        if (!targetClass.isAnnotationPresent(Default.class) &&
                targetClass.getDeclaredFields().length == 0) {
            System.out.println("Аннотация @Default не найдена");
        }
    }

    /**
     * Выводит классы из аннотации @Validate.
     * @param targetClass класс для анализа
     * @throws IllegalArgumentException если передан null, класс не аннотирован
     *         или массив классов пуст
     */
    public static void processForValidate(Class<?> targetClass) {
        if (targetClass == null) {
            throw new IllegalArgumentException("Передан null класс");
        }

        if (!targetClass.isAnnotationPresent(Validate.class)) {
            throw new IllegalArgumentException("Класс не имеет аннотации @Validate");
        }

        Validate validateAnnotation = targetClass.getAnnotation(Validate.class);
        Class<?>[] validationClasses = validateAnnotation.value();

        if (validationClasses.length == 0) {
            throw new IllegalArgumentException("Массив классов для валидации пуст");
        }

        System.out.println("Классы указанные в аннотации @Validate:");
        for (Class<?> validationClass : validationClasses) {
            System.out.println("- " + validationClass.getSimpleName());
        }
    }

    /**
     * Читает значения свойств аннотации @Two.
     * @param targetClass класс для анализа
     * @throws IllegalArgumentException если передан null или класс не аннотирован
     */
    public static void processForTwo(Class<?> targetClass) {
        if (targetClass == null) {
            throw new IllegalArgumentException("Передан null класс");
        }

        if (!targetClass.isAnnotationPresent(Two.class)) {
            throw new IllegalArgumentException("Класс не имеет аннотации @Two");
        }

        Two twoAnnotation = targetClass.getAnnotation(Two.class);
        String firstValue = twoAnnotation.first();
        int secondValue = twoAnnotation.second();

        System.out.println("Значения свойств аннотации @Two:");
        System.out.println("first (String): \"" + firstValue + "\"");
        System.out.println("second (int): " + secondValue);
    }

    /**
     * Обрабатывает кешируемые области из аннотации @Cache.
     * @param targetClass класс для анализа
     * @throws IllegalArgumentException если передан null
     */
    public static void processForCache(Class<?> targetClass) {
        if (targetClass == null) {
            throw new IllegalArgumentException("Передан null класс");
        }

        if (targetClass.isAnnotationPresent(Cache.class)) {
            Cache cacheAnnotation = targetClass.getAnnotation(Cache.class);
            String[] cacheAreas = cacheAnnotation.value();

            if (cacheAreas.length == 0) {
                System.out.println("Список кешируемых областей пуст");
            } else {
                System.out.println("Кешируемые области (" + cacheAreas.length + "):");
                for (String area : cacheAreas) {
                    System.out.println("- " + area);
                }
            }
        } else {
            System.out.println("Аннотация @Cache не найдена");
        }
    }
}