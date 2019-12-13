package com.fudan.xk.service.impl;

import com.fudan.xk.model.Classroom;
import com.fudan.xk.model.Course;
import com.fudan.xk.model.Teacher;
import com.fudan.xk.repository.CourseRepository;
import com.fudan.xk.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

/**
 * @Auther: 99615
 * @Date: 2019/12/9 02:21
 * @Description:
 */
@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Override
    public int addCourses(List<Course> courseList) {
        return courseRepository.saveAll(courseList).size();
    }

    @Override
    public List<Course> findAllCourses() {
        
        return courseRepository.findAll();
    }

    @Override
    public List<Course> findAllCourseByIdList(List<String> courseIdList){
        return courseRepository.findAllById(courseIdList);
    }
    @Override
    public Course findCourseByCourseId(String courseId) {
        return courseRepository.findCourseByCourseId(courseId);
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> findCoursesByTeachers(Teacher teacher) {
        return courseRepository.findCoursesByTeachers(teacher);
    }

    @Override
    public List<Course> findCoursesByCourseNameLike(String name) {
        return courseRepository.findCoursesByCourseNameContaining(name);
    }

    @Override
    public int selectCourse(String courseId) {
        return courseRepository.addSelectByCourseId(courseId);
    }

    @Override
    public int dropCourse(String courseId) {
        return courseRepository.dropCourseByCourseId(courseId);
    }

//    @Override
//    public void updateCourseByCourseIdAndAndClassrooms(String courseId, Set<Classroom> classroomSet) {
//        courseRepository.updateCourseByCourseIdAndAndClassrooms(courseId, classroomSet);
//    }
}
