package poly.edu.java6.feature.auth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.edu.java6.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameOrEmailOrPhone(String username, String email, String phone);
    Optional<User> findByEmailOrPhone(String email, String phone);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    Page<User> findAllByRole(User.Role role, Pageable pageable);
    Page<User> findAll(Pageable pageable);
}
