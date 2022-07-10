package de.digiweek.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import de.digiweek.persistence.entity.TrafficRecorderEntity;
import de.digiweek.persistence.repository.TrafficRecordRepository;
import de.digiweek.persistence.repository.TrafficRecorderRepository;

@SpringBootTest()
@Transactional
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=MYSQL" })
public class TrafficRecordServiceTest {

    @Autowired
    TrafficRecordService trafficRecordService;

    @Autowired
    TrafficRecordRepository trafficRecordRepository;

    @Autowired
    TrafficRecorderRepository trafficRecorderRepository;

    @Test
    public void testLoadInductionLoopCsv() throws IOException {
        String fileContent = loadFile("inductionLoopTest.csv");

        String fileContentNew = loadFile("inductionLoopTest_new.csv");

        TrafficRecorderEntity recorder = new TrafficRecorderEntity();
        recorder.setExternalId("110_ABC");
        trafficRecorderRepository.saveAndFlush(recorder);

        trafficRecordService.loadInductionLoopCsv(fileContent);

        assertThat(trafficRecordService.findAll(), contains(hasProperty("carCount", is(17))));

        trafficRecordService.loadInductionLoopCsv(fileContentNew);
        assertThat(trafficRecordService.findAll(), contains(hasProperty("carCount", is(999))));
    }

    private static String loadFile(String resourceName) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                TrafficRecordServiceTest.class.getClassLoader().getResourceAsStream(resourceName)));

        StringBuilder stringBuilder = new StringBuilder();
        int lastChar = -1;
        do {
            lastChar = reader.read();
            if (lastChar != -1) {
                stringBuilder.append(Character.toChars(lastChar));
            }
        } while (lastChar != -1);

        return stringBuilder.toString();
    }

}
