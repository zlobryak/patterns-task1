package ry.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.delivery.data.DataGenerator.Registration.generateUser;
import static ru.netology.delivery.data.DataGenerator.generateDate;

public class ChromeTest {

    /**
     * @param city Use String "random" to generate
     */
    public static void cityAutoFill(String city) {
        if (city.equals("random")) {

            $("[data-test-id=city] input")
                    .sendKeys(generateUser("ru").getCity());
        } else {
            $("[data-test-id=city] input")
                    .sendKeys(city);
        }
    }

    /**
     * @param dateToSetShift use int to shift from today
     * @return will return generated day for assertion
     */
    public static String dateAutoFill(int dateToSetShift, String dateFormat) {
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME),
                        Keys.BACK_SPACE);
        String dateToSet = generateDate(dateToSetShift, dateFormat);
        $(".calendar-input input")
                .setValue(dateToSet).sendKeys(Keys.ESCAPE);
        return dateToSet;
    }

    /**
     * @param name Use String "random" to generate
     */
    public static void nameAutoFill(String name) {
        if (name.equals("random")) {
            $("[data-test-id=name] input")
                    .setValue(generateUser("ru").getName());
        } else {
            $("[data-test-id=name] input")
                    .setValue(name);
        }
    }

    /**
     * @param phone Use String "random" to generate
     */
    public static void phoneAtoFill(String phone) {
        if (phone.equals("random")) {
            $("[data-test-id=phone] input")
                    .setValue(generateUser("ru").getPhone());
        } else {
            $("[data-test-id=phone] input")
                    .setValue(phone);
        }
    }

    /**
     * @param agreement Use true or false To check the box,
     *                  or false not to tick
     */
    public static void agreementAutoCheck(boolean agreement) {
        if (agreement) {
            $(withText("соглашаюсь")).click();
        }
    }

    public static void pushTheButton(String buttonText) {
        $$(".button__text").findBy(Condition.text(buttonText)).click();
    }
    @Test
    @DisplayName("Should successfully book the card with given date and then rebook it with new date")
    void shouldBookCardWithRegistrationMethodHappyPath() {
//        Configuration.holdBrowserOpen = true;
        int dateToSetShift = 4;
        String dateToRebook = generateDate(5, "dd.MM.yyyy");
        open("http://localhost:9999/");
        cityAutoFill("random");
        String dateToSet = dateAutoFill(dateToSetShift, "dd.MM.yyyy");
        nameAutoFill("random");
        phoneAtoFill("random");
        agreementAutoCheck(true);
        pushTheButton("Запланировать");

        $(".notification__content")
                .shouldHave(Condition.text(
                                "Встреча успешно запланирована на " + dateToSet),
                        Duration.ofSeconds(15)
                )
                .shouldBe(Condition.visible);

        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME),
                        Keys.BACK_SPACE);
        $(".calendar-input input")
                .setValue(dateToRebook).sendKeys(Keys.ESCAPE);

        pushTheButton("Запланировать");
        pushTheButton("Перепланировать");

        $(".notification__content")
                .shouldHave(Condition.text(
                                "Встреча успешно запланирована на " + dateToRebook),
                        Duration.ofSeconds(15)
                )
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if book with wrong city name")
    void WrongCityMessageTest() {
        int dateToSetShift = 1;
        open("http://localhost:9999/");
        cityAutoFill("Мга");
        dateAutoFill(dateToSetShift, "dd.MM.yyyy");
        nameAutoFill("random");
        phoneAtoFill("random");
        agreementAutoCheck(true);
        pushTheButton("Запланировать");
        $("[data-test-id='city'] ,input-sub")
                .shouldHave(Condition.text(
                        "Доставка в выбранный город недоступна")
                )
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if book with wrong date")
    void WrongDateMessageTest() {
        open("http://localhost:9999/");
        int dateToSetShift = 1;
        cityAutoFill("random");
        dateAutoFill(dateToSetShift, "dd.MM.yyyy");
        nameAutoFill("random");
        phoneAtoFill("random");
        agreementAutoCheck(true);
        pushTheButton("Запланировать");
        $("[data-test-id='date']")
                .shouldHave(Condition.text(
                        "Заказ на выбранную дату невозможен")
                )
                .shouldBe(Condition.visible);
    }
    @Test
    @DisplayName("Should get error message if book with wrong name ")
    void WrongNameMessageTest() {
        open("http://localhost:9999/");
        int dateToSetShift = 3;
        cityAutoFill("random");
        dateAutoFill(dateToSetShift, "dd.MM.yyyy");
        nameAutoFill("111");
        phoneAtoFill("random");
        agreementAutoCheck(true);
        pushTheButton("Запланировать");
        $("[data-test-id='name'] ,input-sub")
                .shouldHave(Condition.text(
                        "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")
                )
                .shouldBe(Condition.visible);
    }
    @Test
    @DisplayName("The color of the text of agreement should be red. " +
            "There should be no message that the operation completed successfully.")
    void AgreementNotSetTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        int dateToSetShift = 3;
        cityAutoFill("random");
        dateAutoFill(dateToSetShift, "dd.MM.yyyy");
        nameAutoFill("random");
        phoneAtoFill("111");
        agreementAutoCheck(false);
        pushTheButton("Запланировать");
        $(".input_invalid").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));
        $(".notification__content")
                .shouldNotHave(Condition.text(
                                "Встреча успешно запланирована на "),
                        Duration.ofSeconds(15)
                );

    }

    // Тесты, которые выявили баги

    @Test
    @DisplayName("Should get error message if book with wrong phone number ")
    void WrongPhoneMessageTest() {
        open("http://localhost:9999/");
        int dateToSetShift = 3;
        cityAutoFill("random");
        dateAutoFill(dateToSetShift, "dd.MM.yyyy");
        nameAutoFill("random");
        phoneAtoFill("111");
        agreementAutoCheck(true);
        pushTheButton("Запланировать");

        $(".notification__content")
                .shouldNotHave(Condition.text(
                                "Встреча успешно запланирована на "),
                        Duration.ofSeconds(15)
                );
        $("[data-test-id='phone'] ,input-sub")
                .shouldHave(Condition.text(
                        "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")
                )
                .shouldBe(Condition.visible);
    }
    @Test
    @DisplayName("Should successfully book the card with name that contains Yo letter")
    void shouldBookCardWithLetterYo() {
        open("http://localhost:9999/");
        int dateToSetShift = 3;
        cityAutoFill("random");
        String dateToSet = dateAutoFill(dateToSetShift, "dd.MM.yyyy");
        nameAutoFill("Фёдор Фёдорович");
        phoneAtoFill("random");
        agreementAutoCheck(true);
        pushTheButton("Запланировать");
        $(".notification__content")
                .shouldHave(Condition.text(
                                "Встреча успешно запланирована на " + dateToSet),
                        Duration.ofSeconds(15)
                );

    }

}