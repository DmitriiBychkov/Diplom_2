package org.example.clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.models.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    private static final String ORDER_PATH = "/api/orders";

    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order, String accessToken) {
        return given()
                .spec(getSpec())
                .auth().oauth2(accessToken)
                .log().all() // логируем реквест
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then()
                .log().all(); // логируем респонс
    }

    @Step("Получение списка заказов конкретного пользователя")
    public ValidatableResponse getUserOrderList(String accessToken) {
        return given()
                .spec(getSpec())
                .auth().oauth2(accessToken)
                .log().all() // логируем реквест
                .when()
                .get(ORDER_PATH)
                .then()
                .log().all(); // логируем респонс
    }
}
