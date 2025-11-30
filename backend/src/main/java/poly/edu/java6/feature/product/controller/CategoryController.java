package poly.edu.java6.feature.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poly.edu.java6.feature.product.dto.categoryMenu.CategoriesMenuRequest;
import poly.edu.java6.feature.product.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public List<CategoriesMenuRequest> getCategory() {
        List<CategoriesMenuRequest> cateList = categoryService.getALlCategories();
        return cateList;
    }
}
