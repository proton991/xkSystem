package com.fudan.xk.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @Auther: 崔欣宇
 * @Date: 2019/12/8 22:51
 * @Description:
 */
@Data
@EqualsAndHashCode(exclude = {"courses", "beginTime", "endTime"})
@Entity
@Table(name = "time_slot")
public class TimeSlot implements Serializable {


    private String tsId;

    //Mon-8:55

    private String beginTime;

    //Mon-11:35

    private String endTime;


    private Set<Course> courses = new HashSet<>();

    @Id
    @Column(name = "ts_id")
    public String getTsId() {
        return tsId;
    }

    public void setTsId(String tsId) {
        this.tsId = tsId;
    }

    @Column(name = "begin_time", nullable = false)
    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    @Column(name = "end_time", nullable = false)
    public String getEndTime() {
        return endTime;
    }

    @ManyToMany(mappedBy = "timeSlots")
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return beginTime + " to " + endTime;
    }
}
