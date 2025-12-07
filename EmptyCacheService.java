/**
 * Сервис с пустым кешем.
 */
@Cache
public class EmptyCacheService {
    public String getData() {
        return "Some...";
    }
}