package poly.edu.java6.feature.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import poly.edu.java6.feature.auth.dto.login.LoginResponse;
import poly.edu.java6.feature.auth.dto.login.LoginRequest;
import poly.edu.java6.feature.auth.dto.logup.LogupRequest;
import poly.edu.java6.feature.auth.dto.logup.LogupResponse;
import poly.edu.java6.feature.auth.repository.AuthRepository;
import poly.edu.java6.feature.auth.service.JwtService;
import poly.edu.java6.feature.auth.service.AuthService;
import poly.edu.java6.model.User;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtTokenProvider;

    @Autowired
    private AuthRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    HttpServletResponse response;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {

        User user = userRepo
                .findByUsernameOrEmailOrPhone(loginRequest.getUsername(), loginRequest.getUsername(),
                        loginRequest.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        boolean rememberMe = loginRequest.isRememberMe();

        String token = jwtTokenProvider.generateToken(user, rememberMe);
        int maxAgeSeconds = rememberMe ? (7 * 24 * 60 * 60) : (1 * 24 * 60 * 60);
        Cookie jwtCookie = new Cookie("jwt-token", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(maxAgeSeconds);
        jwtCookie.setPath("/");
        jwtCookie.setAttribute("SameSite", "Lax");
        jwtCookie.setSecure(false);
        response.addCookie(jwtCookie);
        return ResponseEntity.ok(new LoginResponse(true, "Login successful", null));
    }

    @PostMapping("/logup")
    public ResponseEntity<?> signup(@RequestBody LogupRequest req) {
        String message = authService.signup(req);
        boolean success = message.equals("Đăng ký thành công!");
        return ResponseEntity.ok(new LogupResponse(success, message));
    }

    @GetMapping("/check-auth-status")
    public ResponseEntity<?> checkAuthStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = auth != null && auth.isAuthenticated()
                && !auth.getPrincipal().equals("anonymousUser");
        return ResponseEntity.ok().body(Map.of("isAuthenticated", isAuthenticated));
    }

    @GetMapping("/user/profile")
    public ResponseEntity<?> getUserProfile() {
        return ResponseEntity.ok().body("userProfileData");
    }
}
