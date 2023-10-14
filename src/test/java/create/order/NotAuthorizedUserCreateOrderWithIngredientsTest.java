package create.order;

import clients.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.Test;
import pojo.classes.Ingredients;

import java.util.List;
import java.util.Random;

public class NotAuthorizedUserCreateOrderWithIngredientsTest {
    private String accessToken;

    @Test
    @DisplayName("Cоздание заказа")
    @Description("Создание заказа НЕавторизованным пользователем, с передачей ингредиентов")
    public void orderShouldBeCreated() {

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
}