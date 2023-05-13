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
        faker = new Faker(new Locale(locale));
        return faker.address().city();
    }

    public static String generateName(String locale) {
        faker = new Faker(new Locale(locale));
        return faker.name().fullName();
    }

    public static String generatePhone(String locale) {
        faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        public Registration() {
        }
        public static UserInfo generateUser(String locale) {
            return new UserInfo(
                    generateCity(locale),
                    generateName(locale),
                    generatePhone(locale)
            );
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}