package poly.edu.java6.feature.product.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import poly.edu.java6.feature.product.dto.productCRUD.prodcuctFindAll.ProductFindAllResponse;
import poly.edu.java6.feature.product.dto.productCRUD.prodcuctFindAll.ProductSummary;
import poly.edu.java6.feature.product.dto.productCRUD.productCreate.ProductVariantRequest;
import poly.edu.java6.feature.product.dto.productCRUD.productCreate.SaveProductRequest;
import poly.edu.java6.feature.product.dto.productCRUD.productGetByID.ProductGetByIdResponse;
import poly.edu.java6.feature.product.dto.productCRUD.productUpdate.UpdateProductRequest;
import poly.edu.java6.feature.product.dto.productCRUD.productUpdate.UpdateProductVariantRequest;
import poly.edu.java6.feature.product.dto.productDetail.ProductDetailRequest;
import poly.edu.java6.feature.product.dto.productDisplay.ProductDisplayRequest;
import poly.edu.java6.feature.product.dto.productImages.ProductImageRequest;
import poly.edu.java6.feature.product.repository.CategoriesRepository;
import poly.edu.java6.feature.product.repository.ProductImageRepository;
import poly.edu.java6.feature.product.repository.ProductRepository;
import poly.edu.java6.feature.product.repository.ProductVariantRepository;
import poly.edu.java6.feature.size.repository.SizeRepository;
import poly.edu.java6.feature.product.service.ProductService;
import poly.edu.java6.model.Category;
import poly.edu.java6.model.Product;
import poly.edu.java6.model.ProductImage;
import poly.edu.java6.model.ProductVariant;
import poly.edu.java6.service.IFileStorageService;
import poly.edu.java6.utils.IdUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    @Autowired
    CategoriesRepository categoriesRepository;

    @Autowired
    IFileStorageService fileStorageService;

    @Override
    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }

    private static Page<ProductDisplayRequest> mapPageToDTO(Page<Product> products, List<ProductImage> productImages) {
        return products.map(product -> {

            List<ProductImageRequest> images = productImages.stream()
                    .filter(img -> img.getProduct().getProductCode().equals(product.getProductCode()))
                    .map(productImage -> new ProductImageRequest(
                            productImage.getImageId(),
                            productImage.getImageUrl(),
                            productImage.getIsPrimary()))
                    .collect(Collectors.toList());

            return new ProductDisplayRequest(
                    product.getProductCode(),
                    product.getName(),
                    product.getPrice(),
                    product.getDiscount(),
                    images);
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
        Product product = productRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + Id));
        ;
        List<ProductImage> productImages = productImageRepository.findByProduct(product);

        List<ProductImageRequest> images = productImages.stream()
                .map(productImage -> new ProductImageRequest(
                        productImage.getImageId(),
                        productImage.getImageUrl(),
                        productImage.getIsPrimary()))
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
                sizeDTOList);
    }

    @Override
    public Page<ProductFindAllResponse> findAll(Pageable pageable) {
        Page<ProductSummary> productSummaries = productRepository.findProductSummary(pageable);
        return productSummaries.map(summary -> new ProductFindAllResponse(
                summary.getPrimaryImageUrl(),
                summary.getProductCode(),
                summary.getName(),
                summary.getCategoryName(),
                summary.getTotalStock()));
    }

    @Override
    public Product findByProductCode(String productCode) {
        Product pr = productRepository.findProductByProductCode(productCode);
        return pr;
    }

    @Override
    public Product createProduct(SaveProductRequest cpr, List<MultipartFile> files) {
        Product newProduct = new Product();
        String id = IdUtils.generateCustomRandomId("PR", 5);
        newProduct.setProductCode(id);
        newProduct.setName(cpr.getProductName());
        newProduct.setDescription(cpr.getDescription());
        newProduct.setPrice(cpr.getPrice());
        newProduct.setDiscount(cpr.getDiscount());
        Category category = categoriesRepository.findById(cpr.getCategoryCode())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        newProduct.setCategory(category);

        List<ProductVariant> variants = cpr.getProductVariantRequest().stream()
                .map(variantDto -> {
                    ProductVariant variant = new ProductVariant();
                    variant.setSize(sizeRepository.findByName(variantDto.getSize())
                            .orElseThrow(() -> new RuntimeException("Size not found")));
                    variant.setStock(variantDto.getStockQuantity());
                    variant.setProduct(newProduct);
                    return variant;
                })
                .collect(Collectors.toList());

        newProduct.setVariants(variants);

        List<ProductImage> images = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String imageUrl = fileStorageService.uploadFile(file);
            ProductImage image = new ProductImage();
            image.setImageUrl(imageUrl);
            image.setIsPrimary(i == 0);
            image.setProduct(newProduct);
            images.add(image);
        }
        newProduct.setImages(images);
        return productRepository.save(newProduct);
    }

    @Override
    public ProductGetByIdResponse findProductById(String Id) {
        Product product = productRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + Id));
        ;
        List<ProductImage> productImages = productImageRepository.findByProduct(product);

        List<ProductImageRequest> images = productImages.stream()
                .map(productImage -> new ProductImageRequest(
                        productImage.getImageId(),
                        productImage.getImageUrl(),
                        productImage.getIsPrimary()))
                .collect(Collectors.toList());

        List<ProductVariant> variants = productVariantRepository.findByProduct(product);

        List<ProductGetByIdResponse.ProductVariantDTO> productVariantDTOList = new ArrayList<>();

        for (ProductVariant variant : variants) {
            ProductGetByIdResponse.ProductVariantDTO variantDTO = new ProductGetByIdResponse.ProductVariantDTO();

            variantDTO.setVariantId(variant.getVariantId());
            variantDTO.setSize(variant.getSize().getSizeId());
            variantDTO.setStock(variant.getStock());

            productVariantDTOList.add(variantDTO);
        }

        List<ProductGetByIdResponse.SizeDTO> sizeDTOList = variants.stream()
                .map(variant -> variant.getSize())
                .distinct()
                .map(sizeEntity -> {
                    ProductGetByIdResponse.SizeDTO sizeDTO = new ProductGetByIdResponse.SizeDTO();
                    sizeDTO.setSizeId(sizeEntity.getSizeId());
                    sizeDTO.setSizeName(sizeEntity.getName());
                    return sizeDTO;
                })
                .collect(Collectors.toList());

        return new ProductGetByIdResponse(
                product.getProductCode(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getDiscount(),
                product.getCategory().getCategoryCode(),
                images,
                productVariantDTOList,
                sizeDTOList);
    }

    @Override
    public void deleteProduct(String productCode) {
        productVariantRepository.deleteByProduct_ProductCode(productCode);
        productImageRepository.deleteByProduct_ProductCode(productCode);
        productRepository.deleteById(productCode);
    }

    @Override
    public Product updateProduct(String productId, UpdateProductRequest request, List<MultipartFile> newFiles) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        updateBasicFields(existingProduct, request);
        updateImages(existingProduct, newFiles);
        updateVariants(existingProduct, request.getProductVariantRequest());
        return productRepository.save(existingProduct);
    }

    private void updateBasicFields(Product existingProduct, UpdateProductRequest request) {
        if (request.getProductName() != null) {
            existingProduct.setName(request.getProductName());
        }
        if (request.getDescription() != null) {
            existingProduct.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            existingProduct.setPrice(request.getPrice());
        }
        if (request.getDiscount() != null) {
            existingProduct.setDiscount(request.getDiscount());
        }

        if (request.getCategoryCode() != null) {
            Category category = categoriesRepository.findById(request.getCategoryCode())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            existingProduct.setCategory(category);
        }
    }

    private void updateImages(Product existingProduct, List<MultipartFile> newFiles) {
        if (newFiles != null && !newFiles.isEmpty()) {
            List<ProductImage> oldImages = new ArrayList<>(existingProduct.getImages());
            for (ProductImage oldImage : oldImages) {
                fileStorageService.deleteFile(oldImage.getImageUrl());
            }
            productImageRepository.deleteAll(oldImages);
            existingProduct.getImages().clear();
            List<ProductImage> newImages = new ArrayList<>();
            for (int i = 0; i < newFiles.size(); i++) {
                MultipartFile file = newFiles.get(i);
                String imageUrl = fileStorageService.uploadFile(file);
                ProductImage image = new ProductImage();
                image.setImageUrl(imageUrl);
                image.setIsPrimary(i == 0);
                image.setProduct(existingProduct);
                newImages.add(image);
            }
            existingProduct.getImages().addAll(newImages);
        }
    }

    private void updateVariants(Product existingProduct, List<UpdateProductVariantRequest> variantRequests) {
        Set<Integer> existingVariantIds = existingProduct.getVariants().stream()
                .map(ProductVariant::getVariantId)
                .collect(Collectors.toSet());

        Set<Integer> requestVariantIds = variantRequests.stream()
                .filter(v -> v.getVariatId() != null)
                .map(UpdateProductVariantRequest::getVariatId)
                .collect(Collectors.toSet());

        existingVariantIds.stream()
                .filter(id -> !requestVariantIds.contains(id))
                .forEach(idToDelete -> {
                    productVariantRepository.deleteById(idToDelete);
                });

        existingProduct.getVariants().removeIf(variant -> !requestVariantIds.contains(variant.getVariantId()));

        for (UpdateProductVariantRequest variantDto : variantRequests) {
            if (variantDto.getVariatId() == null) {
                ProductVariant newVariant = new ProductVariant();
                newVariant.setProduct(existingProduct);
                newVariant.setSize(sizeRepository.findById(variantDto.getSizeId())
                        .orElseThrow(() -> new RuntimeException("Size not found")));
                newVariant.setStock(variantDto.getStockQuantity());
                existingProduct.getVariants().add(newVariant);
            } else {
                ProductVariant existingVariant = existingProduct.getVariants().stream()
                        .filter(v -> v.getVariantId().equals(variantDto.getVariatId()))
                        .findFirst()
                        .orElseThrow(
                                () -> new RuntimeException("Variant not found with ID: " + variantDto.getVariatId()));
                existingVariant.setSize(sizeRepository.findById(variantDto.getSizeId())
                        .orElseThrow(() -> new RuntimeException("Size not found")));
                existingVariant.setStock(variantDto.getStockQuantity());
            }
        }
    }
}
