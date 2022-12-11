import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.clients.OrderClient;
import org.example.clients.UserClient;
import org.example.generators.OrderGenerator;
import org.example.generators.UserGenerator;
import org.example.models.Order;
import org.example.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;

//@RunWith(Parameterized.class)
public class CreateOrderTest {
    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();
    }

    @After
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }

//    public CreateOrderTest(Order order) {
//        this.order = order;
//    }

//    @Parameterized.Parameters
//    public static Object[][] getOrderData() {
//        return new Object[][]{
//                {OrderGenerator.getWithBlackOnly()},
//                {OrderGenerator.getWithGrayOnly()},
//                {OrderGenerator.getWithBlackAndGray()},
//                {OrderGenerator.getWithoutColor()}
//        };
//    }

    @Test
    @DisplayName("Создание заказа с авторизацией и ингредиентами")
    public void orderCanBeCreatedWithLoginAndIngredents() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
//        track = response.extract().path("track");
//        int actualTrack = response.extract().path("track");
//        Assert.assertEquals("Order isn`t created", track, actualTrack);
//        int statusCode = response.extract().statusCode();
//        Assert.assertEquals("Wrong status create", SC_CREATED, statusCode);
    }
}
