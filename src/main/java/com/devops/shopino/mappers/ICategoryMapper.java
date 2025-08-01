package com.devops.shopino.mappers;

import com.devops.shopino.dto.request.CategoryRequest;
import com.devops.shopino.entities.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICategoryMapper {

    Category categoryRequestToCategory(CategoryRequest request);
    List<Category> categoryRequestsToCategories(List<CategoryRequest> requests);

}
