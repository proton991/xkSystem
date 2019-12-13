package com.fudan.xk.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @Auther: 崔欣宇
 * @Date: 2019/11/1 18:42
 * @Description:
 */
@Data
@EqualsAndHashCode(exclude = {"selectedCourses", "stuName"})
@Entity
@Table(name = "student")
public class Student implements Serializable {


    private String stuId;


    private String stuName;


    private Set<Course> selectedCourses = new HashSet<>();

    public Student() {
        super();
    }

    @Id
    @Column(name = "stu_id")
    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    @Column(name = "stu_name")
    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    @JsonIgnoreProperties(value = {"students"})
    @ManyToMany(targetEntity = Course.class)
    @JoinTable(name = "stu_course", joinColumns = {@JoinColumn(name = "stu_id")}, inverseJoinColumns = {
            @JoinColumn(name = "course_id")})
    public Set<Course> getSelectedCourses() {
        return selectedCourses;
    }

    public void setSelectedCourses(Set<Course> selectedCourses) {
        this.selectedCourses = selectedCourses;
    }

    @Override
    public String toString() {
        return stuName;
    }
}
