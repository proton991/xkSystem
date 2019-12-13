package com.fudan.xk.service;

import com.fudan.xk.model.Classroom;
import com.fudan.xk.model.Course;
import com.fudan.xk.model.Teacher;
import org.apache.kafka.common.protocol.types.Field;

import java.util.List;
import java.util.Set;

/**
 * @Auther: 99615
 * @Date: 2019/12/9 02:21
 * @Description:
 */
public interface CourseService {
    int addCourses(List<Course> courseList);

    List<Course> findAllCourses();

    List<Course> findAllCourseByIdList(List<String> courseIdList);

    Course findCourseByCourseId(String courseId);


    Course saveCourse(Course course);

    List<Course> findCoursesByTeachers(Teacher teacher);

    List<Course> findCoursesByCourseNameLike(String name);

    int selectCourse(String courseId);

    int dropCourse(String courseId);
}
