package com.fudan.xk.repository;

import com.fudan.xk.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Auther: 99615
 * @Date: 2019/12/9 02:13
 * @Description:
 */
@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, String> {

    TimeSlot findTimeSlotByTsId(String tsId);

}
