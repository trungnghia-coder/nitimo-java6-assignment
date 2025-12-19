package poly.edu.java6.feature.auth.dto.guestManage.fetchCutomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FetchCustomerDTO {
    String id;
    String fullName;
    String phoneNumber;
    String email;
    String address;
    String status;
    Integer totalOrder;
}
