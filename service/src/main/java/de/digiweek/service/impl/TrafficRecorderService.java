package de.digiweek.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import de.digiweek.persistence.entity.CityDirection;
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

    private ObjectMapper objectMapper = new ObjectMapper();

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

    public void loadTrafficRecorderJson(String json) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(json);
        ArrayNode features = (ArrayNode) jsonNode.get("features");

        for (JsonNode feature : features) {
            TrafficRecorderEntity trafficRecorderEntity = new TrafficRecorderEntity();

            CityDirection cityDirection = getCityDirection(feature);
            trafficRecorderEntity.setCityDirection(cityDirection);

            String externalId = feature.get("properties").get("ID").asText();
            trafficRecorderEntity.setExternalId(externalId);

            ArrayNode coordinates = (ArrayNode) feature.get("geometry").get("coordinates");
            BigDecimal longitude = new BigDecimal(coordinates.get(0).asText());
            BigDecimal latitude = new BigDecimal(coordinates.get(1).asText());
            trafficRecorderEntity.setLatitude(latitude);
            trafficRecorderEntity.setLongitude(longitude);

            String location = feature.get("properties").get("Lage").asText();
            trafficRecorderEntity.setLocation(location);
            String neighbor = feature.get("properties").get("Nachbardet").asText();
            trafficRecorderEntity.setNeighbor(neighbor);
            String specialty = feature.get("properties").get("Besonderhe").asText();
            trafficRecorderEntity.setSpecialty(specialty);
            trafficRecorderEntity = trafficrecorderRepository.overrideByExternalIdEntity(trafficRecorderEntity);

            trafficrecorderRepository.saveAndFlush(trafficRecorderEntity);

        }

    }

    public List<TrafficRecorderEntity> findByExternalId(String externalId) {
        return trafficrecorderRepository.findByExternalId(externalId);
    }

    private CityDirection getCityDirection(JsonNode feature) {
        String cityDirectionStr = feature.get("properties").get("Richtung_s").asText();
        CityDirection cityDirection = null;
        switch (cityDirectionStr) {
            case "Einwärts":
                cityDirection = CityDirection.in;
                break;
            case "Auswärts":
                cityDirection = CityDirection.out;
                break;
            case "Einfahrt VW":
                cityDirection = CityDirection.inVW;
                break;
            case "Ausfahrt VW":
                cityDirection = CityDirection.outVW;
                break;

        }
        return cityDirection;
    }

}
