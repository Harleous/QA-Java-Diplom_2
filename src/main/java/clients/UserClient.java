package clients;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import pojoClasses.CreateUser;
import pojoClasses.LoginUser;
import pojoClasses.NoEmailUserLogin;
import pojoClasses.NotValidPairEmailPassword;

import static clients.BaseClient.getSpec;
import static clients.BaseClient.getSpecAuth;
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


    public ValidatableResponse create(CreateUser createUser) {

        return given()
                .spec(getSpec())
                .contentType(ContentType.JSON)
                .body(createUser)
                .when()
                .post("/api/auth/register")
                .then();

    }
}
