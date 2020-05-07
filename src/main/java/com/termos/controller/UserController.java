package com.termos.controller;

import com.termos.dto.ApiResponse;
import com.termos.dto.UserDTO;
import com.termos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.util.stream.Stream;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    ResponseEntity <ApiResponse<Stream<UserDTO>>> users() {
        var response = new ApiResponse<Stream<UserDTO>>();
        response.isSuccess = true;
        response.payload = userService.findAll();
        response.message = "Listed users successfully.";
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{id}")
    ResponseEntity<ApiResponse<UserDTO>> get(@PathVariable String id) {
        var response = new ApiResponse<UserDTO>();
        var user = userService.getById(id);
        if(user == null){
            response.message = "User doesn't exist.";
            response.isSuccess = false;
            return ResponseEntity.badRequest().body(response);
        }
        response.message = "Returned user successfully.";
        response.isSuccess = true;
        response.payload = user;
        return ResponseEntity.ok(response);
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse> create(@RequestBody @Valid UserDTO user){
        var response = new ApiResponse();
        var u = userService.getByEmail(user.email);
        if(u != null){
            response.message = "User does already exist.";
            response.isSuccess = false;
            return ResponseEntity.badRequest().body(response);
        }

        response.isSuccess = true;
        response.message = "User has been created.";
        response.payload = userService.persistNew(user).userId;
        return ResponseEntity.created(URI.create("/users/"+(response.payload))).body(response);
    }

    @PutMapping("/users/{id}")
    ResponseEntity<ApiResponse> update(@RequestBody UserDTO user, @PathVariable String id){
        var response = new ApiResponse();
        var u = userService.getById(id);
        if(u == null){
            response.message = "User doesn't exist.";
            response.isSuccess = false;
            return ResponseEntity.badRequest().body(response);
        }
        u.authorities = user.authorities;
        u.city = user.city;
        u.enabled = user.enabled;
        u.firstName = user.firstName;
        u.matchingPassword = user.matchingPassword;
        u.password = user.password;
        u.regDate = user.regDate;
        u.surName = user.surName;
        response.isSuccess = true;
        response.message = "User successfully updated.";
        response.payload = userService.persistNew(u);
        return ResponseEntity.ok(response);
    }

    //delete
    @DeleteMapping("/users/{id}")
    ResponseEntity<ApiResponse> delete(@PathVariable String id) {
        var response = new ApiResponse();
        var u = userService.getById(id);
        if(u == null){
            response.message = "User doesn't exist.";
            response.isSuccess = false;
            return ResponseEntity.badRequest().body(response);
        }

        response.isSuccess = true;
        response.message = "User successfully deleted.";
        response.payload = userService.remove(id);
        return ResponseEntity.ok(response);
    }
}