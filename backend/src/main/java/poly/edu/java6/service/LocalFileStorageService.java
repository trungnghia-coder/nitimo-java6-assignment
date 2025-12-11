package poly.edu.java6.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import poly.edu.java6.utils.IdUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class LocalFileStorageService implements IFileStorageService {
    private final Path fileStorageLocation = Paths.get("uploads/images").toAbsolutePath().normalize();

    public LocalFileStorageService() {
        try {
            // Đảm bảo thư mục tồn tại
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public String uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            throw new RuntimeException("File name is empty.");
        }

        String fileExtension = "";
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            fileExtension = fileName.substring(lastDotIndex);
        }

        String uniqueFileName = IdUtils.generateNewId() + fileExtension;

        try {
            Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return "/images/" + uniqueFileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public boolean deleteFile(String fileUrl) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileUrl).normalize();
            File fileToDelete = filePath.toFile();

            if (fileToDelete.exists()) {
                return Files.deleteIfExists(filePath);
            } else {
                return false;
            }
        } catch (Exception ex) {
            System.err.println("Could not delete file: " + fileUrl + ". Error: " + ex.getMessage());
            return false;
        }
    }
}
