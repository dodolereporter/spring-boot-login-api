package fr.codesbuster.solidstock.api.service.impl;


import fr.codesbuster.solidstock.api.entity.LocationEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.repository.LocationRepository;
import fr.codesbuster.solidstock.api.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Override
    public LocationEntity createLocation(LocationEntity locationEntity) {
        if (locationEntity == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Location cannot be null");
        }

        if (locationRepository.existsByName(locationEntity.getName())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Location already exists");
        }

        return locationRepository.save(locationEntity);
    }

    @Override
    public List<LocationEntity> getLocations() {
        return locationRepository.findAll();
    }

    @Override
    public void deleteLocation(Long id) {
        if (id == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Location id cannot be null");
        }

        if (!locationRepository.existsById(id)) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Location does not exist");
        }

        locationRepository.deleteById(id);
    }

    @Override
    public LocationEntity updateLocation(LocationEntity locationEntity) {
        if (locationEntity == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Location cannot be null");
        }

        if (!locationRepository.existsById(locationEntity.getId())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Location does not exist");
        }

       return locationRepository.save(locationEntity);
    }

    @Override
    public LocationEntity getLocation(Long id) {
        if (id == null) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Location id cannot be null");
        }

        Optional<LocationEntity> locationEntity = locationRepository.findById(id);

        if (locationEntity.isEmpty()) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Location does not exist");
        }

        return locationEntity.get();
    }
}