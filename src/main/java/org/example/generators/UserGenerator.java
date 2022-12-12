package org.example.generators;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.models.User;

import java.util.Random;

public class UserGenerator {
    public static String email = new Random().nextInt(1000) + "new-test-mail" + new Random().nextInt(1000) + "@yandex.ru";
    public static String password = RandomStringUtils.randomAlphabetic(6) + new Random().nextInt(100);
    public static String name = RandomStringUtils.randomAlphabetic(7) + new Random().nextInt(10);

    public static User getRandomUser() {
        return new User(email, password, name);
    }

    public static User getWithoutEmail() {
        return new User("", password, name);
    }

    public static User getWithoutPassword() {
        return new User(email, "", name);
    }

    public static User getWithoutName() {
        return new User(email, password, "");
    }
}