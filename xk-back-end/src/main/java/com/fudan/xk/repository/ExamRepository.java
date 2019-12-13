package com.fudan.xk.repository;

import com.fudan.xk.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 99615
 * @Date: 2019/12/9 02:10
 * @Description:
 */
@Repository
public interface ExamRepository extends JpaRepository<Exam, String> {

    Exam findExamByExamId(String examId);

    Exam findExamByCourseId(String courseId);
}
