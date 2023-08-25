package createUserTests;

import clients.UserClient;
import dataProviders.NormalUserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import pojoClasses.CreateUser;
import pojoClasses.LoginUser;

public class CreateSameUserTest {
    private String accessToken;
    private UserClient userClient = new UserClient();
    @Test
    @DisplayName("Cоздание покупателя c уже существующими данными")
    @Description("Создание покупателя с данными, зарегистрированными в базе данных")
    public void SameUserShouldNotBeCreatedTest(){



        CreateUser createUser = NormalUserData.randomUserData();
        userClient.create(createUser)
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


        userClient.create(createUser)
                .log().all()
                .statusCode(403)
                .body("message", Matchers.equalTo( "User already exists"));



    }
    //Удаление
    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.delete(accessToken).log().all().statusCode(202);
        }
    }
}
