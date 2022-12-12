import io.qameta.allure.Description;
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

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class GetOrderTest {
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
    @DisplayName("Проверка получения списка заказов")
    @Description("Запрос от авторизованного пользователя")
    public void getOrdersWithToken() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
        orderClient.createOrder(accessToken);
        ValidatableResponse responseList = orderClient.getUserOrderList(accessToken);
        ArrayList<String> trackInList = responseList.extract().path("orders._id");
        Assert.assertNotNull("Order list should not be null", trackInList);
        int statusCode = responseList.extract().statusCode();
        Assert.assertEquals("Wrong status get list orders", SC_OK, statusCode);
    }

    @Test
    @DisplayName("Проверка получения списка заказов")
    @Description("Запрос от неавторизованного пользователя")
    public void getOrdersWithoutToken() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
        orderClient.createOrder(accessToken);
        ValidatableResponse responseList = orderClient.getUserOrderList("");
        String actualMessage = responseList.extract().path("message");
        Assert.assertEquals("Wrong message for unauthorised user", "You should be authorised", actualMessage);
        int statusCode = responseList.extract().statusCode();
        Assert.assertEquals("Wrong status for unauthorised user", SC_UNAUTHORIZED, statusCode);
    }
}