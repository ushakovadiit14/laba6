import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест для аннотации @Validate согласно заданию 2.2
 */
public class ValidateTest {

    @Test
    void testValidateAnnotationExtractsClasses() {
        // Arrange & Act
        boolean hasAnnotation = TestForValidate.class.isAnnotationPresent(Validate.class);
        Validate annotation = TestForValidate.class.getAnnotation(Validate.class);

        // Assert
        assertTrue(hasAnnotation,
                "Класс TestForValidate должен иметь аннотацию @Validate");

        assertNotNull(annotation,
                "Аннотация не должна быть null");

        Class<?>[] classes = annotation.value();
        assertEquals(3, classes.length,
                "Должно быть 3 класса в аннотации");

        assertEquals(String.class, classes[0],
                "Первый класс должен быть String");
        assertEquals(Integer.class, classes[1],
                "Второй класс должен быть Integer");
        assertEquals(Double.class, classes[2],
                "Третий класс должен быть Double");

        // Тестируем обработчик
        assertDoesNotThrow(() -> {
            AnnotationProcessor.processForValidate(TestForValidate.class);
        }, "Обработчик должен работать без ошибок для непустого массива");
    }

    @Test
    void testEmptyValidationArrayThrowsException() {
        // Создаем тестовый класс с пустым массивом
        @Validate({})
        class EmptyValidationTestClass {}

        // Проверяем что выбрасывается исключение при обработке
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> AnnotationProcessor.processForValidate(EmptyValidationTestClass.class)
        );

        assertTrue(exception.getMessage().contains("пуст") ||
                        exception.getMessage().contains("empty") ||
                        exception.getMessage().contains("Массив"),
                "Должно быть сообщение о пустом массиве");
    }

    @Test
    void testNullClassThrowsException() {
        // Проверяем обработку null
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> AnnotationProcessor.processForValidate(null)
        );

        assertTrue(exception.getMessage().contains("null"),
                "Должно быть сообщение о null");
    }

    @Test
    void testClassWithoutValidateAnnotationThrowsException() {
        // Класс без аннотации @Validate
        class NoAnnotationClass {}

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> AnnotationProcessor.processForValidate(NoAnnotationClass.class)
        );

        assertTrue(exception.getMessage().contains("не имеет") ||
                        exception.getMessage().contains("not have"),
                "Должно быть сообщение об отсутствии аннотации");
    }
}