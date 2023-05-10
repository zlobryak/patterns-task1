package ry.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.AutoRegistration;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.delivery.data.DataGenerator.*;
import static ru.netology.delivery.data.DataGenerator.Registration.generateUser;
import static ru.netology.delivery.data.DataGenerator.generateDate;

public class ChromeTest {
    // TODO Сделать проверку на букву ё
//    public String generateDateToSet(long addDays, String pattern) {
//        return LocalDate.now()
//                .plusDays(addDays)
//                .format(DateTimeFormatter
//                        .ofPattern(pattern));
//    }

    @Test
    void shouldBookCardDeliveryHappyPath() {
        Configuration.holdBrowserOpen = true;
        String dateToSet = generateDate(4, "dd.MM.yyyy");
        String dateToReplan = generateDate(5, "dd.MM.yyyy");

        open("http://localhost:9999/");

        $("[data-test-id=city] input")
                .sendKeys(generateUser("ru").getCity());
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME),
                        Keys.BACK_SPACE);
        $(".calendar-input input")
                .setValue(dateToSet).sendKeys(Keys.ESCAPE);
        $("[data-test-id=name] input")
                .setValue(generateUser("ru").getName());
        $("[data-test-id=phone] input")
                .setValue(generateUser("ru").getPhone());

        $(withText("соглашаюсь")).click();
        $(withText("Запланировать")).click();

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
        $(withText("Запланировать")).click();
        $("[data-test-id='replan-notification'] button").click();
        $(".notification__content")
                .shouldHave(Condition.text(
                                "Встреча успешно запланирована на " + dateToReplan),
                        Duration.ofSeconds(15)
                )
                .shouldBe(Condition.visible);

    }

    @Test
    void shouldBookCardWithRegistrationMethodHappyPath() {
        Configuration.holdBrowserOpen = true;
        int dateToSetShift = 4;
        String dateToReplan = generateDate(5, "dd.MM.yyyy");
        String city= "random";
        String name = "random";
        String phone = "random";
        boolean agreement = true;

        open("http://localhost:9999/");
        AutoRegistration registration = new AutoRegistration();
        registration.cityAutoFill(city);
        registration.dateAutoFill(dateToSetShift);
        registration.nameAotuFill(name);
        registration.phoneAtoFill(phone);
        registration.agreementAutoCheck(agreement);
        registration.pushTheButton("Запланировать");


        $(".notification__content")
                .shouldHave(Condition.text(
                                "Встреча успешно запланирована на " + generateDate(dateToSetShift, "dd.MM.yyyy")),
                        Duration.ofSeconds(15)
                )
                .shouldBe(Condition.visible);

        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME),
                        Keys.BACK_SPACE);
        $(".calendar-input input")
                .setValue(dateToReplan).sendKeys(Keys.ESCAPE);
        $(withText("Запланировать")).click();

        registration.pushTheButton("Перепланировать");
        $(".notification__content")
                .shouldHave(Condition.text(
                                "Встреча успешно запланирована на " + dateToReplan),
                        Duration.ofSeconds(15)
                )
                .shouldBe(Condition.visible);
    }
    //    @Test
//    void shouldBookCardDeliveryWrongDate() {
//        open("http://localhost:9999/");
//        SelenideElement block = $("fieldset");
//        block.$("[data-test-id=city] input").sendKeys("Челябинск");
//
//
//        String dateToSet = generateDateToSet(1, "dd.MM.yyyy");
//
//        $("[data-test-id='date'] input")
//                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME),
//                        Keys.BACK_SPACE);
//
//        block.$(".calendar-input input").setValue(dateToSet).sendKeys(Keys.ESCAPE);
//        block.$("[data-test-id=name] input").setValue("Василий Пупкин");
//        block.$("[data-test-id=phone] input").setValue("+79062421277");
//
//        block.$(withText("соглашаюсь")).click();
//        block.$(withText("Запланировать")).click();
//
//        $("[data-test-id='date']")
//                .shouldHave(Condition.text(
//                        "Заказ на выбранную дату невозможен")
//                )
//                .shouldBe(Condition.visible);
//    }
//    @Test
//    void shouldBookCardDeliveryWrongPhone() {
//        open("http://localhost:9999/");
//        SelenideElement block = $("fieldset");
//        block.$("[data-test-id=city] input").sendKeys("Челябинск");
//
//
//        String dateToSet = generateDateToSet(3, "dd.MM.yyyy");
//
//        $("[data-test-id='date'] input")
//                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME),
//                        Keys.BACK_SPACE);
//
//        block.$(".calendar-input input").setValue(dateToSet);
//        block.$("[data-test-id=name] input").setValue("Василий Пупкин");
//        block.$("[data-test-id=phone] input").setValue("543");
//
//        block.$(withText("соглашаюсь")).click();
//        block.$(withText("Запланировать")).click();
//
//        $("[data-test-id='phone'] ,input-sub")
//                .shouldHave(Condition.text(
//                        "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")
//                )
//                .shouldBe(Condition.visible);
//    }
//    @Test
//    void shouldBookCardDeliveryWrongName() {
//        open("http://localhost:9999/");
//        SelenideElement block = $("fieldset");
//        block.$("[data-test-id=city] input").sendKeys("Челябинск");
//
//
//        String dateToSet = generateDateToSet(3, "dd.MM.yyyy");
//
//        $("[data-test-id='date'] input")
//                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME),
//                        Keys.BACK_SPACE);
//
//        block.$(".calendar-input input").setValue(dateToSet);
//        block.$("[data-test-id=name] input").setValue("1");
//        block.$("[data-test-id=phone] input").setValue("+79012345678");
//
//        block.$(withText("соглашаюсь")).click();
//        block.$(withText("Запланировать")).click();
//
//        $("[data-test-id='name'] ,input-sub")
//                .shouldHave(Condition.text(
//                        "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")
//                )
//                .shouldBe(Condition.visible);
//    }@Test
//    void shouldBookCardDeliveryWrongCity() {
//        open("http://localhost:9999/");
//        SelenideElement block = $("fieldset");
//        block.$("[data-test-id=city] input").sendKeys("Мга");
//
//
//        String dateToSet = generateDateToSet(3, "dd.MM.yyyy");
//
//        $("[data-test-id='date'] input")
//                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME),
//                        Keys.BACK_SPACE);
//
//        block.$(".calendar-input input").setValue(dateToSet);
//        block.$("[data-test-id=name] input").setValue("Илья");
//        block.$("[data-test-id=phone] input").setValue("+79012345678");
//
//        block.$(withText("соглашаюсь")).click();
//        block.$(withText("Запланировать")).click();
//
//        $("[data-test-id='city'] ,input-sub")
//                .shouldHave(Condition.text(
//                        "Доставка в выбранный город недоступна")
//                )
//                .shouldBe(Condition.visible);
//    }

}