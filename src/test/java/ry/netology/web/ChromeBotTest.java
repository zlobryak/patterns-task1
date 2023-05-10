package ry.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.AutoRegistration;
import ru.netology.delivery.data.ChromeTestBot;

import java.time.Duration;

import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Selenide.$;
import static ru.netology.delivery.data.DataGenerator.generateDate;

public class ChromeBotTest {
    @Test
    void usingBotHappyPath() {
        String dateToSet = ChromeTestBot.autoRegistration(
                3,
                "dd.MM.yyyy",
                "random",
                "random",
                "random",
                true,
                "Запланировать"

        );
        String dateToReplan = generateDate(5, "dd.MM.yyyy");
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
    void shouldBookCardWithRegistrationMethodWrongCity() {
        ChromeTestBot.autoRegistration(
                3,
                "dd.MM.yyyy",
                "Мга",
                "random",
                "random",
                true,
                "Запланировать"

        );
        $("[data-test-id='city'] ,input-sub")
                .shouldHave(Condition.text(
                        "Доставка в выбранный город недоступна")
                )
                .shouldBe(Condition.visible);
    }
    @Test
    void shouldBookCardWithRegistrationMethodWrongDate() {
        ChromeTestBot.autoRegistration(
                2,
                "dd.MM.yyyy",
                "random",
                "random",
                "random",
                true,
                "Запланировать"

        );
        $("[data-test-id='date']")
                .shouldHave(Condition.text(
                        "Заказ на выбранную дату невозможен")
                )
                .shouldBe(Condition.visible);
    }
    @Test
    void shouldBookCardWithRegistrationMethodWrongName() {
        ChromeTestBot.autoRegistration(
                4,
                "dd.MM.yyyy",
                "random",
                "123",
                "random",
                true,
                "Запланировать"

        );
        $("[data-test-id='name'] ,input-sub")
                .shouldHave(Condition.text(
                        "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")
                )
                .shouldBe(Condition.visible);
    }
    @Test
    void shouldBookCardWithRegistrationMethodWrongAgreement() {
        ChromeTestBot.autoRegistration(
                4,
                "dd.MM.yyyy",
                "random",
                "random",
                "random",
                false,
                "Запланировать"
        );
        $(".input_invalid").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));
    }
}
