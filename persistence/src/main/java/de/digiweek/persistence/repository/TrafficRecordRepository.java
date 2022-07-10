package de.digiweek.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
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

}
