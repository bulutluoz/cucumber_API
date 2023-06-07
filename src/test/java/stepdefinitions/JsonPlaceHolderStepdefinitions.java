package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Assert;
import utilities.ConfigReader;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class JsonPlaceHolderStepdefinitions {
    String endPoint="";
    JsonPath responseJP;
    JSONObject postRequestBody;
    Response response;

    @Given("Kullanici {string} base URL'ini kullanir")
    public void kullanici_base_url_ini_kullanir(String istenenBaseUrl) {
        endPoint = ConfigReader.getProperty(istenenBaseUrl);
    }
    @Then("Path parametreleri icin {string} kullanir")
    public void path_parametreleri_icin_kullanir(String pathParams) {
        endPoint= endPoint+"/"+pathParams;
    }
    @And("jPH server a GET request gonderir ve testleri yapmak icin response degerini kaydeder")
    public void jphServerAGETRequestGonderirVeTestleriYapmakIcinResponseDegeriniKaydeder() {
        System.out.println(endPoint);
        response= given().when().get(endPoint);

    }




    @Then("jPH respons'da status degerinin {int}")
    public void jphResponsDaStatusDegerinin(int statusCode) {
        System.out.println(response.statusCode());
        Assert.assertEquals(statusCode,response.statusCode());
    }

    @And("jPH respons'da content type degerinin {string}")
    public void jphResponsDaContentTypeDegerinin(String contentType) {
        Assert.assertEquals(contentType,response.contentType());
    }


    @Then("jPH GET respons body'sinde {string} degerinin Integer {int}")
    public void jphGETResponsBodySindeDegerininInteger(String attribute, int value) {
        responseJP = response.jsonPath();
        Assert.assertEquals(value,responseJP.getInt(attribute));
    }

    @And("jPH GET respons body'sinde {string} degerinin String {string}")
    public void jphGETResponsBodySindeDegerininString(String attribute, String value) {
        responseJP = response.jsonPath();
        Assert.assertEquals(value,responseJP.getString(attribute));
    }


    @Then("POST request icin {string},{string},{int} {int} bilgileri ile request body olusturur")
    public void post_request_icin_bilgileri_ile_request_body_olusturur(String title, String body, Integer userId, Integer id) {
        postRequestBody =new JSONObject();
        postRequestBody.put("title",title);
        postRequestBody.put("body",body);
        postRequestBody.put("userId",userId);
        postRequestBody.put("id",id);
    }

    @And("jPH server a POST request gonderir ve testleri yapmak icin response degerini kaydeder")
    public void jphServerAPOSTRequestGonderirVeTestleriYapmakIcinResponseDegeriniKaydeder() {

        response = given()
                        .when().body(postRequestBody.toString()).contentType(ContentType.JSON)
                        .put(endPoint);
        response.prettyPrint();

    }

    @And("jPH respons daki {string} header degerinin {string}")
    public void jphResponsDakiHeaderDegerinin(String headerAttribute, String value) {
        assertEquals(value,response.header("Connection"));
    }


    @Then("response attribute degerlerinin {string},{string},{int} {int}")
    public void response_attribute_degerlerinin(String title, String body, Integer userId, Integer id) {


        responseJP = response.jsonPath();
        Assert.assertEquals(title,responseJP.getString("title"));
        Assert.assertEquals(body,responseJP.getString("body"));
        Assert.assertEquals(userId,(Integer)responseJP.getInt("userId"));
        Assert.assertEquals(id,(Integer)responseJP.getInt("id"));
    }
}
