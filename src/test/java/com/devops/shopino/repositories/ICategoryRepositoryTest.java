package com.devops.shopino.repositories;

import com.devops.shopino.entities.Category;
import com.devops.shopino.entities.Product;
import net.datafaker.providers.base.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ICategoryRepositoryTest {

    @Autowired
    private ICategoryRepository underTest;
    private Category category;


    @BeforeEach
    void setUp() {
        this.category = Category
                .builder()
                .name("Automotive Accessories")
                .description("Lorem Ipsum has been the industry's standard dummy text ever since the 1500s")
                .build();
    }

    @Test
    void testSave_WhenCategoryIsNotNull_ShouldReturnCategory() {
        // Given
        Category expectedResult = this.category;

        // When
        Category actualResult = this.underTest.save(expectedResult);

        // Assert
        assertNotNull(actualResult);
        assertEquals(
                expectedResult,
                actualResult,
                () -> "Expected category does not match the actual category"
        );
    }

    @Test
    void testSave_WhenCategoryIsNull_ShouldThrowException() {
        assertThrows(
                Exception.class,
                () -> underTest.save(null)
        );
    }


    @Test
    void testFindById_WhenCategoryExists_ShouldReturnCategory() {
        // Given
        Category expectedResult = this.category;
        this.underTest.save(expectedResult);

        // When
        Optional<Category> optionalActualResult = this.underTest.findById(expectedResult.getId());

        // Assert
        assertTrue(optionalActualResult.isPresent());
        assertEquals(
                expectedResult,
                optionalActualResult.get(),
                () -> "Expected category does not match the actual category"
        );
    }

    @Test
    void testFindById_WhenCategoryDoesNotExist_ShouldReturnEmpty() {

        Category expectedResult = this.category;
        this.underTest.save(expectedResult);
        UUID nonExistingCategoryId = UUID.randomUUID();

        // When
        Optional<Category> optionalActualResult = this.underTest.findById(nonExistingCategoryId);

        // Assert
        assertTrue(optionalActualResult.isEmpty());
    }

    @Test
    void testFindByName_WhenCategoryExists_ShouldReturnCategory() {

        // Given
        Category expectedResult = this.category;
        this.underTest.save(expectedResult);

        // When
        Optional<Category> optionalActualResult = this.underTest.findByName(expectedResult.getName());

        // Assert
        assertTrue(optionalActualResult.isPresent());
        assertEquals(
                expectedResult,
                optionalActualResult.get(),
                () -> "Expected category does not match the actual category"
        );
    }

    @Test
    void testFindByName_WhenCategoryDoesNotExist_ShouldReturnEmpty() {

        // Given
        Category expectedResult = this.category;
        this.underTest.save(expectedResult);
        String nonExistingCategoryName = "Non existing category";

        // When
        Optional<Category> optionalActualResult = this.underTest.findByName(nonExistingCategoryName);

        // Assert
        assertTrue(optionalActualResult.isEmpty());

    }



}