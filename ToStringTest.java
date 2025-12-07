import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест для аннотации @ToString согласно заданию 2.1
 */
public class ToStringTest {

    @Test
    void testToStringIncludesCorrectFields() {
        // Arrange (подготовка)
        TestForToString obj = new TestForToString();

        // Act (действие) - используем метод toString() класса
        String result = obj.toString();

        System.out.println("Результат toString: " + result);

        // Assert (проверки)

        // 1. Поля без аннотации или с @ToString(Mode.YES) должны быть включены
        assertTrue(result.contains("name=Иван"),
                "Поле 'name' должно быть включено в строковое представление");

        assertTrue(result.contains("age=25"),
                "Поле 'age' должно быть включено");

        assertTrue(result.contains("user=username"),
                "Поле 'user' должно быть включено");

        // 2. Поля с @ToString(Mode.NO) должны быть исключены
        assertFalse(result.contains("password"),
                "Поле 'password' с @ToString(Mode.NO) должно быть исключено");
        assertFalse(result.contains("12345"),
                "Значение поля 'password' должно быть исключено");

        // 3. Проверка формата вывода
        assertTrue(result.startsWith("TestForToString{"),
                "Строка должна начинаться с имени класса");
        assertTrue(result.endsWith("}"),
                "Строка должна заканчиваться закрывающей скобкой");
    }

    @Test
    void testToStringWithNullObject() {
        // Тест обработки null объекта в ToStringFormatter
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ToStringFormatter.format(null);
        });

        assertTrue(exception.getMessage().contains("null"));
    }

    @Test
    void testClassWithToStringNo() {
        // Тестируем класс с аннотацией @ToString(Mode.NO)
        @ToString(Mode.NO)
        class TestClassNo {
            private String field = "value";
        }

        TestClassNo obj = new TestClassNo();
        String result = ToStringFormatter.format(obj);

        assertEquals("TestClassNo{}", result,
                "Класс с @ToString(Mode.NO) должен возвращать только имя класса");
    }

    @Test
    void testToStringFormatterWithRegularObject() {
        // Тестируем ToStringFormatter напрямую
        TestForToString obj = new TestForToString();
        String result = ToStringFormatter.format(obj);

        // Проверяем что содержит нужные поля
        assertTrue(result.contains("name=Иван"));
        assertTrue(result.contains("age=25"));
        assertTrue(result.contains("user=username"));

        // Проверяем что НЕ содержит password
        assertFalse(result.contains("password"));
        assertFalse(result.contains("12345"));
    }
}