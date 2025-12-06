package poly.edu.java6.feature.product.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import poly.edu.java6.feature.product.dto.productDetail.ProductDetailRequest;
import poly.edu.java6.feature.product.dto.productDisplay.ProductDisplayRequest;
import poly.edu.java6.feature.product.dto.productImages.ProductImageRequest;
import poly.edu.java6.feature.product.repository.ProductImageRepository;
import poly.edu.java6.feature.product.repository.ProductRepository;
import poly.edu.java6.feature.product.repository.ProductVariantRepository;
import poly.edu.java6.feature.size.repository.SizeRepository;
import poly.edu.java6.feature.product.service.ProductService;
import poly.edu.java6.model.Product;
import poly.edu.java6.model.ProductImage;
import poly.edu.java6.model.ProductVariant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplement implements ProductService {
    @Autowired
    ProductImageRepository productImageRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductVariantRepository productVariantRepository;

    @Autowired
    SizeRepository sizeRepository;

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

    @Override
    public ProductDetailRequest getProductById(String Id) {
        Product product = productRepository.findById(Id).
        orElseThrow(() -> new RuntimeException("Product not found with ID: " + Id));;
        List<ProductImage> productImages = productImageRepository.findByProduct(product);

        List<ProductImageRequest> images = productImages.stream()
                .map(productImage -> new ProductImageRequest(
                        productImage.getImageId(),
                        productImage.getImageUrl(),
                        productImage.getIsPrimary()
                ))
                .collect(Collectors.toList());

        List<ProductVariant> variants = productVariantRepository.findByProduct(product);

        List<ProductDetailRequest.ProductVariantDTO> productVariantDTOList = new ArrayList<>();

        for (ProductVariant variant : variants) {
            ProductDetailRequest.ProductVariantDTO variantDTO = new ProductDetailRequest.ProductVariantDTO();

            variantDTO.setVariantId(variant.getVariantId());
            variantDTO.setSizeId(variant.getSize().getSizeId());
            variantDTO.setStock(variant.getStock());

            productVariantDTOList.add(variantDTO);
        }

        List<ProductDetailRequest.SizeDTO> sizeDTOList = variants.stream()
                .map(variant -> variant.getSize())
                .distinct()
                .map(sizeEntity -> {
                    ProductDetailRequest.SizeDTO sizeDTO = new ProductDetailRequest.SizeDTO();
                    sizeDTO.setSizeName(sizeEntity.getName());
                    return sizeDTO;
                })
                .collect(Collectors.toList());

        return new ProductDetailRequest(
                product.getProductCode(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getDiscount(),
                images,
                productVariantDTOList,
                sizeDTOList
        );
    }

    @Override
    public Product findByProductCode(String productCode) {
        Product pr = productRepository.findProductByProductCode(productCode);
        return pr;
    }
}
