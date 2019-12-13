package com.fudan.xk.repository;

import com.fudan.xk.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Auther: 99615
 * @Date: 2019/12/9 02:17
 * @Description:
 */
@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, String> {
    Classroom findClassroomByCrId(String crId);
}
