package com.fudan.xk.service.impl;

import com.fudan.xk.model.Student;
import com.fudan.xk.repository.StudentRepository;
import com.fudan.xk.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Auther: 99615
 * @Date: 2019/12/9 03:02
 * @Description:
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Override
    public int addStudents(List<Student> studentList) {
        return studentRepository.saveAll(studentList).size();
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student findStudentById(String stuId) {
        return studentRepository.findStudentByStuId(stuId);
    }
}
