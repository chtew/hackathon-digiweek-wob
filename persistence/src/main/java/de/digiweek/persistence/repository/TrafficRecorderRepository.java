package de.digiweek.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import de.digiweek.persistence.entity.TrafficRecorderEntity;

/**
 * TrafficRecorder Repository class
 */
@Repository
public interface TrafficRecorderRepository extends JpaRepository<TrafficRecorderEntity, Long> {

}
