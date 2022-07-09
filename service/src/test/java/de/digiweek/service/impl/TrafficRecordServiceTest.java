package de.digiweek.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import de.digiweek.persistence.repository.TrafficRecordRepository;
import de.digiweek.persistence.repository.TrafficRecorderRepository;

@SpringBootTest()
// @DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=MYSQL"})
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
        
        // System.out.println(fileContent);
        trafficRecordService.loadInductionLoopCsv(fileContent);
    }

    private static String loadFile(String resourceName) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(TrafficRecordServiceTest.class.getClassLoader().getResourceAsStream(resourceName)));
        
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
