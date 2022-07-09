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

import de.digiweek.persistence.entity.TrafficRecordEntity;
import de.digiweek.service.impl.TrafficRecordService;
import de.digiweek.persistence.exception.NotificationException;
import de.digiweek.rest.exception.NotificationDto;
import io.swagger.v3.oas.annotations.Operation;

/**
 * TrafficRecord RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/trafficrecord")
public class TrafficRecordController {

    static final Logger LOG = LoggerFactory.getLogger(TrafficRecordController.class);

    @Autowired
    private TrafficRecordService trafficRecordService;

    @Operation(summary = "Get all trafficrecord")
    @GetMapping
    public List<TrafficRecordEntity> findAll() {
        return this.trafficRecordService.findAll();
    }

    @Operation(summary = "Get all trafficrecord without trafficRecorder")
    @GetMapping(value = "find-without-trafficRecorder")
    public List<TrafficRecordEntity> findAllWithoutTrafficRecorder() {
        return trafficRecordService.findAllWithoutTrafficRecorder();
    }

    @Operation(summary = "Get all trafficrecord without other trafficRecorder")
    @GetMapping(value = "find-without-other-trafficRecorder/{id}")
    public List<TrafficRecordEntity> findAllWithoutOtherTrafficRecorder(@PathVariable("id") Long id) {
        return trafficRecordService.findAllWithoutOtherTrafficRecorder(id);
    }

    @Operation(summary = "Get trafficrecord with id")
    @GetMapping(value = "/{id}")
    public TrafficRecordEntity findById(@PathVariable("id") Long id) {
        return this.trafficRecordService.findById(id);
    }

    @Operation(summary = "Create trafficrecord")
    @PostMapping
    public TrafficRecordEntity save(@Valid @RequestBody TrafficRecordEntity entity) {
        return update(entity);
    }

    @Operation(summary = "Update trafficrecord")
    @PutMapping
    public TrafficRecordEntity update(@Valid @RequestBody TrafficRecordEntity entity) {
        return trafficRecordService.saveOrUpdate(entity);
    }

    @Operation(summary = "Delete trafficrecord")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id) throws NotificationException {
        trafficRecordService.delete(id);
    }

    @PostMapping(value = "/inductionLoopCsv")
    public void uploadInductionLoopCsv(@RequestParam("file") MultipartFile file) throws IOException {
        String fileContent = new String(file.getBytes());
        trafficRecordService.loadInductionLoopCsv(fileContent);
    }

    @ExceptionHandler(value = { EntityNotFoundException.class })
    public ResponseEntity<Object> handleException(EntityNotFoundException ex) {
        LOG.info("TrafficRecord not found. {}", ex.getMessage());
        NotificationDto output = new NotificationDto("error.trafficrecord.notfound", "TrafficRecord not found.");
        return new ResponseEntity<>(output, HttpStatus.NOT_FOUND);
    }
}
