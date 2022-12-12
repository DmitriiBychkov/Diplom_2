import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.clients.UserClient;
import org.example.generators.UserGenerator;
import org.example.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;

public class CreateUserTest {
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

    @Test
    @DisplayName("При успешном создании уникального пользователя, создаётся его accessToken")
    public void userCanBeCreated() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
        boolean isUserCreated = responseCreate.extract().path("success");
        Assert.assertTrue("User isn`t created", isUserCreated);
        int statusCode = responseCreate.extract().statusCode();
        Assert.assertEquals("Wrong status create user", SC_OK, statusCode);
    }

    @Test
    @DisplayName("Создание дубля пользователя невозможно")
    public void userCanNotBeCreatedDouble() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        ValidatableResponse responseCreateDouble = userClient.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
        String actualMessage = responseCreateDouble.extract().path("message");
        Assert.assertEquals("Wrong message create double", "User already exists", actualMessage);
        int statusCode = responseCreateDouble.extract().statusCode();
        Assert.assertEquals("Wrong status create double", SC_FORBIDDEN, statusCode);
    }
}