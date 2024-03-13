package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.ProductEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    @Test
    void createProduct_ValidProduct_ReturnsSavedProduct() {
        ProductEntity product = new ProductEntity();
        product.setName("TestName");

        try {
            ProductEntity savedProduct = productService.createProduct(product);

            Assertions.assertNotNull(savedProduct);
            Assertions.assertEquals("TestName", savedProduct.getName());
        } finally {
            // Remove the created product
            productRepository.deleteAll();
        }
    }

    @Test
    void createProduct_NullProduct_ThrowsAPIException() {
        Assertions.assertThrows(APIException.class, () -> productService.createProduct(null));
    }

    @Test
    void getProducts_ReturnsListOfProducts() {
        // Create a product for the test
        ProductEntity product = new ProductEntity();
        product.setName("TestName");
        productRepository.save(product);

        try {
            // Test retrieving the list of products
            List<ProductEntity> products = productService.getProducts();

            // Check if the list is not empty
            Assertions.assertFalse(products.isEmpty());
        } finally {
            // Remove the created product
            productRepository.deleteAll();
        }
    }

    @Test
    void deleteProduct_ExistingId_DeletesProduct() {
        // Create a product for the test
        ProductEntity product = new ProductEntity();
        product.setName("TestName");
        ProductEntity savedProduct = productRepository.save(product);

        try {
            // Delete the created product
            productService.deleteProduct(savedProduct.getId());

            // Check if the product has been deleted from the database
            Assertions.assertFalse(productRepository.existsById(savedProduct.getId()));
        } finally {
            // Remove the created product
            productRepository.deleteAll();
        }
    }

    @Test
    void deleteProduct_NullId_ThrowsAPIException() {
        Assertions.assertThrows(APIException.class, () -> productService.deleteProduct(null));
    }

    @Test
    void updateProduct_ExistingProduct_ReturnsUpdatedProduct() {
        // Create a product for the test
        ProductEntity product = new ProductEntity();
        product.setName("TestName");
        ProductEntity savedProduct = productRepository.save(product);

        try {
            // Update the product
            savedProduct.setName("UpdatedName");
            ProductEntity updatedProduct = productService.updateProduct(savedProduct);

            // Check if the product has been updated correctly
            Assertions.assertEquals("UpdatedName", updatedProduct.getName());
        } finally {
            // Remove the created product
            productRepository.deleteAll();
        }
    }

    @Test
    void updateProduct_NonExistingProduct_ThrowsAPIException() {
        // Create a product with a non-existing ID
        ProductEntity product = new ProductEntity();
        product.setId(999L); // Non-existing ID
        product.setName("TestName");

        // Check if an APIException is thrown when trying to update a non-existing product
        Assertions.assertThrows(APIException.class, () -> productService.updateProduct(product));
    }

    @Test
    void getProduct_ExistingId_ReturnsProduct() {
        // Create a product for the test
        ProductEntity product = new ProductEntity();
        product.setName("TestName");
        ProductEntity savedProduct = productRepository.save(product);

        try {
            // Retrieve the product by its ID
            ProductEntity retrievedProduct = productService.getProduct(savedProduct.getId());

            // Check if the retrieved product is the same as the saved one
            Assertions.assertEquals(savedProduct.getId(), retrievedProduct.getId());
            Assertions.assertEquals(savedProduct.getName(), retrievedProduct.getName());
        } finally {
            // Remove the created product
            productRepository.deleteAll();
        }
    }

    @Test
    void getProduct_NonExistingId_ThrowsAPIException() {
        // Check if an APIException is thrown when trying to retrieve a product with a non-existing ID
        Assertions.assertThrows(APIException.class, () -> productService.getProduct(999L)); // Non-existing ID
    }
}
