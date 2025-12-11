package poly.edu.java6.feature.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import poly.edu.java6.feature.product.dto.MessageResponse;
import poly.edu.java6.feature.product.dto.productCRUD.prodcuctFindAll.ProductFindAllResponse;
import poly.edu.java6.feature.product.dto.productCRUD.productCreate.SaveProductRequest;
import poly.edu.java6.feature.product.dto.productCRUD.productCreate.SaveProductResponse;
import poly.edu.java6.feature.product.dto.productCRUD.productUpdate.UpdateProductRequest;
import poly.edu.java6.feature.product.dto.productDetail.ProductDetailRequest;
import poly.edu.java6.feature.product.dto.productDisplay.ProductDisplayRequest;
import poly.edu.java6.feature.product.service.ProductService;
import poly.edu.java6.model.Product;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/product")
public class AdminProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public Page<ProductFindAllResponse> getAllProdcut(Pageable pageable) {
        Page<ProductFindAllResponse> cateList = productService.findAll(pageable);
        return cateList;
    }

    @GetMapping("/{code}")
    public ProductDetailRequest getProductByCode(@PathVariable("code") String productCode) {
        return productService.getProductById(productCode);
    }

    @PostMapping("/create")
    public SaveProductResponse createProduct(
            @RequestPart("productData") String productJson,
            @RequestPart("images") List<MultipartFile> files) {
        SaveProductRequest saveProductRequest;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            saveProductRequest = objectMapper.readValue(productJson, SaveProductRequest.class);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi định dạng JSON sản phẩm: " + e.getMessage());
        }
        productService.createProduct(saveProductRequest, files);
        return new SaveProductResponse(true, "Thêm sản phẩm thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable String id){
        Optional<Product> existing = productService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(false, "Product not found with id: " + id));
        }
        productService.deleteProduct(id);
        return ResponseEntity.ok(new MessageResponse(true, "Product deleted successfully"));
    }

    @PatchMapping("{id}")
    public ResponseEntity<MessageResponse> updateProduct(@PathVariable String id,
                                                         @RequestPart("data") String productJson,
                                                         @RequestPart("images") List<MultipartFile> files){
        UpdateProductRequest updateProductRequest;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            updateProductRequest = objectMapper.readValue(productJson, UpdateProductRequest.class);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi định dạng JSON sản phẩm: " + e.getMessage());
        }
        productService.updateProduct(id, updateProductRequest, files);
        return ResponseEntity.ok(new MessageResponse(true, "Product updated successfully"));
    }

}
