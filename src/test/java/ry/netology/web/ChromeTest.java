package ry.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.AutoRegistration;

import java.time.Duration;

import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.delivery.data.DataGenerator.generateDate;

public class ChromeTest {
    AutoRegistration registration = new AutoRegistration();
    // TODO Сделать проверку на букву ё
    @Test
    void shouldBookCardWithRegistrationMethodHappyPath() {
        Configuration.holdBrowserOpen = true;
        int dateToSetShift = 4;
        String dateToReplan = generateDate(5, "dd.MM.yyyy");
        open("http://localhost:9999/");
        registration.cityAutoFill("random");
        String dateToSet = registration.dateAutoFill(dateToSetShift);
        registration.nameAutoFill("random");
        registration.phoneAtoFill("random");
        registration.agreementAutoCheck(true);
        registration.pushTheButton("Запланировать");

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

        registration.pushTheButton("Запланировать");
        registration.pushTheButton("Перепланировать");

        $(".notification__content")
                .shouldHave(Condition.text(
                                "Встреча успешно запланирована на " + dateToReplan),
                        Duration.ofSeconds(15)
                )
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldBookCardWithRegistrationMethodWrongCity() {
        int dateToSetShift = 1;
        open("http://localhost:9999/");

        registration.cityAutoFill("Мга");
        registration.dateAutoFill(dateToSetShift);
        registration.nameAutoFill("random");
        registration.phoneAtoFill("random");
        registration.agreementAutoCheck(true);
        registration.pushTheButton("Запланировать");
        $("[data-test-id='city'] ,input-sub")
                .shouldHave(Condition.text(
                        "Доставка в выбранный город недоступна")
                )
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldBookCardWithRegistrationMethodWrongDate() {
        open("http://localhost:9999/");
        int dateToSetShift = 1;
        registration.cityAutoFill("random");
        registration.dateAutoFill(dateToSetShift);
        registration.nameAutoFill("random");
        registration.phoneAtoFill("random");
        registration.agreementAutoCheck(true);
        registration.pushTheButton("Запланировать");
        $("[data-test-id='date']")
                .shouldHave(Condition.text(
                        "Заказ на выбранную дату невозможен")
                )
                .shouldBe(Condition.visible);
    }
    @Test
    void shouldBookCardWithRegistrationMethodWrongName() {
        open("http://localhost:9999/");
        int dateToSetShift = 3;
        registration.cityAutoFill("random");
        registration.dateAutoFill(dateToSetShift);
        registration.nameAutoFill("111");
        registration.phoneAtoFill("random");
        registration.agreementAutoCheck(true);
        registration.pushTheButton("Запланировать");
        $("[data-test-id='name'] ,input-sub")
                .shouldHave(Condition.text(
                        "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")
                )
                .shouldBe(Condition.visible);
    }
//    @Test
//    void shouldBookCardWithRegistrationMethodWrongPhone() {
//        open("http://localhost:9999/");
//        int dateToSetShift = 3;
//        registration.cityAutoFill("random");
//        registration.dateAutoFill(dateToSetShift);
//        registration.nameAutoFill("random");
//        registration.phoneAtoFill("111");
//        registration.agreementAutoCheck(true);
//        registration.pushTheButton("Запланировать");
//        $("[data-test-id='name'] ,input-sub")
//                .shouldHave(Condition.text(
//                        "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")
//                )
//                .shouldBe(Condition.visible);
//    }
    @Test
    void shouldBookCardWithRegistrationMethodWrongAgreement() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        int dateToSetShift = 3;
        registration.cityAutoFill("random");
        registration.dateAutoFill(dateToSetShift);
        registration.nameAutoFill("random");
        registration.phoneAtoFill("111");
        registration.agreementAutoCheck(false);
        registration.pushTheButton("Запланировать");
        $(".input_invalid").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));
    }

}