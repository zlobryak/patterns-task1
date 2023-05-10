package ry.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.delivery.data.DataGenerator.generateDate;
import ru.netology.delivery.data.AutoRegistration;

public class ChromeTest {
    // TODO Сделать проверку на букву ё
    @Test
    void shouldBookCardWithRegistrationMethodHappyPath() {
        Configuration.holdBrowserOpen = true;
        int dateToSetShift = 4;
        String dateToReplan = generateDate(5, "dd.MM.yyyy");
        open("http://localhost:9999/");
        AutoRegistration registration = new AutoRegistration();
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
}