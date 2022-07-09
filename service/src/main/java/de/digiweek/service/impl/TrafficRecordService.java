package de.digiweek.service.impl;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sound.sampled.LineListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

import de.digiweek.persistence.entity.TrafficRecordEntity;
import de.digiweek.persistence.repository.TrafficRecordRepository;
import de.digiweek.persistence.repository.TrafficRecorderRepository;
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

    @Autowired
    private TrafficRecorderRepository trafficRecorderRepository;

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
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();

        List<String[]> lines = new ArrayList<>();
        try {
            lines = csvReader.readAll();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (CsvException e1) {
            e1.printStackTrace();
        }

        List<TrafficRecordEntity> parsedEntities = new ArrayList<>();
        for (String[] lineElems : lines) {
            TrafficRecordEntity entity = new TrafficRecordEntity();
            entity.setRecordDate(convertToDate(lineElems[3]));
            entity.setCarCount(Integer.parseInt(lineElems[13]));
            entity.setWeekday(Integer.parseInt(lineElems[7]));
            entity.setHoliday(parseBoolean(lineElems[10]));
            entity.setPlantHoliday(parseBoolean(lineElems[12]));
            entity.setVacationLowerSaxony(parseBoolean(lineElems[11]));
            entity.setWeekend(parseBoolean(lineElems[9]));            
        }

        // System.out.println(records);
        try {
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(parsedEntities.get(0)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private Date convertToDate(String value) {
        return Date.from(LocalDate.parse(value).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    private boolean parseBoolean(String s) {
        if (s.equals("0")) {
            return false;
        } else if (s.equals("1")) {
            return true;
        }
        throw new IllegalArgumentException(s + " must be '0' or '1'!");
    }

}
