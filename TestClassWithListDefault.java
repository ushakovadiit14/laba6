/**
 * Тестовый класс с List по умолчанию.
 */
@Default(java.util.List.class)
public class TestClassWithListDefault {
    @Default(Double.class)
    private int fieldWithDoubleDefault;
}