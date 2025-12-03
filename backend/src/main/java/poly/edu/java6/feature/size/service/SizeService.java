package poly.edu.java6.feature.size.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.java6.feature.size.repository.SizeRepository;
import poly.edu.java6.model.Size;

@Service
public class SizeService {
    @Autowired
    private SizeRepository sizeRepository;

    public Size findByName(String sizeName) {
        return sizeRepository.findByName(sizeName)
                .orElseThrow(() -> new EntityNotFoundException("Size không hợp lệ: " + sizeName));
    }
}
