package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.VATEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.repository.VATRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class VATServiceTest {

    @Autowired
    private VATService vatService;

    @Autowired
    private VATRepository vatRepository;

    @Test
    void createVAT_ValidVAT_ReturnsSavedVAT() {
        VATEntity vat = new VATEntity();
        vat.setRate(52);

        try {
            VATEntity savedVAT = vatService.createVAT(vat);

            assertNotNull(savedVAT);
            assertEquals(52, savedVAT.getRate());
        } finally {
            // Remove the created VAT
            vatRepository.deleteAll();
        }
    }

    @Test
    void createVAT_NullVAT_ThrowsAPIException() {
        assertThrows(APIException.class, () -> vatService.createVAT(null));
    }

    @Test
    void getVATs_ReturnsListOfVATs() {
        // Crée une taxe sur la valeur ajoutée (VAT) pour le test
        VATEntity vat = new VATEntity();
        vat.setRate(20);
        vatRepository.save(vat);

        try {
            // Test de récupération de la liste des taxes sur la valeur ajoutée (VAT)
            List<VATEntity> vats = vatService.getVATs();

            // Vérifie si la liste n'est pas vide
            assertFalse(vats.isEmpty());
        } finally {
            // Remove the created VAT
            vatRepository.deleteAll();
        }
    }

    @Test
    void deleteVAT_ExistingId_DeletesVAT() {
        // Crée une taxe sur la valeur ajoutée (VAT) pour le test
        VATEntity vat = new VATEntity();
        vat.setRate(20);
        VATEntity savedVAT = vatRepository.save(vat);

        try {
            // Supprime la taxe sur la valeur ajoutée (VAT) créée
            vatService.deleteVAT(savedVAT.getId());

            // Vérifie si la VAT a été supprimée de la base de données
            assertFalse(vatRepository.existsById(savedVAT.getId()));
        } finally {
            // Remove the created VAT
            vatRepository.deleteAll();
        }
    }

    @Test
    void deleteVAT_NullId_ThrowsAPIException() {
        assertThrows(APIException.class, () -> vatService.deleteVAT(null));
    }

    @Test
    void updateVAT_ExistingVAT_ReturnsUpdatedVAT() {
        // Crée une taxe sur la valeur ajoutée (VAT) pour le test
        VATEntity vat = new VATEntity();
        vat.setRate(20);
        VATEntity savedVAT = vatRepository.save(vat);

        try {
            // Met à jour la taxe sur la valeur ajoutée (VAT)
            savedVAT.setRate(25);
            VATEntity updatedVAT = vatService.updateVAT(savedVAT);

            // Vérifie si la VAT a été mise à jour correctement
            assertEquals(25, updatedVAT.getRate());
        } finally {
            // Remove the created VAT
            vatRepository.deleteAll();
        }
    }

    @Test
    void updateVAT_NonExistingVAT_ThrowsAPIException() {
        // Crée une taxe sur la valeur ajoutée (VAT) avec un ID inexistant
        VATEntity vat = new VATEntity();
        vat.setId(999L); // ID inexistant
        vat.setRate(20);

        // Vérifie si une APIException est levée lors de la tentative de mise à jour d'une VAT inexistant
        assertThrows(APIException.class, () -> vatService.updateVAT(vat));
    }

    @Test
    void getVAT_ExistingId_ReturnsVAT() {
        // Crée une taxe sur la valeur ajoutée (VAT) pour le test
        VATEntity vat = new VATEntity();
        vat.setRate(20);
        VATEntity savedVAT = vatRepository.save(vat);

        try {
            // Récupère la taxe sur la valeur ajoutée (VAT) par son ID
            VATEntity retrievedVAT = vatService.getVAT(savedVAT.getId());

            // Vérifie si la VAT récupérée est la même que celle enregistrée
            assertEquals(savedVAT.getId(), retrievedVAT.getId());
            assertEquals(savedVAT.getRate(), retrievedVAT.getRate());
        } finally {
            // Remove the created VAT
            vatRepository.deleteAll();
        }
    }

    @Test
    void getVAT_NonExistingId_ThrowsAPIException() {
        // Vérifie si une APIException est levée lors de la tentative de récupération d'une VAT avec un ID inexistant
        assertThrows(APIException.class, () -> vatService.getVAT(999L)); // ID inexistant
    }
}

