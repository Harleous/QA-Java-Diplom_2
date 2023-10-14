package change.userdata;

import clients.UserClient;
import data.providers.NormalUserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import pojo.classes.CreateUser;
import pojo.classes.LogOutUser;
import pojo.classes.LoginUser;

public class ChangeUserDataWithNOAuthorizationTest {
    private String accessToken;
    public static String token;

    @Test
    @DisplayName("Изменение данных  покупателя")
    @Description("Изменение данных покупателя без авторизации")
    public void notAuthorizedUserDataShouldBeChangedTest() {


        CreateUser createUser = NormalUserData.randomUserData();
        UserClient.create(createUser);
        //Логин
        LoginUser loginUser = LoginUser.fromCreateUserData(createUser);
        token = UserClient.loginUser(loginUser)
                .extract().jsonPath()
                .get("refreshToken");

LogOutUser logOutUser = new LogOutUser(token);
        UserClient.logOutUser(logOutUser.getToken());

        CreateUser createUser1 = NormalUserData.randomUserData();

        UserClient.changeUserDataWithNoAuth(createUser1);


        loginUser.fromCreateUserData(createUser);
        accessToken = UserClient.loginUser(loginUser)
                .log().all()
                .statusCode(200)
                .body("success", Matchers.equalTo(true))
                .extract().jsonPath()
                .get("accessToken");
    }
        @After
        public void tearDown() {
            if (accessToken != null) {
                UserClient.delete(accessToken).log().all().statusCode(202);
            }
    }
}