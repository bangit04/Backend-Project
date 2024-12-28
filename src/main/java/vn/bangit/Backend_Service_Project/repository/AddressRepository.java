package vn.bangit.Backend_Service_Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.bangit.Backend_Service_Project.model.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity,Long> {
    AddressEntity findByUserIdAndAddressType(Long userId, Integer addressType);
}
