package com.fudan.xk.service;

import com.fudan.xk.model.TimeSlot;

import java.util.List;

/**
 * @Auther: 99615
 * @Date: 2019/12/9 02:15
 * @Description:
 */
public interface TimeSlotService {
    int addTimeSlot(List<TimeSlot> timeSlotList);

    TimeSlot findTimeSlotByTsId(String tsId);
}
