package poly.edu.java6.feature.auth.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.java6.feature.auth.dto.logup.LogupRequest;
import poly.edu.java6.feature.auth.dto.passwordChange.PasswordChangeRequest;
import poly.edu.java6.feature.auth.dto.updateUserProfile.UpdateUserProfileRequest;
import poly.edu.java6.feature.auth.dto.userInformation.UserProfileResponce;
import poly.edu.java6.feature.auth.repository.AuthRepository;
import poly.edu.java6.feature.auth.service.AuthService;
import poly.edu.java6.model.User;
import poly.edu.java6.utils.IdUtils;

import java.time.LocalDateTime;

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
}
