package util;

import com.github.javafaker.Faker;

public class FakerData {
    private static final Faker FAKER = new Faker();

    public static String getEmail() {
        return FAKER.internet().emailAddress();
    }

    public static String getPassword() {
        return FAKER.internet().password();
    }

    public static String getName() {
        return FAKER.name().firstName();
    }

}
