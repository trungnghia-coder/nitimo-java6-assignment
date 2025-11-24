package poly.edu.java6.feature.auth.dto.passwordChange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeRequest {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
