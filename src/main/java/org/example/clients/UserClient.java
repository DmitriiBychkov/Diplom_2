package org.example.clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.models.Credentials;
import org.example.models.CredentialsWithWrongEmail;
import org.example.models.CredentialsWithWrongPassword;
import org.example.models.User;

import static io.restassured.RestAssured.given;
import static org.example.generators.UserGenerator.*;

public class UserClient extends Client {
    private static final String CREATE_PATH = "/api/auth/register";
    private static final String LOGIN_PATH = "/api/auth/login";
    private static final String DELETE_PATH = "/api/auth/user";

    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user) {
        return given()
                .spec(getSpec())
                .log().all() // логируем реквест
                .body(user)
                .when()
                .post(CREATE_PATH)
                .then()
                .log().all(); // логируем респонс
    }

    @Step("Вход пользователя")
    public ValidatableResponse loginUser(Credentials credentials) {
        return given()
                .spec(getSpec())
                .log().all() // логируем реквест
                .body(credentials)
                .when()
                .post(LOGIN_PATH)
                .then()
                .log().all(); // логируем респонс
    }

    @Step("Вход пользователя с неверной почтой")
    public ValidatableResponse loginUserWithWrongEmail(CredentialsWithWrongEmail credentialsWithWrongEmail) {
        return given()
                .spec(getSpec())
                .log().all() // логируем реквест
                .body(credentialsWithWrongEmail)
                .when()
                .post(LOGIN_PATH)
                .then()
                .log().all(); // логируем респонс
    }

    @Step("Вход пользователя с неверным паролем")
    public ValidatableResponse loginUserWithWrongPassword(CredentialsWithWrongPassword credentialsWithWrongPassword) {
        return given()
                .spec(getSpec())
                .log().all() // логируем реквест
                .body(credentialsWithWrongPassword)
                .when()
                .post(LOGIN_PATH)
                .then()
                .log().all(); // логируем респонс
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .log().all() // логируем реквест
                .when()
                .delete(DELETE_PATH)
                .then()
                .log().all(); // логируем респонс
    }

    @Step("Редактирование пользователя")
    public ValidatableResponse editUser(String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .log().all() // логируем реквест
                .body("{\"email\": \"" + "1" + email + "\", \"password\": \"" + "1" + password + "\", \"name\": \"" + "1" + name + "\" }")
                .when()
                .patch(DELETE_PATH)
                .then()
                .log().all(); // логируем респонс
    }
}