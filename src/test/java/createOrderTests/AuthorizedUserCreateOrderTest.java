package createOrderTests;



import clients.UserClient;
import dataProviders.NormalUserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;
import pojoClasses.CreateUser;
import pojoClasses.Ingredients;

import pojoClasses.LoginUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AuthorizedUserCreateOrderTest {
    private String accessToken;



    @Test
    @DisplayName("Cоздание заказа")
    @Description("Создание заказа авторизованным пользователем")
    public void orderShouldBeCreated() {
        CreateUser createUser = NormalUserData.randomUserData();
        UserClient.create(createUser)
                .log().all()
                .statusCode(200)
                .body("success", Matchers.equalTo(true));

        //Логин
        LoginUser loginUser = LoginUser.fromCreateUserData(createUser);
        accessToken = UserClient.loginUser(loginUser)
                .log().all()
                .statusCode(200)
                .body("success", Matchers.equalTo(true))
                .extract().jsonPath().get("accessToken");

        List<String> ids =  UserClient.getIngredients(accessToken).extract().jsonPath().getList("data._id");
        System.out.println(ids);

        int randomIngredientId1 = new Random().nextInt(ids.size());
        int randomIngredientId2 = new Random().nextInt(ids.size());
        List<String> ingredientsForOrder = List.of(new String[]{ids.get(randomIngredientId1), ids.get(randomIngredientId2)});


        Ingredients ingredients = new Ingredients(ingredientsForOrder);
        UserClient.createOrder(ingredients.getIngredients())
                .log().all()
                .statusCode(200)
                .body("success", Matchers.equalTo(true));

        /*List<String> ids =  UserClient.getIngredients(accessToken).extract().jsonPath().getList("data._id");
System.out.println(ids);

        //int randomIngredientId = new Random().nextInt(ids.size());
        String ingredients = ids.get(new Random().nextInt(ids.size()));




        //Ingredients ingredients = new Ingredients(ingred);
UserClient.createOrder(ingredients )
        .log().all()
        .statusCode(200)
        .body("success", Matchers.equalTo(true));
*/


    }
}
