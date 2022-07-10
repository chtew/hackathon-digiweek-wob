package de.digiweek.persistence.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.digiweek.persistence.entity.TrafficRecorderEntity;

/**
 * TrafficRecorder Repository class
 */
@Repository
public interface TrafficRecorderRepository extends JpaRepository<TrafficRecorderEntity, Long> {

    public List<TrafficRecorderEntity> findByExternalId(String externalId);

    @Transactional
    default TrafficRecorderEntity overrideByExternalIdEntity(TrafficRecorderEntity entity) {

        List<TrafficRecorderEntity> persistedEntities = findByExternalId(entity.getExternalId());
        if (persistedEntities != null && !persistedEntities.isEmpty()) {
            entity.setId(persistedEntities.get(0).getId());
        }
        return entity;
    }

}