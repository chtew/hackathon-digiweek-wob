package de.digiweek.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * TrafficRecord Entity class
 */
@XmlRootElement
@Entity
@Table(name = "TRAFFICRECORD")
public class TrafficRecordEntity extends AbstractEntity<Long> {

    // entity fields
    @Column(name = "RECORDDATE")
    private String recordDate;

    @Column(name = "WEEKDAY")
    private Integer weekday;

    @Column(name = "WEEKEND")
    private String weekend;

    @Column(name = "HOLIDAY")
    private String holiday;

    @Column(name = "VACATIONLOWERSAXONY")
    private String vacationLowerSaxony;

    @Column(name = "PLANTHOLIDAY")
    private String plantHoliday;

    @Column(name = "CARCOUNT")
    private Integer carCount;

    // entity relations
    @JsonFilter("filterId")
    @ManyToOne
    @JoinColumn(name = "TRAFFICRECORDER_ID")
    private TrafficRecorderEntity trafficRecorder;

    // entity fields getters and setters
    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public String getWeekend() {
        return weekend;
    }

    public void setWeekend(String weekend) {
        this.weekend = weekend;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public String getVacationLowerSaxony() {
        return vacationLowerSaxony;
    }

    public void setVacationLowerSaxony(String vacationLowerSaxony) {
        this.vacationLowerSaxony = vacationLowerSaxony;
    }

    public String getPlantHoliday() {
        return plantHoliday;
    }

    public void setPlantHoliday(String plantHoliday) {
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
