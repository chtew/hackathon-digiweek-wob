package de.digiweek.service.impl;
import java.util.List;
import de.digiweek.persistence.entity.TrafficRecordEntity;
import de.digiweek.persistence.repository.TrafficRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 
 * TrafficRecord Service class
 *
 */
@Service
public class TrafficRecordService implements ServiceInterface<TrafficRecordEntity, TrafficRecordRepository> {

    @Autowired
    private TrafficRecordRepository trafficrecordRepository;

    @Override
    public TrafficRecordRepository getRepository() {
        return trafficrecordRepository;
    }

    public List<TrafficRecordEntity> findAllWithoutTrafficRecorder() {
        return trafficrecordRepository.findAllWithoutTrafficRecorder();
    }

    public List<TrafficRecordEntity> findAllWithoutOtherTrafficRecorder(Long id) {
        return trafficrecordRepository.findAllWithoutOtherTrafficRecorder(id);
    }

}
