package com.fudan.xk.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.fudan.xk.base.controller.BaseApiController;
import com.fudan.xk.base.result.Result;
import com.fudan.xk.base.result.ResultCode;
import com.fudan.xk.model.*;
import com.fudan.xk.service.ApplicationService;
import com.fudan.xk.service.CourseService;
import com.fudan.xk.service.StudentService;
import com.fudan.xk.service.TeacherService;
import com.fudan.xk.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: 99615
 * @Date: 2019/12/9 09:49
 * @Description:
 */
@RestController
public class CourseController extends BaseApiController {
    private static Logger log = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ApplicationService applicationService;

    @RequestMapping(value = "/getCourses", method = RequestMethod.GET)
    public Result<Object> getCourses(@RequestParam("token") String token) {
        Jedis jedis = new Jedis();
        JSONObject res = null;
        List<JSONObject> resList = new ArrayList<>();
        String username = jedis.get(token);
        if (username != null) {
            log.info("###############" + username);
            List<Course> courseList = courseService.findAllCourses();
            for (int i = 0; i < courseList.size(); i++) {
                Course c = courseList.get(i);
                res = c.toJsonObj();
                resList.add(res);
            }
            JSONObject val = new JSONObject();
            val.put("courseInfo", resList);
            return Result.success(val);
        } else
            log.info("#############token not found! ####################");
        return Result.failure();
    }

    @RequestMapping(value = "/teacher/passApplication", method = RequestMethod.POST)
    public Result<Object> passApplication(@RequestParam("token") String token, @RequestParam("appId") String appId){
        Jedis jedis = new Jedis();
        JSONObject res = new JSONObject();
        String username = jedis.get(token);
        if (username != null) {
            Application application = applicationService.findApplicationByApplicationId(appId);
            String courseId = application.getCourseId();
            String stuId = application.getStuId();
            Course course = courseService.findCourseByCourseId(courseId);
            Student student = studentService.findStudentById(stuId);
            //选课人数+申请通过人数 > 所有教室的最小容量，自动驳回申请
            int minCapacity = Integer.MAX_VALUE;
            Set<Classroom> classroomSet = course.getClassrooms();
            for (Classroom classroom : classroomSet) {
                int capacity = classroom.getCapacity();
                if (capacity < minCapacity)
                    minCapacity = capacity;
            }
            int selected = course.getSelectedQuantity();
            if (selected >= minCapacity)
                return Result.failure(ResultCode.CLASSROOM_CAPACITY_FULL2);

            //通过申请
            //student
            Set<Course> courseSet = student.getSelectedCourses();
            courseSet.add(course);
            studentService.saveStudent(student);

            //course
            course.setSelectedQuantity(course.getSelectedQuantity() + 1);
            courseService.saveCourse(course);

            //application state
            application.setPassed("true");
            applicationService.saveApplication(application);

            return Result.success();
        }
        return Result.failure();
    }
    @RequestMapping(value = "/teacher/getApplications", method = RequestMethod.GET)
    public Result<Object> getApplicationInfoByTea(@RequestParam("token") String token){
        Jedis jedis = new Jedis();
        JSONObject res = new JSONObject();
        String username = jedis.get(token);
        List<JSONObject> objList = new ArrayList<>();
        if (username != null) {
            Teacher teacher = teacherService.findTeacherById(username);
            List<Course> courseList = courseService.findCoursesByTeachers(teacher);
            List<String> idList = new ArrayList<>();
            for (int i = 0; i < courseList.size(); i++) {
                idList.add(courseList.get(i).getCourseId());
            }
            List<Application> appList = applicationService.findApplicationByCourseId(idList);
            for (Application tmp : appList){
                JSONObject jObj = tmp.toJsonObj();
                jObj.put("appId", tmp.getApplicationId());
                jObj.put("stuId", tmp.getStuId());
                jObj.put("courseName", courseService.findCourseByCourseId(tmp.getCourseId()).getCourseName());
                objList.add(jObj);
            }
            res.put("appInfo", objList);
            return Result.success(res);
        }
        return Result.failure();
    }
    @RequestMapping(value = "/teacher/getCourses", method = RequestMethod.GET)
    public Result<Object> getTeachCourses(@RequestParam("token") String token) {
        Jedis jedis = new Jedis();
        JSONObject res = new JSONObject();
        String username = jedis.get(token);
        List<JSONObject> objList = new ArrayList<>();
        if (username != null) {
            Teacher teacher = teacherService.findTeacherById(username);
            List<Course> courseList = courseService.findCoursesByTeachers(teacher);
            for (int i = 0; i < courseList.size(); i++) {
                Course c = courseList.get(i);
                objList.add(c.toJsonObj());
            }
            res.put("teachCourseList", objList);
            return Result.success(res);
        }
        return Result.failure();
    }

