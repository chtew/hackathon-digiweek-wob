package de.digiweek.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * TrafficRecorder Entity class
 */
@XmlRootElement
@Entity
@Table(name = "trafficrecorder")
public class TrafficRecorderEntity extends AbstractEntity<Long> {

    // entity fields
    @Column(name = "externalid")
    private String externalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "citydirection")
    private CityDirection cityDirection;

    @Column(name = "neighbor")
    private String neighbor;

    @Column(name = "specialty")
    private String specialty;

    @Column(name = "location")
    private String location;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    // entity relations
    @JsonFilter("filterId")
    @OneToMany(mappedBy = "trafficRecorder")
    private Set<TrafficRecordEntity> trafficRecord;

    // entity fields getters and setters
    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public CityDirection getCityDirection() {
        return cityDirection;
    }

    public void setCityDirection(CityDirection cityDirection) {
        this.cityDirection = cityDirection;
    }

    public String getNeighbor() {
        return neighbor;
    }

    public void setNeighbor(String neighbor) {
        this.neighbor = neighbor;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    // entity relations getters and setters
    public Set<TrafficRecordEntity> getTrafficRecord() {
        return trafficRecord;
    }

    public void setTrafficRecord(Set<TrafficRecordEntity> trafficRecord) {
        this.trafficRecord = trafficRecord;
    }

}
