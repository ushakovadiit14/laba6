/**
 * Тестовый класс для @Cache с тремя областями.
 */
@Cache({"users", "products", "orders"})
public class TestForCache {
    private String data = "cache data";
}