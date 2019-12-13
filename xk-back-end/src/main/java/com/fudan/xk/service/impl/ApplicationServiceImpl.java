package com.fudan.xk.service.impl;

import com.fudan.xk.model.Application;
import com.fudan.xk.repository.ApplicationRepository;
import com.fudan.xk.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Auther: 99615
 * @Date: 2019/12/12 18:40
 * @Description:
 */
@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public Application addApplication(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public List<Application> findApplicationsByStuId(String stuId) {
        return applicationRepository.findApplicationsByStuId(stuId);
    }

    @Override
    public Application findApplicationByStuIdAndCourseId(String stuId, String courseId) {
        return applicationRepository.findApplicationByStuIdAndCourseId(stuId, courseId);
    }

    @Override
    public void deleteApplicationByStuIdAndCourseId(String stuId, String courseId) {
        applicationRepository.deleteApplicationByStuIdAndCourseId(stuId, courseId);
    }

    @Override
    public List<Application> findApplicationByCourseId(List<String> courseId) {
        return applicationRepository.findApplicationByCourseId(courseId);
    }

    @Override
    public Application findApplicationByApplicationId(String appId) {
        return applicationRepository.findApplicationByApplicationId(appId);
    }

    @Override
    public Application saveApplication(Application application) {
        return applicationRepository.save(application);
    }
}
