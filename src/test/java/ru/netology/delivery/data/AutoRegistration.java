package ru.netology.delivery.data;

import org.openqa.selenium.Keys;

import java.util.Objects;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static ru.netology.delivery.data.DataGenerator.Registration.generateUser;
import static ru.netology.delivery.data.DataGenerator.generateDate;

public class AutoRegistration {
    public void cityAutoFill(String city) {
        if (Objects.equals(city, "random")) {

            $("[data-test-id=city] input")
                    .sendKeys(generateUser("ru").getCity());
        } else {
            $("[data-test-id=city] input")
                    .sendKeys(city);
        }
    }

    public String dateAutoFill(int dateToSetShift) {
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME),
                        Keys.BACK_SPACE);
        String dateToSet = generateDate(dateToSetShift, "dd.MM.yyyy");
        $(".calendar-input input")
                .setValue(dateToSet).sendKeys(Keys.ESCAPE);
        return dateToSet;
    }

    public void nameAotuFill(String name) {
        if (Objects.equals(name, "random")) {
            $("[data-test-id=name] input")
                    .setValue(generateUser("ru").getName());
        } else {
            $("[data-test-id=name] input")
                    .setValue(name);
        }
    }

    public void phoneAtoFill(String phone) {
        if (Objects.equals(phone, "random")) {
            $("[data-test-id=phone] input")
                    .setValue(generateUser("ru").getPhone());
        } else {
            $("[data-test-id=phone] input")
                    .setValue(phone);
        }
    }
    public void agreementAutoCheck(boolean agreement) {
        if (agreement) {
            $(withText("соглашаюсь")).click();
        }
    }

    public void pushTheButton(String buttonText){
        $(withText(buttonText)).click();
    }
}

