package com.fudan.xk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Auther: 99615
 * @Date: 2019/12/8 22:44
 * @Description:
 */
@Data
@EqualsAndHashCode(exclude = {"courses", "Capacity"})
@Entity
@Table(name = "classroom")
public class Classroom implements Serializable {

    private String crId;
    private int Capacity;
    private Set<Course> courses = new HashSet<>();


    @ManyToMany(mappedBy = "classrooms")
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Id
    @Column(name = "cr_id")
    public String getCrId() {
        return crId;
    }

    public void setCrId(String crId) {
        this.crId = crId;
    }

    @Column(name = "capacity", nullable = false)
    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }

    @Override
    public String toString() {
        return crId;
    }

    public boolean hasConflict(){
        Set<Course> courseSet = courses;
        List<TimeSlot> tsList = new ArrayList<>();
        for (Course c : courseSet){
            List<TimeSlot> tmp = new ArrayList<>(c.getTimeSlots());
            tsList.addAll(tmp);
        }
        int size1 = tsList.size();
        Set<TimeSlot> tsSet = new HashSet<>(tsList);
        List<TimeSlot> tsList2 = new ArrayList<>(tsSet);
        int size2 = tsList2.size();
        if(size1 != size2)
            return true;
        else
            return false;

    }
}
