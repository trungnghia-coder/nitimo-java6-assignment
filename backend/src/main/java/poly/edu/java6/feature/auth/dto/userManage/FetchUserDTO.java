package poly.edu.java6.feature.auth.dto.userManage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FetchUserDTO {
    String id;
    String name;
    String phone;
    String email;
    String role;
    String status;
}
