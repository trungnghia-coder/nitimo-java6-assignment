package poly.edu.java6.feature.auth.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import poly.edu.java6.feature.auth.repository.AuthRepository;
import poly.edu.java6.feature.auth.service.AuthService;
import poly.edu.java6.model.User;
import poly.edu.java6.utils.IdUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String signup(LogupRequest req) {
        String email = req.getEmail();
        if (authRepository.findByEmail(email).isPresent()) {
            return "Email exsited!";
        }

        if (!req.getPassword().equals(req.getConfirmPassword())) {
            return "Confirm password unsuccess!";
        }

        User user = new User();
        String newId = IdUtils.generateNewId();
        user.setUsername(newId);
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        authRepository.save(user);
        return "Đăng ký thành công!";
    }

    @Override
    public void changePasswordByEmailOrPhone(String email, String phone, PasswordChangeRequest password) {
        User user = authRepository.findByEmailOrPhone(email, phone)
                .orElseThrow(() -> new RuntimeException("User not found: " + email + "or" + phone));

        processPasswordChange(user, password);
    }

    private void processPasswordChange(User user, PasswordChangeRequest password) {
        if (!passwordEncoder.matches(password.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Mật khẩu cũ không chính xác");
        }

        if (password.getNewPassword() == null || password.getNewPassword().isBlank()) {
            throw new RuntimeException("Mật khẩu mới không được để trống");
        }

        if (!password.getNewPassword().equals(password.getConfirmPassword())) {
            throw new RuntimeException("Mật khẩu mới và mật khẩu xác nhận không khớp");
        }
        String newHashedPassword = passwordEncoder.encode(password.getNewPassword());
        user.setPassword(newHashedPassword);
        user.setUpdatedAt(LocalDateTime.now());

        authRepository.save(user);
    }

    private UserProfileResponce mapToDTO(User user) {
        return new UserProfileResponce(
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress()
        );
    }

    @Override
    public UserProfileResponce getUserProfileByEmailOrPhone(String email, String phone) {
        User user = authRepository.findByEmailOrPhone(email, phone)
                .orElseThrow(() -> new RuntimeException("User not found: " + email + "or" + phone));
        return mapToDTO(user);
    }

    private User updateUserEntity(User user, UpdateUserProfileRequest userRequest) {
        user.setFullName(userRequest.getFullName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        user.setAddress(userRequest.getAddress());
        user.setUpdatedAt(LocalDateTime.now());
        return authRepository.save(user);
    }


    private static UpdateUserProfileRequest mapToDTORequest(User user) {
        return new UpdateUserProfileRequest(
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress()
        );
    }

    @Override
    @Transactional
    public UpdateUserProfileRequest updateUserProfile(String username, UpdateUserProfileRequest userRequest) {
        User user = authRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        User updatedUser = updateUserEntity(user, userRequest);
        return mapToDTORequest(updatedUser);
    }

    @Override
    public User findUserByUsername(String username) {
        return authRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng: " + username));
    }

    @Override
    public Page<FetchCustomerDTO> findAll(Pageable pageable) {
        Page<User> users = authRepository.findAllByRole(User.Role.USER, pageable);
        return users.map(user -> new FetchCustomerDTO(
                user.getUsername(),
                user.getFullName(),
                user.getPhone(),
                user.getEmail(),
                user.getAddress(),
                user.getStatus().toString(),
                user.getOrders().size()));
    }

    @Override
    public Optional<FindCustomerDTO> findByUsername(String username) {
        Optional<User> user = authRepository.findByUsername(username);
        return user.map(u -> new FindCustomerDTO(
                u.getUsername(),
                u.getFullName(),
                u.getEmail(),
                u.getPhone(),
                u.getAddress(),
                u.getCreatedAt()
        ));
    }

    @Override
    public User updateCustomerProfile(String username,UpdateCustomerRequest request) {
        User user = authRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        return authRepository.save(user);
    }

    @Override
    public User deleteCustomer(String username) {
        User user = authRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        user.setStatus(User.Status.INACTIVE);
        return authRepository.save(user);
    }

    @Override
    public Page<FetchUserDTO> findAllUser(Pageable pageable) {
        Page<User> users = authRepository.findAll(pageable);
        return users.map(user -> new FetchUserDTO(
                user.getUsername(),
                user.getFullName(),
                user.getPhone(),
                user.getEmail(),
                user.getRole().toString(),
                user.getStatus().toString()));
    }

    @Override
    public Optional<FindUserDTO> findUserById(String id) {
        Optional<User> user = authRepository.findByUsername(id);
        return user.map(u -> new FindUserDTO(
                u.getUsername(),
                u.getFullName(),
                u.getEmail(),
                u.getPhone(),
                u.getRole().toString(),
                u.getStatus().toString()
        ));
    }

    @Override
    public User createUser(CreateUserRequest req) {
        User user = new User();
        String newId = IdUtils.generateNewId();
        user.setUsername(newId);
        user.setFullName(req.getFullName());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setPhone(req.getPhone());
        user.setEmail(req.getEmail());
        user.setStatus(User.Status.ACTIVE);
        user.setRole(User.Role.ADMIN);
        return authRepository.save(user);
    }

    @Override
    public User updateUser(String username, UpdateUserRequest request) {
        User user = authRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        return authRepository.save(user);
    }
}