    @RequestMapping(value = "/getCoursesFuzzy", method = RequestMethod.GET)
    public Result<Object> getCourseByName(@RequestParam("token") String token, @RequestParam("courseName") String courseName) {
        if (courseName.length() == 0)
            return Result.success();
        List<JSONObject> objList = new ArrayList<>();
        Jedis jedis = new Jedis();
        String username = jedis.get(token);
        if (username != null) {
            List<Course> courseList = courseService.findCoursesByCourseNameLike(courseName);
            for (Course c : courseList) {
                objList.add(c.toJsonObj());
            }
            JSONObject res = new JSONObject();
            res.put("courseInfo", objList);
            return Result.success(res);
        }
        return Result.failure();
    }

    public boolean checkConflict(Set<Course> courseSet, Course course) {
        for (Course c : courseSet) {
            String[][] tmp = c.ts2Array();
            String[][] target = course.ts2Array();
            System.out.println(course.getCourseName());
            for (int i = 0; i < tmp.length; i++) {
                for (int j = 0; j < target.length; j++) {
                    boolean res = hasJoin(target[j], tmp[i]);
                    if (res)
                        return res;
                }
            }
        }
        return false;

    }

    @RequestMapping(value = "/student/selectCourse", method = RequestMethod.POST)
    public Result<Object> selectCourse(@RequestParam("token") String token, @RequestParam("courseId") String courseId) {
        Jedis jedis = new Jedis();
        int res = 0;
        String username = jedis.get(token);
        if (username != null) {
            Course c = courseService.findCourseByCourseId(courseId);
            Student s = studentService.findStudentById(username);
            Set<Course> selectedCourses = s.getSelectedCourses();
            if (selectedCourses.size() == 0 || !selectedCourses.contains(c)) {
                //检查数量是否到上限
                if (c.getSelectedQuantity() >= c.getStockQuantity())
                    return Result.failure(ResultCode.COURSE_NO_STOCK);
                //检查与所选课程时间是否有冲突
                if (checkConflict(selectedCourses, c))
                    return Result.failure(ResultCode.COURSE_TIME_CONFLICT);
                //课程数据处理
                res = courseService.selectCourse(courseId);
                //学生数据处理
                selectedCourses.add(c);
                s.setSelectedCourses(selectedCourses);
                studentService.saveStudent(s);
            } else {
                return Result.failure(ResultCode.COURSE_ALREADY_SELECTED);
            }
            if (res == 1) {
                return Result.success(res);
            }
        }
        return Result.failure();
    }

    @RequestMapping(value = "/student/dropCourse", method = RequestMethod.POST)
    public Result<Object> dropCourse(@RequestParam("token") String token, @RequestParam("courseId") String courseId) {
        Jedis jedis = new Jedis();
        int res = 0;
        String username = jedis.get(token);
        if (username != null) {
            Course c = courseService.findCourseByCourseId(courseId);
            Student s = studentService.findStudentById(username);
            Set<Course> selectedCourses = s.getSelectedCourses();
            if (selectedCourses.size() == 0 || !selectedCourses.contains(c)) {

            } else {
                //课程数据处理
                res = courseService.dropCourse(courseId);
                //学生数据处理
                selectedCourses.remove(c);
                s.setSelectedCourses(selectedCourses);
                studentService.saveStudent(s);
            }
        }
        if (res == 1) {
            return Result.success();
        }
        return Result.failure(ResultCode.COURSE_NO_SELECT);
    }

