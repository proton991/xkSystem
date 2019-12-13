package com.fudan.xk.model;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"classrooms", "timeSlots", "selectedQuantity", "students", "teachers", "courseName", "stockQuantity"})
@Entity
@Table(name = "course")
public class Course implements Serializable {


    private Integer selectedQuantity;

    private Set<Student> students = new HashSet<>();

    private Set<Teacher> teachers = new HashSet<>();

    private String courseId;

    private String courseName;

    private Integer stockQuantity;

    private Set<Classroom> classrooms = new HashSet<>();

    private Set<TimeSlot> timeSlots = new HashSet<>();

    private <T> List<String> getNameList(Set<T> objList) {
        List<String> res = new ArrayList<>();
        for (T obj : objList) {
            res.add(obj.toString());
        }
        return res;
    }

    public JSONObject toJsonObj() {
        JSONObject res = new JSONObject();
        res.put("courseId", this.getCourseId());
        res.put("courseName", this.getCourseName());
        res.put("selectedQuantity", this.getSelectedQuantity());
        res.put("stockQuantity", this.getStockQuantity());
        res.put("time", getNameList(this.getTimeSlots()).toString());
        res.put("classroom", getNameList(this.getClassrooms()).toString());
        res.put("teacher", getNameList(this.getTeachers()).toString());
        return res;
    }

    @JsonIgnoreProperties(value = {"courses"})
    @ManyToMany(targetEntity = Classroom.class)
    @JoinTable(name = "course_cr", joinColumns = {@JoinColumn(name = "course_id")}, inverseJoinColumns = {
            @JoinColumn(name = "cr_id")})
    public Set<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(Set<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    @JsonIgnoreProperties(value = {"courses"})
    @ManyToMany(targetEntity = TimeSlot.class)
    @JoinTable(name = "course_ts", joinColumns = {@JoinColumn(name = "course_id")}, inverseJoinColumns = {
            @JoinColumn(name = "ts_id")})
    public Set<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(Set<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }


    @ManyToMany(mappedBy = "selectedCourses")
    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

//	@JsonIgnoreProperties(value = {"teachCourses"})
//    @ManyToMany(mappedBy = "teachCourses")
//    public Set<Teacher> getTeachers() {
//        return teachers;
//    }

    @JsonIgnoreProperties(value = {"teachCourses"})
    @ManyToMany(targetEntity = Teacher.class)
    @JoinTable(name = "tea_course", joinColumns = {@JoinColumn(name = "course_id")}, inverseJoinColumns = {
            @JoinColumn(name = "teacher_id")})
    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }


    @Id
    @Column(name = "course_id")
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Column(name = "course_name", nullable = false)
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Column(name = "stock_quantity")
    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return courseName;
    }

    @Column(name = "selected_quantity")
    public Integer getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(Integer selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }

    public String[][] ts2Array() {
        String[][] res = new String[timeSlots.size()][2];
        int i = 0;
        for(TimeSlot ts : timeSlots){
            res[i][0] = ts.getBeginTime();
            res[i][1] = ts.getEndTime();
            i++;
        }
        return res;
    }
}
