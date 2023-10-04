package createOrderTests;

import clients.UserClient;
import dataProviders.NormalUserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import pojoClasses.CreateUser;
import pojoClasses.Ingredients;
import pojoClasses.LoginUser;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class AuthorizedUserCreateOrderWrongIngredientsIds {
    private String accessToken;

    @Test
    @DisplayName("Cоздание заказа")
    @Description("Создание заказа авторизованным пользователем")
    public void orderShouldBeCreated() {
        CreateUser createUser = NormalUserData.randomUserData();
        UserClient.create(createUser)
                .log().all()
                .statusCode(200)
                .body("success", equalTo(true));

        //Логин
        LoginUser loginUser = LoginUser.fromCreateUserData(createUser);
        accessToken = UserClient.loginUser(loginUser)
                .log().all()
                .statusCode(200)
                .body("success", equalTo(true))
                .extract().jsonPath().get("accessToken");



        List<String> ingredientsForOrder = List.of(new String[]{"12345NoFat","54321EatLess"});


        Ingredients ingredients = new Ingredients(ingredientsForOrder);
       /* Response responses =*/
        UserClient.createOrder(ingredients.getIngredients())
                .log().all()
                .statusCode(500)
                .log().all();
               /* .extract().response();*/

      /*  XmlPath xmlPath=new XmlPath(responses.asString());
        String errorCode= xmlPath.getString("<message>");
        errorCode.compareTo("Internal Server Error"); equalTo("Internal Server Error");*/


    }
    @After
    public void tearDown () {
        if (accessToken != null) {
            UserClient.delete(accessToken).log().all().statusCode(202);
        }
    }
}
