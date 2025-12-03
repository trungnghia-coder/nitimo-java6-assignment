package poly.edu.java6.feature.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import poly.edu.java6.feature.auth.repository.AuthRepository;
import poly.edu.java6.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthRepository authRepository;

    @Autowired
    public CustomUserDetailsService(AuthRepository userRepository) {
        this.authRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user = authRepository.findByUsername(identifier)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with identifier: " + identifier));
        return new CustomUserDetails(user);
    }
}
