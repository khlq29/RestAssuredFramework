package api.test;

import java.io.InputStream;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;



import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.mapper.ObjectMapperType;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;


import api.payload.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class UserTest3 {

	User userPayload;

	@BeforeClass
	public void setup() {
		userPayload = new User();

		userPayload.setId(1234);
		userPayload.setUsername("John_Cena123");
		userPayload.setFirstName("John");
		userPayload.setLastName("Cena");
		userPayload.setEmail("john_cena098@gmail.com");
		userPayload.setPassword("john1234cena");
		userPayload.setPhone("+91 1234567890");

		

	}

	@Test(priority = 0)
	public void testPostUser() {

		Response response = UserEndPoints.createUser(userPayload);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);

	}

	@Test(priority = 2)
	public void testGetUserByNameSchemaValidation() {

		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
		InputStream userJsonSchema = getClass().getClassLoader().getResourceAsStream("response-schema.json");
		response.then().statusCode(200).and().assertThat().body(JsonSchemaValidator.matchesJsonSchema(userJsonSchema));

	}

	@Test(priority = 1)
	public void testGetUserByNameHamCrest() {
		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
		response.then().statusCode(200).body("id", equalTo(1234));
		response.then().log().all();
		response.then().body("username", equalTo("John_Cena123")).and().body("lastName", containsString("Cena"));

	}

	@Test
	public void testGetUserById() {

		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		// Deserialize the JSON response into a User object
		Username user = response.as(Username.class, ObjectMapperType.JACKSON_2);
		System.out.println(user.toString());
		// Now, 'user' contains the deserialized JSON response
//      System.out.println("User ID: " + username.getId());
//       System.out.println("Username: " + user.getUsername());
		// Username username = new Username();

//		EmployeeDetails emp = new EmployeeDetails(1, "Chaitra", 1234, "Banglore", "chaitra@gmail.com");
//		
//		ObjectMapper obj = new ObjectMapper();
//		obj.writeValue(new File("./Sample.json"), emp);
	}

	@Test(priority = 1)
	public void testGetUserByNameJsonCompareValidation() throws IOException {
		Response response = UserEndPoints.readUser(this.userPayload.getUsername());

		String expectedJson = new String(Files.readAllBytes(Paths.get("src/test/resources/ExpectedResponse.json")));
		
        //not fixed
		response.then().statusCode(200).body(equalTo(expectedJson));
		//assertEquals(expectingjson, equalTo(actuallingJson));

	}

}
