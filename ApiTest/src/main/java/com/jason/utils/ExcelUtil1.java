package com.jason.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil1 {
	/*
	 *读取excel文件
	 */
	public static Object[][] readExcelData(String filePath,int sheetId) throws  Exception{
		File file = new File(filePath);
		FileInputStream fileInputStream = new FileInputStream(file);
		
		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream); 
		XSSFSheet sheet = workbook.getSheetAt(sheetId);
		int numberrow = sheet.getPhysicalNumberOfRows();//计算出有效行数
		int numbercell = sheet.getRow(0).getLastCellNum();//计算出第一行的总列数
		
		Object[][] dttData = new Object[numberrow-1][numbercell];
		for(int i=1;i<numberrow;i++) {
			if(null==sheet.getRow(i)) {
				continue;
			}
			for(int j=0;j<numbercell;j++) {
				if(null==sheet.getRow(i).getCell(j)) {
					continue;
				}
				XSSFCell cell = sheet.getRow(i).getCell(j);
				cell.setCellType(CellType.STRING);
				dttData[i-1][j] = cell.getStringCellValue();
			}
		}
		return dttData;
	}
	
	/**
     * 把内容写入Excel
     * @param list 传入要写的内容，此处以一个List内容为例，先把要写的内容放到一个list中
     * @param  outputStream  把输出流怼到要写入的Excel上，准备往里面写数据
	 * @throws FileNotFoundException 
     */
    public static void writeExcel(List<List> list, String filePath) throws FileNotFoundException {
        OutputStream outputStream = new FileOutputStream(new File(filePath));
    	
    	//创建工作簿
        XSSFWorkbook xssfWorkbook = null;
        xssfWorkbook = new XSSFWorkbook();

        //创建工作表
        XSSFSheet xssfSheet;
        xssfSheet = xssfWorkbook.createSheet("testResult");

        //创建行
        XSSFRow xssfRow;

        //创建列，即单元格Cell
        XSSFCell xssfCell;

        //把List里面的数据写到excel中
        /*for (int i=0;i<list.size();i++) {
            //从第一行开始写入
            xssfRow = xssfSheet.createRow(i);
            //创建每个单元格Cell，即列的数据
            List sub_list =list.get(i);
            for (int j=0;j<sub_list.size();j++) {
                xssfCell = xssfRow.createCell(j); //创建单元格
                xssfCell.setCellValue((String)sub_list.get(j)); //设置单元格内容
            }
        }*/
        xssfRow = xssfSheet.createRow(0);//创建第一行
        //创建第一行的表头
       	xssfCell = xssfRow.createCell(0);
        xssfCell.setCellValue("caseNum");
        xssfCell = xssfRow.createCell(1);
        xssfCell.setCellValue("type");
        xssfCell = xssfRow.createCell(2);
        xssfCell.setCellValue("url");
        xssfCell = xssfRow.createCell(3);
        xssfCell.setCellValue("request");
        xssfCell = xssfRow.createCell(4);
        xssfCell.setCellValue("expectedResult");
        xssfCell = xssfRow.createCell(5);
        xssfCell.setCellValue("expectedCode");
        xssfCell = xssfRow.createCell(6);
        xssfCell.setCellValue("actualCode");
        xssfCell = xssfRow.createCell(7);
        xssfCell.setCellValue("response");
        xssfCell = xssfRow.createCell(8);
        xssfCell.setCellValue("result");
        
        //List<String> sub_list1 = list.get(0);
        List<List> sub_list2 = list.get(1);
        for(int i=1;i<=sub_list2.size();i++) {
        	xssfRow =xssfSheet.createRow(i);
        	List sub_sub_list =sub_list2.get(i-1);
        	for(int j=0;j<sub_sub_list.size();j++) {
        		xssfCell = xssfRow.createCell(j); //创建单元格
                xssfCell.setCellValue((String)sub_sub_list.get(j)); //设置单元格内容
        	}
        }
        
        //列宽自适应
        /*for(int i=0;i<xssfSheet.getRow(0).getLastCellNum();i++) {
        	xssfSheet.autoSizeColumn(i);
        	xssfSheet.setColumnWidth(i, xssfSheet.getColumnWidth(i)*11/10);
        }*/

        //用输出流写到excel
        try {
            xssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public static List<List> convertMapToList(Map map) {
        List<List> list = new ArrayList<List>();
        List<String> key_list = new LinkedList<String>();
        List<List> value_list = new ArrayList<List>();
        
        //通过entrySet()方法将map集合中的映射关系取出，这个映射关系就是Map.Entry类型
        Set<Entry<String,List>> set = map.entrySet();
        //将关系集合set进行迭代，存放到迭代器中
        Iterator<Entry<String,List>> iter1 = set.iterator();
        //对迭代器进行遍历，通过关系对象获取key，然后添加到key_list列表中
        while (iter1.hasNext()) {
        	String key= iter1.next().getKey();
            key_list.add(key);  
        }
        list.add(key_list);//把key_list添加到数组列表list中
        //list.add(value_list);

        Collection<List> value = map.values();
        Iterator<List> iter2 = value.iterator();
        while (iter2.hasNext()) {
            value_list.add(iter2.next());
        }
        list.add(value_list);
        return list;
    }
}
