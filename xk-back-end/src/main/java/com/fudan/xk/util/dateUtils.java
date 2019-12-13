package com.fudan.xk.util;

/**
 * @Auther: 99615
 * @Date: 2019/12/11 18:51
 * @Description:
 */
public class dateUtils {
    public static boolean hasJoin(String[] time1, String[] time2) {
        String start1 = time1[0];
        String end1 = time1[1];
        String start2 = time2[0];
        String end2 = time2[1];
        //两门课程在同一天上
        int start_ts1 = Integer.parseInt(start1.split("-")[1]);
        int start_ts2 = Integer.parseInt(start2.split("-")[1]);
        int end_ts1 = Integer.parseInt(end1.split("-")[1]);
        int end_ts2 = Integer.parseInt(end2.split("-")[1]);
        String weekday1 = start1.split("-")[0];
        String weekday2 = start2.split("-")[0];
        if (weekday1.equals(weekday2)) {
            if ((start_ts2 - end_ts1 >= 1) || (start_ts1 - end_ts2 >= 1)){
                return false;
            }
            return true;
        }
        return false;
    }
}
