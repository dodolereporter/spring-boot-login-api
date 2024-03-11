package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.LocationEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.repository.LocationRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class LocationServiceTest {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationRepository locationRepository;
 
    @Test
    void createLocation_ValidLocation_ReturnsSavedLocation() {
        LocationEntity location = new LocationEntity();
        location.setName("TestLocation");

        try {
            LocationEntity savedLocation = locationService.createLocation(location);

            assertNotNull(savedLocation);
            assertEquals("TestLocation", savedLocation.getName());
        } finally {
            // Remove the created location
            locationRepository.deleteAll();
        }
    }

    @Test
    void createLocation_NullLocation_ThrowsAPIException() {
        assertThrows(APIException.class, () -> locationService.createLocation(null));
    }

    @Test
    void getLocations_ReturnsListOfLocations() {
        // Crée une localisation pour le test
        LocationEntity location = new LocationEntity();
        location.setName("TestLocation");
        location.setDescription("TestDescription");
        locationRepository.save(location);

        try {
            // Test de récupération de la liste des localisations
            List<LocationEntity> locations = locationService.getLocations();

            // Vérifie si la liste n'est pas vide
            assertFalse(locations.isEmpty());
        } finally {
            // Remove the created location
            locationRepository.deleteAll();
        }
    }

    @Test
    void deleteLocation_ExistingId_DeletesLocation() {
        // Crée une localisation pour le test
        LocationEntity location = new LocationEntity();
        location.setName("TestLocation");
        LocationEntity savedLocation = locationRepository.save(location);

        try {
            // Supprime la localisation créée
            locationService.deleteLocation(savedLocation.getId());

            // Vérifie si la localisation a été supprimée de la base de données
            assertFalse(locationRepository.existsById(savedLocation.getId()));
        } finally {
            // Remove the created location
            locationRepository.deleteAll();
        }
    }

    @Test
    void deleteLocation_NullId_ThrowsAPIException() {
        assertThrows(APIException.class, () -> locationService.deleteLocation(null));
    }

    @Test
    void updateLocation_ExistingLocation_ReturnsUpdatedLocation() {
        // Crée une localisation pour le test
        LocationEntity location = new LocationEntity();
        location.setName("TestLocation");
        LocationEntity savedLocation = locationRepository.save(location);

        try {
            // Met à jour la localisation
            savedLocation.setName("UpdatedLocation");
            LocationEntity updatedLocation = locationService.updateLocation(savedLocation);

            // Vérifie si la localisation a été mise à jour correctement
            assertEquals("UpdatedLocation", updatedLocation.getName());
        } finally {
            // Remove the created location
            locationRepository.deleteAll();
        }
    }

    @Test
    void updateLocation_NonExistingLocation_ThrowsAPIException() {
        // Crée une localisation avec un ID inexistant
        LocationEntity location = new LocationEntity();
        location.setId(999L); // ID inexistant
        location.setName("TestLocation");

        // Vérifie si une APIException est levée lors de la tentative de mise à jour d'une localisation inexistante
        assertThrows(APIException.class, () -> locationService.updateLocation(location));
    }

    @Test
    void getLocation_ExistingId_ReturnsLocation() {
        // Crée une localisation pour le test
        LocationEntity location = new LocationEntity();
        location.setName("TestLocation");
        LocationEntity savedLocation = locationRepository.save(location);

        try {
            // Récupère la localisation par son ID
            LocationEntity retrievedLocation = locationService.getLocation(savedLocation.getId());

            // Vérifie si la localisation récupérée est la même que celle enregistrée
            assertEquals(savedLocation.getId(), retrievedLocation.getId());
            assertEquals(savedLocation.getName(), retrievedLocation.getName());
        } finally {
            // Remove the created location
            locationRepository.deleteAll();
        }
    }

    @Test
    void getLocation_NonExistingId_ThrowsAPIException() {
        // Vérifie si une APIException est levée lors de la tentative de récupération d'une localisation avec un ID inexistant
        assertThrows(APIException.class, () -> locationService.getLocation(999L)); // ID inexistant
    }
}
