package changeUserDataTests;

import clients.BaseClient;
import clients.UserClient;
import dataProviders.NormalUserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Test;
import pojoClasses.CreateUser;
import pojoClasses.LogOutUser;
import pojoClasses.LoginUser;

public class ChangeUserDataWithNOAuthorizationTest {
    private String accessToken;
    public static String refreshToken;




    @Test
    @DisplayName("Изменение данных неавторизованного покупателя")
    @Description("Изменение данных покупателя без авторизации")
    public void notAuthorizedUserDataShouldBeChangedTest() {


        CreateUser createUser = NormalUserData.randomUserData();
        UserClient.create(createUser)
                .log().all()
                .statusCode(200)
                .body("success", Matchers.equalTo(true));

        //Логин
        LoginUser loginUser = LoginUser.fromCreateUserData(createUser);
        refreshToken = UserClient.loginUser(loginUser)
                .log().all()
                .statusCode(200)
                .body("success", Matchers.equalTo(true))
                .extract().jsonPath()
                .get("refreshToken");





        UserClient.logOutUser(refreshToken)
                .log().all()
                .statusCode(200)
                .body("message",Matchers.equalTo( "Successful logout"));








    }
        @After
        public void tearDown() {
            if (accessToken != null) {
                UserClient.delete(accessToken).log().all().statusCode(202);
            }
    }
}