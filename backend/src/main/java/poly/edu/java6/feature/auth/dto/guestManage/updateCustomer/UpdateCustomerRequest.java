package poly.edu.java6.feature.auth.dto.guestManage.updateCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerRequest {
    String fullName;
    String phone;
    String email;
    String address;
}
