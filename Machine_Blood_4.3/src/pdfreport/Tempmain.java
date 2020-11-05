package pdfreport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data_read_write.DatareadN;
import report.ReportController;

public class Tempmain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
/*					File f=new File("test_max250.csv");
			DatareadN d=new DatareadN();
			d.fileRead(f);
			
			Singlepororeport sp=new Singlepororeport();
			List<String> dd=new ArrayList<String>();
			dd.add("1");
			dd.add("1");
			dd.add("1");
			dd.add("1");
			dd.add("1");
			
			sp.Report("C:/Users/Jayesh/Desktop/bp1.pdf",d,"dd","Sun Pharmaceutical","",dd,false);
			
		*/
			
			
			List<String> dd=new ArrayList<String>();
			dd.add("1");
			dd.add("1");
			dd.add("1");
			dd.add("1");
			dd.add("1");
			
			Map<String,String> paths=new HashMap<String,String>();
//			paths.put("Gown GX100_1","C:\\Users\\Meet\\Desktop\\i1.jpg");
//			paths.put("Gown GX101_2","C:\\\\Users\\\\Meet\\\\Desktop\\\\i3.jpg");
//			paths.put("Gown GX101_1","C:\\\\Users\\\\Meet\\\\Desktop\\\\i2.jpg");
//			paths.put("Gown GX100_2","C:\\\\Users\\\\Meet\\\\Desktop\\\\i2.jpg");
//			
			
			paths.put("Gown GX100_1","_");
			paths.put("Gown GX101_2","_");
			paths.put("Gown GX101_1","C:\\\\Users\\\\Meet\\\\Desktop\\\\i2.jpg");
			paths.put("Gown GX100_2","C:\\\\Users\\\\Meet\\\\Desktop\\\\i2.jpg");
			
			
			DatareadN d1=new DatareadN();
		    DatareadN d2=new DatareadN();
		    DatareadN d3=new DatareadN();
		    DatareadN d4=new DatareadN();
		    //DatareadN d5=new DatareadN();
		    //DatareadN d6=new DatareadN();
		    
		    d1.fileRead(new File("TableCsvs/N0048/Gown GX100/Gown GX100_1.csv"));
		    d2.fileRead(new File("TableCsvs/N0048/Gown GX100/Gown GX100_2.csv"));
		    d3.fileRead(new File("TableCsvs/N0048/Gown GX101/Gown GX101_1.csv"));
		    d4.fileRead(new File("TableCsvs/N0048/Gown GX101/Gown GX101_2.csv"));
		   // d5.fileRead(new File("3M900GV_1_0010.csv"));
		   // d6.fileRead(new File("test_sample5_15psi0.csv"));
			
		    List<DatareadN> ds=new ArrayList<DatareadN>();
		    ds.add(d1);
		    ds.add(d2);
		    ds.add(d3);
		    ds.add(d4);
		  //ds.add(d5);
		  //ds.add(d6);
		  
		    System.out.println("obje");
			Multiplepororeport mp=new Multiplepororeport();
//			mp.Report("C:/Users/Jayesh/Desktop/bpmulti.pdf", ds,"Sun Pharmaceutical","ddddd",null,false,"",false);

			mp.Report("C:/Users/Meet/Desktop/bpmulti.pdf", ds,"this is notes", "Hello",
					null, true,false, "",false,paths);
				
			File ff=new File("C:/Users/Meet/Desktop/bpmulti.pdf");
		 	try {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + ff.getAbsolutePath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}

}
