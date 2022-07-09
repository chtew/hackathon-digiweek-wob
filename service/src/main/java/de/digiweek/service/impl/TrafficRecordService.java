package de.digiweek.service.impl;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

import de.digiweek.persistence.entity.TrafficRecordEntity;
import de.digiweek.persistence.repository.TrafficRecordRepository;
import de.digiweek.service.csvdefinitions.InductionLoopRecord;


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

    public void loadInductionLoopCsv(String text) {
        StringReader reader = new StringReader(text);
        CSVReader csvReader = new CSVReaderBuilder(reader).build();
        CsvToBean<InductionLoopRecord> inductionLoopCsvParser = new CsvToBeanBuilder<InductionLoopRecord>(reader).withType(InductionLoopRecord.class).build();

        List<InductionLoopRecord> records = inductionLoopCsvParser.parse();

        System.out.println(records);
        try {
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(records.get(0)));
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
