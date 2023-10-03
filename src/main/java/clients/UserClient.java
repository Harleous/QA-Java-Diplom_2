package clients;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import pojoClasses.CreateUser;
import pojoClasses.LoginUser;
import pojoClasses.NoEmailUserLogin;
import pojoClasses.NotValidPairEmailPassword;

import java.util.ArrayList;
import java.util.List;

import static clients.BaseClient.*;
import static io.restassured.RestAssured.given;

public class UserClient {
    public static ValidatableResponse loginUser(LoginUser loginUser) {
        return   given()
                .spec(getSpec())
                .body(loginUser)
                .when()
                .post("/api/auth/login")
                .then();

    }
    public static ValidatableResponse logOutUser(String token) {

        return   given()
                .spec(getSpecRefresh(token))
                .when()
                .post("/api/auth/logout")
                .then();

    }
    public static ValidatableResponse loginNoEmail (NoEmailUserLogin noEmailUserLogin) {
        return   given()
                .spec(getSpec())
                .body(noEmailUserLogin)
                .when()
                .post("/api/auth/login")
                .then();

    }

    public static ValidatableResponse loginNotValidPairEmailPassword (NotValidPairEmailPassword notValidPairEmailPassword) {
        return   given()
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
    public static ValidatableResponse getIngredients (String accessToken)  {

       return given()
                .spec(getSpecAuth(accessToken))
                .contentType(ContentType.JSON)
                .when()
                .get("/api/ingredients")
                .then();
    }
    public static ValidatableResponse createOrder (ArrayList<String> ingredients)  {

        return given()
                .contentType(ContentType.JSON)
                .body(ingredients)
                .when()
                .post("/api/orders")
                .then();
    }

}
