package com.fudan.xk;

import com.fudan.xk.model.Teacher;
import com.fudan.xk.model.TimeSlot;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: 99615
 * @Date: 2019/12/9 01:20
 * @Description:
 */
public class ExcelUtilTest {
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


    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }
    public static void main(String[] args) {
        Date date = new Date();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        System.out.println(simpleDateFormat.format(date));
    }
}
