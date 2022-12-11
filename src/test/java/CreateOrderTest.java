import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.clients.OrderClient;
import org.example.clients.UserClient;
import org.example.generators.UserGenerator;
import org.example.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

public class CreateOrderTest {
    private UserClient userClient;
    private User user;
    private String accessToken;
    private OrderClient orderClient;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();
        orderClient = new OrderClient();
    }

    @After
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и ингредиентами")
    public void orderCanBeCreatedWithLoginAndIngredients() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
        ValidatableResponse responseCreateOrder = orderClient.createOrder(accessToken);
        boolean isOrderCreated = responseCreateOrder.extract().path("success");
        Assert.assertTrue("Order isn`t created", isOrderCreated);
        int statusCode = responseCreateOrder.extract().statusCode();
        Assert.assertEquals("Wrong status create", SC_OK, statusCode);
    }

    @Test
    @DisplayName("Создание заказа без авторизации с ингредиентами")
    public void orderCanBeCreatedWithoutLoginWithIngredients() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
        ValidatableResponse responseCreateOrder = orderClient.createOrder("");
        boolean isOrderCreated = responseCreateOrder.extract().path("success");
        Assert.assertTrue("Order isn`t created", isOrderCreated);
        int statusCode = responseCreateOrder.extract().statusCode();
        Assert.assertEquals("Wrong status create", SC_OK, statusCode);
    }

    @Test
    @DisplayName("Создание заказа без авторизации и без ингредиентов")
    public void orderCanNotBeCreatedWithoutLoginWithoutIngredients() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
        ValidatableResponse responseCreateOrder = orderClient.createOrderWithoutIngredients("");
        String actualMessage = responseCreateOrder.extract().path("message");
        Assert.assertEquals("Order isn`t created", "Ingredient ids must be provided", actualMessage);
        int statusCode = responseCreateOrder.extract().statusCode();
        Assert.assertEquals("Wrong status create", SC_BAD_REQUEST, statusCode);
    }

    @Test
    @DisplayName("Создание заказа c авторизацией без ингредиентов")
    public void orderCanNotBeCreatedWithLoginWithoutIngredients() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
        ValidatableResponse responseCreateOrder = orderClient.createOrderWithoutIngredients(accessToken);
        String actualMessage = responseCreateOrder.extract().path("message");
        Assert.assertEquals("Order isn`t created", "Ingredient ids must be provided", actualMessage);
        int statusCode = responseCreateOrder.extract().statusCode();
        Assert.assertEquals("Wrong status create", SC_BAD_REQUEST, statusCode);
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиента")
    public void orderCanNotBeCreatedWithWrongIngredients() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
        ValidatableResponse responseCreateOrder = orderClient.createOrderWrongHash(accessToken);
        int statusCode = responseCreateOrder.extract().statusCode();
        Assert.assertEquals("Wrong status error", SC_INTERNAL_SERVER_ERROR, statusCode);
    }
}