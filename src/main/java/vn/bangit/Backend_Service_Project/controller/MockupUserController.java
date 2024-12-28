package vn.bangit.Backend_Service_Project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.bangit.Backend_Service_Project.common.Gender;
import vn.bangit.Backend_Service_Project.controller.request.UserCreationRequest;
import vn.bangit.Backend_Service_Project.controller.request.UserPasswordRequest;
import vn.bangit.Backend_Service_Project.controller.request.UserUpdateRequest;
import vn.bangit.Backend_Service_Project.controller.response.UserResponse;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mockup")
@Tag(name = "USER-MOCKUP-CONTROLLER")
public class MockupUserController {

    @Operation(summary = "Get user list", description = "API retries user from db")
    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(required = false) String keyword,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "20") int size){
        UserResponse userResponse1 = new UserResponse();
        userResponse1.setId(1l);
        userResponse1.setUsername("admin");
        userResponse1.setFirstName("bang");
        userResponse1.setLastName("it");
        userResponse1.setEmail("candyyeukoi10@gmail.com");
        userResponse1.setPhone("0974684656");
        userResponse1.setGender(Gender.MALE);
        userResponse1.setBirthday(new Date());
        UserResponse userResponse2 = new UserResponse();
        userResponse2.setId(2l);
        userResponse2.setUsername("user");
        userResponse2.setFirstName("ronaldo");
        userResponse2.setLastName("it");
        userResponse2.setEmail("abc@gmail.com");
        userResponse2.setPhone("0987654321");
        userResponse2.setGender(Gender.MALE);
        userResponse2.setBirthday(new Date());

        List<UserResponse> userList = List.of(userResponse1,userResponse2);

        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message","user list");
        result.put("data",userList);

        return result;
    }

    @Operation(summary = "Get user detail", description = "API retries user detail by ID")
    @GetMapping("/{userId}")
    public Map<String,Object> getList(@PathVariable Long userId){
        UserResponse userResponse1 = new UserResponse();
        userResponse1.setId(1l);
        userResponse1.setUsername("admin");
        userResponse1.setFirstName("bang");
        userResponse1.setLastName("it");
        userResponse1.setEmail("candyyeukoi10@gmail.com");
        userResponse1.setPhone("0974684656");
        userResponse1.setGender(Gender.MALE);
        userResponse1.setBirthday(new Date());

        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message","user list");
        result.put("data",userResponse1);

        return result;
    }

    @Operation(summary = "Create User", description = "API add user to DB")
    @PostMapping("/add")
    public Map<String,Object> createUser(UserCreationRequest request){
        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message","user created successfully");
        result.put("data",3);

        return result;
    }

    @Operation(summary = "Update User", description = "API add user to DB")
    @PutMapping("/upd")
    public Map<String,Object> updateUser(UserUpdateRequest request){
        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.ACCEPTED.value());
        result.put("message","user updated successfully");
        result.put("data","");

        return result;
    }

    @Operation(summary = "Change Password", description = "API change password for user to database")
    @PatchMapping("/change-pwd")
    public Map<String,Object> changeUser(UserPasswordRequest request){
        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.NO_CONTENT.value());
        result.put("message","user updated successfully");
        result.put("data","");

        return result;
    }

    @Operation(summary = "Delete User", description = "API delete user to database")
    @PatchMapping("/del/{userId}")
    public Map<String,Object> deleteUser(@PathVariable Long userId){
        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.RESET_CONTENT.value());
        result.put("message","user deleted successfully");
        result.put("data","");

        return result;
    }
}
