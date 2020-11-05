package data_read_write;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.MathUtils;

import application.Myapp;

//for read csv file
public class DatareadN {

	public Map<String, Object> data;
	public String filename;
	public File file;

	public DatareadN() {

		data = new HashMap<String, Object>();
	}
	public boolean updatetoUploadedFile() {

		try {

			CsvWriter cs = new CsvWriter();
			cs.wtirefile(file.getAbsolutePath());
			cs.firstLine(data.get("testname") + "");
			cs.newLine("upload","yes");
			List<String> ss = new ArrayList<String>(data.keySet());

			for (int i = 0; i < data.size(); i++) {
				cs.newLine(ss.get(i), data.get(ss.get(i)) + "");
			}

			cs.closefile();
			System.out.println("csv Created");

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	public String getRound(Double r, int round) {

		if (round == 2) {
			r = (double) Math.round(r * 100) / 100;
		} else if (round == 3) {
			r = (double) Math.round(r * 1000) / 1000;

		} else {
			r = (double) Math.round(r * 10000) / 10000;

		}

		return r + "";

	}

	public DatareadN(Map<String, Object> data, String name) {
		filename = name;
		this.data = data;
	}

	public boolean createFile() {

		try {

			CsvWriter cs = new CsvWriter();
			cs.wtirefile(filename);
			cs.firstLine(data.get("testname") + "");
			List<String> ss = new ArrayList<String>(data.keySet());

			for (int i = 0; i < data.size(); i++) {
				cs.newLine(ss.get(i), data.get(ss.get(i)) + "");
			}

			cs.closefile();
			System.out.println("csv Created");

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public void fileRead(File file) {

		this.file=file;
		data.clear();
		filename = stripExtension(file.getName());
		String line = "";

		int ln = 0;
		BufferedReader br = null;
		FileReader fis=null;
		try {
			fis=new FileReader(file);
			br = new BufferedReader(fis);
			while ((line = br.readLine()) != null) {

				if (ln > 0) {

					if(line.contains(",,")||line.contains(", ,"))
					{
						data=null;
						break;
					}
					String key = line.substring(0, line.indexOf(','));

					String value = line.substring(line.indexOf(',') + 1,
							line.length());
					data.put(key, value);

				}
				ln++;

			}
			br.close();
			fis.close();
			
		} catch (Exception e) {
			try {
				br.close();

				fis.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("datareadN : error 22"+filename);
			data=null;
		}

	}

	public List<String> getValuesOf(String s) {
		try {
			String[] ary = s.split(",");
			List<String> dtemp = Arrays.asList(ary);

			return dtemp;
		} catch (Exception e) {
			return null;
		}
	}

	static String stripExtension(String str) {
		// Handle null case specially.

		if (str == null)
			return null;

		// Get position of last '.'.

		int pos = str.lastIndexOf(".");

		// If there wasn't any '.' just return the string as is.

		if (pos == -1)
			return str;

		// Otherwise return the string, up to the dot.

		return str.substring(0, pos);
	}

	public Map<String, Double> getDistributionChart(List<String> diameter,List<String> poresd, int no) {
	
		System.out.println("I M PORESIZE: ");
		
		List<Double> dia=new ArrayList<Double>();
		List<Double> psd=new ArrayList<Double>();
		Map<String, Double> filterdata = new LinkedHashMap();
		double all=0;
		for (int i = 2; i < diameter.size()-1; i++) 
		{

			dia.add(Double.parseDouble(diameter.get(i)));
			psd.add(Double.parseDouble(poresd.get(i)));
			all=all+Double.parseDouble(poresd.get(i));	
	
		}
		System.out.println("Total points : "+(dia.size()-1));
		double max = Collections.max(dia) + 0.001;
		double min = Collections.min(dia);
		double avg = (double) (max - min) / no;

		System.out.println("Min :" + min + " Max :" + max + " avg:" + avg);

		for (int j = 1; j <= no; j++) {
			//List<Double> tempdata = new ArrayList<Double>();
			double start, end;
			double tempsum=0;
			if(j==0)
			{
				start=min;
				end=min+avg;
			}
			else
			{
			  start = Double.parseDouble(getRound(min + (avg * (j - 1)), 3));
			  end = Double.parseDouble(getRound((min + (avg * j)), 3));
			}

			for (int k = 0; k < psd.size(); k++) {
				if (dia.get(k) >= start && dia.get(k) < end) {
				//	tempdata.add(psd.get(k));
					tempsum=tempsum+psd.get(k);
					// data1.remove(k);
				}

			}

			System.out.println("Points : "+start+" : "+end+" - > "+tempsum);
			
			if(tempsum>0)
			{
				filterdata.put(Myapp.getRound(start, 2)+"-"+Myapp.getRound(end, 2),tempsum*100/all);
				
			}

		}

		return filterdata;
	}

}
