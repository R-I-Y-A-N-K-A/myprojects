package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.AuthRequest;
import com.example.demo.model.Users;
import com.example.demo.service.JwtUtil;
import com.example.demo.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "API for managing users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    // @Operation(summary = "Add a new user", description = "Create a user by providing user details")
    // @ApiResponses(value = {
    //     @ApiResponse(responseCode = "201", description = "User created successfully"),
    //     @ApiResponse(responseCode = "400", description = "Bad Request")
    // })
    // @PostMapping("/add")
    // public ResponseEntity<Users> addUser(@Valid @RequestBody Users user){
    //     return ResponseEntity.status(201).body(userService.addUser(user));
    // }

    @Operation(summary = "Retrieve all users", description = "Fetch a list of all registered users")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all users",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Users.class))),
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    // @PreAuthorize("hasRole('ADMIN')") //only ADMIN can view all users
    public ResponseEntity<List<Users>> findAllUsers(){
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }
    
    @Operation(summary = "Retrieve a user by ID", description = "Fetch user details using their unique ID")
    @Parameter(name = "id", description = "The id of the user", required = true)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved all users",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = Users.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    // @PreAuthorize("hasAnyRole('USER', 'ADMIN')") //USER or ADMIN can fetch their own details
    public ResponseEntity<Users> findUserById(@PathVariable Long id){
        return ResponseEntity.status(200).body(userService.getUserById(id));
    }

    @Operation(summary = "Update a user's information", description = "Update user details by ID")
        @Parameter(name = "id", description = "The unique id of the user", required = true)
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully updated the user details"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')") //Only ADMIN can update users
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @Valid @RequestBody Users updatedUser){
        return ResponseEntity.status(200).body(userService.updateUser(id, updatedUser));
    }

    @Operation(summary = "Delete a user", description = "Remove a user by their unique ID")
    @Parameter(name = "id", description = "The unique id of the user", required = true)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deleted the user"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')") //Only ADMIN can delete users
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        return ResponseEntity.status(200).body(userService.deleteUser(id));
    }

    @PostMapping("/auth/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
