package de.digiweek.rest.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import de.digiweek.persistence.entity.TrafficRecordEntity;
import de.digiweek.rest.controller.TrafficRecordController;
import de.digiweek.service.impl.TrafficRecordService;

/**
 * Tests for TrafficRecordController
 *
 * <pre>
 * @WebMvcTest also auto-configures MockMvc which offers a powerful way of
 * easy testing MVC controllers without starting a full HTTP server.
 * </pre>
 */
@WebMvcTest(controllers = TrafficRecordController.class)
public class TrafficRecordControllerIntegrationTest extends AbstractControllerIntegrationTest<TrafficRecordEntity> {

    @MockBean
    private TrafficRecordService trafficrecordService;

    private JacksonTester<TrafficRecordEntity> jsonTrafficRecordEntity;
    private static final String data = "testdata/trafficrecord/";
    private static final String restpath = "/api/trafficrecords/";

    @Override
    public Class<TrafficRecordEntity> getEntityClass() {
        return TrafficRecordEntity.class;
    }

    @Override
    public String getRestPath() {
        return restpath;
    }

    //implement tests here
    @Test
    public void canRetrieveById() throws Exception {

//        TrafficRecordEntity entityToTest = readFromFile(data + "trafficrecord.json");
//        when(appService.findById(0L)).thenReturn(entityToTest);

//        MockHttpServletResponse response = retrieveById(0L);

        // then
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(response.getContentAsString())
//                .isEqualTo(jsonAppDto.write(dto).getJson());
    }

}
