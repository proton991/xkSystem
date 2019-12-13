package com.fudan.xk.util;

import com.fudan.xk.model.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.InputStream;
import java.util.*;


/**
 * @Auther: 崔欣宇
 * @Date: 2019/12/8 21:08
 * @Description:
 */
public class ExcelUtils {

    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    public static List<User> excelToUserList(InputStream inputStream) {
        List<User> list = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
            //工作表对象
            Sheet sheet = workbook.getSheetAt(0);
            //总行数
            int rowLength = sheet.getLastRowNum();
            //            System.out.println("总行数有多少行" + rowLength);
            //工作表的列
            Row row = sheet.getRow(0);
            //总列数
            int colLength = row.getLastCellNum();
            //            System.out.println("总列数有多少列" + colLength);
            //得到指定的单元格
            Cell cell = row.getCell(0);
            for (int i = 1; i <= rowLength; i++) {
                User user = new User();
                row = sheet.getRow(i);
                for (int j = 0; j < colLength; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
                        String data = cell.getStringCellValue();
                        data = data.trim();
                        if (j == 0) {
                            user.setUsername(data);
                        } else if (j == 1) {
                            user.setDbflag(data);
                        } else if (j == 2) {
                            user.setPassword(data);
                        } else if (j == 3) {
                            user.setRole(data);
                        }
                    }
                }
                list.add(user);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static List<Course> excelToCourseList(InputStream inputStream) {
        List<Course> list = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
            //工作表对象
            Sheet sheet = workbook.getSheetAt(0);
            //总行数
            int rowLength = sheet.getLastRowNum();
            System.out.println("总行数有多少行" + rowLength);
            //工作表的列
            Row row = sheet.getRow(0);
            //总列数
            int colLength = row.getLastCellNum();
            System.out.println("总列数有多少列" + colLength);
            //得到指定的单元格
            Cell cell = row.getCell(0);
            for (int i = 1; i <= rowLength; i++) {
                Course course = new Course();
                row = sheet.getRow(i);
                for (int j = 0; j < colLength; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
//                        String data = cell.getStringCellValue();
//                        data = data.trim();
//                        System.out.println(cell.getStringCellValue());
                        if (j == 0) {
                            course.setCourseId(cell.getStringCellValue());
                        } else if (j == 1) {
                            course.setCourseName(cell.getStringCellValue());
                        } else if (j == 2) {
                            course.setSelectedQuantity((int) cell.getNumericCellValue());
                        } else if (j == 3) {
                            course.setStockQuantity((int) cell.getNumericCellValue());
                        }
                    }
                }
                list.add(course);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static List<Exam> excelToExamList(InputStream inputStream) {
        List<Exam> list = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
            //工作表对象
            Sheet sheet = workbook.getSheetAt(0);
            //总行数
            int rowLength = sheet.getLastRowNum();
            //            System.out.println("总行数有多少行" + rowLength);
            //工作表的列
            Row row = sheet.getRow(0);
            //总列数
            int colLength = row.getLastCellNum();
            //            System.out.println("总列数有多少列" + colLength);
            //得到指定的单元格
            Cell cell = row.getCell(0);
            for (int i = 1; i <= rowLength; i++) {
                Exam exam = new Exam();
                row = sheet.getRow(i);
                for (int j = 0; j < colLength; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
                        String data = cell.getStringCellValue();
                        data = data.trim();
                        if (j == 0) {
                            exam.setExamId(data);
                        } else if (j == 1) {
                            exam.setBeginTime(data);
                        } else if (j == 2) {
                            exam.setCourseId(data);
                        } else if (j == 3) {
                            exam.setCourseName(data);
                        } else if (j == 4) {
                            exam.setEndTime(data);
                        } else if (j == 5) {
                            exam.setType(data);
                        }
                    }
                }
                list.add(exam);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static List<ExamResult> excelToExamResultList(InputStream inputStream, String courseId) {
        List<ExamResult> list = new ArrayList<>();
        Workbook workbook;
        try {
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
            //工作表对象
            Sheet sheet = workbook.getSheetAt(0);
            //总行数
            int rowLength = sheet.getLastRowNum();
            //            System.out.println("总行数有多少行" + rowLength);
            //工作表的列
            Row row = sheet.getRow(0);
            //总列数
            int colLength = row.getLastCellNum();
            //            System.out.println("总列数有多少列" + colLength);
            //得到指定的单元格
            Cell cell = row.getCell(0);
            for (int i = 1; i <= rowLength; i++) {
                ExamResult er = new ExamResult();
                row = sheet.getRow(i);
                for (int j = 0; j < colLength; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
                        if (j == 0) {
                            er.setStuId(cell.getStringCellValue());
                        } else if (j == 1) {
                            er.setScore(String.valueOf(cell.getNumericCellValue()));
                        }
                    }
                }
                er.setCourseId(courseId);
                list.add(er);
            }
        } catch (Exception e) {
        }
        return list;
    }
    public static List<Classroom> excelToCrList(InputStream inputStream) {
        List<Classroom> list = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
            //工作表对象
            Sheet sheet = workbook.getSheetAt(0);
            //总行数
            int rowLength = sheet.getLastRowNum();
            //            System.out.println("总行数有多少行" + rowLength);
            //工作表的列
            Row row = sheet.getRow(0);
            //总列数
            int colLength = row.getLastCellNum();
            //            System.out.println("总列数有多少列" + colLength);
            //得到指定的单元格
            Cell cell = row.getCell(0);
            for (int i = 1; i <= rowLength; i++) {
                Classroom classroom = new Classroom();
                row = sheet.getRow(i);
                for (int j = 0; j < colLength; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
                        if (j == 0) {
                            classroom.setCrId(cell.getStringCellValue());
                        } else if (j == 1) {
                            classroom.setCapacity((int) cell.getNumericCellValue());
                        }
                    }
                }
                list.add(classroom);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static List<TimeSlot> excelToTsList(InputStream inputStream) {
        List<TimeSlot> list = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
            //工作表对象
            Sheet sheet = workbook.getSheetAt(0);
            //总行数
            int rowLength = sheet.getLastRowNum();
            //            System.out.println("总行数有多少行" + rowLength);
            //工作表的列
            Row row = sheet.getRow(0);
            //总列数
            int colLength = row.getLastCellNum();
            //            System.out.println("总列数有多少列" + colLength);
            //得到指定的单元格
            Cell cell = row.getCell(0);
            for (int i = 1; i <= rowLength; i++) {
                TimeSlot timeSlot = new TimeSlot();
                row = sheet.getRow(i);
                for (int j = 0; j < colLength; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
                        String data = cell.getStringCellValue();
                        data = data.trim();
                        if (j == 0) {
                            timeSlot.setTsId(data);
                        } else if (j == 1) {
                            timeSlot.setBeginTime(data);
                        } else if (j == 2) {
                            timeSlot.setEndTime(data);
                        }
                    }
                }
                list.add(timeSlot);
            }
        } catch (Exception e) {
        }
        return list;
    }


    public static List<Student> excelToStudentList(InputStream inputStream) {
        List<Student> list = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
            //工作表对象
            Sheet sheet = workbook.getSheetAt(0);
            //总行数
            int rowLength = sheet.getLastRowNum();
            //            System.out.println("总行数有多少行" + rowLength);
            //工作表的列
            Row row = sheet.getRow(0);
            //总列数
            int colLength = row.getLastCellNum();
            //            System.out.println("总列数有多少列" + colLength);
            //得到指定的单元格
            Cell cell = row.getCell(0);
            for (int i = 1; i <= rowLength; i++) {
                Student student = new Student();
                row = sheet.getRow(i);
                for (int j = 0; j < colLength; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
                        String data = cell.getStringCellValue();
                        data = data.trim();
                        if (j == 0) {
                            student.setStuId(data);
                        } else if (j == 1) {
                            student.setStuName(data);
                        }
                    }
                }
                list.add(student);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static List<Teacher> excelToTeacherList(InputStream inputStream) {
        List<Teacher> list = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
            //工作表对象
            Sheet sheet = workbook.getSheetAt(0);
            //总行数
            int rowLength = sheet.getLastRowNum();
            //            System.out.println("总行数有多少行" + rowLength);
            //工作表的列
            Row row = sheet.getRow(0);
            //总列数
            int colLength = row.getLastCellNum();
            //            System.out.println("总列数有多少列" + colLength);
            //得到指定的单元格
            Cell cell = row.getCell(0);
            for (int i = 1; i <= rowLength; i++) {
                Teacher teacher = new Teacher();
                row = sheet.getRow(i);
                for (int j = 0; j < colLength; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
                        String data = cell.getStringCellValue();
                        data = data.trim();
                        if (j == 0) {
                            teacher.setTeacherId(data);
                        } else if (j == 1) {
                            teacher.setTeacherName(data);
                        }
                    }
                }
                list.add(teacher);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static Map<String, List<String>> parsingJoinTable(InputStream inputStream) {
        Map<String, List<String>> res = new HashMap<>();
        List<String> idList1 = new ArrayList<>();
        List<String> idList2 = new ArrayList<>();
        String[] headers = null;
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
            //工作表对象
            Sheet sheet = workbook.getSheetAt(0);

            //总行数
            int rowLength = sheet.getLastRowNum();
            //            System.out.println("总行数有多少行" + rowLength);
            //工作表的列
            Row row = sheet.getRow(0);
            //总列数
            int colLength = row.getLastCellNum();
            //            System.out.println("总列数有多少列" + colLength);

            //parsing header
            headers = new String[colLength];
            Row headRow = sheet.getRow(0);
            for (int i = 0; i < colLength; i++) {
                Cell cell = row.getCell(i);
                headers[i] = cell.getStringCellValue();
            }
            System.out.println(Arrays.toString(headers));

            //得到指定的单元格
            Cell cell = row.getCell(0);
            for (int i = 1; i <= rowLength; i++) {
                Teacher teacher = new Teacher();
                row = sheet.getRow(i);
                for (int j = 0; j < colLength; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
                        String data = cell.getStringCellValue();
                        if (j == 0) {
                            idList1.add(data);
                        } else if (j == 1) {
                            idList2.add(data);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        res.put(headers[0], idList1);
        res.put(headers[1], idList2);
        return res;
    }

}
