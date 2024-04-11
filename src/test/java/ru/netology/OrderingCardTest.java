package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderingCardTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    // Тест, когда все поля заполнены согласно требованиям. Заявка успешно отправлена
    @Test
    public void testWhenTheApplicationIsSuccessful1() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79878008080");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // Тест, когда у заявителя двойная фамилия через дефис. Заявка успешно отправлена
    @Test
    public void testWhenTheApplicationIsSuccessful2() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Салтыков-Щедрин Михаил");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79878008080");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // Тест, когда у заявителя сложносоставные фамилия и имя. Заявка успешно отправлена
    @Test
    public void testWhenTheApplicationIsSuccessful3() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Джордж Ноэл Гордон Байрон");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79878008080");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // Тест, когда у заявителя фамилия и имя указаны в верхнем регистре. Заявка успешно отправлена
    @Test
    public void testWhenTheApplicationIsSuccessful4() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("ИВАНОВ ИВАН");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79878008080");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // Тест, когда у заявителя фамилия и имя указаны в нижнем регистре. Заявка успешно отправлена
    @Test
    public void testWhenTheApplicationIsSuccessful5() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("иванов иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79878008080");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // Тест, когда между фамилией и именем нет пробела. Заявка успешно отправлена
    @Test
    public void testWhenTheApplicationIsSuccessful6() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("ИвановИван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79878008080");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // Тест, когда фамилия и имя заполнены латинскими буквами. Заявка не отправлена
    @Test
    public void testWhenTheApplicationIsInLatin() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ivanov Ivan");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79878008080");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    // Тест, когда в написании фамилии и имени используются специальные символы
    // (кроме разрешённых пробела и дефиса). Заявка не отправлена
    @Test
    public void testWhenTheApplicationIsSymbol() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов_Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79878008080");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    // Тест, когда вместо фамилии и имени цифры. Заявка не отправлена
    @Test
    public void testWhenTheApplicationIsNumbers() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("123456789");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79878008080");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    // Тест, когда поле "Фамилия и имя" не запонено. Заявка не отправлена
    @Test
    public void testWhenTheApplicationIsZero() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79878008080");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();

        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    // Тест, когда в номере телефона 12 цифр. Заявка не отправлена
    @Test
    public void testWhenThePhoneIsIncorrect1() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+798780080800");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест, когда в номере телефона 10 цифр. Заявка не отправлена
    @Test
    public void testWhenThePhoneIsIncorrect2() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7987800808");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест, когда поле "Мобильный телефон" не заполнено. Заявка не отправлена
    @Test
    public void testWhenThePhoneIsZero() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();

        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    // Тест, когда номер телефона указан без специального символа "+". Заявка не отправлена
    @Test
    public void testWhenThePhoneIsIncorrect3() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("79878008080");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест, когда в поле "Мобильный телефон" указаны русские буквы. Заявка не отправлена
    @Test
    public void testWhenThePhoneIsIncorrect4() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("Телефон");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест, когда в поле "Мобильный телефон" указаны специальные символы.
    // Кроме разрешённого символа "+". Заявка не отправлена
    @Test
    public void testWhenThePhoneIsIncorrect5() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("-79878008080");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест, когда в поле "Мобильный телефон" между цифрами номера телефона
    // указаны пробелы. Заявка не отправлена
    @Test
    public void testWhenThePhoneIsIncorrect6() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+ 7 9 8 7 8 0 0 8 0 8 0");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест, когда не кликнули по чек-боксу. Заявка не отправлена
    @Test
    public void testWhenTheCheckboxIsNotClick() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79878008080");
        //driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=agreement]")).getText();

        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }
}
