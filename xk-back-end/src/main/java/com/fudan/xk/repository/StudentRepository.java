package com.fudan.xk.repository;

import com.fudan.xk.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Auther: 99615
 * @Date: 2019/12/9 02:04
 * @Description:
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Student findStudentByStuId(String stuId);

}
