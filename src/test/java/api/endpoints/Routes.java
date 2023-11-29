package api.endpoints;

/*
 Swagger URL --> https://petstore.swagger.io
 
 Create user (Post): https://petstore.swagger.io/v2/user
 Get user (Get): https://petstore.swagger.io/v2/user/{uesrname}
 Update user (Put): https://petstore.swagger.io/v2/user/{uesrname}
 Delete user (Delete): https://petstore.swagger.io/v2/user/{uesrname}
 
 */

public class Routes {
	
	public static String base_url = "https://petstore.swagger.io/v2";
	
	//USER Module
	
	public static String post_url = base_url + "/user";
	public static String get_url = base_url + "/user/{username}";
	public static String update_url = base_url + "/user/{username}";
	public static String delete_url = base_url + "/user/{username}";
	
	//STORE Module
	
	     //for store module URLs
	
	//PET Module 
	
	     // for pet module URLs
	
	
}
