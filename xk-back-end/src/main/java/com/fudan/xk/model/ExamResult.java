package com.fudan.xk.model;

import com.alibaba.fastjson.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Auther: 99615
 * @Date: 2019/12/12 14:35
 * @Description:
 */
@Entity
@Table(name = "exam_result")
@IdClass(ERMultiKeyClass.class)
public class ExamResult implements Serializable {

    private String courseId;

    private String stuId;

    private String score;

    @Id
    @Column(name = "course_id")
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Id
    @Column(name = "stu_id")
    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    @Column(name = "score")
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public JSONObject toJsonObj() {
        JSONObject res = new JSONObject();
        res.put("courseId", courseId);
        res.put("Score", score);
        return res;
    }
}
