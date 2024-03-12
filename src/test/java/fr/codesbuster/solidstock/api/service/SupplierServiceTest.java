package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.SupplierEntity;
import fr.codesbuster.solidstock.api.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class SupplierServiceTest {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private SupplierRepository supplierRepository;

    @Test
    void createSupplier_ValidSupplier_ReturnsSavedSupplier() {
        SupplierEntity supplier = new SupplierEntity();
        supplier.setId(1L);
        supplier.setCompanyName("TestCompany");
        supplier.setFirstName("John");
        supplier.setLastName("Doe");
        supplier.setCity("TestCity");
        supplier.setZipCode("12345");
        supplier.setAddress("TestAddress");
        supplier.setStreetNumber("123");
        supplier.setEmail("test@example.com");
        supplier.setMobilePhone("1234567890");
        supplier.setHomePhone("0987654321");
        supplier.setWorkPhone("9876543210");
        supplier.setFax("123456");
        supplier.setWebsite("www.example.com");
        supplier.setCountry("TestCountry");
        supplier.setNote("TestNote");


        try {
            SupplierEntity savedSupplier = supplierService.createSupplier(supplier);

            assertNotNull(savedSupplier);
            assertEquals("TestCompany", savedSupplier.getCompanyName());
            assertEquals("John", savedSupplier.getFirstName());
            assertEquals("Doe", savedSupplier.getLastName());
            assertEquals("TestCity", savedSupplier.getCity());
            assertEquals("12345", savedSupplier.getZipCode());
            assertEquals("TestAddress", savedSupplier.getAddress());
            assertEquals("123", savedSupplier.getStreetNumber());
            assertEquals("test@example.com", savedSupplier.getEmail());
            assertEquals("1234567890", savedSupplier.getMobilePhone());
            assertEquals("0987654321", savedSupplier.getHomePhone());
            assertEquals("9876543210", savedSupplier.getWorkPhone());
            assertEquals("123456", savedSupplier.getFax());
            assertEquals("www.example.com", savedSupplier.getWebsite());
            assertEquals("TestCountry", savedSupplier.getCountry());
            assertEquals("TestNote", savedSupplier.getNote());

        } finally {
            // Remove the created supplier
            supplierRepository.deleteAll();
        }
    }

    @Test
    void createSupplier_NullSupplier_ThrowsResponseStatusException() {
        assertThrows(ResponseStatusException.class, () -> supplierService.createSupplier(null));
    }


    @Test
    void getSuppliers_ReturnsListOfSuppliers() {
        // Crée des fournisseurs pour le test
        SupplierEntity supplier1 = new SupplierEntity();
        supplier1.setCompanyName("Supplier1");
        supplierRepository.save(supplier1);

        SupplierEntity supplier2 = new SupplierEntity();
        supplier2.setCompanyName("Supplier2");
        supplierRepository.save(supplier2);

        try {
            // Test de récupération de la liste des fournisseurs
            List<SupplierEntity> suppliers = supplierService.getSuppliers();

            // Vérifie si la liste contient les fournisseurs créés
            assertEquals(2, suppliers.size());
        } finally {
            // Remove the created suppliers
            supplierRepository.deleteAll();
        }
    }

    @Test
    void deleteSupplier_ExistingId_DeletesSupplier() {
        // Crée un fournisseur pour le test
        SupplierEntity supplier = new SupplierEntity();
        supplier.setCompanyName("TestSupplier");
        SupplierEntity savedSupplier = supplierRepository.save(supplier);

        try {
            // Supprime le fournisseur créé
            supplierService.deleteSupplier(savedSupplier.getId());

            // Vérifie si le fournisseur a été supprimé de la base de données
            assertFalse(supplierRepository.existsById(savedSupplier.getId()));
        } finally {
            // Remove the created supplier
            supplierRepository.deleteAll();
        }
    }

    @Test
    void deleteSupplier_NullId_ThrowsResponseStatusException() {
        assertThrows(ResponseStatusException.class, () -> supplierService.deleteSupplier(null));
    }

    @Test
    void updateSupplier_ExistingSupplier_ReturnsUpdatedSupplier() {
        // Crée un fournisseur pour le test
        SupplierEntity supplier = new SupplierEntity();
        supplier.setCompanyName("TestSupplier");
        SupplierEntity savedSupplier = supplierRepository.save(supplier);

        try {
            // Met à jour le fournisseur
            savedSupplier.setCompanyName("UpdatedSupplier");
            SupplierEntity updatedSupplier = supplierService.updateSupplier(savedSupplier);

            // Vérifie si le fournisseur a été mis à jour correctement
            assertEquals("UpdatedSupplier", updatedSupplier.getCompanyName());
        } finally {
            // Remove the created supplier
            supplierRepository.deleteAll();
        }
    }

    @Test
    void updateSupplier_NonExistingSupplier_ThrowsResponseStatusException() {
        // Crée un fournisseur avec un ID inexistant
        SupplierEntity supplier = new SupplierEntity();
        supplier.setId(999L); // ID inexistant
        supplier.setCompanyName("TestSupplier");

        // Vérifie si une ResponseStatusException est levée lors de la tentative de mise à jour d'un fournisseur inexistant
        assertThrows(ResponseStatusException.class, () -> supplierService.updateSupplier(supplier));
    }

    @Test
    void getSupplier_ExistingId_ReturnsSupplier() {
        // Crée un fournisseur pour le test
        SupplierEntity supplier = new SupplierEntity();
        supplier.setCompanyName("TestSupplier");
        SupplierEntity savedSupplier = supplierRepository.save(supplier);

        try {
            // Récupère le fournisseur par son ID
            SupplierEntity retrievedSupplier = supplierService.getSupplier(savedSupplier.getId());

            // Vérifie si le fournisseur récupéré est le même que celui enregistré
            assertEquals(savedSupplier.getId(), retrievedSupplier.getId());
            assertEquals(savedSupplier.getCompanyName(), retrievedSupplier.getCompanyName());
        } finally {
            // Remove the created supplier
            supplierRepository.deleteAll();
        }
    }

    @Test
    void getSupplier_NonExistingId_ThrowsResponseStatusException() {
        // Vérifie si une ResponseStatusException est levée lors de la tentative de récupération d'un fournisseur avec un ID inexistant
        assertThrows(ResponseStatusException.class, () -> supplierService.getSupplier(999L)); // ID inexistant
    }
}