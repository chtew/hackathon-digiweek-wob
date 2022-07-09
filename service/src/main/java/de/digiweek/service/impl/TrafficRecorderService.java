package de.digiweek.service.impl;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.digiweek.persistence.entity.TrafficRecordEntity;
import de.digiweek.persistence.entity.TrafficRecorderEntity;
import de.digiweek.persistence.repository.TrafficRecordRepository;
import de.digiweek.persistence.repository.TrafficRecorderRepository;

/**
 * 
 * TrafficRecorder Service class
 *
 */
@Service
public class TrafficRecorderService implements ServiceInterface<TrafficRecorderEntity, TrafficRecorderRepository> {

    @Autowired
    private TrafficRecorderRepository trafficrecorderRepository;

    @Autowired
    private TrafficRecordRepository trafficrecordRepository;

    @Override
    public TrafficRecorderRepository getRepository() {
        return trafficrecorderRepository;
    }


    @Override
    public TrafficRecorderEntity saveOrUpdate(TrafficRecorderEntity entity) {

        Set<TrafficRecordEntity> trafficRecordToSave = entity.getTrafficRecord();

        if (entity.getId() != null) {
            TrafficRecorderEntity entityPrev = this.findById(entity.getId());
            for (TrafficRecordEntity item : entityPrev.getTrafficRecord()) {
                TrafficRecordEntity existingItem = trafficrecordRepository.getById(item.getId());
                existingItem.setTrafficRecorder(null);
                this.trafficrecordRepository.save(existingItem);
            }
        }

        entity.setTrafficRecord(null);
        entity = this.getRepository().save(entity);
        this.getRepository().flush();

        if (trafficRecordToSave != null && !trafficRecordToSave.isEmpty()) {
            for (TrafficRecordEntity item : trafficRecordToSave) {
                TrafficRecordEntity newItem = trafficrecordRepository.getById(item.getId());
                newItem.setTrafficRecorder(entity);
                trafficrecordRepository.save(newItem);
            }
        }
        return this.getRepository().getById(entity.getId());
    }


}
