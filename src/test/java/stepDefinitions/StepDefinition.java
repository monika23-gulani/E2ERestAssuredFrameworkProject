package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import junit.framework.Assert;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.utils; 

public class StepDefinition extends utils{
	RequestSpecification input;
	ResponseSpecification res;
	Response response;
	TestDataBuild data=new TestDataBuild();
	static String place_id;
	
	
	@Given("Add Place payload")
	public void add_place_payload() throws IOException {
		
		input = given().spec(requestSpecification()).body(data.addPlacePayload());
	}
	
	@Given("Add Place payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		input = given().spec(requestSpecification()).body(data.addPlacePayload(name,language,address));
	}

	@When("user calls {string} with {string} Http request")
	public void user_calls_with_http_request(String resource, String reqMethod) {
		APIResources resAPI = APIResources.valueOf(resource);
		System.out.println(resAPI.getResource());
	    // Write code here that turns the phrase above into concrete actions
		res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	    // Write code here that turns the phrase above into concrete actions
		if(reqMethod.equalsIgnoreCase("POST"))
		{
	    response = input.when().post(resAPI.getResource())
	    		.then().spec(res).extract().response();
		}
		else if(reqMethod.equalsIgnoreCase("GET"))
		{
	    response = input.when().get(resAPI.getResource())
	    		.then().spec(res).extract().response();
		}
		else if(reqMethod.equalsIgnoreCase("DEL"))
		{
	    response = input.when().delete(resAPI.getResource())
	    		.then().spec(res).extract().response();
		}
	}
	
	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals(response.getStatusCode(), 200);
	}
	@Then("{string} in response code is {string}")
	public void in_response_code_is(String key, String value) {
	    // Write code here that turns the phrase above into concrete actions
	   assertEquals(getJsonPath(response, key), value);
	}
	
	@Then("verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expName, String resource) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		place_id= getJsonPath(response, "place_id");
		input = given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_http_request(resource,"GET");
		String actualName = getJsonPath(response, "name");
		assertEquals(actualName, expName);
	    
	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	    input = given().spec(requestSpecification()).body(data.deletePayload(place_id));
	}
}
