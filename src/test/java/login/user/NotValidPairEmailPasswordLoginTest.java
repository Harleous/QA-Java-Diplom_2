package login.user;

import clients.UserClient;
import data.providers.NormalUserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import pojo.classes.CreateUser;
import pojo.classes.LoginUser;
import pojo.classes.NotValidPairEmailPassword;

public class NotValidPairEmailPasswordLoginTest {
    private String accessToken;
    private UserClient userClient = new UserClient();
    @Test
    @DisplayName("Cоздание покупателя и авторизация с неверным паролем")
    @Description("Нормальное создание покупателя, но авторизация с другим паролем")
    public void UserShouldNotBeAuthorizedTest(){

        CreateUser createUser = NormalUserData.randomUserData();
          userClient.create(createUser);

        LoginUser loginUser = LoginUser.fromCreateUserData(createUser);
        accessToken = UserClient.loginUser(loginUser)
                .extract().jsonPath()
                .get("accessToken");

        NotValidPairEmailPassword notValidPairEmailPassword = NotValidPairEmailPassword.fromCreateUserData(createUser);
         UserClient.loginNotValidPairEmailPassword(notValidPairEmailPassword)
                .log().all()
                .statusCode(401)
                .body("message", Matchers.equalTo("email or password are incorrect"))
                .extract().jsonPath();
    }
    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.delete(accessToken).log().all().statusCode(202);
        }
    }
}
