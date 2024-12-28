package vn.bangit.Backend_Service_Project.service;

import vn.bangit.Backend_Service_Project.controller.request.UserCreationRequest;
import vn.bangit.Backend_Service_Project.controller.request.UserPasswordRequest;
import vn.bangit.Backend_Service_Project.controller.request.UserUpdateRequest;
import vn.bangit.Backend_Service_Project.controller.response.UserPageResponse;
import vn.bangit.Backend_Service_Project.controller.response.UserResponse;

import java.util.List;

public interface UserService {

    UserPageResponse findAll(String keyword, String sort, int page, int size);

    UserResponse findById(Long id);

    UserResponse findByEmail(String username);

    long save(UserCreationRequest req);

    void update(UserUpdateRequest req);

    void changePassword(UserPasswordRequest req);

    void delete(Long id);
}
