Задание 1. Аннотации


1.1 @Invoke

Реализация:
1. Объявление аннотации @Invoke:
- @Target(ElementType.METHOD) - указывает, что аннотация может применяться только к методам класса
- @Retention(RetentionPolicy.RUNTIME) - аннотация сохраняется во время выполнения программы и доступна через Reflection API

2. Тестовый класс TestForInvoke:
Класс содержит четыре метода:
- justMethod() - обычный метод без аннотации @Invoke, не будет автоматически вызываться
- annotatedMethod() - публичный метод с аннотацией @Invoke, будет автоматически вызван
- privateAnnotatedMethod() - приватный метод с аннотацией @Invoke, будет вызван благодаря method.setAccessible(true)
- methodWithParams(String param) - метод с параметрами и аннотацией @Invoke, не будет вызван (обработчик проверяет методы без параметров)

3. Обработчик в классе AnnotationProcessor:
Метод processForInvoke реализует следующую логику:
1. Проверка входных данных: если передан null объект, выбрасывается IllegalArgumentException
2. Получение всех методов класса через рефлексию: getClass().getDeclaredMethods()
3. Для каждого метода проверка наличия аннотации @Invoke: method.isAnnotationPresent(Invoke.class)
4. Проверка, что метод не имеет параметров: method.getParameterCount() == 0
5. Разрешение доступа к приватным методам: method.setAccessible(true)
6. Вызов метода: method.invoke(obj)

4. Обработка исключений:
- IllegalArgumentException - если передан null объект
- IllegalAccessException - при проблемах доступа к приватным методам
- InvocationTargetException - если в вызываемом методе возникает исключение

Тестирование работы:
При запуске обработчика для объекта TestForInvoke будут вызваны только методы annotatedMethod() и privateAnnotatedMethod(). Метод justMethod() не имеет аннотации, а methodWithParams() имеет параметры, поэтому оба не будут вызваны.
<img width="324" height="105" alt="Снимок экрана 2025-12-08 в 02 12 33" src="https://github.com/user-attachments/assets/69050459-5792-43c4-ba05-838e3677190d" />

1.2 @Default

1. Объявление аннотации @Default:
Аннотация объявлена с такими параметрами:
- @Target({ElementType.TYPE, ElementType.FIELD}) - может применяться к классам и полям
- @Retention(RetentionPolicy.RUNTIME) - доступна во время выполнения
- Обязательное свойство value() типа Class<?> - указывает класс по умолчанию

2. Тестовые классы с @Default:
Создано несколько тестовых классов для демонстрации:
- TestForDefault - @Default(String.class), поле fieldWithIntegerDefault имеет @Default(Integer.class)
- TestClassWithStringDefault - класс с @Default(String.class)
- TestClassWithIntegerDefault - класс с @Default(Integer.class)
- TestClassWithListDefault - класс с @Default(java.util.List.class)

3. Обработчик в классе AnnotationProcessor:
Метод processForDefault:
1. Проверка на null переданного класса
2. Проверка наличия аннотации @Default на уровне класса: targetClass.isAnnotationPresent(Default.class)
3. Если аннотация есть, извлечение значения: defaultAnnotation.value().getName()
4. Вывод имени класса по умолчанию
5. Дополнительная проверка аннотаций на полях класса:
   - Получение всех полей: targetClass.getDeclaredFields()
   - Для каждого поля проверка наличия @Default
   - Вывод информации о классе по умолчанию для полей
   - 
4. Особенности реализации:
- Обработчик работает как с аннотациями на уровне класса, так и на уровне полей
- Используется рефлексия для доступа к полям класса
- Для получения имени класса используется getName() и getSimpleName()

Тестирование работы:
<img width="384" height="128" alt="Снимок экрана 2025-12-08 в 02 17 22" src="https://github.com/user-attachments/assets/a33c4556-4c9c-4ce8-a17f-e1f0b427860f" />
<img width="627" height="152" alt="Снимок экрана 2025-12-08 в 02 17 44" src="https://github.com/user-attachments/assets/dc7634ed-4cbe-49b3-a61c-836f2eb92c39" />

1.3 @ToString

