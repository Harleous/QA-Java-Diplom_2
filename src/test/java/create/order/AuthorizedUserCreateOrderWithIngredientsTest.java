package create.order;


import clients.UserClient;
import data.providers.NormalUserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import pojo.classes.CreateUser;
import pojo.classes.Ingredients;
import pojo.classes.LoginUser;

import java.util.List;
import java.util.Random;

public class AuthorizedUserCreateOrderWithIngredientsTest {


    private String accessToken;

    @Test
    @DisplayName("Cоздание заказа")
    @Description("Создание заказа авторизованным пользователем, с передачей ингредиентов")
    public void orderShouldBeCreated() {
        CreateUser createUser = NormalUserData.randomUserData();
        UserClient.create(createUser);
        //Логин
        LoginUser loginUser = LoginUser.fromCreateUserData(createUser);
        accessToken = UserClient.loginUser(loginUser)
                .extract().jsonPath().get("accessToken");

        List<String> ids = UserClient.getIngredients().extract().jsonPath().getList("data._id");

        int randomIngredientId1 = new Random().nextInt(ids.size());
        int randomIngredientId2 = new Random().nextInt(ids.size());
        List<String> ingredientsForOrder = List.of(new String[]{ids.get(randomIngredientId1), ids.get(randomIngredientId2)});

        Ingredients ingredients = new Ingredients(ingredientsForOrder);
        UserClient.createOrder(ingredients.getIngredients())
                .log().all()
                .statusCode(200)
                .body("success", Matchers.equalTo(true));
    }
    @After
    public void tearDown() {
        if (accessToken != null) {
            UserClient.delete(accessToken).log().all().statusCode(202);
        }
    }
}

