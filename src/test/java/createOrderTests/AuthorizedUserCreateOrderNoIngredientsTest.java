package createOrderTests;

import clients.UserClient;
import dataProviders.NormalUserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import pojoClasses.CreateUser;
import pojoClasses.Ingredients;
import pojoClasses.LoginUser;

import java.util.List;


public class AuthorizedUserCreateOrderNoIngredientsTest {
    private String accessToken;

    @Test
    @DisplayName("Cоздание заказа")
    @Description("Создание заказа авторизованным пользователем, без ингредиентов")
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



        List<String> ingredientsForOrder = List.of(new String[]{});


        Ingredients ingredients = new Ingredients(ingredientsForOrder);
        UserClient.createOrder(ingredients.getIngredients())
                .log().all()
                .statusCode(400)
                .body("success", Matchers.equalTo(false))
                .body("message", Matchers.equalTo("Ingredient ids must be provided"));

    }
    @After
    public void tearDown () {
        if (accessToken != null) {
            UserClient.delete(accessToken).log().all().statusCode(202);
        }
    }
}
