/**
 * Тестовый класс для демонстрации аннотации @Invoke.
 */
public class TestForInvoke {

    /**
     * Обычный метод без аннотации.
     */
    public void justMethod() {
        System.out.println("Это был метод без Invoke");
    }

    /**
     * Метод с аннотацией @Invoke для тестирования.
     */
    @Invoke
    public void annotatedMethod() {
        System.out.println("Это был метод с Invoke");
    }
}