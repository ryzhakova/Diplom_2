package diplom2.service;

import diplom2.entity.User;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;

@Story("Creating user data")
public class UserService {

    @DisplayName("Creating a random user")
    public static User generateRandomUser() {
        return User.builder()
                .password(generatePassword())
                .name(generateRandomName())
                .email(generateRandomEmail())
                .build();
    }

    @DisplayName("Creating a random email address")
    public static String generateRandomEmail() {
        return String.format("%s@%s.ru", RandomStringUtils.randomAlphabetic(6), RandomStringUtils.randomAlphabetic(6)).toLowerCase(Locale.ROOT);
    }

    @DisplayName("Creating a random name")
    public static String generateRandomName() {
        return RandomStringUtils.randomAlphabetic(6);
    }

    @DisplayName("Creating a random password")
    public static String generatePassword() {
        return RandomStringUtils.randomAlphabetic(12);
    }
}
