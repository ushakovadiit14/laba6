import java.lang.reflect.Field;

/**
 * Класс для форматирования строкового представления объектов
 * на основе аннотации @ToString.
 */
public class ToStringFormatter {

    /**
     * Формирует строковое представление объекта с учетом аннотаций @ToString.
     * Правила:
     * 1. Если класс имеет @ToString(Mode.NO) - возвращает только имя класса
     * 2. Если поле имеет @ToString(Mode.NO) - оно исключается
     * 3. Если поле имеет @ToString(Mode.YES) или без аннотации - включается
     * @param obj объект для форматирования
     * @return строковое представление объекта
     * @throws IllegalArgumentException если передан null
     */
    public static String format(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Объект не может быть null");
        }

        Class<?> clazz = obj.getClass();

        // 1. Проверяем аннотацию на классе
        if (clazz.isAnnotationPresent(ToString.class)) {
            ToString classAnnotation = clazz.getAnnotation(ToString.class);
            if (classAnnotation.value() == Mode.NO) {
                return clazz.getSimpleName() + "{}";
            }
        }

        // 2. Собираем поля для включения
        StringBuilder sb = new StringBuilder();
        sb.append(clazz.getSimpleName()).append("{");

        Field[] fields = clazz.getDeclaredFields();
        boolean isFirst = true;

        for (Field field : fields) {
            // Проверяем, нужно ли включать это поле
            if (shouldIncludeField(field)) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(obj);

                    if (!isFirst) {
                        sb.append(", ");
                    }

                    sb.append(field.getName())
                            .append("=")
                            .append(value);

                    isFirst = false;
                } catch (IllegalAccessException e) {
                    if (!isFirst) {
                        sb.append(", ");
                    }

                    sb.append(field.getName())
                            .append("=[ACCESS ERROR]");

                    isFirst = false;
                }
            }
        }

        sb.append("}");
        return sb.toString();
    }

    /**
     * Определяет, нужно ли включать поле в строковое представление.
     * @param field проверяемое поле
     * @return true если поле нужно включить, false если исключить
     */
    private static boolean shouldIncludeField(Field field) {
        // Если поле имеет аннотацию @ToString
        if (field.isAnnotationPresent(ToString.class)) {
            ToString annotation = field.getAnnotation(ToString.class);
            // Включаем только если значение YES
            return annotation.value() == Mode.YES;
        }

        // Если аннотации нет - включаем (по умолчанию YES)
        return true;
    }

    /**
     * Альтернативная версия для использования в методе toString() класса.
     * @param obj объект для форматирования
     * @return строковое представление
     */
    public static String toString(Object obj) {
        return format(obj);
    }
}