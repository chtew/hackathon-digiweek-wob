package de.digiweek.persistence.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.digiweek.persistence.entity.TrafficRecordEntity;

/**
 * TrafficRecord Repository class
 */
@Repository
public interface TrafficRecordRepository extends JpaRepository<TrafficRecordEntity, Long> {

    @Query("SELECT e FROM TrafficRecordEntity e WHERE NOT EXISTS (SELECT r FROM e.trafficRecorder r)")
    public List<TrafficRecordEntity> findAllWithoutTrafficRecorder();

    @Query("SELECT e FROM TrafficRecordEntity e WHERE NOT EXISTS (SELECT r FROM e.trafficRecorder r WHERE r.id <> ?1)")
    public List<TrafficRecordEntity> findAllWithoutOtherTrafficRecorder(Long id);

    @Query("SELECT e FROM TrafficRecordEntity e WHERE e.recordDate = ?2 AND EXISTS (SELECT r FROM e.trafficRecorder r WHERE r.externalId = ?1)")
    public List<TrafficRecordEntity> findByExternalIdAndRecordDate(String externalId, Date date);

    default TrafficRecordEntity overrideByExternalIdAndDate(TrafficRecordEntity entity) {
        if (entity != null && entity.getTrafficRecorder() != null) {
            List<TrafficRecordEntity> persistedEntities = findByExternalIdAndRecordDate(
                    entity.getTrafficRecorder().getExternalId(),
                    entity.getRecordDate());
            if (persistedEntities != null && !persistedEntities.isEmpty()) {
                entity.setId(persistedEntities.get(0).getId());
            }
        }
        return entity;
    }

}
