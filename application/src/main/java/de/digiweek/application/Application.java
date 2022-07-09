package de.digiweek.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main SpringApplication to start the whole project
 */
@SpringBootApplication(scanBasePackages = { "de.digiweek.rest", "de.digiweek.service", "de.digiweek.persistence",
        "de.digiweek.application.config" })
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(new Class[] { Application.class }, args);
    }

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("filterId", SimpleBeanPropertyFilter.filterOutAllExcept("id"));
        filterProvider.addFilter("filterIdName", SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "title"));
        filterProvider.addFilter("filterIdDayCount",
                SimpleBeanPropertyFilter.filterOutAllExcept("id", "carCount", "recordDate"));
        mapper.setFilterProvider(filterProvider);
        return mapper;
    }
}
