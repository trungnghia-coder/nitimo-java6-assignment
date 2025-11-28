package poly.edu.java6.feature.auth.dto.updateUserProfile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserProfileResponce {
    private boolean success;
    private String message;
}
