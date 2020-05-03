package moe.yo3explorer.azusa.cgm.entity;

import moe.yo3explorer.azusa.azusa.control.DateUnixtimeAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "history", schema = "dexcom", catalog = "postgres")
@IdClass(HistoryEntityPK.class)
@NamedQueries({
    @NamedQuery(name = HistoryEntity.FIND_BY_DATE,query = "SELECT a FROM HistoryEntity a WHERE a.date = :day")
})
public class HistoryEntity {
    public static final String FIND_BY_DATE = "History.FindByDate";
    private Date date;
    private Time time;
    private Integer filtered;
    private Integer unfiltered;
    private Integer rssi;
    private Integer glucose;
    private Integer trend;
    private Integer sessionState;
    private Integer meterGlucose;
    private Integer eventType;
    private Integer carbs;
    private Integer insulin;
    private Integer eventSubType;
    private Integer specialGlucoseValue;
    private Timestamp dateadded;
    private Timestamp dateupdated;

    @Id
    @Column(name = "date")
    @JsonbTypeAdapter(DateUnixtimeAdapter.class)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Id
    @Column(name = "time")
    @JsonbTypeAdapter(DateUnixtimeAdapter.class)
    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Basic
    @Column(name = "filtered")
    public Integer getFiltered() {
        return filtered;
    }

    public void setFiltered(Integer filtered) {
        this.filtered = filtered;
    }

    @Basic
    @Column(name = "unfiltered")
    public Integer getUnfiltered() {
        return unfiltered;
    }

    public void setUnfiltered(Integer unfiltered) {
        this.unfiltered = unfiltered;
    }

    @Basic
    @Column(name = "rssi")
    public Integer getRssi() {
        return rssi;
    }

    public void setRssi(Integer rssi) {
        this.rssi = rssi;
    }

    @Basic
    @Column(name = "glucose")
    public Integer getGlucose() {
        return glucose;
    }

    public void setGlucose(Integer glucose) {
        this.glucose = glucose;
    }

    @Basic
    @Column(name = "trend")
    public Integer getTrend() {
        return trend;
    }

    public void setTrend(Integer trend) {
        this.trend = trend;
    }

    @Basic
    @Column(name = "\"sessionState\"")
    public Integer getSessionState() {
        return sessionState;
    }

    public void setSessionState(Integer sessionState) {
        this.sessionState = sessionState;
    }

    @Basic
    @Column(name = "\"meterGlucose\"")
    public Integer getMeterGlucose() {
        return meterGlucose;
    }

    public void setMeterGlucose(Integer meterGlucose) {
        this.meterGlucose = meterGlucose;
    }

    @Basic
    @Column(name = "\"eventType\"")
    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    @Basic
    @Column(name = "carbs")
    public Integer getCarbs() {
        return carbs;
    }

    public void setCarbs(Integer carbs) {
        this.carbs = carbs;
    }

    @Basic
    @Column(name = "insulin")
    public Integer getInsulin() {
        return insulin;
    }

    public void setInsulin(Integer insulin) {
        this.insulin = insulin;
    }

    @Basic
    @Column(name = "\"eventSubType\"")
    public Integer getEventSubType() {
        return eventSubType;
    }

    public void setEventSubType(Integer eventSubType) {
        this.eventSubType = eventSubType;
    }

    @Basic
    @Column(name = "\"specialGlucoseValue\"")
    public Integer getSpecialGlucoseValue() {
        return specialGlucoseValue;
    }

    public void setSpecialGlucoseValue(Integer specialGlucoseValue) {
        this.specialGlucoseValue = specialGlucoseValue;
    }

    @Basic
    @Column(name = "dateadded")
    @JsonbTypeAdapter(DateUnixtimeAdapter.class)
    public Timestamp getDateadded() {
        return dateadded;
    }

    public void setDateadded(Timestamp dateadded) {
        this.dateadded = dateadded;
    }

    @Basic
    @Column(name = "dateupdated")
    public Timestamp getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Timestamp dateupdated) {
        this.dateupdated = dateupdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryEntity that = (HistoryEntity) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(time, that.time) &&
                Objects.equals(filtered, that.filtered) &&
                Objects.equals(unfiltered, that.unfiltered) &&
                Objects.equals(rssi, that.rssi) &&
                Objects.equals(glucose, that.glucose) &&
                Objects.equals(trend, that.trend) &&
                Objects.equals(sessionState, that.sessionState) &&
                Objects.equals(meterGlucose, that.meterGlucose) &&
                Objects.equals(eventType, that.eventType) &&
                Objects.equals(carbs, that.carbs) &&
                Objects.equals(insulin, that.insulin) &&
                Objects.equals(eventSubType, that.eventSubType) &&
                Objects.equals(specialGlucoseValue, that.specialGlucoseValue) &&
                Objects.equals(dateadded, that.dateadded) &&
                Objects.equals(dateupdated, that.dateupdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, filtered, unfiltered, rssi, glucose, trend, sessionState, meterGlucose, eventType, carbs, insulin, eventSubType, specialGlucoseValue, dateadded, dateupdated);
    }
}
