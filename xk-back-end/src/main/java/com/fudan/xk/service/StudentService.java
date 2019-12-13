package com.fudan.xk.service;

import com.fudan.xk.model.Student;

import java.util.List;

/**
 * @Auther: 99615
 * @Date: 2019/12/9 03:01
 * @Description:
 */
public interface StudentService  {
    int addStudents(List<Student> studentList);

    Student saveStudent(Student student);

    Student findStudentById(String stuId);
}