1. Создание enum Mode:
Перед созданием аннотации необходимо определить перечисление Mode:
public enum Mode {
    YES,  // Элемент должен быть включён в строковое представление
    NO    // Элемент должен быть исключён из строкового представления
}

2. Объявление аннотации @ToString:
Аннотация объявлена с такими параметрами:
- @Target({ElementType.TYPE, ElementType.FIELD}) - может применяться к классам и полям
- @Retention(RetentionPolicy.RUNTIME) - доступна во время выполнения

3. Тестовый класс TestForToString:
Класс содержит следующие поля:
name = "Иван" - поле без аннотации, по умолчанию включается (Mode.YES)
age = 25 - поле без аннотации, по умолчанию включается (Mode.YES)
password = "12345" - поле с аннотацией @ToString(Mode.NO), исключается из вывода
user = "username" - поле без аннотации, по умолчанию включается (Mode.YES)

4. Класс ToStringFormatter:
- Создан отдельный класс ToStringFormatter, который содержит метод format() со следующей логикой:
- Проверка на null переданного объекта
- Проверка аннотации @ToString на уровне класса
- Если класс имеет @ToString(Mode.NO) - возвращается только имя класса
- Для каждого поля класса:
Проверка наличия аннотации @ToString
Если аннотация есть и значение Mode.NO - поле пропускается
Если аннотации нет или значение Mode.YES - поле включается в вывод
- Формирование строки в формате: ClassName{field1=value1, field2=value2}

5. Особенности реализации:
Используется рефлексия для получения всех полей класса
Обработка приватных полей через field.setAccessible(true)
Обработка исключений IllegalAccessException
Учет аннотаций как на уровне класса, так и на уровне полей

Тестирование работы:
<img width="603" height="103" alt="Снимок экрана 2025-12-08 в 02 22 04" src="https://github.com/user-attachments/assets/c91dc639-e037-415d-b564-7996eeee6e32" />

1.4 @Validate

1. Объявление аннотации @Validate:
Аннотация объявлена с такими параметрами:
- @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE}) - может применяться к классам и другим аннотация
- @Retention(RetentionPolicy.RUNTIME) - доступна во время выполнения

2. Тестовый класс TestForValidate:
Класс содержит аннотацию:
@Validate({String.class, Integer.class, Double.class})
public class TestForValidate {
    private String data = "test data";
}

3. Обработчик в классе AnnotationProcessor:
- Метод processForValidate:
- Проверка на null переданного класса
- Проверка наличия аннотации @Validate
- Если аннотации нет - выбрасывается IllegalArgumentException
- Получение массива классов из аннотации
- Проверка, что массив не пуст (требование задания)
- Вывод списка классов с их простыми именами

4. Особенности реализации:
Проверка пустого массива с выбрасыванием исключения
Использование getSimpleName() для вывода кратких имён классов
Обработка различных типов исключений

Тестирование работы:
<img width="486" height="191" alt="Снимок экрана 2025-12-08 в 02 25 04" src="https://github.com/user-attachments/assets/c0fa6162-eb83-45d3-8953-c201a8f22e3a" />

1.5 @Two

1. Объявление аннотации @Two:
Аннотация объявлена с такими параметрами:
- @Target(ElementType.TYPE) - может применяться только к классам
- @Retention(RetentionPolicy.RUNTIME) - доступна во время выполнения

Два обязательных свойства:
- String first() - строковое значение
- int second() - числовое значение

2. Тестовый класс TestForTwo:
Класс содержит аннотацию:
@Two(first = "Пример строки", second = 42)
public class TestForTwo {
    private String data = "test data";
}

3. Обработчик в классе AnnotationProcessor:
- Метод processForTwo реализует следующую логику:
- Проверка на null переданного класса
- Проверка наличия аннотации @Two
- Получение значений свойств first и second
- Форматированный вывод значений с указанием типов

4. Особенности реализации:
Проверка обязательных свойств аннотации
Форматированный вывод с указанием типов данных
Обработка отсутствия аннотации

Тестирование работы:
<img width="373" height="108" alt="Снимок экрана 2025-12-08 в 02 27 16" src="https://github.com/user-attachments/assets/bf8b5496-6525-4a43-8a81-8a81c63019c3" />

