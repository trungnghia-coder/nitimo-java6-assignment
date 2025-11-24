package poly.edu.java6.feature.auth.dto.passwordChange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeResponce {
    private boolean success;
    private String message;
}
