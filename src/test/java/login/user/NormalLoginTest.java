package login.user;

import clients.UserClient;
import data.providers.NormalUserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import pojo.classes.CreateUser;
import pojo.classes.LoginUser;

public class NormalLoginTest {
    private String accessToken;

    @Test
    @DisplayName("Авторизация покупателя")
    @Description("Авторизация покупателя с заполнением всех полей")
    public void userShouldBeCreatedTest(){



        CreateUser createUser = NormalUserData.randomUserData();
        UserClient.create(createUser);
        //Логин
        LoginUser loginUser = LoginUser.fromCreateUserData(createUser);

        ValidatableResponse response = UserClient.loginUser(loginUser);
                accessToken = response .extract().jsonPath()
                .get("accessToken");
               response .log().all()
                .statusCode(200)
                .body("success", Matchers.equalTo(true));
    }
    //Удаление
    @After
    public void tearDown() {
        if (accessToken != null) {
            UserClient.delete(accessToken).log().all().statusCode(202);
        }
    }
}
