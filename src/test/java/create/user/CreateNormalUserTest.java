package create.user;

import clients.UserClient;
import data.providers.NormalUserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import pojo.classes.CreateUser;

public class CreateNormalUserTest {
    private String accessToken;
    private UserClient userClient = new UserClient();
    @Test
    @DisplayName("Cоздание покупателя")
    @Description("Нормальное создание покупателя с заполнением всех полей")
    public void userShouldBeCreatedTest(){

        CreateUser createUser = NormalUserData.randomUserData();
        userClient.create(createUser)
                .log().all()
                .statusCode(200)
                .body("success", Matchers.equalTo(true));
        }
    @After
    public void tearDown() {
if(accessToken != null) {
    userClient.delete(accessToken).log().all().statusCode(202);
}
    }
}
