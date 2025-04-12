package com.example.demo;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.demo.model.Users;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
// import static io.restassured.RestAssured.when;

public class UserControllerTest {

    private Long createdUserId;
    
    @BeforeClass
    public void setup(){
        RestAssured.baseURI="http://localhost:8080/api/users";
    }

    @Test(priority = 1)
    public void testAddUser(){
        Users user=new Users();
        user.setUsername("riyanka");
        user.setPassword("Riyanka@23");
        user.setPhno("7596890445");
        user.setEmail("riyanka@gmail.com");

        Response response=given().contentType(ContentType.JSON)
                                .body(user)
                                .when()
                                .post("/add")
                                .then()
                                .statusCode(201)
                                .extract().response();
        
        createdUserId = response.getBody().jsonPath().getLong("id"); // Capture ID of the created user
        Assert.assertNotNull(createdUserId, "User ID should not be null");
        Assert.assertEquals(response.jsonPath().getString("username"), "riyanka", "Username should match");
    }

    //Adding user with missing fields
    @Test(priority = 2)
    public void testAddUserWithMissingFields(){
        Users user=new Users();
        user.setUsername("megha"); //missing password, phno and email
        Response response=given().contentType(ContentType.JSON)
                            .body(user)
                            .when()
                            .post("/add")
                            .then()
                            .statusCode(400) //Bad request
                            .extract().response();

        Assert.assertTrue(response.getBody().asString().contains("Validation failed"), "Error message should indicate failure");
    }

    @Test(priority = 3, dependsOnMethods = "testAddUser")
    public void testGetAllUsers(){
    Response response = given()
        .when()
        .get() 
        .then()
        .statusCode(200) 
        .extract().response();

    Assert.assertTrue(response.getBody().jsonPath().getList("$").size() > 0, "User list is empty!");
    response.getBody().jsonPath().getList("$").forEach(user -> {
        Assert.assertNotNull(user, "Each user in the list should not be null");
    });
}
    
    @Test(priority = 4, dependsOnMethods = "testAddUser")
    public void testGetUserById(){
        Response response=given()
        .when()
        .get("/"+ createdUserId)
        .then()
        .statusCode(200)
        .extract().response();
        
        Assert.assertNotNull(response.getBody().jsonPath().getString("username"), "Username should not be null");
        Assert.assertEquals(response.jsonPath().getString("id"), String.valueOf(createdUserId), "User ID should match the requested ID");
    }
    
    //Fetch a non-existent user
    @Test(priority = 5)
    public void testGetUserById_NotFound(){
        long nonExistentId = 9999; // Assuming this ID doesn't exist
        Response response = given()
        .when()
        .get("/" + nonExistentId)
        .then()
        .statusCode(404) // Not found
        .extract().response(); 
        
        // Verify response content and error message
        Assert.assertTrue(response.getBody().asString().contains("User with ID " + nonExistentId + " not found"), 
        "Error message should indicate user not found");
    }
    
    @Test(priority = 6, dependsOnMethods = "testAddUser")
    public void testUpdateUser(){
        Users updateUser=new Users();
        updateUser.setUsername("riyankakundu");
        updateUser.setPassword("Riyanka@239");
        updateUser.setPhno("6289619409");
        updateUser.setEmail("riyankakundu@gmail.com");
        
        Response response=given().contentType(ContentType.JSON)
                        .body(updateUser)
                        .when()
                        .put("/" +createdUserId)
                        .then()
                        .statusCode(200)
                        .extract().response();
                        
        Assert.assertEquals(response.jsonPath().getString("username"), "riyankakundu", "Username should be updated");
        }

    @Test(priority = 7)
    public void testUpdateUser_NotFound(){
    long nonExistentId = 9999; // Assuming this user ID doesn't exist
    
    Users updateUser = new Users();
    updateUser.setUsername("nonexistentuser");
    updateUser.setPassword("Nonexistent@123");
    updateUser.setPhno("0000000000");
    updateUser.setEmail("nonexistentuser@gmail.com");
    
    Response response = given().contentType(ContentType.JSON)
                                .body(updateUser)
                                .when()
                                .put("/" + nonExistentId)
                                .then()
                                .statusCode(404) // Not found
                                .extract().response();
    
    // Verify response content and error message
    Assert.assertTrue(response.getBody().asString().contains("User with ID " + nonExistentId + " not found"), 
    "Error message should indicate user not found");
}


@Test(priority = 8, dependsOnMethods = "testAddUser")
public void testDeleteUser(){

    Response response=given()
                        .when()
                        .delete("/"+ createdUserId)
                        .then()
                        .statusCode(200)
                        .extract().response();
                        
    Assert.assertEquals(response.getBody().asString(), "User deleted successfully!", "Delete response should match");
    }
                    
    @Test(priority = 9)
    public void testDeleteUser_NotFound(){
    long nonExistentId = 9999; // Assuming this user ID doesn't exist
    
    Response response = given()
    .when()
    .delete("/" + nonExistentId)
    .then()
    .statusCode(404) // Not found
    .extract().response();
    
    // Verify response content and error message
    Assert.assertTrue(response.getBody().asString().contains("User with ID " + nonExistentId + " not found"), 
    "Error message should indicate user not found");
}

}
