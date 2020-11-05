package pdfreport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import application.Myapp;
import data_read_write.DatareadN;

public class ExcelReport {

	public static void exportToExcel(String filepath, DatareadN d,
			String notes, String company) throws IOException,
			InvalidFormatException {

		Workbook workbook = new XSSFWorkbook(
				OPCPackage.open(new FileInputStream("Book1.xlsx"))); // or
																		// sample.xls

		Sheet sh = workbook.getSheetAt(0);
		String sheetName = sh.getSheetName();
		Sheet sh1 = workbook.getSheetAt(1);
		String sheetName1 = sh1.getSheetName();
		System.out.println("Sheet Name : " + sheetName + " : " + sheetName1);

		CellStyle cellStyle = workbook.createCellStyle();

		// create from 10th row

		List<String> flow, dp, dt,fpt;


		flow = d.getValuesOf("" + d.data.get("flow"));
		dp = d.getValuesOf("" + d.data.get("Dt"));
		dt = d.getValuesOf("" + d.data.get("Dp"));
		fpt = d.getValuesOf("" + d.data.get("ans"));
		
		
		//System.out.println(dd + " \n" + psd);
		//Map<String, Double> points = d.getDistributionChart(flow, dp, 40);
		List<String> allkeys = new ArrayList<String>();
		//allkeys.addAll(points.keySet());

		int row = 11, row1 = 1;
		

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String dateStr = dateFormat.format(cal.getTime());
		String x = d.data.get("samplediameter") + "";
		double mp = Myapp.getRound(d.data.get("bpressure") + "", 2);
		
		sh.getRow(0).createCell(2).setCellValue("" + dateStr);
		sh.getRow(0).createCell(0).setCellValue(company);

		sh.getRow(2).createCell(2).setCellValue("0078");
		sh.getRow(2).createCell(6).setCellValue(d.data.get("testdate") + "");
		sh.getRow(3).createCell(2).setCellValue(d.data.get("sample") + "");
		sh.getRow(3).createCell(6).setCellValue(d.data.get("testtime") + "");
		sh.getRow(4).createCell(2).setCellValue("" + x);

		sh.getRow(4).createCell(6).setCellValue(d.data.get("duration") + "");
		sh.getRow(5).createCell(2).setCellValue(d.data.get("thikness") + "");
		sh.getRow(5).createCell(6).setCellValue("Analyst");
		sh.getRow(6).createCell(2).setCellValue(d.data.get("fluidname") + "");
		sh.getRow(6).createCell(6).setCellValue(notes);

		// summary data

		sh.createRow(9).createCell(0).setCellValue("" + mp);
		

		/*
		 * sh1.createRow(row).createCell(0).setCellValue("Pressure");
		 * sh.getRow(row).createCell(1).setCellValue("Diameter");
		 * sh.getRow(row).createCell(2).setCellValue("Wet Flow");
		 * sh.getRow(row).createCell(3).setCellValue("Dry FLow");
		 * sh.getRow(row).createCell(4).setCellValue("Incr FF");
		 * sh.getRow(row).createCell(5).setCellValue("Cumm. FF");
		 * sh.getRow(row).createCell(6).setCellValue("PSD");
		 * sh.getRow(row).createCell(7).setCellValue("AVg. Dia");
		 * sh.getRow(row).createCell(8).setCellValue("Half-Dry flow");
		 */

		/*
		 * 
		 * p[0]=""+0;
		 * 
		 * wf[0]=""; df[0]=""+0; ff[0]=""+0; cff[0]=""+0; ad[0]="";
		 * 
		 * hf[0]=""+0;
		 */
		//row++;
		for (int i = 0; i < flow.size(); i++) {
			try {

				row++;
				sh.createRow(row).createCell(0)
						.setCellValue(Myapp.getRound(flow.get(i), 2));
				sh.getRow(row).createCell(1)
						.setCellValue(Myapp.getRound(dp.get(i), 2));
				sh.getRow(row).createCell(2)
						.setCellValue(Myapp.getRound(dt.get(i), 2));
				sh.getRow(row).createCell(3)
				.setCellValue(Myapp.getRound(fpt.get(i), 2));
		
			

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		Name rangeCell = workbook.getName("flo");
		String reference = sheetName + "!$A$" + (13) + ":$A$" + (row + 1);
		rangeCell.setRefersToFormula(reference);
		rangeCell = workbook.getName("dp");
		reference = sheetName + "!$B$" + (13) + ":$B$" + (row + 1);
		rangeCell.setRefersToFormula(reference);

		rangeCell = workbook.getName("dtt");
		reference = sheetName + "!$C$" + (13) + ":$C$" + (row + 1);
		rangeCell.setRefersToFormula(reference);		
		rangeCell = workbook.getName("fptt");
		reference = sheetName + "!$D$" + (13) + ":$D$" + (row + 1);
		rangeCell.setRefersToFormula(reference);
		
		FileOutputStream f = new FileOutputStream(filepath);
		workbook.write(f);
		f.close();

		System.out.println("Number Of Sheets" + workbook.getNumberOfSheets());
		Sheet s = workbook.getSheetAt(0);
		System.out.println("Number Of Rows:" + s.getLastRowNum());
	}

}
