package org.example.clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    private static final String ORDER_PATH = "/api/orders";

    @Step("Создание заказа")
    public ValidatableResponse createOrder(String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .log().all() // логируем реквест
                .body("{\"ingredients\": \"61c0c5a71d1f82001bdaaa6d\" }")
                .when()
                .post(ORDER_PATH)
                .then()
                .log().all(); // логируем респонс
    }

    @Step("Создание заказа без ингредиентов")
    public ValidatableResponse createOrderWithoutIngredients(String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .log().all() // логируем реквест
                .when()
                .post(ORDER_PATH)
                .then()
                .log().all(); // логируем респонс
    }

    @Step("Создание заказа с неверным хешем")
    public ValidatableResponse createOrderWrongHash(String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .log().all() // логируем реквест
                .body("{\"ingredients\": \"123\" }")
                .when()
                .post(ORDER_PATH)
                .then()
                .log().all(); // логируем респонс
    }

    @Step("Получение списка заказов конкретного пользователя")
    public ValidatableResponse getUserOrderList(String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .log().all() // логируем реквест
                .when()
                .get(ORDER_PATH)
                .then()
                .log().all(); // логируем респонс
    }
}