package ry.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.AutoRegistration;

import java.time.Duration;

import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.delivery.data.DataGenerator.generateDate;

public class ChromeTest {
    @Test
    @DisplayName("Should successfully book the card with given date and then rebook it with new date")
    void shouldBookCardWithRegistrationMethodHappyPath() {
        Configuration.holdBrowserOpen = true;
        int dateToSetShift = 4;
        String dateToReplan = generateDate(5, "dd.MM.yyyy");
        open("http://localhost:9999/");
        AutoRegistration.cityAutoFill("random");
        String dateToSet = AutoRegistration.dateAutoFill(dateToSetShift, "dd.MM.yyyy");
        AutoRegistration.nameAutoFill("random");
        AutoRegistration.phoneAtoFill("random");
        AutoRegistration.agreementAutoCheck(true);
        AutoRegistration.pushTheButton("Запланировать");

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
                .setValue(dateToReplan).sendKeys(Keys.ESCAPE);

        AutoRegistration.pushTheButton("Запланировать");
        AutoRegistration.pushTheButton("Перепланировать");

        $(".notification__content")
                .shouldHave(Condition.text(
                                "Встреча успешно запланирована на " + dateToReplan),
                        Duration.ofSeconds(15)
                )
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if book with wrong city name")
    void WrongCityMessageTest() {
        int dateToSetShift = 1;
        open("http://localhost:9999/");

        AutoRegistration.cityAutoFill("Мга");
        AutoRegistration.dateAutoFill(dateToSetShift, "dd.MM.yyyy");
        AutoRegistration.nameAutoFill("random");
        AutoRegistration.phoneAtoFill("random");
        AutoRegistration.agreementAutoCheck(true);
        AutoRegistration.pushTheButton("Запланировать");
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
        AutoRegistration.cityAutoFill("random");
        AutoRegistration.dateAutoFill(dateToSetShift, "dd.MM.yyyy");
        AutoRegistration.nameAutoFill("random");
        AutoRegistration.phoneAtoFill("random");
        AutoRegistration.agreementAutoCheck(true);
        AutoRegistration.pushTheButton("Запланировать");
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
        AutoRegistration.cityAutoFill("random");
        AutoRegistration.dateAutoFill(dateToSetShift, "dd.MM.yyyy");
        AutoRegistration.nameAutoFill("111");
        AutoRegistration.phoneAtoFill("random");
        AutoRegistration.agreementAutoCheck(true);
        AutoRegistration.pushTheButton("Запланировать");
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
        AutoRegistration.cityAutoFill("random");
        AutoRegistration.dateAutoFill(dateToSetShift, "dd.MM.yyyy");
        AutoRegistration.nameAutoFill("random");
        AutoRegistration.phoneAtoFill("111");
        AutoRegistration.agreementAutoCheck(false);
        AutoRegistration.pushTheButton("Запланировать");
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
        AutoRegistration.cityAutoFill("random");
        AutoRegistration.dateAutoFill(dateToSetShift, "dd.MM.yyyy");
        AutoRegistration.nameAutoFill("random");
        AutoRegistration.phoneAtoFill("111");
        AutoRegistration.agreementAutoCheck(true);
        AutoRegistration.pushTheButton("Запланировать");

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
        AutoRegistration.cityAutoFill("random");
        String dateToSet = AutoRegistration.dateAutoFill(dateToSetShift, "dd.MM.yyyy");
        AutoRegistration.nameAutoFill("Фёдор Фёдорович");
        AutoRegistration.phoneAtoFill("random");
        AutoRegistration.agreementAutoCheck(true);
        AutoRegistration.pushTheButton("Запланировать");
        $(".notification__content")
                .shouldHave(Condition.text(
                                "Встреча успешно запланирована на " + dateToSet),
                        Duration.ofSeconds(15)
                );

    }

}