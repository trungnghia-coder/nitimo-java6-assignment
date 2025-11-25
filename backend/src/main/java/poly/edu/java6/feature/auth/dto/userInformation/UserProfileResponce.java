package poly.edu.java6.feature.auth.dto.userInformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponce {
    private String fullName;
    private String email;
    private String phone;
    private String address;
}
