import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.clients.UserClient;
import org.example.generators.UserGenerator;
import org.example.models.Credentials;
import org.example.models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class NegativeLoginTest {
    private UserClient userClient;
    private User user;
    private User userWithoutPassword;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();
        userWithoutPassword = UserGenerator.getWithoutPassword();
    }

    @Test
    @DisplayName("Вход под несуществующим пользователем невозможен")
    public void wrongLoginReturnError() {
        userClient.createUser(userWithoutPassword);
        ValidatableResponse responseLogin = userClient.loginUser(Credentials.from(user));
        String actualMessage = responseLogin.extract().path("message");
        Assert.assertEquals("Wrong message if wrong login", "email or password are incorrect", actualMessage);
        int statusCode = responseLogin.extract().statusCode();
        Assert.assertEquals("Wrong status if wrong login", SC_UNAUTHORIZED, statusCode);
    }
}