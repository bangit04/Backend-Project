package vn.bangit.Backend_Service_Project.controller.request;

import lombok.Getter;
import vn.bangit.Backend_Service_Project.common.Gender;
import vn.bangit.Backend_Service_Project.model.AddressEntity;

import java.util.Date;
import java.util.List;

@Getter
public class UserUpdateRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date birthday;
    private String username;
    private String email;
    private String phone;
    private List<AddressRequest> addresses;

}
