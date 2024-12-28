package vn.bangit.Backend_Service_Project.controller.request;

import lombok.Getter;
import vn.bangit.Backend_Service_Project.common.Gender;
import vn.bangit.Backend_Service_Project.common.UserType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
public class UserCreationRequest implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date birthday;
    private String username;
    private String email;
    private String phone;
    private UserType type;
    private List<AddressRequest> address;
}
