package com.fudan.xk.service.impl;

import com.fudan.xk.model.Exam;
import com.fudan.xk.repository.ExamRepository;
import com.fudan.xk.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Auther: 99615
 * @Date: 2019/12/9 02:11
 * @Description:
 */
@Service
@Transactional
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamRepository examRepository;


    @Override
    public int addExams(List<Exam> examList) {
        return examRepository.saveAll(examList).size();
    }

    @Override
    public Exam findExamByExamId(String examId) {
        return examRepository.findExamByExamId(examId);
    }

    @Override
    public Exam saveExam(Exam exam) {
        return examRepository.save(exam);
    }

    @Override
    public Exam findExamByCourseId(String courseId) {
        return examRepository.findExamByCourseId(courseId);
    }
}
