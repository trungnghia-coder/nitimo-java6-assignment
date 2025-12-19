package poly.edu.java6.feature.auth.dto.guestManage.findCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindCustomerDTO {
    String fullName;
    String email;
    String phone;
    String address;
    LocalDateTime joined;
}
