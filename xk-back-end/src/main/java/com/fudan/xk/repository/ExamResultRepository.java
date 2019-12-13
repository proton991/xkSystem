package com.fudan.xk.repository;

import com.fudan.xk.model.ERMultiKeyClass;
import com.fudan.xk.model.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Auther: 99615
 * @Date: 2019/12/12 15:09
 * @Description:
 */
@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, ERMultiKeyClass> {

    ExamResult findExamResultByStuIdAndCourseId(String stuId, String courseId);
}
