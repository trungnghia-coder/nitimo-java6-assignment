package poly.edu.java6.feature.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.java6.model.Size;

import java.util.List;

public interface SizeRepository extends JpaRepository<Size, Integer> {
}
