package de.digiweek.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * TrafficRecord Entity class
 */
@XmlRootElement
@Entity
@Table(name = "trafficrecord")
public class TrafficRecordEntity extends AbstractEntity<Long> {

    // entity fields
    @Column(name = "recorddate")
    private Date recordDate;

    @Column(name = "weekday")
    private Integer weekday;

    @Column(name = "holiday")
    private Boolean holiday;

    @Column(name = "vacationlowersaxony")
    private Boolean vacationLowerSaxony;

    @Column(name = "weekend")
    private Boolean weekend;

    @Column(name = "plantholiday")
    private Boolean plantHoliday;

    @Column(name = "carcount")
    private Integer carCount;

    // entity relations
    @JsonFilter("filterId")
    @ManyToOne
    @JoinColumn(name = "trafficrecorder_id")
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

    public Boolean getWeekend() {
        return weekend;
    }

    public void setWeekend(Boolean weekend) {
        this.weekend = weekend;
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
