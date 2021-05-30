package com.achilles.wild.server.tool.file.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 生成Excel
 * @author Pang
 *
 */
public class ExcelWriteUtil {

    @SuppressWarnings("resource")
	public static boolean write(String outPath,List<Map<String,Object>> dataList){  
        String fileType = outPath.substring(outPath.lastIndexOf(".") + 1, outPath.length());  
        org.apache.poi.ss.usermodel.Workbook wb = null;   // 创建工作文档对象  
        if (fileType.equals("xls")) {  
            wb = new HSSFWorkbook();  
        } else if (fileType.equals("xlsx")) {  
            wb = new XSSFWorkbook();  
        } 
        Sheet sheet = (Sheet) wb.createSheet("sheet1");  // 创建sheet对象  
        for (int i = 0; i <= dataList.size(); i++) {   // 循环写入行数据,多循环一次，因为第一行需要字段名
            Row row = (Row) sheet.createRow(i); 
            Map<String,Object> params = null;
            if (i>0) {
            	params = dataList.get(i-1);
			}else{
				params = dataList.get(i);
			}
            int j = 0;
            for (String key : params.keySet()) {//生成列数据
            	Cell cell = row.createCell(j);  
            	j++;
            	Object value = params.get(key);
            	if (value==null||"".equals(value)) {
					continue;
				}
               	if (i==0) {
               		cell.setCellValue(key); 
    			}else{
    				cell.setCellValue(value.toString());  
    			}
			}
        }  
        // 创建文件流  
        OutputStream stream = null;
		try {
			stream = new FileOutputStream(outPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
        // 写入数据  
        try {
			wb.write(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}  
        // 关闭文件流  
        try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
        return true;  
    }  
	
    
    
    
    
    
//	 public static void createExcel(OutputStream os) throws WriteException,IOException{
//	        //创建工作薄
//	        WritableWorkbook workbook = Workbook.createWorkbook(new File("C://Users//Administrator//Desktop//JAVA生成EXCEL测试.xls"));
////	        WritableWorkbook workbook = jxl.Workbook.createWorkbook(new File("C://Users//Administrator//Desktop//JAVA生成EXCEL测试.xlsx"));
//	        //创建新的一页
//	        WritableSheet sheet = workbook.createSheet("First Sheet",0);
//	        //创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
//	        Label xuexiao = new Label(0,0,"学校");
//	        sheet.addCell(xuexiao);
//	        Label zhuanye = new Label(1,0,"专业");
//	        sheet.addCell(zhuanye);
//	        Label jingzhengli = new Label(2,0,"专业竞争力");
//	        sheet.addCell(jingzhengli);
//	        
//	        Label qinghua = new Label(0,1,"清华大学");
//	        sheet.addCell(qinghua);
//	        Label jisuanji = new Label(1,1,"计算机专业");
//	        sheet.addCell(jisuanji);
//	        Label gao = new Label(2,1,"高");
//	        sheet.addCell(gao);
//	        
//	        Label beida = new Label(0,2,"北京大学");
//	        sheet.addCell(beida);
//	        Label falv = new Label(1,2,"法律专业");
//	        sheet.addCell(falv);
//	        Label zhong = new Label(2,2,"中");
//	        sheet.addCell(zhong);
//	        
//	        Label ligong = new Label(0,3,"北京理工大学");
//	        sheet.addCell(ligong);
//	        Label hangkong = new Label(1,3,"航空专业");
//	        sheet.addCell(hangkong);
//	        Label di = new Label(2,3,"低");
//	        sheet.addCell(di);
//	        
//	        //把创建的内容写入到输出流中，并关闭输出流
//	        workbook.write();
//	        workbook.close();
////	        os.close();
//	    }
}
