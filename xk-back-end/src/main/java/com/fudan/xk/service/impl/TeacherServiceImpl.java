package com.fudan.xk.service.impl;

import com.fudan.xk.model.Course;
import com.fudan.xk.model.Teacher;
import com.fudan.xk.repository.TeacherRepository;
import com.fudan.xk.service.TeacherService;
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
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Override
    public int addTeachers(List<Teacher> teacherList) {
        return teacherRepository.saveAll(teacherList).size();
    }

    @Override
    public Teacher findTeacherById(String tcId) {
        return teacherRepository.findTeacherByTeacherId(tcId);
    }

    @Override
    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

}
