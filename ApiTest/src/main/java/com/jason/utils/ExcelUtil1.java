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
	 *��ȡexcel�ļ�
	 */
	public static Object[][] readExcelData(String filePath,int sheetId) throws  Exception{
		File file = new File(filePath);
		FileInputStream fileInputStream = new FileInputStream(file);
		
		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream); 
		XSSFSheet sheet = workbook.getSheetAt(sheetId);
		int numberrow = sheet.getPhysicalNumberOfRows();//�������Ч����
		int numbercell = sheet.getRow(0).getLastCellNum();//�������һ�е�������
		
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
     * ������д��Excel
     * @param list ����Ҫд�����ݣ��˴���һ��List����Ϊ�����Ȱ�Ҫд�����ݷŵ�һ��list��
     * @param  outputStream  ���������Ҫд���Excel�ϣ�׼��������д����
	 * @throws FileNotFoundException 
     */
    public static void writeExcel(List<List> list, String filePath) throws FileNotFoundException {
        OutputStream outputStream = new FileOutputStream(new File(filePath));
    	
    	//����������
        XSSFWorkbook xssfWorkbook = null;
        xssfWorkbook = new XSSFWorkbook();

        //����������
        XSSFSheet xssfSheet;
        xssfSheet = xssfWorkbook.createSheet("testResult");

        //������
        XSSFRow xssfRow;

        //�����У�����Ԫ��Cell
        XSSFCell xssfCell;

        //��List���������д��excel��
        /*for (int i=0;i<list.size();i++) {
            //�ӵ�һ�п�ʼд��
            xssfRow = xssfSheet.createRow(i);
            //����ÿ����Ԫ��Cell�����е�����
            List sub_list =list.get(i);
            for (int j=0;j<sub_list.size();j++) {
                xssfCell = xssfRow.createCell(j); //������Ԫ��
                xssfCell.setCellValue((String)sub_list.get(j)); //���õ�Ԫ������
            }
        }*/
        xssfRow = xssfSheet.createRow(0);//������һ��
        //������һ�еı�ͷ
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
        		xssfCell = xssfRow.createCell(j); //������Ԫ��
                xssfCell.setCellValue((String)sub_sub_list.get(j)); //���õ�Ԫ������
        	}
        }
        
        //�п�����Ӧ
        /*for(int i=0;i<xssfSheet.getRow(0).getLastCellNum();i++) {
        	xssfSheet.autoSizeColumn(i);
        	xssfSheet.setColumnWidth(i, xssfSheet.getColumnWidth(i)*11/10);
        }*/

        //�������д��excel
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
        
        //ͨ��entrySet()������map�����е�ӳ���ϵȡ�������ӳ���ϵ����Map.Entry����
        Set<Entry<String,List>> set = map.entrySet();
        //����ϵ����set���е�������ŵ���������
        Iterator<Entry<String,List>> iter1 = set.iterator();
        //�Ե��������б�����ͨ����ϵ�����ȡkey��Ȼ����ӵ�key_list�б���
        while (iter1.hasNext()) {
        	String key= iter1.next().getKey();
            key_list.add(key);  
        }
        list.add(key_list);//��key_list��ӵ������б�list��
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
