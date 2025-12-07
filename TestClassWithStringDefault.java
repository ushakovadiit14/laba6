/**
 * Тестовый класс с String по умолчанию.
 */
@Default(String.class)
public class TestClassWithStringDefault {
    @Default(Integer.class)
    private String fieldWithIntegerDefault;
}