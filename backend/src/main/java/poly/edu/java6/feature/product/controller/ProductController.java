package poly.edu.java6.feature.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poly.edu.java6.feature.product.dto.productDisplay.ProductDisplayRequest;
import poly.edu.java6.feature.product.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public Page<ProductDisplayRequest> getAllProdcut(Pageable pageable) {
        Page<ProductDisplayRequest> cateList = productService.getProductDisplay(pageable);
        return cateList;
    }
}
