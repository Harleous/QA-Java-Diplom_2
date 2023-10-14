package get.user.orders;

import clients.UserClient;
import data.providers.NormalUserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import pojo.classes.CreateUser;
import pojo.classes.Ingredients;
import pojo.classes.LogOutUser;
import pojo.classes.LoginUser;

import java.util.List;
import java.util.Random;

public class GetNotAuthorizesdUserOrdersTest {
    private String accessToken;
    private String token;
    @Test
    @DisplayName("Получение заказов пользователя")
    @Description("Получение заказов неавторизованного пользователя")
    public void orderShouldBeCreated() {
        CreateUser createUser = NormalUserData.randomUserData();
        UserClient.create(createUser);
        //Логин
        LoginUser loginUser = LoginUser.fromCreateUserData(createUser);
        accessToken = UserClient.loginUser(loginUser)
                .extract().jsonPath().get("accessToken");

        token = UserClient.loginUser(loginUser)
                .extract().jsonPath().get("refreshToken");

        List<String> ids = UserClient.getIngredients().extract().jsonPath().getList("data._id");

        int randomIngredientId1 = new Random().nextInt(ids.size());
        int randomIngredientId2 = new Random().nextInt(ids.size());
        List<String> ingredientsForOrder = List.of(new String[]{ids.get(randomIngredientId1), ids.get(randomIngredientId2)});

//заказ1
        Ingredients ingredients = new Ingredients(ingredientsForOrder);
        UserClient.createOrder(ingredients.getIngredients());
        //заказ2
        Ingredients ingredients1 = new Ingredients(ingredientsForOrder);
        UserClient.createOrder(ingredients1.getIngredients());
        //заказ3
        Ingredients ingredients2 = new Ingredients(ingredientsForOrder);
        UserClient.createOrder(ingredients2.getIngredients());

        LogOutUser logOutUser = new LogOutUser(token);
        UserClient.logOutUser(logOutUser.getToken());


        UserClient.getUserOrdersNoToken()
                .log().all()
                .statusCode(401)
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo( "You should be authorised"));
    }
    @After
    public void tearDown () {
        if (accessToken != null) {
            UserClient.delete(accessToken).log().all().statusCode(202);
        }
    }
}
