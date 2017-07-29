package enabler.office;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utils.FileUtil;
import utils.JSONUtil;
import utils.StringUtil;
import utils.XlsUtil;

public class ExcelEnabler {
	/**
	 * 
	 * @param path
	 * @param content 数据格式：{r:{c:v}}
	 * @throws IOException 
	 */
	public void write(String filepath,String content) throws IOException{
		ExcelData data=new ExcelData(content);
		FileUtil.createFile(filepath,true);
		String fileType = filepath.substring(filepath.lastIndexOf(".") + 1, filepath.length());
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook();
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook();
		} else {
			throw new IllegalArgumentException("您的文档格式不正确!");
		}
		Sheet sheet1 = (Sheet) wb.createSheet("sheet1");
		int startRow=0;
		//名字就是序号
		Set<String> rowNames=data.getRowName();
		for (String rowName : rowNames) {
			int rowNum=Integer.parseInt(rowName);
			Row row = (Row) sheet1.createRow(rowNum);
			Set<String> colNames=data.getColName(rowNum);
			for (String colName : colNames) {
				int colNum=Integer.parseInt(colName);
				Cell cell = row.createCell(colNum);
				String value=data.getRowCol(rowNum, colNum);
				cell.setCellValue(value);
			}
		}
		
		
//		for (int i = 0; i < data.getRowCount(); i++) {
//			Row row = (Row) sheet1.createRow(i+startRow);
//			List<String> l=strs.get(i);
//			Map<String,String> col=data.getRow(index);
//			for (int j = 0; j < l.size(); j++) {
//				Cell cell = row.createCell(j);
//				cell.setCellValue(l.get(j));
//			}
//		}
		try {
			OutputStream stream;
			stream = new FileOutputStream(filepath);
			wb.write(stream);
			stream.close();
			wb.close();
		} catch (Exception e) {
			new RuntimeException(e);
		}
	}
	public String read(String filePath) throws IOException{
		String fileType = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
		InputStream stream = new FileInputStream(filePath);
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook(stream);
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook(stream);
		} else {
//			wb.close();
			System.out.println("您输入的excel格式不正确");
			return "";
		}
		Sheet sheet1 = wb.getSheetAt(0);
		System.out.println("最后一行序号:"+sheet1.getLastRowNum());
		Map<String,Map<String,String>> map=new HashMap<>();
		for (Row row : sheet1) {
			Map<String,String> m=new HashMap<>();
			int rowNum=row.getRowNum();
			for (Cell cell : row) {
				int colNum=cell.getColumnIndex();
				String value=getCellValue(cell);
//				String value=cell.getStringCellValue();
				m.put(String.valueOf(colNum), value);
//				System.out.print(cell.getStringCellValue() + "  ");
			}
			map.put(String.valueOf(rowNum), m);
		}
//		int rowSum=sheet1.getLastRowNum();
//		for (int i = 0; i < rowSum; i++) {
//			Row row=sheet1.getRow(i);
//			if(row==null) continue;
//			Map<String,String> m=new HashMap<>();
//			int colSum=row.getLastCellNum();
//			for (int j = 0; j < colSum; j++) {
//				Cell cell=row.getCell(j);
//				String value=cell.getStringCellValue();
//				if(!StringUtil.isBlank(value)){
//					m.put(String.valueOf(j), value);
//				}
//			}
//			if(m.size()>0){
//				map.put(String.valueOf(i), m);
//			}
//		}
		if(wb!=null){
			wb.close();
		}
		String rs=JSONUtil.stringify(map);
		System.out.println("读取的内容为："+rs);
		return rs;
	}
	/** 
     * 根据excel单元格类型获取excel单元格值 
     * @param cell 
     * @return 
     */  
    private String getCellValue(Cell cell) {  
        String cellvalue = "";  
        if (cell != null) {  
            // 判断当前Cell的Type  
            switch (cell.getCellType()) {  
            // 如果当前Cell的Type为NUMERIC  
            case HSSFCell.CELL_TYPE_NUMERIC: {  
                short format = cell.getCellStyle().getDataFormat();  
                if(format == 14 || format == 31 || format == 57 || format == 58){   //excel中的时间格式  
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    
                    double value = cell.getNumericCellValue();    
                    Date date = DateUtil.getJavaDate(value);    
                    cellvalue = sdf.format(date);    
                }  
                // 判断当前的cell是否为Date  
                else if (HSSFDateUtil.isCellDateFormatted(cell)) {  //先注释日期类型的转换，在实际测试中发现HSSFDateUtil.isCellDateFormatted(cell)只识别2014/02/02这种格式。  
                    // 如果是Date类型则，取得该Cell的Date值           // 对2014-02-02格式识别不出是日期格式  
                    Date date = cell.getDateCellValue();  
                    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");  
                    cellvalue= formater.format(date);  
                } else { // 如果是纯数字  
                    // 取得当前Cell的数值  
                    cellvalue = NumberToTextConverter.toText(cell.getNumericCellValue());   
                      
                }  
                break;  
            }  
            // 如果当前Cell的Type为STRIN  
            case HSSFCell.CELL_TYPE_STRING:  
                // 取得当前的Cell字符串  
                cellvalue = cell.getStringCellValue().replaceAll("'", "''");  
                break;  
            case  HSSFCell.CELL_TYPE_BLANK:  
                cellvalue = null;  
                break;  
            // 默认的Cell值  
            default:{  
                cellvalue = " ";  
            }  
            }  
        } else {  
            cellvalue = "";  
        }  
        return cellvalue;  
    }  
	public static void main(String[] args) throws IOException { 
		ExcelEnabler enabler=new ExcelEnabler();
		String filePath="e:/短信汇总.xlsx";
		String json="{\"1\":{\"2\":\"ff\"}}";
//		enabler.write("e:/excelenabler1.xls", json);
		System.out.println(enabler.read(filePath));
//		XlsUtil.readTest(filePath);
	}
}
