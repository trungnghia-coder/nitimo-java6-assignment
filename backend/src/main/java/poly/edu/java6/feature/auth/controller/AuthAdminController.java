package poly.edu.java6.feature.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poly.edu.java6.feature.auth.dto.MessageResponse;
import poly.edu.java6.feature.auth.dto.guestManage.fetchCutomer.FetchCustomerDTO;
import poly.edu.java6.feature.auth.dto.guestManage.findCustomer.FindCustomerDTO;
import poly.edu.java6.feature.auth.dto.guestManage.updateCustomer.UpdateCustomerRequest;
import poly.edu.java6.feature.auth.service.AuthService;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/auth")
public class AuthAdminController {
    @Autowired
    private AuthService authService;

    @GetMapping("/")
    public Page<FetchCustomerDTO> getAllCustomer(Pageable pageable) {
        Page<FetchCustomerDTO> cusList = authService.findAll(pageable);
        return cusList;
    }

    @GetMapping("/{id}")
    public Optional<FindCustomerDTO> getCustomer(@PathVariable String id) {
        return authService.findByUsername(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponse> updateCustomer(@PathVariable String id, @RequestBody UpdateCustomerRequest request) {
        authService.updateCustomerProfile(id, request);
        return  ResponseEntity.ok(new MessageResponse("Update successful", true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCustomer(@PathVariable String id) {
        authService.deleteCustomer(id);
        return  ResponseEntity.ok(new MessageResponse("Delete successful", true));
    }
}
