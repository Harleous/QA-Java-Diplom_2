package create.user;

import clients.UserClient;
import data.providers.NormalUserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import pojo.classes.CreateUser;
import pojo.classes.LoginUser;

public class CreateSameUserTest {
    private String accessToken;
    private UserClient userClient = new UserClient();
    @Test
    @DisplayName("Cоздание покупателя c уже существующими данными")
    @Description("Создание покупателя с данными, зарегистрированными в базе данных")
    public void SameUserShouldNotBeCreatedTest(){

        CreateUser createUser = NormalUserData.randomUserData();
        userClient.create(createUser);
        //Логин
        LoginUser loginUser = LoginUser.fromCreateUserData(createUser);
        accessToken = UserClient.loginUser(loginUser)
                .extract().jsonPath().get("accessToken");

        userClient.create(createUser)
                .log().all()
                .statusCode(403)
                .body("message", Matchers.equalTo( "User already exists"));
    }
    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.delete(accessToken).log().all().statusCode(202);
        }
    }
}
