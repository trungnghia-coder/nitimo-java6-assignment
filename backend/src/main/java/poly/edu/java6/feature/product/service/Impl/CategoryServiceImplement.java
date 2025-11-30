package poly.edu.java6.feature.product.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poly.edu.java6.feature.product.dto.categoryMenu.CategoriesMenuRequest;
import poly.edu.java6.feature.product.repository.CategoriesRepository;
import poly.edu.java6.feature.product.service.CategoryService;
import poly.edu.java6.model.Category;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImplement implements CategoryService {
    @Autowired
    private CategoriesRepository categoriesRepository;

    private static List<CategoriesMenuRequest> mapListToDTORequest(List<Category> categories) {
        return categories.stream()
                .map(category -> new CategoriesMenuRequest(
                        category.getCategoryCode(),
                        category.getName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoriesMenuRequest>  getALlCategories() {
        List<Category> categories = categoriesRepository.findAll();
        return mapListToDTORequest(categories);
    }
}
