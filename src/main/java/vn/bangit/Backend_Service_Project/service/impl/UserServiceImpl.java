package vn.bangit.Backend_Service_Project.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import vn.bangit.Backend_Service_Project.common.UserStatus;
import vn.bangit.Backend_Service_Project.controller.UserController;
import vn.bangit.Backend_Service_Project.controller.request.UserCreationRequest;
import vn.bangit.Backend_Service_Project.controller.request.UserPasswordRequest;
import vn.bangit.Backend_Service_Project.controller.request.UserUpdateRequest;
import vn.bangit.Backend_Service_Project.controller.response.UserPageResponse;
import vn.bangit.Backend_Service_Project.controller.response.UserResponse;
import vn.bangit.Backend_Service_Project.exception.ResourceNotFoundException;
import vn.bangit.Backend_Service_Project.model.AddressEntity;
import vn.bangit.Backend_Service_Project.model.UserEntity;
import vn.bangit.Backend_Service_Project.repository.AddressRepository;
import vn.bangit.Backend_Service_Project.repository.UserRepository;
import vn.bangit.Backend_Service_Project.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserPageResponse findAll(String keyword, String sort, int page, int size) {
        log.info("findAll start");

        Sort.Order order = new Sort.Order(Sort.Direction.ASC,"id");
        if(StringUtils.hasLength(sort)){
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sort);
            if(matcher.find()){
                String columnName = matcher.group(1);
                if(matcher.group(3).equalsIgnoreCase("asc")){
                    order = new Sort.Order(Sort.Direction.ASC,columnName);
                } else order = new Sort.Order(Sort.Direction.DESC,columnName);
            }
        }
        int pageNo = 0;
        if(pageNo > 0){
            pageNo = page - 1;
        }
        Pageable pageable = PageRequest.of(pageNo,size,Sort.by(order));

        Page<UserEntity> entityPage;
        if(StringUtils.hasLength(keyword)){
            keyword = "%" + keyword.toLowerCase() + "%";
            entityPage = userRepository.searchByKeyword(keyword,pageable);
        } else {
            entityPage = userRepository.findAll(pageable);
        }
        return getUserPageResponse(page,size,entityPage);
    }

    @Override
    public UserResponse findById(Long id) {
        log.info("Find user by id: {}",id);

        UserEntity userEntity = getUserEntity(id);

        return UserResponse.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .gender(userEntity.getGender())
                .birthday(userEntity.getBirthday())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .build();
    }

    @Override
    public UserResponse findByEmail(String username) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long save(UserCreationRequest req) {
        log.info("Saving user: {}", req);

        UserEntity user = new UserEntity();
        user.setId(req.getId());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setGender(req.getGender());
        user.setBirthday(req.getBirthday());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setUsername(req.getUsername());
        user.setType(req.getType());
        user.setStatus(UserStatus.NONE);
        userRepository.save(user);

        if(user.getId() != null){
            List<AddressEntity> addresses = new ArrayList<>();
            req.getAddress().forEach(
                    address -> {
                        AddressEntity addressEntity = new AddressEntity();
                        addressEntity.setApartmentNumber(address.getApartmentNumber());
                        addressEntity.setFloor(address.getFloor());
                        addressEntity.setBuilding(address.getBuilding());
                        addressEntity.setStreetNumber(address.getStreetNumber());
                        addressEntity.setStreet(address.getStreet());
                        addressEntity.setCity(address.getCity());
                        addressEntity.setCountry(address.getCountry());
                        addressEntity.setAddressType(address.getAddressType());
                        addressEntity.setUserId(user.getId());
                        addresses.add(addressEntity);
                    }
            );
            addressRepository.saveAll(addresses);
            log.info("Save address: {}",addresses);
        }
        return 1;
    }

    @Override
    public void update(UserUpdateRequest req) {
        log.info("Updating user: {}", req);

        UserEntity user = getUserEntity(req.getId());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setGender(req.getGender());
        user.setBirthday(req.getBirthday());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setUsername(req.getUsername());

        userRepository.save(user);
        log.info("Updated user: {}", user);
        List<AddressEntity> addresses = new ArrayList<>();

        req.getAddresses().forEach(
                address -> {
                    AddressEntity addressEntity = addressRepository.findByUserIdAndAddressType(user.getId(),address.getAddressType());
                    if (addressEntity == null) {
                        addressEntity = new AddressEntity();
                    }
                    addressEntity.setApartmentNumber(address.getApartmentNumber());
                    addressEntity.setFloor(address.getFloor());
                    addressEntity.setBuilding(address.getBuilding());
                    addressEntity.setStreetNumber(address.getStreetNumber());
                    addressEntity.setStreet(address.getStreet());
                    addressEntity.setCity(address.getCity());
                    addressEntity.setCountry(address.getCountry());
                    addressEntity.setAddressType(address.getAddressType());
                    addressEntity.setUserId(user.getId());

                    addresses.add(addressEntity);
                }
        );

        addressRepository.saveAll(addresses);
        log.info("Updated addresses: {}", addresses);
    }

    @Override
    public void changePassword(UserPasswordRequest req) {
        log.info("Changing password for user: {}", req);

        // Get user by id
        UserEntity user = getUserEntity(req.getId());
        if (req.getPassword().equals(req.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }

        userRepository.save(user);
        log.info("Changed password for user: {}", user);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user: {}", id);

        // Get user by id
        UserEntity user = getUserEntity(id);
        user.setStatus(UserStatus.INACTIVE);

        userRepository.save(user);
        log.info("Deleted user id: {}", id);
    }

    private UserEntity getUserEntity(Long id){
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private static UserPageResponse getUserPageResponse(int page, int size, Page<UserEntity> userEntities){
        log.info("Convert User Entity Page");

        List<UserResponse> userList = userEntities.stream().map(entity -> UserResponse.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .gender(entity.getGender())
                .birthday(entity.getBirthday())
                .username(entity.getUsername())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .build()).toList();
        UserPageResponse response = new UserPageResponse();
        response.setPageNumber(page);
        response.setPageSize(size);
        response.setTotalElements(userEntities.getTotalElements());
        response.setTotalPages(userEntities.getTotalPages());
        response.setUsers(userList);

        return response;
    }
}
