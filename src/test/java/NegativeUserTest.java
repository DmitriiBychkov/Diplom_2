import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.clients.UserClient;
import org.example.generators.UserGenerator;
import org.example.models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;

public class NegativeUserTest {
    private UserClient userClient;
    private User userWithoutEmail;
    private User userWithoutPassword;
    private User userWithoutName;

    @Before
    public void setUp() {
        userClient = new UserClient();
        userWithoutEmail = UserGenerator.getWithoutEmail();
        userWithoutPassword = UserGenerator.getWithoutPassword();
        userWithoutName = UserGenerator.getWithoutName();
    }

    @Test
    @DisplayName("Создание пользователя без почты невозможно")
    public void userCanNotBeCreatedWithoutEmail() {
        ValidatableResponse responseCreate = userClient.createUser(userWithoutEmail);
        String actualMessage = responseCreate.extract().path("message");
        Assert.assertEquals("Wrong message created without email", "Email, password and name are required fields", actualMessage);
        int statusCode = responseCreate.extract().statusCode();
        Assert.assertEquals("Wrong status created without email", SC_FORBIDDEN, statusCode);
    }

    @Test
    @DisplayName("Создание пользователя без пароля невозможно")
    public void userCanNotBeCreatedWithoutPassword() {
        ValidatableResponse responseCreate = userClient.createUser(userWithoutPassword);
        String actualMessage = responseCreate.extract().path("message");
        Assert.assertEquals("Wrong message created without password", "Email, password and name are required fields", actualMessage);
        int statusCode = responseCreate.extract().statusCode();
        Assert.assertEquals("Wrong status created without password", SC_FORBIDDEN, statusCode);
    }

    @Test
    @DisplayName("Создание пользователя без имени невозможно")
    public void userCanNotBeCreatedWithoutName() {
        ValidatableResponse responseCreate = userClient.createUser(userWithoutName);
        String actualMessage = responseCreate.extract().path("message");
        Assert.assertEquals("Wrong message created without name", "Email, password and name are required fields", actualMessage);
        int statusCode = responseCreate.extract().statusCode();
        Assert.assertEquals("Wrong status created without name", SC_FORBIDDEN, statusCode);
    }
}