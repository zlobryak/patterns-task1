package ru.netology.delivery.data;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static ru.netology.delivery.data.DataGenerator.Registration.generateUser;
import static ru.netology.delivery.data.DataGenerator.generateDate;

public class AutoRegistration {
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
     *                 or false not to tick
     */
    public static void agreementAutoCheck(boolean agreement) {
        if (agreement) {
            $(withText("соглашаюсь")).click();
        }
    }

    public static void pushTheButton(String buttonText){
        $$(".button__text").findBy(Condition.text(buttonText)).click();
    }
}

