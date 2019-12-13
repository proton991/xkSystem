package com.fudan.xk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Auther: 崔欣宇
 * @Date: 2019/11/1 18:55
 * @Description:
 */
@Data
@EqualsAndHashCode(exclude = {"teacherName", "teachCourses"})
@Entity
@Table(name = "teacher")
public class Teacher {


    private String teacherId;


    private String teacherName;


    private Set<Course> teachCourses = new HashSet<>();

    @Id
    @Column(name = "teacher_id")
    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    @Column(name = "teacher_name")
    @NotNull
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }


    @ManyToMany(mappedBy = "teachers")
    public Set<Course> getTeachCourses() {
        return teachCourses;
    }

    public void setTeachCourses(Set<Course> teachCourses) {
        this.teachCourses = teachCourses;
    }

    @Override
    public String toString() {
        return teacherName;
    }

    public boolean hasConflict() {
        Set<Course> courseSet = teachCourses;
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

