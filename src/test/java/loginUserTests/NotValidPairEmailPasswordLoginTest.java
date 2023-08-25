package loginUserTests;

import clients.UserClient;
import dataProviders.NormalUserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import pojoClasses.CreateUser;
import pojoClasses.LoginUser;
import pojoClasses.NotValidPairEmailPassword;

public class NotValidPairEmailPasswordLoginTest {
    private String accessToken;
    private UserClient userClient = new UserClient();
    @Test
    @DisplayName("Cоздание покупателя и авторизация с неверным паролем")
    @Description("Нормальное создание покупателя, но авторизация с другим паролем")
    public void useerShouldNotBeAuthorizedTest(){



        CreateUser createUser = NormalUserData.randomUserData();
          userClient.create(createUser)
                .log().all()
                .statusCode(200)
                .body("success", Matchers.equalTo(true));

        LoginUser loginUser = LoginUser.fromCreateUserData(createUser);
        accessToken = UserClient.loginUser(loginUser)
                .log().all()
                .statusCode(200)
                .body("success", Matchers.equalTo(true))
                .extract().jsonPath()
                .get("accessToken");




        NotValidPairEmailPassword notValidPairEmailPassword = NotValidPairEmailPassword.fromCreateUserData(createUser);
         UserClient.loginNotValidPairEmailPassword(notValidPairEmailPassword)
                .log().all()
                .statusCode(401)
                .body("message", Matchers.equalTo("email or password are incorrect"))
                .extract().jsonPath();



    }
    //Удаление
    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.delete(accessToken).log().all().statusCode(202);
        }
    }
}
