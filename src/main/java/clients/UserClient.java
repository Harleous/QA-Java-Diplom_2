package clients;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import pojoClasses.*;

import java.lang.System.Logger;
import java.util.List;

import static clients.BaseClient.*;
import static io.restassured.RestAssured.given;

public class UserClient {
    public static ValidatableResponse loginUser(LoginUser loginUser) {
        return given()
                .spec(getSpec())
                .body(loginUser)
                .when()
                .post("/api/auth/login")
                .then();

    }

    public static ValidatableResponse logOutUser(String token) {
        LogOutUser logOutUser = new LogOutUser(token
        );
        return given()
                .spec(getSpec())
                .body(logOutUser)
                .when()
                .post("/api/auth/logout")
                .then();

    }

    public static ValidatableResponse loginNoEmail(NoEmailUserLogin noEmailUserLogin) {
        return given()
                .spec(getSpec())
                .body(noEmailUserLogin)
                .when()
                .post("/api/auth/login")
                .then();

    }

    public static ValidatableResponse loginNotValidPairEmailPassword(NotValidPairEmailPassword notValidPairEmailPassword) {
        return given()
                .spec(getSpec())
                .body(notValidPairEmailPassword)
                .when()
                .post("/api/auth/login")
                .then();

    }

    public static ValidatableResponse delete(String accessToken) {
        return given()
                .spec(getSpecAuth(accessToken))
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/auth/user")
                .then();
    }

    public static ValidatableResponse changeUserData(String accessToken, CreateUser createUser1) {

        return given()
                .spec(getSpecAuth(accessToken))
                .contentType(ContentType.JSON)
                .body(createUser1)
                .when()
                .patch("/api/auth/user")
                .then();
    }

    public static ValidatableResponse changeUserDataWithNoAuth(CreateUser createUser1) {

        return given()
                .spec(getSpec())
                .contentType(ContentType.JSON)
                .body(createUser1)
                .when()
                .patch("/api/auth/user")
                .then();
    }

    public static ValidatableResponse create(CreateUser createUser) {

        return given()
                .spec(getSpec())
                .contentType(ContentType.JSON)
                .body(createUser)
                .when()
                .post("/api/auth/register")
                .then();

    }

    public static ValidatableResponse getIngredients() {

        return given()
                .spec(getSpec())
                .contentType(ContentType.JSON)
                .when()
                .get("/api/ingredients")
                .then();
    }

    public static ValidatableResponse createOrder(List<String> ingredients) {
        Ingredients ingredients1 = new Ingredients(ingredients);
        return given()
                .spec(getSpec())
                .contentType(ContentType.JSON)
                .log().all()
                .body(ingredients1)
                .when()
                .post("/api/orders")
                .then();
    }

    public static ValidatableResponse getUserOrders(String accessToken) {
        return given()
                .spec(getSpecAuth(accessToken))
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/api/orders")
                .then();

    }

    public static ValidatableResponse getUserOrdersNoToken() {return given()
            .spec(getSpec())
            .contentType(ContentType.JSON)
            .log().all()
            .when()
            .get("/api/orders")
            .then();
    }
}