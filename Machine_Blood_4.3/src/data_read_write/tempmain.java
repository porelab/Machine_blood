package data_read_write;

import java.io.File;
import java.util.List;

public class tempmain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DatareadN dd=new DatareadN();
			dd.fileRead(new File("file.csv"));
			
			List<String> psd=dd.getValuesOf(dd.data.get("psd")+"");
			List<String> dia=dd.getValuesOf(dd.data.get("diameter")+"");
			
		System.out.println(dd.getDistributionChart(dia, psd, 5));
		
	}

}
