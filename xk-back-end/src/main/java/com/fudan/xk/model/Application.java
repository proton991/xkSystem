package com.fudan.xk.model;

import com.alibaba.fastjson.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Auther: 99615
 * @Date: 2019/12/12 16:24
 * @Description:
 */
@Entity
@Table(name = "application")
public class Application implements Serializable {

    private String applicationId;

    private String stuId;

    private String courseId;

    private String passed;

    private String content;

    private String date;

    @Column(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Id
    @Column(name = "app_id")
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    @Column(name = "stu_id")
    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    @Column(name = "course_id")
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Column(name = "passed")
    public String getPassed() {
        return passed;
    }

    public void setPassed(String passed) {
        this.passed = passed;
    }

    @Column(columnDefinition = "TEXT", name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public JSONObject toJsonObj() {
        JSONObject res = new JSONObject();
        res.put("courseId", courseId);
        res.put("passed", passed);
        res.put("content", content);
        return res;
    }
}
