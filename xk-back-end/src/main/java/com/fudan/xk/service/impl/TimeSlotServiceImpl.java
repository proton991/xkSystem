package com.fudan.xk.service.impl;

import com.fudan.xk.model.TimeSlot;
import com.fudan.xk.repository.TimeSlotRepository;
import com.fudan.xk.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Auther: 99615
 * @Date: 2019/12/9 02:16
 * @Description:
 */
@Service
@Transactional
public class TimeSlotServiceImpl implements TimeSlotService {
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Override
    public int addTimeSlot(List<TimeSlot> timeSlotList) {
        return timeSlotRepository.saveAll(timeSlotList).size();
    }

    @Override
    public TimeSlot findTimeSlotByTsId(String tsId) {
        return timeSlotRepository.findTimeSlotByTsId(tsId);
    }
}