1.6 @Cache

1. Объявление аннотации @Cache:
Аннотация объявлена с такими параметрами:
- @Target(ElementType.TYPE) - может применяться только к классам
- @Retention(RetentionPolicy.RUNTIME) - доступна во время выполнения


2. Тестовые классы:
Создано два тестовых класса:
- TestForCache с аннотацией @Cache({"users", "products", "orders"})
- EmptyCacheService с аннотацией @Cache (пустой массив по умолчанию)

3. Обработчик в классе AnnotationProcessor:
- Метод processForCache реализует следующую логику:
- Проверка на null переданного класса
- Проверка наличия аннотации @Cache
- Получение массива строк из аннотации
- Если массив пуст - вывод сообщения "Список кешируемых областей пуст"
- Если массив не пуст - вывод списка областей с их количеством

4. Особенности реализации:
Обработка default значения (пустой массив)
Вывод количества областей для непустого списка
Форматированный вывод с маркерами списка

Тестирование работы:
<img width="312" height="195" alt="Снимок экрана 2025-12-08 в 02 29 19" src="https://github.com/user-attachments/assets/f33c9ae5-e0f7-48a9-9ee7-d70a63bebe5f" />
<img width="341" height="126" alt="Снимок экрана 2025-12-08 в 02 29 40" src="https://github.com/user-attachments/assets/83e0e4f9-91dc-4852-ac43-30d03c43874e" />

Задание 2. Тестирование

2.1 тест для @ToString

1. Тестовый класс ToStringTest:
Содержит следующие тесты:
testToStringIncludesCorrectFields() - основной тест для проверки включения/исключения полей
- testToStringWithNullObject() - тест обработки null объекта
- testClassWithToStringNo() - тест класса с @ToString(Mode.NO)
- testToStringFormatterWithRegularObject() - тест работы ToStringFormatter

2. Основной тест testToStringIncludesCorrectFields():
Логика теста:
- Создание объекта TestForToString
- Вызов метода toString()
- Проверка, что строка содержит поля name, age, user
- Проверка, что строка НЕ содержит поле password и его значение
- Проверка формата вывода (начинается с имени класса, заканчивается })

3. Особенности тестирования:
Использование assertTrue() и assertFalse() для проверки содержания
Использование assertEquals() для проверки точных значений
Проверка edge cases (null объект, класс с Mode.NO)

Результаты тестирования:
Все тесты проходят успешно, подтверждая корректность работы:
<img width="1198" height="141" alt="Снимок экрана 2025-12-08 в 02 33 56" src="https://github.com/user-attachments/assets/75862aa7-7294-4bbd-83ee-17177b31949e" />

2.2 тест для @Validate

1. Тестовый класс ValidateTest:
Содержит следующие тесты:
- testValidateAnnotationExtractsClasses() - тест извлечения классов из аннотации
- testEmptyValidationArrayThrowsException() - тест исключения при пустом массиве
- testNullClassThrowsException() - тест обработки null класса
- testClassWithoutValidateAnnotationThrowsException() - тест класса без аннотации

2. Тест testEmptyValidationArrayThrowsException():
Основной тест:
- Создание локального класса с аннотацией @Validate({})
- Вызов AnnotationProcessor.processForValidate() для этого класса
- Проверка, что выбрасывается IllegalArgumentException
- Проверка, что сообщение исключения содержит информацию о пустом массиве

3. Использование assertThrows:
В JUnit 5 используется assertThrows для проверки исключений:
IllegalArgumentException exception = assertThrows(
    IllegalArgumentException.class,
    () -> AnnotationProcessor.processForValidate(EmptyValidationTestClass.class)
);

4. Особенности тестирования:
Использование локальных классов для изоляции тестов
Проверка не только факта исключения, но и его сообщения
Тестирование различных граничных случаев

Результаты тестирования:
<img width="1186" height="195" alt="Снимок экрана 2025-12-08 в 02 36 20" src="https://github.com/user-attachments/assets/6734d9ad-5015-437e-a3ea-9fa24572ce36" />

