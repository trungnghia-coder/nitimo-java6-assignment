package poly.edu.java6.feature.auth.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import poly.edu.java6.feature.auth.dto.guestManage.fetchCutomer.FetchCustomerDTO;
import poly.edu.java6.feature.auth.dto.guestManage.findCustomer.FindCustomerDTO;
import poly.edu.java6.feature.auth.dto.guestManage.updateCustomer.UpdateCustomerRequest;
import poly.edu.java6.feature.auth.dto.logup.LogupRequest;
import poly.edu.java6.feature.auth.dto.passwordChange.PasswordChangeRequest;
import poly.edu.java6.feature.auth.dto.userManage.CreateUserRequest;
import poly.edu.java6.feature.auth.dto.userManage.FetchUserDTO;
import poly.edu.java6.feature.auth.dto.updateUserProfile.UpdateUserProfileRequest;
import poly.edu.java6.feature.auth.dto.userInformation.UserProfileResponce;
import poly.edu.java6.feature.auth.dto.userManage.FindUserDTO;
import poly.edu.java6.feature.auth.dto.userManage.UpdateUserRequest;
import poly.edu.java6.model.User;

import java.util.Optional;

public interface AuthService {
    String signup(LogupRequest logupRequest);
    void changePasswordByEmailOrPhone(String email, String phone, PasswordChangeRequest password);
    UserProfileResponce getUserProfileByEmailOrPhone(String email, String phone);
    UpdateUserProfileRequest updateUserProfile(String username, UpdateUserProfileRequest profileDTO);

    User findUserByUsername(String username);

    //Customer
    Page<FetchCustomerDTO> findAll(Pageable pageable);
    Optional<FindCustomerDTO> findByUsername(String username);
    User updateCustomerProfile(String username, UpdateCustomerRequest profileDTO);
    User deleteCustomer(String username);

    //Staff
    Page<FetchUserDTO> findAllUser(Pageable pageable);
    Optional<FindUserDTO> findUserById(String id);
    User createUser(CreateUserRequest createUserRequest);
    User updateUser(String username, UpdateUserRequest updateUserRequest);

}
