import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.clients.UserClient;
import org.example.generators.UserGenerator;
import org.example.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class ChangeUserInfoTest {
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
    @DisplayName("Изменение данных пользователя с авторизацией")
    public void userCanBeEdit() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
        ValidatableResponse responseEdit = userClient.editUser(accessToken);
        boolean isUserEdited = responseEdit.extract().path("success");
        Assert.assertTrue("User-info isn`t changed", isUserEdited);
        int statusCode = responseEdit.extract().statusCode();
        Assert.assertEquals("Wrong status for change", SC_OK, statusCode);
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void userCanNotBeEdit() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        ValidatableResponse responseEdit = userClient.editUser("wrongToken");
        accessToken = responseCreate.extract().path("accessToken");
        String actualMessage = responseEdit.extract().path("message");
        Assert.assertEquals("Wrong message for bad change", "You should be authorised", actualMessage);
        int statusCode = responseEdit.extract().statusCode();
        Assert.assertEquals("Wrong status for bad change", SC_UNAUTHORIZED, statusCode);
    }
}