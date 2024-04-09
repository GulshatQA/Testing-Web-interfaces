package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;

public class OrderingCardTest {

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }
}
