package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }


    private static Faker faker;

    static void setFaker() {
        faker = new Faker(new Locale("ru"));
    }


    /**
     * @param addDays How many days to shift
     * @param pattern Format could be find in DateTimeFormatter.
     *                For example dd.MM.yyyy will return xx.xx.xxxx
     */
    public static String generateDate(int addDays, String pattern) {
        return LocalDate.now()
                .plusDays(addDays)
                .format(DateTimeFormatter
                        .ofPattern(pattern));
    }

    public static String generateCity(String locale) {
        // TODO: добавить логику для объявления переменной city
        //  и задания её значения, генерацию можно выполнить
        // с помощью Faker, либо используя массив валидных
        // городов и класс Random
       return null;
    }

    public static String generateName(String locale) {
        return String.valueOf(faker.name());
    }

    public static String generatePhone(String locale) {
        // TODO: добавить логику для объявления переменной phone
        //  и задания её значения, для генерации можно
        // использовать Faker
        return null;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            // TODO: добавить логику для создания пользователя user
            //  с использованием методов generateCity(locale),
            // generateName(locale), generatePhone(locale)
            return null;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}