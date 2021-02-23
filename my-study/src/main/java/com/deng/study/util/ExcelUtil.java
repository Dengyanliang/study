package com.deng.study.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/2/23 17:22
 */
@Slf4j
public class ExcelUtil {

    /**
     * 读取数据 cvs
     */
    public void readCSV() {
//        UserTest.setUserInfo();
        String charset = "GBK";
        String srcPath = "D:\\test";
        File path = new File(srcPath);
        if (path.exists() && path.isDirectory()) {
            for (File file : path.listFiles()) {
                log.info("fileName:" + file.getName());
                try (CSVReader csvReader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(new FileInputStream(file), charset))).build()) {
                    Iterator<String[]> iterator = csvReader.iterator();
                    iterator.next();
                    while (iterator.hasNext()) {
                        String[] ss = iterator.next();
//                        log.info("----->"+ss[0]);
                        // 添加数据
//                        this.addAnonymous(ss);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取数据 excel
     *
     * @throws Exception
     */
    public void readExcel() throws Exception {

        String srcPath = "/Users/yanliangdeng/老话题导入.xlsx";
        File path = new File(srcPath);

        //1.读取Excel文档对象

        Workbook workbook = this.getWorkbok(path);
        //2.Workbook（第一个表格）
        Sheet momentSheet = workbook.getSheetAt(0);
        //获得最后一行的行号
        int lastRowNum = momentSheet.getLastRowNum();
        for (int i = 1; i <= lastRowNum; i++) {//遍历每一行
            //3.获得要解析的行
            Row row = momentSheet.getRow(i);
            //4.获得每个单元格中的内容（String）

//            Iterator<Cell> iterator = row.cellIterator();
//            iterator.next();
//            while(iterator.hasNext()){
//                Cell cell = iterator.next();
//                log.info("value:"+cell.getStringCellValue());
//            }

//            Long momentId = this.addMoment(row);
            Sheet contentSheet = workbook.getSheetAt(1);
            for (int j = 1; j <= contentSheet.getLastRowNum(); j++) {//遍历每一行
                //3.获得要解析的行
                Row contentRow = contentSheet.getRow(j);
//                this.addComment(momentId, contentRow);
            }
        }
    }

    public static Workbook getWorkbok(File file) throws IOException {
        String EXCEL_XLS = "xls";
        String EXCEL_XLSX = "xlsx";
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if (file.getName().endsWith(EXCEL_XLS)) {     //Excel&nbsp;2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) {    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }
}
