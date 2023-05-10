package ru.netology.delivery.data;

import static com.codeborne.selenide.Selenide.open;

public class ChromeTestBot {
    /**
     * @param shiftDateToSet int to shift date
     * @param dateFormat dd.MM.yyyy for app-card-delivery.jar
     * @param city Use "random" or test data
     * @param name Use "random" or test data
     * @param phone Use "random" or test data
     * @param agreement True to check the box and false to not check
     * @param buttonText Use text for search the button from all the buttons at page
     * @return Returns generated date.
     */
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
