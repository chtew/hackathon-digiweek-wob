package de.digiweek.rest.controller;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.digiweek.persistence.entity.TrafficRecorderEntity;
import de.digiweek.persistence.exception.NotificationException;
import de.digiweek.rest.exception.NotificationDto;
import de.digiweek.service.impl.TrafficRecorderService;
import io.swagger.v3.oas.annotations.Operation;

/**
 * TrafficRecorder RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/trafficrecorder")
public class TrafficRecorderController {

    static final Logger LOG = LoggerFactory.getLogger(TrafficRecorderController.class);

    @Autowired
    private TrafficRecorderService trafficRecorderService;

    @Operation(summary = "Get all trafficrecorder")
    @GetMapping
    public List<TrafficRecorderEntity> findAll() {
        return this.trafficRecorderService.findAll();
    }

    @Operation(summary = "Get trafficrecorder with id")
    @GetMapping(value = "/{id}")
    public TrafficRecorderEntity findById(@PathVariable("id") Long id) {
        return this.trafficRecorderService.findById(id);
    }

    @Operation(summary = "Create trafficrecorder")
    @PostMapping
    public TrafficRecorderEntity save(@Valid @RequestBody TrafficRecorderEntity entity) {
        return update(entity);
    }

    @Operation(summary = "Update trafficrecorder")
    @PutMapping
    public TrafficRecorderEntity update(@Valid @RequestBody TrafficRecorderEntity entity) {
        return trafficRecorderService.saveOrUpdate(entity);
    }

    @Operation(summary = "Delete trafficrecorder")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id) throws NotificationException {
        trafficRecorderService.delete(id);
    }

    @ExceptionHandler(value = { EntityNotFoundException.class })
    public ResponseEntity<Object> handleException(EntityNotFoundException ex) {
        LOG.info("TrafficRecorder not found. {}", ex.getMessage());
        NotificationDto output = new NotificationDto("error.trafficrecorder.notfound", "TrafficRecorder not found.");
        return new ResponseEntity<>(output, HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/trafficRecordersJSON")
    public void uploadTrafficRecorderJson(@RequestParam("file") MultipartFile file) throws IOException {
        String fileContent = new String(file.getBytes());
        trafficRecorderService.loadTrafficRecorderJson(fileContent);
    }
}
