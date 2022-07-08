package de.digiweek.rest.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import de.digiweek.persistence.entity.AbstractEntity;

public abstract class AbstractControllerAcceptanceTest<ENTITY extends AbstractEntity<Long>> {

    final static Logger LOG = LoggerFactory.getLogger(AbstractControllerAcceptanceTest.class);

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        // create Object Mapper
        mapper = new ObjectMapper();
        JacksonTester.initFields(this, new ObjectMapper());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public abstract Class<ENTITY> getEntityClass();

    public abstract String getRestPath();

    public abstract JacksonTester<ENTITY> getJsonTester();

    @Test
    public abstract void canCreate() throws Exception;

    @Test
    public abstract void canRetrieveById() throws Exception;

    @Test
    public abstract void canUpdate() throws Exception;

    @Test
    public abstract void canDelete() throws Exception;

    protected ENTITY readFromFile(String path) throws Exception {
        try {
            URL res = getClass().getClassLoader().getResource(path);
            File file = new File(res.getFile());
            ENTITY entity = mapper.readValue(file, getEntityClass());
            return entity;
        } catch (IOException e) {
            LOG.error("JSON mapper failed", e);
            throw new Exception("JSON mapper failed");
        }
    }

    protected String readJsonStringFromFile(String path) throws Exception {
        try {
            URL res = getClass().getClassLoader().getResource(path);
            File file = new File(res.getFile());

            StringBuilder contentBuilder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {

                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null) {
                    contentBuilder.append(sCurrentLine);
                }
            }
            return contentBuilder.toString();
        } catch (IOException e) {
            LOG.error("JSON mapper failed", e);
            throw new Exception("JSON mapper failed");
        }
    }

    protected MockHttpServletResponse create(ENTITY entity) throws Exception {
        String applicationString = getJsonTester().write(entity).getJson();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(getRestPath())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(applicationString);

        MockHttpServletResponse response = mvc.perform(builder)
                .andReturn().getResponse();

        LOG.info(response.getContentAsString());
        return response;
    }

    protected MockHttpServletResponse createFromString(String applicationString) throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(getRestPath())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(applicationString);

        MockHttpServletResponse response = mvc.perform(builder)
                .andReturn().getResponse();

        LOG.info(response.getContentAsString());
        return response;
    }

    protected MockHttpServletResponse update(ENTITY entity) throws Exception {
        String applicationString = getJsonTester().write(entity).getJson();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(getRestPath())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(applicationString);

        MockHttpServletResponse response = mvc.perform(builder)
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        LOG.info(response.getContentAsString());
        return response;
    }

    protected MockHttpServletResponse retrieveById(Long id) throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get(getRestPath() + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        LOG.info(response.getContentAsString());
        return response;
    }

    protected MockHttpServletResponse delete(Long id) throws Exception {
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.delete(getRestPath() + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        LOG.info(response.getContentAsString());
        return response;
    }
}
