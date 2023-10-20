package clients;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import pojo.classes.*;
import java.util.List;
import static clients.BaseClient.*;
import static clients.BaseConstants.*;
import static io.restassured.RestAssured.given;

public class UserClient {
    @Step("Логин пользователя")
    public static ValidatableResponse loginUser(LoginUser loginUser) {
        return given()
                .spec(getSpec())
                .body(loginUser)
                .when()
                .post(LOGIN_USER_PATH)
                .then();

    }
@Step("Логаут пользователя")
    public static ValidatableResponse logOutUser(String token) {
        LogOutUser logOutUser = new LogOutUser(token
        );
        return given()
                .spec(getSpec())
                .body(logOutUser)
                .when()
                .post(LOGOUT_USER_PATH)
                .then();

    }
@Step("Логин пользователя без email")
    public static ValidatableResponse loginNoEmail(NoEmailUserLogin noEmailUserLogin) {
        return given()
                .spec(getSpec())
                .body(noEmailUserLogin)
                .when()
                .post(LOGIN_USER_PATH)
                .then();

    }
@Step("Логин пользователя с невалидной парой email/password")
    public static ValidatableResponse loginNotValidPairEmailPassword(NotValidPairEmailPassword notValidPairEmailPassword) {
        return given()
                .spec(getSpec())
                .body(notValidPairEmailPassword)
                .when()
                .post(LOGIN_USER_PATH)
                .then();

    }
@Step("Удаление пользователя")
    public static ValidatableResponse delete(String accessToken) {
        return given()
                .spec(getSpecAuth(accessToken))
                .contentType(ContentType.JSON)
                .when()
                .delete(CHANGE_DELETE_USER_PATH)
                .then();
    }
@Step("Изменение данных пользователя")
    public static ValidatableResponse changeUserData(String accessToken, CreateUser createUser1) {

        return given()
                .spec(getSpecAuth(accessToken))
                .contentType(ContentType.JSON)
                .body(createUser1)
                .when()
                .patch(CHANGE_DELETE_USER_PATH)
                .then();
    }
@Step("Изменение данных пользователся без авторизации")
    public static ValidatableResponse changeUserDataWithNoAuth(CreateUser createUser1) {

        return given()
                .spec(getSpec())
                .contentType(ContentType.JSON)
                .body(createUser1)
                .when()
                .patch(CHANGE_DELETE_USER_PATH)
                .then();
    }

    @Step("Создание пользователя")
    public static ValidatableResponse create(CreateUser createUser) {

        return given()
                .spec(getSpec())
                .contentType(ContentType.JSON)
                .body(createUser)
                .when()
                .post(CREATE_USER)
                .then();

    }
@Step("Получение списка ингредиентов")
    public static ValidatableResponse getIngredients() {

        return given()
                .spec(getSpec())
                .contentType(ContentType.JSON)
                .when()
                .get(GET_INGREDIENTS)
                .then();
    }
@Step("Создание заказа")
    public static ValidatableResponse createOrder(List<String> ingredients) {
        Ingredients ingredients1 = new Ingredients(ingredients);
        return given()
                .spec(getSpec())
                .contentType(ContentType.JSON)
                .log().all()
                .body(ingredients1)
                .when()
                .post(CREATE_GET_ORDER)
                .then();
    }
@Step("Получение заказов пользователя")
    public static ValidatableResponse getUserOrders(String accessToken) {
        return given()
                .spec(getSpecAuth(accessToken))
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get(CREATE_GET_ORDER)
                .then();
    }
@Step("Получение заказов пользователя без авторизации")
    public static ValidatableResponse getUserOrdersNoToken() {
        return given()
                .spec(getSpec())
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get(CREATE_GET_ORDER)
                .then();
    }
}