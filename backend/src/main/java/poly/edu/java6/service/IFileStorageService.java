package poly.edu.java6.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {
    String uploadFile(MultipartFile file);
    boolean deleteFile(String fileUrl);
}
