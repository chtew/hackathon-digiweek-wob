package de.digiweek.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import de.digiweek.persistence.entity.CityDirection;
import de.digiweek.persistence.entity.TrafficRecorderEntity;

@SpringBootTest()
@Transactional
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=MYSQL" })
class TrafficRecorderServiceTest {

    @Autowired
    TrafficRecorderService trafficRecorderService;

    @Test
    public void testLoadTrafficRecordersJson() throws IOException {
        String fileContent = loadFile("trafficRecordersTest.json");

        trafficRecorderService.loadTrafficRecorderJson(fileContent);
        List<TrafficRecorderEntity> trafficRecorders = trafficRecorderService.findAll();
        assertThat(trafficRecorderService.findAll(), hasItems(hasProperty("externalId", is("110_ABC"))));

        trafficRecorders = trafficRecorderService.findByExternalId("110_ABC");
        assertEquals(1, trafficRecorders.size());
        assertEquals(CityDirection.in, trafficRecorders.get(0).getCityDirection());

        trafficRecorderService.loadTrafficRecorderJson(fileContent);
        trafficRecorders = trafficRecorderService.findByExternalId("110_ABC");
        assertEquals(1, trafficRecorders.size());
        assertEquals(CityDirection.out, trafficRecorders.get(0).getCityDirection());
    }

    private static String loadFile(String resourceName) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                TrafficRecorderServiceTest.class.getClassLoader().getResourceAsStream(resourceName)));

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
