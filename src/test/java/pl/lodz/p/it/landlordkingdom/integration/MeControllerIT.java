package pl.lodz.p.it.landlordkingdom.integration;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.landlordkingdom.mok.dto.AuthenticatedChangePasswordRequest;
import pl.lodz.p.it.landlordkingdom.mok.dto.UpdateUserDataRequest;
import pl.lodz.p.it.landlordkingdom.mok.dto.Verify2FATokenRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class MeControllerIT extends BaseConfig {
    private static String ME_URL = baseUrl;
    private static String AUTH_URL = baseUrl;

    private String adminToken;

    @BeforeEach
    public void setUp() {
        ME_URL = baseUrl + "/me";
        AUTH_URL = baseUrl + "/auth";
        loadDataSet("src/test/resources/datasets/userForMeIT.xml");

        Verify2FATokenRequest verifyRequest = new Verify2FATokenRequest("adminUser", "20099984");

        Response response = given()
                .contentType(ContentType.JSON)
                .header("X-Forwarded-For", "203.0.113.195")
                .when()
                .body(verifyRequest)
                .post(AUTH_URL + "/verify-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        adminToken = response.path("token");
    }


    @Test
    @DisplayName("getUserData_userExists_returnOK")
    public void getUserData_userExists_returnOK() {
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(ME_URL)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("login", equalTo("adminUser"));
    }

    @Test
    @DisplayName("updateUserData_IfMatchCorrect_returnOK")
    public void updateUserData_IfMatchCorrect_returnOK() {
        Response response = given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(ME_URL)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("language", equalTo("en"))
                .extract()
                .response();

        String etag = response.getHeader(HttpHeaders.ETAG);
        etag = etag.substring(1, etag.length() - 1);
        UpdateUserDataRequest updateRequest = new UpdateUserDataRequest("admin", "user", "pl", "Pacific/Midway");

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .header(HttpHeaders.IF_MATCH, etag)
                .when()
                .body(updateRequest)
                .put(ME_URL)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("language", equalTo("pl"));
    }

    @Test
    @DisplayName("updateUserData_IfMatchIncorrect_returnPreconditionFailed")
    public void updateUserData_IfMatchIncorrect_returnPreconditionFailed() {
        Response response = given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .get(ME_URL)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("language", equalTo("en"))
                .extract()
                .response();

        String etag = response.getHeader(HttpHeaders.ETAG);
        etag = etag.substring(1, etag.length() - 1);
        UpdateUserDataRequest updateRequest = new UpdateUserDataRequest("admin", "user", "pl","Pacific/Midway");

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .header(HttpHeaders.IF_MATCH, etag)
                .when()
                .body(updateRequest)
                .put(ME_URL)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("language", equalTo("pl"));

        UpdateUserDataRequest updateRequest2 = new UpdateUserDataRequest("admin", "user", "en","Pacific/Midway");

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .header(HttpHeaders.IF_MATCH, etag)
                .when()
                .body(updateRequest2)
                .put(ME_URL)
                .then()
                .assertThat()
                .statusCode(HttpStatus.PRECONDITION_FAILED.value());
    }

    @Test
    @DisplayName("changePassword_oldPasswordValid_returnOK")
    public void changePassword_userExists_returnOK() {
        AuthenticatedChangePasswordRequest changePasswordRequest = new AuthenticatedChangePasswordRequest("password", "newPassword");

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .body(changePasswordRequest)
                .post(ME_URL + "/change-password")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("changePasswordWithToken_oldPasswordNotValid_returnPRECONDITIONFAILED")
    public void changePasswordWithToken_oldPasswordNotValid_returnPRECONDITIONFAILED() {
        AuthenticatedChangePasswordRequest changePasswordRequest = new AuthenticatedChangePasswordRequest("oldPassword", "newPassword");

        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(adminToken)
                .when()
                .body(changePasswordRequest)
                .post(ME_URL + "/change-password")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}
