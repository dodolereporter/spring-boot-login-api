package fr.codesbuster.solidstock.api.service;


import fr.codesbuster.solidstock.api.entity.LocationEntity;

import java.util.List;

public interface LocationService {

    LocationEntity createLocation(LocationEntity locationEntity);

    List<LocationEntity> getLocations();

    void deleteLocation(Long id);

    LocationEntity updateLocation(LocationEntity locationEntity);

    LocationEntity getLocation(Long id);
}