    @RequestMapping(value = "/student/getDynamicTable", method = RequestMethod.GET)
    public Result<Object> getDynamicTable(@RequestParam("token") String token) {
        Map<String, Integer> weekDayMap = new HashMap<>();
        weekDayMap.put("Mon", 0);
        weekDayMap.put("Tue", 1);
        weekDayMap.put("Wed", 2);
        weekDayMap.put("Thu", 3);
        weekDayMap.put("Fri", 4);
//        JSONObject[][] table = new JSONObject[5][];
        List<List<JSONObject>> table = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            List<JSONObject> list = new ArrayList<>();
            table.add(list);
        }
        JSONObject tmp = null;
        int start = 0;
        int time = 0;
        Jedis jedis = new Jedis();
        String username = jedis.get(token);
        List<JSONObject> objList = new ArrayList<>();
        if (username != null) {
            Student student = studentService.findStudentById(username);
            Set<Course> selectedCourse = student.getSelectedCourses();
            for (Course c : selectedCourse) {
                objList.add(c.toJsonObj());
                Set<TimeSlot> timeSlots = c.getTimeSlots();
                for (TimeSlot ts : timeSlots) {
                    tmp = new JSONObject();
                    tmp.put("courseName", c.getCourseName());
                    String startTime = ts.getBeginTime();
                    String endTime = ts.getEndTime();
                    String weekDay = startTime.split("-")[0];
                    int wkdIndex = weekDayMap.get(weekDay);
                    int startIndex = Integer.parseInt(startTime.split("-")[1]);
                    int endIndex = Integer.parseInt(endTime.split("-")[1]);
                    time = endIndex - startIndex;
                    tmp.put("start", startIndex);
                    tmp.put("time", time);
                    tmp.put("weekday", wkdIndex);
                    table.get(wkdIndex).add(tmp);
                }
            }
//            JSONObject res = new JSONObject();
//            res.put("dynamicTable", table);
            return Result.success(table);
        }
        return Result.failure();
    }

    @RequestMapping(value = "/student/getSelectedCourse", method = RequestMethod.GET)
    public Result<Object> getSelectedCourse(@RequestParam("token") String token) {
        Jedis jedis = new Jedis();
        String username = jedis.get(token);
        List<JSONObject> objList = new ArrayList<>();
        if (username != null) {
            Student student = studentService.findStudentById(username);
            Set<Course> selectedCourse = student.getSelectedCourses();
            for (Course c : selectedCourse) {
                objList.add(c.toJsonObj());
            }
            JSONObject res = new JSONObject();
            res.put("courseInfo", objList);
            return Result.success(res);
        }
        return Result.failure();
    }

    @RequestMapping(value = "/student/apply", method = RequestMethod.POST)
    public Result<Object> submitApplication(@RequestParam("token") String token, @RequestParam("courseId") String courseId, @RequestParam("content") String content) {
        Jedis jedis = new Jedis();
        String username = jedis.get(token);
        JSONObject res = new JSONObject();
        if (username != null) {
            Course course = courseService.findCourseByCourseId(courseId);
            Student student = studentService.findStudentById(username);
            Set<Course> selectedCourses = student.getSelectedCourses();

            //申请课程不存在
            if (course == null) {
                return Result.failure(ResultCode.COURSE_NOT_FOUND);
            }

            //申请择课程已选
            if (selectedCourses.size() != 0 && selectedCourses.contains(course)) {
                return Result.failure(ResultCode.COURSE_ALREADY_SELECTED);
            }

            //申请课程与已选课程时间有冲突
            if (selectedCourses.size() != 0) {
                if (checkConflict(selectedCourses, course))
                    return Result.failure(ResultCode.COURSE_TIME_CONFLICT);
            }

            //已经提交过相关申请
            Application tmp = applicationService.findApplicationByStuIdAndCourseId(username, courseId);
            if (tmp != null)
                return Result.failure(ResultCode.APPLICATION_EXISTS);

            //选课人数+申请通过人数 > 所有教室的最小容量，自动驳回申请
            int minCapacity = Integer.MAX_VALUE;
            Set<Classroom> classroomSet = course.getClassrooms();
            for (Classroom classroom : classroomSet) {
                int capacity = classroom.getCapacity();
                if (capacity < minCapacity)
                    minCapacity = capacity;
            }
            int selected = course.getSelectedQuantity();
            if (selected >= minCapacity)
                return Result.failure(ResultCode.CLASSROOM_CAPACITY_FULL);

            //申请课程还有余量，驳回申请
            if (course.getSelectedQuantity() < course.getStockQuantity()) {
                return Result.failure(ResultCode.COURSE_SELECTABLE);
            }
            Application application = new Application();
            application.setApplicationId(UUIDUtil.getUUID());
            application.setContent(content);
            application.setCourseId(courseId);
            application.setStuId(username);
            application.setPassed("false");
            //set date
            Date dateNow = new Date();
            String pattern = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(dateNow);
            application.setDate(date);
            applicationService.addApplication(application);
            return Result.success();
        }
        return Result.failure();
    }

    @RequestMapping(value = "/student/getApplicationInfo", method = RequestMethod.GET)
    public Result<Object> getApplicationInfo(@RequestParam("token") String token) {
        Jedis jedis = new Jedis();
        String username = jedis.get(token);
        List<JSONObject> objList = new ArrayList<>();
        JSONObject res = new JSONObject();
        if (username != null) {
            List<Application> appList = applicationService.findApplicationsByStuId(username);
            for (int i = 0; i < appList.size(); i++) {
                JSONObject tmp = appList.get(i).toJsonObj();
                Course c = courseService.findCourseByCourseId(tmp.getString("courseId"));
                tmp.put("courseName", c.getCourseName());
                objList.add(tmp);
            }
            res.put("appInfo", objList);
            return Result.success(res);
        }
        return Result.failure();
    }

    @RequestMapping(value = "/student/dropApplication", method = RequestMethod.POST)
    public Result<Object> dropApplication(@RequestParam("token") String token, @RequestParam("courseId") String courseId) {
        Jedis jedis = new Jedis();
        String username = jedis.get(token);
        if (username != null) {
            applicationService.deleteApplicationByStuIdAndCourseId(username, courseId);
            return Result.success();
        }
        return Result.failure();
    }

    public boolean hasJoin(String[] time1, String[] time2) {
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
            if ((start_ts2 - end_ts1 >= 1) || (start_ts1 - end_ts2 >= 1)) {
                return false;
            }
            return true;
        }
        return false;
    }

}
