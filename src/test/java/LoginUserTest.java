import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.clients.UserClient;
import org.example.generators.UserGenerator;
import org.example.models.Credentials;
import org.example.models.CredentialsWithWrongEmail;
import org.example.models.CredentialsWithWrongPassword;
import org.example.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class LoginUserTest {
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
    @DisplayName("Вход пользователя под существующим пользователем возможен")
    public void userCanBeLogin() {
        userClient.createUser(user);
        ValidatableResponse responseLogin = userClient.loginUser(Credentials.from(user));
        accessToken = responseLogin.extract().path("accessToken");
        int statusCodeLogin = responseLogin.extract().statusCode();
        Assert.assertEquals("Wrong status for login", SC_OK, statusCodeLogin);
    }

    @Test
    @DisplayName("Вход пользователя невозможен с неверной почтой")
    public void loginWithWrongEmail() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        ValidatableResponse responseLogin = userClient.loginUserWithWrongEmail(CredentialsWithWrongEmail.from(user));
        accessToken = responseCreate.extract().path("accessToken");
        String actualMessage = responseLogin.extract().path("message");
        Assert.assertEquals("Wrong message without enter email", "email or password are incorrect", actualMessage);
        int statusCode = responseLogin.extract().statusCode();
        Assert.assertEquals("Wrong status without enter email", SC_UNAUTHORIZED, statusCode);
    }

    @Test
    @DisplayName("Вход пользователя невозможен с неверным паролем")
    public void loginWithWrongPassword() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        ValidatableResponse responseLogin = userClient.loginUserWithWrongPassword(CredentialsWithWrongPassword.from(user));
        accessToken = responseCreate.extract().path("accessToken");
        String actualMessage = responseLogin.extract().path("message");
        Assert.assertEquals("Wrong message without enter password", "email or password are incorrect", actualMessage);
        int statusCode = responseLogin.extract().statusCode();
        Assert.assertEquals("Wrong status without enter password", SC_UNAUTHORIZED, statusCode);
    }
}