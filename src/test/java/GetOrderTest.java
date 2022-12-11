import org.example.clients.OrderClient;
import org.example.clients.UserClient;
import org.junit.After;
import org.junit.Before;

public class GetOrderTest {

    private OrderClient orderClient;
    private String accessToken;
    private UserClient userClient;

    @Before
    public void setUp() {
        userClient = new UserClient();
        orderClient = new OrderClient();
    }

    @After
    public void clean() {
        userClient.deleteUser(accessToken);
    }

//    @Test
//    @DisplayName("Проверка получения списка заказов")
//    @Description("Список авторизованного пользователя")
//    public void GetOrders() {
//        ValidatableResponse response = orderClient.createOrder(OrderGenerator.getWithBlackAndGray());
//        track = response.extract().path("track");
//        ValidatableResponse responseList = orderClient.getUserOrderList(accessToken);
//        ArrayList<String> trackInList = responseList.extract().path("orders.id");
//        Assert.assertNotNull("Order list is not be null", trackInList);
//        int statusCode = responseList.extract().statusCode();
//        Assert.assertEquals("Wrong status create", SC_OK, statusCode);
//    }
}
