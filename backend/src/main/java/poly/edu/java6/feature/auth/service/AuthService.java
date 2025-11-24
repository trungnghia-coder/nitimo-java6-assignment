package poly.edu.java6.feature.auth.service;

import poly.edu.java6.feature.auth.dto.logup.LogupRequest;
import poly.edu.java6.feature.auth.dto.passwordChange.PasswordChangeRequest;

public interface AuthService {
    String signup(LogupRequest logupRequest);
    void changePasswordByEmail(String email, PasswordChangeRequest password);
    void changePasswordByPhone(String username, PasswordChangeRequest password);
}
