package de.digiweek.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import de.digiweek.persistence.entity.TrafficRecorderEntity;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * Tests for TrafficRecorderRepository
 */
@DataJpaTest
public class TrafficRecorderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TrafficRecorderRepository repository;

    @Test
    public void testFindAll() {
        List<TrafficRecorderEntity> trafficrecorders = repository.findAll();
        assertTrue(trafficrecorders.isEmpty());
    }
}
