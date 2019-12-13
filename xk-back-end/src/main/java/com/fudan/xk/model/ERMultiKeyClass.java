package com.fudan.xk.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Auther: 99615
 * @Date: 2019/12/12 15:24
 * @Description:
 */
@Data
@EqualsAndHashCode
public class ERMultiKeyClass implements Serializable {

    private String stuId;

    private String courseId;

    @Id
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Id
    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }
}
