package api.test;

import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.UserEndPoints2;
import api.payload.User;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class UserTest2{
	
	Faker faker;
	User userPayload;
	
	public Logger logger; // for logs
	
	@BeforeClass
	public void setup()
	{
	   faker = new Faker();//for fake data
	   userPayload = new User();
	   
	   userPayload.setId(faker.idNumber().hashCode());
	   userPayload.setUsername(faker.name().username());
	   userPayload.setFirstName(faker.name().firstName());
	   userPayload.setLastName(faker.name().lastName());
	   //userPayload.setFirstname("abcqwe");
	   //userPayload.setLastname("mnbvcx");
	   userPayload.setEmail(faker.internet().emailAddress());
	   userPayload.setPassword(faker.internet().password(5, 10));
	   userPayload.setPhone(faker.phoneNumber().cellPhone());
	   
	   
	   //logs
	   logger = LogManager.getLogger(this.getClass());
	   logger.debug("debugging------");
	}
	
	//test case using properties file
	
	@Test(priority =1)
	public void testPostUser() {
	
		logger.info("---------- Creating user-----------");
		Response response = UserEndPoints2.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("---------- User is created-----------");
		
	}
	
	@Test(priority =2)
	public void testGetUserByName() {
		
		InputStream schemaval = getClass ().getClassLoader ()
			    .getResourceAsStream ("Schema.json");
		Response response = UserEndPoints2.readUser(this.userPayload.getUsername());
		response.then().body(JsonSchemaValidator.matchesJsonSchema (schemaval)).and().statusCode(300).log().all();
		//response.getstatusCode(200);
		
		Assert.assertEquals(response.getStatusCode(),200);
		
	}
	
	@Test(priority =3)
	public void testUpdateUserByName() {
		//update data using payload
		logger.info("---------- updating user-----------");
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());
		
		Response response = UserEndPoints2.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().body();
		//chai assertion
		//response.then().log().body().statusCode(200);
		
		//testng assertion
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("---------- user is upadated-----------");
		
		//checking data after update
		Response responseAfterUpdate = UserEndPoints2.readUser(this.userPayload.getUsername());
	
		Assert.assertEquals(responseAfterUpdate.getStatusCode(),200);
	}
	
	@Test(priority =4)
	public void testDeleteUserByName() {
		//update data using payload
		logger.info("---------- deleting user-----------");
		Response response = UserEndPoints2.deleteUser(this.userPayload.getUsername());
		
		//chai assertion
		//response.then().log().body().statusCode(200);
		
		//testng assertion
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("---------- user is deleted-----------");
	}
	
	@Test(priority =5)
	public void testGetUserByNameAfterDeletion() {
		Response response = UserEndPoints2.readUser(this.userPayload.getUsername());
		response.then().log().all();
		//response.getstatusCode(200);
		
		Assert.assertEquals(response.getStatusCode(),404);
	}


}
