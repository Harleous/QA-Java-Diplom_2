package clients;

import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

public class BaseClient {
    protected static RequestSpecification getSpec(){
        return new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .setContentType(ContentType.JSON)
                .setBaseUri("https://stellarburgers.nomoreparties.site")
                .build();
    }
    protected static RequestSpecification getSpecAuth(String accessToken){
        return new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .addHeader("Authorization", accessToken)
                .setContentType(ContentType.JSON)
                .setBaseUri("https://stellarburgers.nomoreparties.site")
                .build();
    }
    protected static RequestSpecification getSpecRefresh (String refreshToken){
        JSONObject requestBody = new JSONObject()
                .put("token", refreshToken);
        return new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .setContentType(ContentType.JSON)
                .setBody(requestBody)
                .setBaseUri("https://stellarburgers.nomoreparties.site")
                .build();
    }
}
