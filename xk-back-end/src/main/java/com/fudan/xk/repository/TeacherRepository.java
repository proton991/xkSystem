package com.fudan.xk.repository;

import com.fudan.xk.model.Course;
import com.fudan.xk.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 99615
 * @Date: 2019/12/9 02:08
 * @Description:
 */
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {
    Teacher findTeacherByTeacherId(String teacherId);


}
