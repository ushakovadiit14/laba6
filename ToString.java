import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для контроля полей в строковом представлении.
 * Может применяться к классам и полям.
 * Имеет свойство value с возможными значениями YES или NO.
 * Значение по умолчанию: YES.
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ToString {
    /**
     * Режим отображения поля или класса.
     * @return значение режима (YES или NO)
     */
    Mode value() default Mode.YES;
}