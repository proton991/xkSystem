package com.fudan.xk.service;

import com.fudan.xk.model.ExamResult;

import java.util.List;

/**
 * @Auther: 99615
 * @Date: 2019/12/12 15:06
 * @Description:
 */
public interface ExamResultService {
    int addExamResult(List<ExamResult> erList);

    ExamResult findExamResult(String stuId, String courseId);
}
