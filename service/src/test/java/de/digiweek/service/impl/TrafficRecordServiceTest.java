package de.digiweek.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
public class TrafficRecordServiceTest {

    @Autowired
    TrafficRecordService trafficRecordService;

    @Test
    public void testLoadInductionLoopCsv() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("inductionLoopTest.csv")));
        
        StringBuilder stringBuilder = new StringBuilder();
        int lastChar = -1;
        do {
            lastChar = reader.read();
            if (lastChar != -1) {
                stringBuilder.append(Character.toChars(lastChar));
            }
        } while (lastChar != -1);

        String fileContent = stringBuilder.toString();
        
        System.out.println(fileContent);
        trafficRecordService.loadInductionLoopCsv(fileContent);
    }
    
}
