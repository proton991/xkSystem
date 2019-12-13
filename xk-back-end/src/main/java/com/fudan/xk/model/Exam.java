package com.fudan.xk.model;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @Auther: 99615
 * @Date: 2019/12/8 23:00
 * @Description:
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "exam")
public class Exam implements Serializable {

    private String courseName;

    private String type;

    private String courseId;

    private String examId;

    //Mon-8:55

    private String beginTime;

    //Mon-11:35

    private String endTime;

    @Column(name = "begin_time")
    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    @Column(name = "end_time")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Column(name = "course_id")
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Id
    @Column(name = "exam_id")
    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "courseName")
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public JSONObject toJsonObj(){
        JSONObject obj = new JSONObject();
        obj.put("examId", this.examId);
        obj.put("courseName", this.courseName);
        obj.put("time", this.beginTime + " to " + this.endTime);
        obj.put("type", this.type);
        return obj;
    }
}
