package getUserOrdersTests;

import clients.UserClient;
import dataProviders.NormalUserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import pojoClasses.CreateUser;
import pojoClasses.Ingredients;
import pojoClasses.LogOutUser;
import pojoClasses.LoginUser;

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
        UserClient.create(createUser)
                .log().all()
                .statusCode(200)
                .body("success", Matchers.equalTo(true));

        //Логин
        LoginUser loginUser = LoginUser.fromCreateUserData(createUser);
        accessToken = UserClient.loginUser(loginUser)
                .extract().jsonPath().get("accessToken");

        token = UserClient.loginUser(loginUser)
                .log().all()
                .statusCode(200)
                .body("success", Matchers.equalTo(true))
                .extract().jsonPath().get("refreshToken");

        List<String> ids = UserClient.getIngredients().extract().jsonPath().getList("data._id");

        int randomIngredientId1 = new Random().nextInt(ids.size());
        int randomIngredientId2 = new Random().nextInt(ids.size());
        List<String> ingredientsForOrder = List.of(new String[]{ids.get(randomIngredientId1), ids.get(randomIngredientId2)});

//заказ1
        Ingredients ingredients = new Ingredients(ingredientsForOrder);
        UserClient.createOrder(ingredients.getIngredients())
                .log().all()
                .statusCode(200)
                .body("success", Matchers.equalTo(true));
        //заказ2
        Ingredients ingredients1 = new Ingredients(ingredientsForOrder);
        UserClient.createOrder(ingredients1.getIngredients())
                .log().all()
                .statusCode(200)
                .body("success", Matchers.equalTo(true));
        //заказ3
        Ingredients ingredients2 = new Ingredients(ingredientsForOrder);
        UserClient.createOrder(ingredients2.getIngredients())
                .log().all()
                .statusCode(200)
                .body("success", Matchers.equalTo(true));

        LogOutUser logOutUser = new LogOutUser(token);
        UserClient.logOutUser(logOutUser.getToken())
                .log().all()
                .statusCode(200)
                .body("message",Matchers.equalTo( "Successful logout"));


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
