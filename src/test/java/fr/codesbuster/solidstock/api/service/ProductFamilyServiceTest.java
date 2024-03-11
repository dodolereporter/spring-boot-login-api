package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.ProductFamilyEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.repository.ProductFamilyRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductFamilyServiceTest {

    @Autowired
    private ProductFamilyService productFamilyService;

    @Autowired
    private ProductFamilyRepository productFamilyRepository;

    @Test
    void createProductFamily_ValidProductFamily_ReturnsSavedProductFamily() {
        ProductFamilyEntity productFamily = new ProductFamilyEntity();
        productFamily.setName("TestName");

        try {
            ProductFamilyEntity savedProductFamily = productFamilyService.createProductFamily(productFamily);

            assertNotNull(savedProductFamily);
            assertEquals("TestName", savedProductFamily.getName());
        } finally {
            // Remove the created product family
            productFamilyRepository.deleteAll();
        }
    }

    @Test
    void createProductFamily_NullProductFamily_ThrowsAPIException() {
        assertThrows(APIException.class, () -> productFamilyService.createProductFamily(null));
    }

    @Test
    void getProductFamilies_ReturnsListOfProductFamilies() {
        // Create a product family for the test
        ProductFamilyEntity productFamily = new ProductFamilyEntity();
        productFamily.setName("TestName");
        productFamilyRepository.save(productFamily);

        try {
            // Test retrieving the list of product families
            List<ProductFamilyEntity> productFamilies = productFamilyService.getProductFamilies();

            // Check if the list is not empty
            assertFalse(productFamilies.isEmpty());
        } finally {
            // Remove the created product family
            productFamilyRepository.deleteAll();
        }
    }

    @Test
    void deleteProductFamily_ExistingId_DeletesProductFamily() {
        // Create a product family for the test
        ProductFamilyEntity productFamily = new ProductFamilyEntity();
        productFamily.setName("TestName");
        ProductFamilyEntity savedProductFamily = productFamilyRepository.save(productFamily);

        try {
            // Delete the created product family
            productFamilyService.deleteProductFamily(savedProductFamily.getId());

            // Check if the product family has been deleted from the database
            assertFalse(productFamilyRepository.existsById(savedProductFamily.getId()));
        } finally {
            // Remove the created product family
            productFamilyRepository.deleteAll();
        }
    }

    @Test
    void deleteProductFamily_NullId_ThrowsAPIException() {
        assertThrows(APIException.class, () -> productFamilyService.deleteProductFamily(null));
    }

    @Test
    void updateProductFamily_ExistingProductFamily_ReturnsUpdatedProductFamily() {
        // Create a product family for the test
        ProductFamilyEntity productFamily = new ProductFamilyEntity();
        productFamily.setName("TestName");
        ProductFamilyEntity savedProductFamily = productFamilyRepository.save(productFamily);

        try {
            // Update the product family
            savedProductFamily.setName("UpdatedName");
            ProductFamilyEntity updatedProductFamily = productFamilyService.updateProductFamily(savedProductFamily);

            // Check if the product family has been updated correctly
            assertEquals("UpdatedName", updatedProductFamily.getName());
        } finally {
            // Remove the created product family
            productFamilyRepository.deleteAll();
        }
    }

    @Test
    void updateProductFamily_NonExistingProductFamily_ThrowsAPIException() {
        // Create a product family with a non-existing ID
        ProductFamilyEntity productFamily = new ProductFamilyEntity();
        productFamily.setId(999L); // Non-existing ID
        productFamily.setName("TestName");

        // Check if an APIException is thrown when trying to update a non-existing product family
        assertThrows(APIException.class, () -> productFamilyService.updateProductFamily(productFamily));
    }

    @Test
    void getProductFamily_ExistingId_ReturnsProductFamily() {
        // Create a product family for the test
        ProductFamilyEntity productFamily = new ProductFamilyEntity();
        productFamily.setName("TestName");
        ProductFamilyEntity savedProductFamily = productFamilyRepository.save(productFamily);

        try {
            // Retrieve the product family by its ID
            ProductFamilyEntity retrievedProductFamily = productFamilyService.getProductFamily(savedProductFamily.getId());

            // Check if the retrieved product family is the same as the saved one
            assertEquals(savedProductFamily.getId(), retrievedProductFamily.getId());
            assertEquals(savedProductFamily.getName(), retrievedProductFamily.getName());
        } finally {
            // Remove the created product family
            productFamilyRepository.deleteAll();
        }
    }

    @Test
    void getProductFamily_NonExistingId_ThrowsAPIException() {
        // Check if an APIException is thrown when trying to retrieve a product family with a non-existing ID
        assertThrows(APIException.class, () -> productFamilyService.getProductFamily(999L)); // Non-existing ID
    }
}
