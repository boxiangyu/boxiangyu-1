package com.jason.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	/**
     * ��ȡExcel�ļ�������
     * @param inputStream excel�ļ�����InputStream����ʽ����
     * @param sheetName sheet����
     * @return ��List����excel������
	 * @throws FileNotFoundException 
     */
    public static List<Map<String, String>> readExcel(String filePath, String sheetName) throws FileNotFoundException {
    	File file = new File(filePath);
		FileInputStream fileInputStream = new FileInputStream(file);

        //���幤����
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(fileInputStream);
        } catch (Exception e) {
            System.out.println("Excel data file cannot be found!");
        }

        //���幤����
        XSSFSheet xssfSheet;
        if (sheetName.equals("")) {
            // Ĭ��ȡ��һ���ӱ�
            xssfSheet = xssfWorkbook.getSheetAt(0);
        } else {
            xssfSheet = xssfWorkbook.getSheet(sheetName);
        }

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        //������
        //Ĭ�ϵ�һ��Ϊ�����У�index = 0
        XSSFRow titleRow = xssfSheet.getRow(0);

        //ѭ��ȡÿ�е�����
        for (int rowIndex = 1; rowIndex < xssfSheet.getPhysicalNumberOfRows(); rowIndex++) {
            XSSFRow xssfRow = xssfSheet.getRow(rowIndex);
            if (xssfRow == null) {
                continue;
            }

            Map<String, String> map = new LinkedHashMap<String, String>();
            //ѭ��ȡÿ����Ԫ��(cell)������
            for (int cellIndex = 0; cellIndex < xssfRow.getPhysicalNumberOfCells(); cellIndex++) {
                XSSFCell titleCell = titleRow.getCell(cellIndex);
                XSSFCell xssfCell = xssfRow.getCell(cellIndex);
                map.put(getString(titleCell),getString(xssfCell));
            }
            list.add(map);
        }
        return list;
    }

    /**
     * �ѵ�Ԫ�������תΪ�ַ���
     * @param xssfCell ��Ԫ��
     * @return �ַ���
     */
    public static String getString(XSSFCell xssfCell) {
        if (xssfCell == null) {
            return "";
        }
        if (xssfCell.getCellTypeEnum() == CellType.NUMERIC) {
            return String.valueOf(xssfCell.getNumericCellValue());
        } else if (xssfCell.getCellTypeEnum() == CellType.BOOLEAN) {
            return String.valueOf(xssfCell.getBooleanCellValue());
        } else {
            return xssfCell.getStringCellValue();
        }
    }

    /**
     * ������д��Excel
     * @param list ����Ҫд�����ݣ��˴���һ��List����Ϊ�����Ȱ�Ҫд�����ݷŵ�һ��list��
     * @param outputStream ���������Ҫд���Excel�ϣ�׼��������д����
     */
    public static void writeExcel(List<List> list, OutputStream outputStream) {
        //����������
        XSSFWorkbook xssfWorkbook = null;
        xssfWorkbook = new XSSFWorkbook();

        //����������
        XSSFSheet xssfSheet;
        xssfSheet = xssfWorkbook.createSheet();

        //������
        XSSFRow xssfRow;

        //�����У�����Ԫ��Cell
        XSSFCell xssfCell;

        //��List���������д��excel��
        for (int i=0;i<list.size();i++) {
            //�ӵ�һ�п�ʼд��
            xssfRow = xssfSheet.createRow(i);
            //����ÿ����Ԫ��Cell�����е�����
            List sub_list =list.get(i);
            for (int j=0;j<sub_list.size();j++) {
                xssfCell = xssfRow.createCell(j); //������Ԫ��
                xssfCell.setCellValue((String)sub_list.get(j)); //���õ�Ԫ������
            }
        }

        //�������д��excel
        try {
            xssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

}
