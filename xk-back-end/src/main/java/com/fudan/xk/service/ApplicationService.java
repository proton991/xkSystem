package com.fudan.xk.service;

import com.fudan.xk.model.Application;

import java.util.List;

/**
 * @Auther: 99615
 * @Date: 2019/12/12 18:40
 * @Description:
 */
public interface ApplicationService {
    Application addApplication(Application application);

    List<Application> findApplicationsByStuId(String stuId);

    Application findApplicationByStuIdAndCourseId(String stuId, String courseId);

    void deleteApplicationByStuIdAndCourseId(String stuId, String courseId);

    List<Application> findApplicationByCourseId(List<String> courseId);

    Application findApplicationByApplicationId(String appId);

    Application saveApplication(Application application);
}
