package com.fudan.xk.service;

import com.fudan.xk.model.Classroom;

import java.util.List;

/**
 * @Auther: 99615
 * @Date: 2019/12/9 02:19
 * @Description:
 */
public interface ClassroomService {
    int addClassrooms(List<Classroom> classroomList);

    List<Classroom> findAllClassroomsByIds(List<String> crIdList);

    Classroom findClassroomById(String crId);

    List<Classroom> findAll();
}
