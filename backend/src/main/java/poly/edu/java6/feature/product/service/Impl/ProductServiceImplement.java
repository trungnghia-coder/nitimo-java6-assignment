package poly.edu.java6.feature.product.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import poly.edu.java6.feature.product.dto.categoryMenu.CategoriesMenuRequest;
import poly.edu.java6.feature.product.dto.productDisplay.ProductDisplayRequest;
import poly.edu.java6.feature.product.dto.productImages.ProductImageRequest;
import poly.edu.java6.feature.product.repository.ProductImageRepository;
import poly.edu.java6.feature.product.repository.ProductRepository;
import poly.edu.java6.feature.product.service.ProductService;
import poly.edu.java6.model.Product;
import poly.edu.java6.model.ProductImage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplement implements ProductService {
    @Autowired
    ProductImageRepository productImageRepository;

    @Autowired
    ProductRepository productRepository;

    private static Page<ProductDisplayRequest> mapPageToDTO(Page<Product> products, List<ProductImage> productImages) {
        return products.map(product -> {

            List<ProductImageRequest> images = productImages.stream()
                    .filter(img -> img.getProduct().getProductCode().equals(product.getProductCode()))
                    .map(productImage -> new ProductImageRequest(
                            productImage.getImageId(),
                            productImage.getImageUrl(),
                            productImage.getIsPrimary()
                    ))
                    .collect(Collectors.toList());

            return new ProductDisplayRequest(
                    product.getProductCode(),
                    product.getName(),
                    product.getPrice(),
                    product.getDiscount(),
                    images
            );
        });
    }

    @Override
    public Page<ProductDisplayRequest> getProductDisplay(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        List<ProductImage> productImages = productImageRepository.findAll();
        return mapPageToDTO(products, productImages);
    }
}
