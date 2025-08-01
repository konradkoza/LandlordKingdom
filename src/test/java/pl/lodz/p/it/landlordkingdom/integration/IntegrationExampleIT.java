package pl.lodz.p.it.landlordkingdom.integration;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.landlordkingdom.mok.dto.AuthenticationRequest;

import static io.restassured.RestAssured.given;

public class IntegrationExampleIT extends BaseConfig {


    @Test
    public void test() {
        given()
                .when()
                .get(baseUrl)
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void dbunitTest() throws Exception {
        loadDataSet("src/test/resources/datasets/user.xml");

        AuthenticationRequest request = new AuthenticationRequest("test_login1", "password", "en");

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(request)
                .post(baseUrl + "/auth/signin-2fa")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());


//        ReplacementDataSet dataSetFromDb = createDataSetFromDb();
//        IDataSet resultDataset = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/datasets/userResult.xml"));
//        assertEquals(resultDataset, dataSetFromDb);
    }
}
