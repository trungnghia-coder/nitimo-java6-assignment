package poly.edu.java6.feature.auth.service;

import poly.edu.java6.feature.auth.dto.logup.LogupRequest;
import poly.edu.java6.feature.auth.dto.passwordChange.PasswordChangeRequest;
import poly.edu.java6.feature.auth.dto.userInformation.UserProfileRequest;
import poly.edu.java6.feature.auth.dto.userInformation.UserProfileResponce;

public interface AuthService {
    String signup(LogupRequest logupRequest);
    void changePasswordByEmail(String email, PasswordChangeRequest password);
    void changePasswordByPhone(String username, PasswordChangeRequest password);

    UserProfileResponce getUserProfileByEmail(String email);
    UserProfileResponce getUserProfileByPhone(String phone);

    UserProfileRequest updateUserProfileByEmail(String email, UserProfileRequest profileDTO);
    UserProfileRequest updateUserProfileByUsername(String username, UserProfileRequest profileDTO);
}
