package de.digiweek.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import de.digiweek.persistence.entity.TrafficRecordEntity;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * Tests for TrafficRecordRepository
 */
@DataJpaTest
public class TrafficRecordRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TrafficRecordRepository repository;

    @Test
    public void testFindAll() {
        List<TrafficRecordEntity> trafficrecords = repository.findAll();
        assertTrue(trafficrecords.isEmpty());
    }
}
