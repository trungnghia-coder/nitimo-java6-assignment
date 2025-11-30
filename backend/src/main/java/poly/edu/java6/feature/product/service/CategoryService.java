package poly.edu.java6.feature.product.service;

import poly.edu.java6.feature.product.dto.categoryMenu.CategoriesMenuRequest;
import poly.edu.java6.model.Category;

import java.util.List;

public interface CategoryService {
    List<CategoriesMenuRequest> getALlCategories();
}
