package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.QuantityTypeEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.repository.QuantityTypeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class QuantityTypeServiceTest {

    @Autowired
    private QuantityTypeService quantityTypeService;

    @Autowired
    private QuantityTypeRepository quantityTypeRepository;

    @Test
    void createQuantityType_ValidQuantityType_ReturnsSavedQuantityType() {
        QuantityTypeEntity quantityType = new QuantityTypeEntity();
        quantityType.setUnit("TestUnit");

        try {
            QuantityTypeEntity savedQuantityType = quantityTypeService.createQuantityType(quantityType);

            assertNotNull(savedQuantityType);
            assertEquals("TestUnit", savedQuantityType.getUnit());
        } finally {
            // Remove the created quantity type
            quantityTypeRepository.deleteAll();
        }
    }

    @Test
    void createQuantityType_NullQuantityType_ThrowsAPIException() {
        assertThrows(APIException.class, () -> quantityTypeService.createQuantityType(null));
    }

    @Test
    void getQuantityTypes_ReturnsListOfQuantityTypes() {
        // Crée un type de quantité pour le test
        QuantityTypeEntity quantityType = new QuantityTypeEntity();
        quantityType.setUnit("TestUnit");
        quantityType.setDescription("TestDescription");
        quantityType.setName("TestName");
        quantityTypeRepository.save(quantityType);

        try {
            // Test de récupération de la liste des types de quantité
            List<QuantityTypeEntity> quantityTypes = quantityTypeService.getQuantityTypes();

            // Vérifie si la liste n'est pas vide
            assertFalse(quantityTypes.isEmpty());
        } finally {
            // Remove the created quantity type
            quantityTypeRepository.deleteAll();
        }
    }

    @Test
    void deleteQuantityType_ExistingId_DeletesQuantityType() {
        // Crée un type de quantité pour le test
        QuantityTypeEntity quantityType = new QuantityTypeEntity();
        quantityType.setUnit("TestUnit");
        QuantityTypeEntity savedQuantityType = quantityTypeRepository.save(quantityType);

        try {
            // Supprime le type de quantité créé
            quantityTypeService.deleteQuantityType(savedQuantityType.getId());

            // Vérifie si le type de quantité a été supprimé de la base de données
            assertFalse(quantityTypeRepository.existsById(savedQuantityType.getId()));
        } finally {
            // Remove the created quantity type
            quantityTypeRepository.deleteAll();
        }
    }

    @Test
    void deleteQuantityType_NullId_ThrowsAPIException() {
        assertThrows(APIException.class, () -> quantityTypeService.deleteQuantityType(null));
    }

    @Test
    void updateQuantityType_ExistingQuantityType_ReturnsUpdatedQuantityType() {
        // Crée un type de quantité pour le test
        QuantityTypeEntity quantityType = new QuantityTypeEntity();
        quantityType.setUnit("TestUnit");
        QuantityTypeEntity savedQuantityType = quantityTypeRepository.save(quantityType);

        try {
            // Met à jour le type de quantité
            savedQuantityType.setUnit("UpdatedUnit");
            QuantityTypeEntity updatedQuantityType = quantityTypeService.updateQuantityType(savedQuantityType);

            // Vérifie si le type de quantité a été mis à jour correctement
            assertEquals("UpdatedUnit", updatedQuantityType.getUnit());
        } finally {
            // Remove the created quantity type
            quantityTypeRepository.deleteAll();
        }
    }

    @Test
    void updateQuantityType_NonExistingQuantityType_ThrowsAPIException() {
        // Crée un type de quantité avec un ID inexistant
        QuantityTypeEntity quantityType = new QuantityTypeEntity();
        quantityType.setId(999L); // ID inexistant
        quantityType.setUnit("TestUnit");

        // Vérifie si une APIException est levée lors de la tentative de mise à jour d'un type de quantité inexistant
        assertThrows(APIException.class, () -> quantityTypeService.updateQuantityType(quantityType));
    }

    @Test
    void getQuantityType_ExistingId_ReturnsQuantityType() {
        // Crée un type de quantité pour le test
        QuantityTypeEntity quantityType = new QuantityTypeEntity();
        quantityType.setUnit("TestUnit");
        QuantityTypeEntity savedQuantityType = quantityTypeRepository.save(quantityType);

        try {
            // Récupère le type de quantité par son ID
            QuantityTypeEntity retrievedQuantityType = quantityTypeService.getQuantityType(savedQuantityType.getId());

            // Vérifie si le type de quantité récupéré est le même que celui enregistré
            assertEquals(savedQuantityType.getId(), retrievedQuantityType.getId());
            assertEquals(savedQuantityType.getUnit(), retrievedQuantityType.getUnit());
        } finally {
            // Remove the created quantity type
            quantityTypeRepository.deleteAll();
        }
    }

    @Test
    void getQuantityType_NonExistingId_ThrowsAPIException() {
        // Vérifie si une APIException est levée lors de la tentative de récupération d'un type de quantité avec un ID inexistant
        assertThrows(APIException.class, () -> quantityTypeService.getQuantityType(999L)); // ID inexistant
    }
}