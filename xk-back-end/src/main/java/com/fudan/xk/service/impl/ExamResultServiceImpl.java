package com.fudan.xk.service.impl;

import com.fudan.xk.model.ExamResult;
import com.fudan.xk.repository.ExamResultRepository;
import com.fudan.xk.service.ExamResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Auther: 99615
 * @Date: 2019/12/12 15:08
 * @Description:
 */
@Service
@Transactional
public class ExamResultServiceImpl implements ExamResultService {
    @Autowired
    ExamResultRepository examResultRepository;

    @Override
    public int addExamResult(List<ExamResult> erList) {
        return examResultRepository.saveAll(erList).size();
    }

    @Override
    public ExamResult findExamResult(String stuId, String courseId) {
        return examResultRepository.findExamResultByStuIdAndCourseId(stuId, courseId);
    }
}
