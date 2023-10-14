package clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static clients.BaseConstants.BASE_URI;

public class BaseClient {
    protected static RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URI)
                .build();
    }

    protected static RequestSpecification getSpecAuth(String accessToken) {
        return new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .addHeader("Authorization", accessToken)
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URI)
                .build();
    }
}
