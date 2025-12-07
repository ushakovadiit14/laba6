/**
 * Тестовый класс для демонстрации аннотации @ToString.
 * Аннотирован на уровне класса с режимом YES.
 * Содержит поля с разными режимами отображения.
 */
@ToString
public class TestForToString {
    private String name = "Иван";
    private int age = 25;

    /**
     * Поле исключено из строкового представления.
     */
    @ToString(Mode.NO)
    private String password = "12345";

    private String user = "username";

    /**
     * Метод формирует строковое представление объекта
     * на основе аннотаций @ToString.
     * @return строковое представление объекта
     */
    @Override
    public String toString() {
        return ToStringFormatter.format(this);
    }

    // Геттеры для тестов (необязательно, но полезно)
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getUser() { return user; }
    public String getPassword() { return password; }
}