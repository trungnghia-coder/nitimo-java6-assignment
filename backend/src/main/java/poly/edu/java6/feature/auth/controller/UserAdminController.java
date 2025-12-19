package poly.edu.java6.feature.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poly.edu.java6.feature.auth.dto.MessageResponse;
import poly.edu.java6.feature.auth.dto.login.LoginResponse;
import poly.edu.java6.feature.auth.dto.userManage.CreateUserRequest;
import poly.edu.java6.feature.auth.dto.userManage.FetchUserDTO;
import poly.edu.java6.feature.auth.dto.userManage.FindUserDTO;
import poly.edu.java6.feature.auth.dto.userManage.UpdateUserRequest;
import poly.edu.java6.feature.auth.repository.AuthRepository;
import poly.edu.java6.feature.auth.service.AuthService;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/user")
public class UserAdminController {
    @Autowired
    private AuthService authService;

    @Autowired
    private AuthRepository authRepository;

    @GetMapping("/")
    public Page<FetchUserDTO> getAllUser(Pageable pageable) {
        Page<FetchUserDTO> stasList = authService.findAllUser(pageable);
        return stasList;
    }

    @GetMapping("/{id}")
    public Optional<FindUserDTO> getUser(@PathVariable String id) {
        return authService.findUserById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createUser(@RequestBody CreateUserRequest req) {
        String email = req.getEmail();
        if (authRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.ok(new MessageResponse("Email existed!",false));
        }

        if (!req.getPassword().equals(req.getConfirmPassword())) {
            return ResponseEntity.ok(new MessageResponse("Confirm password unsuccess!",false));
        }
        authService.createUser(req);
        return ResponseEntity.ok(new MessageResponse("User created!",true));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponse> updateUser(@PathVariable String id, @RequestBody UpdateUserRequest request) {
        authService.updateUser(id, request);
        return  ResponseEntity.ok(new MessageResponse("Update successful", true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCustomer(@PathVariable String id) {
        authService.deleteCustomer(id);
        return  ResponseEntity.ok(new MessageResponse("Delete successful", true));
    }
}
