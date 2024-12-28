package vn.bangit.Backend_Service_Project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bangit.Backend_Service_Project.common.Gender;
import vn.bangit.Backend_Service_Project.controller.request.UserCreationRequest;
import vn.bangit.Backend_Service_Project.controller.request.UserPasswordRequest;
import vn.bangit.Backend_Service_Project.controller.request.UserUpdateRequest;
import vn.bangit.Backend_Service_Project.controller.response.UserResponse;
import vn.bangit.Backend_Service_Project.service.UserService;
import vn.bangit.Backend_Service_Project.service.impl.UserServiceImpl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Tag(name = "USER-CONTROLLER")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get user list", description = "API retries user from db")
    @GetMapping("/list")
    public Map<String, Object> getList(@RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) String sort,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "20") int size){
        log.info("Get user list");

        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message","user list");
        result.put("data",userService.findAll(keyword,sort,page,size));

        return result;
    }

    @Operation(summary = "Get user detail", description = "API retries user detail by ID")
    @GetMapping("/{userId}")
    public Map<String,Object> getList(@PathVariable @Min(1) Long userId){
        log.info("Get user detail by ID: {}", userId);

        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message","user list");
        result.put("data",userService.findById(userId));

        return result;
    }

    @Operation(summary = "Create User", description = "API add new user to DB")
    @PostMapping("/add")
    public ResponseEntity<Object> createUser(@RequestBody UserCreationRequest request){
        log.info("Create User: {}", request);

        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message","user created successfully");
        result.put("data",userService.save(request));

        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @Operation(summary = "Update User", description = "API update user to DB")
    @PutMapping("/upd")
    public Map<String,Object> updateUser(@RequestBody UserUpdateRequest request){
        log.info("Updating user: {}",request);

        Map<String,Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.ACCEPTED.value());
        result.put("message","user updated successfully");
        result.put("data","");

        return result;
    }

    @Operation(summary = "Change Password", description = "API change password for user to database")
    @PatchMapping("/change-pwd")
    public Map<String,Object> changePassword(@RequestBody UserPasswordRequest request){
        log.info("Changing password for user: {}", request);

        userService.changePassword(request);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.NO_CONTENT.value());
        result.put("message", "Password updated successfully");
        result.put("data", "");

        return result;
    }

    @Operation(summary = "Delete User", description = "API delete user to database")
    @DeleteMapping("/del/{userId}")
    public Map<String,Object> deleteUser(@PathVariable Long userId){
        log.info("Deleting user: {}", userId);

        userService.delete(userId);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.RESET_CONTENT.value());
        result.put("message", "User deleted successfully");
        result.put("data", "");

        return result;
    }
}
