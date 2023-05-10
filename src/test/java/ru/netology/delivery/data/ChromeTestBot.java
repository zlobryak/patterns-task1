package ru.netology.delivery.data;

import static com.codeborne.selenide.Selenide.open;

public class ChromeTestBot {
    public static String autoRegistration(
            int shiftDateToSet,
            String dateFormat,
            String city,
            String name,
            String phone,
            boolean agreement,
            String buttonText
    ) {
        open("http://localhost:9999/");
        AutoRegistration.cityAutoFill(city);
        String dateToSet = AutoRegistration.dateAutoFill(shiftDateToSet, dateFormat);
        AutoRegistration.nameAutoFill(name);
        AutoRegistration.phoneAtoFill(phone);
        AutoRegistration.agreementAutoCheck(agreement);
        AutoRegistration.pushTheButton(buttonText);
        return dateToSet;

    }
}
