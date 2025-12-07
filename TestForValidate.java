/**
 * Тестовый класс для демонстрации аннотации @Validate.
 */

@Validate({String.class, Integer.class, Double.class})
public class TestForValidate {
    private String data = "test data";
}
