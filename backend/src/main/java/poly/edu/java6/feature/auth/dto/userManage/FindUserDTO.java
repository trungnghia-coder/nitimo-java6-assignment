package poly.edu.java6.feature.auth.dto.userManage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindUserDTO {
    String userId;
    String fullName;
    String email;
    String phone;
    String role;
    String status;
}
