package create.order;

import clients.UserClient;
import data.providers.NormalUserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import pojo.classes.CreateUser;
import pojo.classes.Ingredients;
import pojo.classes.LoginUser;

import java.util.List;

public class AuthorizedUserCreateOrderWrongIngredientsIds {
    private String accessToken;

    @Test
    @DisplayName("Cоздание заказа")
    @Description("Создание заказа авторизованным пользователем  с неверным хешем ингредиентов")
    public void orderShouldBeCreated() {
        CreateUser createUser = NormalUserData.randomUserData();
        UserClient.create(createUser);

        //Логин
        LoginUser loginUser = LoginUser.fromCreateUserData(createUser);
        accessToken = UserClient.loginUser(loginUser)
                .extract().jsonPath().get("accessToken");

        List<String> ingredientsForOrder = List.of(new String[]{"12345NoFat","54321EatLess"});

        Ingredients ingredients = new Ingredients(ingredientsForOrder);
       /* Response responses =*/
        UserClient.createOrder(ingredients.getIngredients())
                .log().all()
                .statusCode(500)
                .log().all();
               }
    @After
    public void tearDown () {
        if (accessToken != null) {
            UserClient.delete(accessToken).log().all().statusCode(202);
        }
    }
}
