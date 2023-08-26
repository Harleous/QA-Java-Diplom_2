package changeUserDataTests;

import clients.UserClient;
import dataProviders.NormalUserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import pojoClasses.CreateUser;
import pojoClasses.LoginUser;

public class ChangeUserDataWithAuthorizationTest {
    private String accessToken;
    private UserClient userClient = new UserClient();
    @Test
    @DisplayName("Изменение данных покупателя")
    @Description("Изменение данных покупателя с авторизацией")
    public void AuthorizedUserDataShouldBeChangedTest(){



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
                .extract().jsonPath()
                .get("accessToken");

        CreateUser createUser1 = NormalUserData.randomUserData();

                userClient.changeUserData(accessToken, createUser1)
                .log().all()
                .statusCode(200)
                .body("success", Matchers.equalTo(true));

        LoginUser loginUser1 = LoginUser.fromCreateUserData(createUser1);
         UserClient.loginUser(loginUser1)
                .log().all()
                .statusCode(200)
                .body("success", Matchers.equalTo(true));

    }
    //Удаление
    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.delete(accessToken).log().all().statusCode(202);
        }
    }
}
