package de.digiweek.rest.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;

import de.digiweek.persistence.entity.TrafficRecorderEntity;
import de.digiweek.rest.controller.TrafficRecorderController;
import de.digiweek.service.impl.TrafficRecorderService;

/**
 * Tests for TrafficRecorderController
 *
 * <pre>
 * @WebMvcTest also auto-configures MockMvc which offers a powerful way of
 * easy testing MVC controllers without starting a full HTTP server.
 * </pre>
 */
@WebMvcTest(controllers = TrafficRecorderController.class)
public class TrafficRecorderControllerIntegrationTest extends AbstractControllerIntegrationTest<TrafficRecorderEntity> {

    @MockBean
    private TrafficRecorderService trafficrecorderService;

    private JacksonTester<TrafficRecorderEntity> jsonTrafficRecorderEntity;
    private static final String data = "testdata/trafficrecorder/";
    private static final String restpath = "/api/trafficrecorders/";

    @Override
    public Class<TrafficRecorderEntity> getEntityClass() {
        return TrafficRecorderEntity.class;
    }

    @Override
    public String getRestPath() {
        return restpath;
    }

    // implement tests here
    @Test
    public void canRetrieveById() throws Exception {

        // TrafficRecorderEntity entityToTest = readFromFile(data +
        // "trafficrecorder.json");
        // when(appService.findById(0L)).thenReturn(entityToTest);

        // MockHttpServletResponse response = retrieveById(0L);

        // then
        // assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        // assertThat(response.getContentAsString())
        // .isEqualTo(jsonAppDto.write(dto).getJson());
    }

}
