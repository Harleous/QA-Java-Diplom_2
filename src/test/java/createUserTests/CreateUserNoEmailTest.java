package createUserTests;

import clients.UserClient;
import dataProviders.NoEmailUser;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import pojoClasses.CreateUser;
import pojoClasses.LoginUser;

public class CreateUserNoEmailTest {
    private String accessToken;

    @Test
    @DisplayName("Неправильное создание покупателя")
    @Description("Cоздание покупателя без поля Email")
    public void UserWithNoEmailShouldNotBeCreatedTest(){



        CreateUser createUser = NoEmailUser.noEmailUserDataProvider();
        UserClient.create(createUser)
                .log().all()
                .statusCode(403)
                .body("success", Matchers.equalTo(false))
                .body( "message", Matchers.equalTo("Email, password and name are required fields"));

        //Логин
        LoginUser loginUser = LoginUser.fromCreateUserData(createUser);
        accessToken = UserClient.loginUser(loginUser)
                .log().all()
                .statusCode(401)
                .body(/*"success", Matchers.equalTo("false"),*/ "message", Matchers.equalTo("email or password are incorrect"))
                .extract().jsonPath().get("accessToken");



    }
    //Удаление
    @After
    public void tearDown() {
        if (accessToken != null) {
            UserClient.delete(accessToken).log().all().statusCode(202);
        }
    }
}
