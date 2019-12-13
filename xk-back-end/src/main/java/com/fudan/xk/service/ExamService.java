package com.fudan.xk.service;

import com.fudan.xk.model.Exam;

import java.util.List;

/**
 * @Auther: 99615
 * @Date: 2019/12/9 02:10
 * @Description:
 */
public interface ExamService {

    int addExams(List<Exam> examList);

    Exam findExamByExamId(String examId);

    Exam saveExam(Exam exam);

    Exam findExamByCourseId(String courseIdList);
}
