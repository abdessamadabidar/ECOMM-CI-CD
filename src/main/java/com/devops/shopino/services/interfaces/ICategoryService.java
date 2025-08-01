package com.devops.shopino.services.interfaces;

import com.devops.shopino.dto.request.CategoryRequest;
import com.devops.shopino.entities.Category;

import java.util.List;
import java.util.UUID;

public interface ICategoryService {
    List<Category> getAllCategories();
    Category regsiterCategory(CategoryRequest request);
    List<Category> registerCategories(List<CategoryRequest> categoryRequestList);
    Category findById(UUID id);

}
