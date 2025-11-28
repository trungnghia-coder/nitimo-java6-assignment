package poly.edu.java6.feature.auth.service;

import poly.edu.java6.feature.auth.dto.logup.LogupRequest;
import poly.edu.java6.feature.auth.dto.passwordChange.PasswordChangeRequest;
import poly.edu.java6.feature.auth.dto.updateUserProfile.UpdateUserProfileRequest;
import poly.edu.java6.feature.auth.dto.userInformation.UserProfileResponce;

public interface AuthService {
    String signup(LogupRequest logupRequest);
    void changePasswordByEmailOrPhone(String email, String phone, PasswordChangeRequest password);

    UserProfileResponce getUserProfileByEmailOrPhone(String email, String phone);

    UpdateUserProfileRequest updateUserProfile(String username, UpdateUserProfileRequest profileDTO);
}
