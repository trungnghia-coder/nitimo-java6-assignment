package poly.edu.java6.feature.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import poly.edu.java6.feature.product.dto.productCRUD.prodcuctFindAll.ProductFindAllResponse;
import poly.edu.java6.feature.product.dto.productCRUD.productCreate.SaveProductRequest;
import poly.edu.java6.feature.product.dto.productCRUD.productUpdate.UpdateProductRequest;
import poly.edu.java6.feature.product.dto.productDetail.ProductDetailRequest;
import poly.edu.java6.feature.product.dto.productDisplay.ProductDisplayRequest;
import poly.edu.java6.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Page<ProductDisplayRequest> getProductDisplay(Pageable pageable);
    ProductDetailRequest getProductById(String Id);
    Product findByProductCode(String productCode);
    Optional<Product> findById(String id);

    Page<ProductFindAllResponse> findAll(Pageable pageable);
    Product createProduct(SaveProductRequest saveProductRequest, List<MultipartFile> files);
    void deleteProduct(String productCode);
    Product updateProduct(String productId, UpdateProductRequest request, List<MultipartFile> newFiles);
}
