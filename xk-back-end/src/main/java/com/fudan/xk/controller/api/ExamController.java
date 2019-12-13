package com.fudan.xk.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.fudan.xk.base.controller.BaseApiController;
import com.fudan.xk.base.result.Result;
import com.fudan.xk.model.*;
import com.fudan.xk.service.*;
import com.fudan.xk.util.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Auther: 99615
 * @Date: 2019/12/12 11:13
 * @Description:
 */
@RestController
public class ExamController extends BaseApiController {
    @Autowired
    ExamService examService;

    @Autowired
    CourseService courseService;

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    ExamResultService examResultService;

    @RequestMapping(value = "/teacher/importResult", method = RequestMethod.POST)
    public Result<Object> importExamResult(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            String UPLOADED_FOLDER = "D:\\vueUpload\\examResult\\";
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        String[] arr = filename.split("_");
        String courseId = ExcelUtils.getFileNameNoEx(arr[2]);
        List<ExamResult> erList = ExcelUtils.excelToExamResultList(inputStream, courseId);
        examResultService.addExamResult(erList);

        return Result.failure();
    }
    @RequestMapping(value = "/teacher/getExamInfo", method = RequestMethod.GET)
    public Result<Object> getExamInfoByTeacher(@RequestParam("token") String token){
        List<JSONObject> objList = new ArrayList<>();
        Jedis jedis = new Jedis();
        String username = jedis.get(token);
        if (username != null) {
            Teacher teacher = teacherService.findTeacherById(username);
            Set<Course> teachCourses = teacher.getTeachCourses();
            for (Course c : teachCourses){
                Exam exam = examService.findExamByCourseId(c.getCourseId());
                objList.add(exam.toJsonObj());
            }
            JSONObject res = new JSONObject();
            res.put("examInfo", objList);
            return Result.success(res);
        }

        return Result.failure();
    }

    @RequestMapping(value = "/student/getExamInfo", method = RequestMethod.GET)
    public Result<Object> getExamInfo(@RequestParam("token") String token) {
        List<JSONObject> objList = new ArrayList<>();
        Jedis jedis = new Jedis();
        String username = jedis.get(token);
        if (username != null) {
            Student student = studentService.findStudentById(username);
            Set<Course> selectedCourse = student.getSelectedCourses();
            for (Course c : selectedCourse){
                Exam exam = examService.findExamByCourseId(c.getCourseId());
                objList.add(exam.toJsonObj());
            }
            JSONObject res = new JSONObject();
            res.put("examInfo", objList);
            return Result.success(res);
        }

        return Result.failure();
    }

    @RequestMapping(value = "/student/getExamResult", method = RequestMethod.GET)
    public Result<Object> getExamResult(@RequestParam("token") String token) {
        List<JSONObject> objList = new ArrayList<>();
        Jedis jedis = new Jedis();
        String username = jedis.get(token);
        if (username != null) {
            Student student = studentService.findStudentById(username);
            Set<Course> selectedCourse = student.getSelectedCourses();
            for (Course c : selectedCourse){
                ExamResult er = examResultService.findExamResult(username, c.getCourseId());
                JSONObject tmp = er.toJsonObj();
                tmp.put("courseName", c.getCourseName());
                objList.add(tmp);
            }
            JSONObject res = new JSONObject();
            res.put("examResult", objList);
            return Result.success(res);
        }

        return Result.failure();
    }
}
