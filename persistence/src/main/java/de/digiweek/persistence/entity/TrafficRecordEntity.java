package de.digiweek.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFilter;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TrafficRecord Entity class
 */
@Entity
@Table(name = "TRAFFICRECORD")
public class TrafficRecordEntity extends AbstractEntity<Long> {

    // date
    // entity fields
    @Column(name = "RECORDDATE")
    private Date recordDate;

    @Column(name = "WEEKDAY")
    private Integer weekday;

    // bool
    @Column(name = "WEEKEND")
    private Boolean weekend;

    // bool
    @Column(name = "HOLIDAY")
    private Boolean holiday;

    // bool
    @Column(name = "VACATIONLOWERSAXONY")
    private Boolean vacationLowerSaxony;

    // bool
    @Column(name = "PLANTHOLIDAY")
    private Boolean plantHoliday;

    @Column(name = "CARCOUNT")
    private Integer carCount;

    // entity relations
    @JsonFilter("filterId")
    @ManyToOne
    @JoinColumn(name = "TRAFFICRECORDER_ID")
    private TrafficRecorderEntity trafficRecorder;

    // entity fields getters and setters
    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public Boolean getWeekend() {
        return weekend;
    }

    public void setWeekend(Boolean weekend) {
        this.weekend = weekend;
    }

    public Boolean getHoliday() {
        return holiday;
    }

    public void setHoliday(Boolean holiday) {
        this.holiday = holiday;
    }

    public Boolean getVacationLowerSaxony() {
        return vacationLowerSaxony;
    }

    public void setVacationLowerSaxony(Boolean vacationLowerSaxony) {
        this.vacationLowerSaxony = vacationLowerSaxony;
    }

    public Boolean getPlantHoliday() {
        return plantHoliday;
    }

    public void setPlantHoliday(Boolean plantHoliday) {
        this.plantHoliday = plantHoliday;
    }

    public Integer getCarCount() {
        return carCount;
    }

    public void setCarCount(Integer carCount) {
        this.carCount = carCount;
    }

    // entity relations getters and setters
    public TrafficRecorderEntity getTrafficRecorder() {
        return trafficRecorder;
    }

    public void setTrafficRecorder(TrafficRecorderEntity trafficRecorder) {
        this.trafficRecorder = trafficRecorder;
    }

}
