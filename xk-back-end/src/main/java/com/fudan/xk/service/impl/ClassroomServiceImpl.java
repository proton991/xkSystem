package com.fudan.xk.service.impl;

import com.fudan.xk.model.Classroom;
import com.fudan.xk.repository.ClassroomRepository;
import com.fudan.xk.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Auther: 99615
 * @Date: 2019/12/9 02:19
 * @Description:
 */
@Service
@Transactional
public class ClassroomServiceImpl implements ClassroomService {
    @Autowired
    private ClassroomRepository classroomRepository;
    @Override
    public int addClassrooms(List<Classroom> classroomList) {
        return classroomRepository.saveAll(classroomList).size();
    }
    @Override
    public List<Classroom> findAllClassroomsByIds(List<String> crIdList){
        return classroomRepository.findAllById(crIdList);
    }

    @Override
    public Classroom findClassroomById(String crId) {
        return classroomRepository.findClassroomByCrId(crId);
    }

    @Override
    public List<Classroom> findAll() {
        return classroomRepository.findAll();
    }
}
