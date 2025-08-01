package com.devops.shopino.services;

import com.devops.shopino.dto.request.CategoryRequest;
import com.devops.shopino.entities.Category;
import com.devops.shopino.mappers.ICategoryMapper;
import com.devops.shopino.repositories.ICategoryRepository;
import com.devops.shopino.services.interfaces.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {
    private final ICategoryRepository categoryRepository;
    private final ICategoryMapper mapper;
    private final static Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category regsiterCategory(CategoryRequest request) {
        Optional<Category> optional = categoryRepository
                .findByName(request.getName());
        if (optional.isPresent()) {
            LOGGER.error("Category {} already exist!", request.getName());
            throw new RuntimeException("Category already exist");
        }
        return categoryRepository.save(mapper.categoryRequestToCategory(request));
    }

    @Override
    public List<Category> registerCategories(List<CategoryRequest> categoryRequestList) {
        categoryRequestList
                .forEach(cr -> {
                    Optional<Category> optional = categoryRepository
                            .findByName(cr.getName());
                    if (optional.isPresent()) {
                        LOGGER.error("Category {} already exist!", cr.getName());
                        throw new RuntimeException("Category already exist");
                    }

                });


        return categoryRepository
                .saveAll(
                        mapper.categoryRequestsToCategories(categoryRequestList)
                );
    }

    @Override
    public Category findById(UUID id) {

        return categoryRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            LOGGER.error("Category {} does not exist!", id);
                            return new RuntimeException("Category not found");
                        }
                );
    }
}
