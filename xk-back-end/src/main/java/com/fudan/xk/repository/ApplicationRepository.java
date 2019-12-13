package com.fudan.xk.repository;

import com.fudan.xk.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Auther: 99615
 * @Date: 2019/12/12 18:41
 * @Description:
 */
public interface ApplicationRepository extends JpaRepository<Application, String> {

    List<Application> findApplicationByCourseId(List<String> courseId);

    List<Application> findApplicationsByStuId(String stuId);

    Application findApplicationByStuIdAndCourseId(String stuId, String courseId);

    void deleteApplicationByStuIdAndCourseId(String stuId, String courseId);

    Application findApplicationByApplicationId(String appId);

}
