package com.fudan.xk.controller;

import com.alibaba.fastjson.JSONObject;
import com.fudan.xk.base.result.Result;
import com.fudan.xk.model.*;
import com.fudan.xk.service.*;
import com.fudan.xk.util.ExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Auther: 99615
 * @Date: 2019/12/7 19:47
 * @Description:
 */
@CrossOrigin(origins = "*", allowCredentials = "true")
@RestController
public class UploadController {
    private static Logger log = LoggerFactory.getLogger(UploadController.class);
//    private SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ExamService examService;
    @Autowired
    private TimeSlotService timeSlotService;


    @RequestMapping(value = "/admin/import", method = RequestMethod.POST)
    public Result<Object> importData(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        try {
            // Get the file and save it somewhere
            log.info("file uploaded: " + file.getOriginalFilename());
            byte[] bytes = file.getBytes();
            String UPLOADED_FOLDER = "D:\\vueUpload\\";
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        if (filename.equals("user.xlsx")) {
            List<User> userList = ExcelUtils.excelToUserList(inputStream);
            userService.addUsers(userList);
        }
        if (filename.equals("student.xlsx")) {
            List<Student> studentList = ExcelUtils.excelToStudentList(inputStream);
            studentService.addStudents(studentList);
        }
        if (filename.equals("teacher.xlsx")) {
            List<Teacher> teacherList = ExcelUtils.excelToTeacherList(inputStream);
            teacherService.addTeachers(teacherList);
        }
        if (filename.equals("course.xlsx")) {
            List<Course> courseList = ExcelUtils.excelToCourseList(inputStream);
            courseService.addCourses(courseList);
        }
        if (filename.equals("time_slot.xlsx")) {
            List<TimeSlot> timeSlotList = ExcelUtils.excelToTsList(inputStream);
            timeSlotService.addTimeSlot(timeSlotList);
        }
        if (filename.equals("classroom.xlsx")) {
            List<Classroom> classroomList = ExcelUtils.excelToCrList(inputStream);
            classroomService.addClassrooms(classroomList);
        }
        if (filename.equals("exam.xlsx")) {
            List<Exam> examList = ExcelUtils.excelToExamList(inputStream);
            examService.addExams(examList);
        }
        if (filename.equals("course_cr.xlsx")) {
            Map<Course, Set<Classroom>> course_crList = new HashMap<>();
//            Map<Classroom, Set<Course>> cr_courseList = new HashMap<>();
            Map<String, List<String>> res = ExcelUtils.parsingJoinTable(inputStream);
            List<String> courseIdList = res.get("course_id");
            List<String> crIdList = res.get("cr_id");
            for (int i = 0; i < courseIdList.size(); i++) {
                String crId = crIdList.get(i);
                String courseId = courseIdList.get(i);
                Classroom classroom = classroomService.findClassroomById(crId);
                Course course = courseService.findCourseByCourseId(courseId);
                Set<Classroom> crSet = course_crList.get(course);
//                Set<Course> courseSet = cr_courseList.get(classroom);
                if (classroom != null && course != null) {
                    if (crSet != null) {
                        crSet.add(classroom);
                        course_crList.put(course, crSet);

//                        courseSet.add(course);
//                        cr_courseList.put(classroom, courseSet);
                    } else {
                        Set<Classroom> tmp1 = new HashSet<>();
                        tmp1.add(classroom);
                        course_crList.put(course, tmp1);

//                        Set<Course> tmp2 = new HashSet<>();
//                        tmp2.add(course);
//                        cr_courseList.put(classroom, tmp2);
                    }
                }
            }
            for (Map.Entry entry : course_crList.entrySet()) {
                Course course = (Course) entry.getKey();
                Set<Classroom> classroomSet = (Set<Classroom>) entry.getValue();
                course.setClassrooms(classroomSet);
                courseService.saveCourse(course);
            }
//            for (Map.Entry entry : cr_courseList.entrySet()){
//                Classroom classroom = (Classroom) entry.getKey();
//                Set<Course> courseSet = (Set<Course>) entry.getValue();
//                classroom.setCourses(courseSet);
//            }
        }
//        if (filename.equals("course_exam.xlsx")) {
//            Map<String, List<String>> res = ExcelUtils.parsingJoinTable(inputStream);
//            List<String> courseIdList = res.get("course_id");
//            List<String> examIdList = res.get("exam_id");
//
//            for (int i = 0; i < courseIdList.size(); i++) {
//                String examId = examIdList.get(i);
//                String courseId = courseIdList.get(i);
//                Exam exam = examService.findExamByExamId(examId);
//                Course course = courseService.findCourseByCourseId(courseId);
//                if (exam != null && course != null){
//                    course.setExam(exam);
//                    courseService.saveCourse(course);
//                    exam.setCourse(course);
////                    examService.saveExam(exam);
//                }
//            }
//
//        }
        if (filename.equals("course_ts.xlsx")) {
            Map<Course, Set<TimeSlot>> course_tsList = new HashMap<>();
//            Map<TimeSlot, Set<Course>> ts_courseList = new HashMap<>();
            Map<String, List<String>> res = ExcelUtils.parsingJoinTable(inputStream);
            List<String> courseIdList = res.get("course_id");
            List<String> tsIdList = res.get("ts_id");
//            System.out.println(courseIdList.toString());
//            System.out.println(tsIdList.toString());
            for (int i = 0; i < courseIdList.size(); i++) {
                String tsId = tsIdList.get(i);
                String courseId = courseIdList.get(i);
                TimeSlot timeSlot = timeSlotService.findTimeSlotByTsId(tsId);
                Course course = courseService.findCourseByCourseId(courseId);
                Set<TimeSlot> tsSet = course_tsList.get(course);
//                Set<Course> courseSet = ts_courseList.get(timeSlot);
                if (timeSlot != null && course != null) {
                    if (tsSet != null) {
                        System.out.println("add set");
                        tsSet.add(timeSlot);
                        course_tsList.put(course, tsSet);
//                        courseSet.add(course);
//                        ts_courseList.put(timeSlot, courseSet);
                    } else {
                        System.out.println("new set");
                        Set<TimeSlot> tmp1 = new HashSet<>();
                        tmp1.add(timeSlot);
                        course_tsList.put(course, tmp1);
//                        Set<Course> tmp2 = new HashSet<>();
//                        tmp2.add(course);
//                        ts_courseList.put(timeSlot, tmp2);
                    }
                }
            }
            for (Map.Entry entry : course_tsList.entrySet()) {
                Course course = (Course) entry.getKey();
                Set<TimeSlot> timeSlotSet = (Set<TimeSlot>) entry.getValue();
                course.setTimeSlots(timeSlotSet);
                courseService.saveCourse(course);
            }
//            for (Map.Entry entry : ts_courseList.entrySet()){
//                TimeSlot timeSlot = (TimeSlot) entry.getKey();
//                Set<Course> courseSet = (Set<Course>) entry.getValue();
//                timeSlot.setCourses(courseSet);
//            }
        }
        if (filename.equals("tea_course.xlsx")) {
            Map<Course, Set<Teacher>> course_tcList = new HashMap<>();
//            Map<Teacher, Set<Course>> tc_courseList = new HashMap<>();
            Map<String, List<String>> res = ExcelUtils.parsingJoinTable(inputStream);
            List<String> courseIdList = res.get("course_id");
            List<String> tcIdList = res.get("teacher_id");
            System.out.println("courseList Size : " + courseIdList.size());
            System.out.println("teacherList Size : " + tcIdList.size());
            System.out.println(tcIdList.toString());
            System.out.println(courseIdList.toString());
            for (int i = 0; i < courseIdList.size(); i++) {
                String tcId = tcIdList.get(i);
                String courseId = courseIdList.get(i);
                Teacher teacher = teacherService.findTeacherById(tcId);
                Course course = courseService.findCourseByCourseId(courseId);
                Set<Teacher> tcSet = course_tcList.get(course);
//                Set<Course> courseSet = tc_courseList.get(teacher);
                if (teacher != null && course != null) {
                    if (tcSet != null) {
                        tcSet.add(teacher);
                        course_tcList.put(course, tcSet);
//                        courseSet.add(course);
//                        tc_courseList.put(teacher, courseSet);
                    } else {
                        Set<Teacher> tmp1 = new HashSet<>();
                        tmp1.add(teacher);
                        course_tcList.put(course, tmp1);
//
//                        Set<Course> tmp2 = new HashSet<>();
//                        tmp2.add(course);
//                        tc_courseList.put(teacher, tmp2);
                    }
                }
            }
            for (Map.Entry entry : course_tcList.entrySet()) {
                Course course = (Course) entry.getKey();
                Set<Teacher> teacherSet = (Set<Teacher>) entry.getValue();
                course.setTeachers(teacherSet);
                courseService.saveCourse(course);
            }
//            for (Map.Entry entry : tc_courseList.entrySet()){
//                Teacher teacher = (Teacher) entry.getKey();
//                Set<Course> courseSet = (Set<Course>) entry.getValue();
//                teacher.setTeachCourses(courseSet);
//                teacherService.saveTeacher(teacher);
//            }
        }

        //检查原始数据中的冲突
        //教室冲突，一个教室的课程有相同的TimeSlot
        return Result.success();
    }

    @RequestMapping(value = "/admin/checkConflict", method = RequestMethod.GET)
    public Result<Object> checkConflict(@RequestParam("token") String token) {
        Jedis jedis = new Jedis();
        String username = jedis.get(token);
        JSONObject res = new JSONObject();
        List<String> conflictTeacherName = new ArrayList<>();
        List<String> conflictCrId = new ArrayList<>();
        res.put("ClassroomHasConflict", "false");
        res.put("TeacherHasConflict", "false");
        if (username != null) {
            List<Classroom> classroomList = classroomService.findAll();
            List<Teacher> teacherList = teacherService.findAll();
            for (Classroom classroom : classroomList) {
                boolean flag = classroom.hasConflict();
                if (flag) {
                    res.put("ClassroomHasConflict", "true");
                    conflictCrId.add(classroom.getCrId());
                }
            }
            res.put("ConflictClassroom", conflictCrId);
            for (Teacher teacher : teacherList) {
                boolean flag = teacher.hasConflict();
                if (flag) {
                    res.put("TeacherHasConflict", "true");
                    conflictTeacherName.add(teacher.getTeacherName());
                }
            }
            res.put("ConflictTeacher", conflictTeacherName);
        }

        return Result.success(res);
    }


}
