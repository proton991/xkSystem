package com.fudan.xk.service;

import com.fudan.xk.model.Course;
import com.fudan.xk.model.Teacher;

import java.util.List;

/**
 * @Auther: 99615
 * @Date: 2019/12/9 03:02
 * @Description:
 */
public interface TeacherService {
    int addTeachers(List<Teacher> teacherList);

    Teacher findTeacherById(String tcId);

    Teacher saveTeacher(Teacher teacher);

    List<Teacher> findAll();
}
