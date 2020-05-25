package cn.com.wind.wdp.util;

import cn.com.wind.wdp.bean.TranslationResult;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    public static void createExcel(String excel_name, String[] headList,
                                   List<TranslationResult> resultList) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow row = sheet.createRow(0);

        //添加文件头
        for (int i = 0; i < headList.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(headList[i]);
        }

        //添加数据
        for (int i = 0; i < resultList.size(); i++) {
            XSSFRow row_value = sheet.createRow(i + 1);
            TranslationResult translationResult = resultList.get(i);
            Class resultClass = translationResult.getClass();
            Field[] fields = resultClass.getDeclaredFields();

            for (int j = 0; j < fields.length; j++) {
                Field f = fields[j];
                f.setAccessible(true);
                XSSFCell cell = row_value.createCell(j);
                if (f.getGenericType().toString().equals("class java.lang.String")) {
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue((String) f.get(translationResult));
                }
                if (f.getGenericType().toString().equals("int")) {
                    cell.setCellType(CellType.NUMERIC);
                    cell.setCellValue((Integer) f.get(translationResult));
                }
            }

        }

        FileOutputStream fos = new FileOutputStream(excel_name);
        workbook.write(fos);
        fos.flush();
        fos.close();
    }

}
