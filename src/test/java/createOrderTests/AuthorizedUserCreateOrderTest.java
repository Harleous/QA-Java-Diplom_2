package createOrderTests;

import clients.UserClient;
import dataProviders.NormalUserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;
import pojoClasses.CreateUser;
import pojoClasses.LoginUser;

import java.io.IOException;
import java.util.HashMap;

public class AuthorizedUserCreateOrderTest {
    private String accessToken;
    private UserClient userClient = new UserClient();
private String ingredients;

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

        ingredients =  UserClient.getIngredients(accessToken)
                .log().all()
                .statusCode(200)
                .extract().path("data._id[3]" );


UserClient.createOrder( ingredients)
        .log().all()
        .statusCode(200)
        .body("success", Matchers.equalTo(true));


    }
}
