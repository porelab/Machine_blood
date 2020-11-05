package pdfreport;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import data_read_write.DatareadN;

public class NMultiplepororeport {
    Document document = new Document(PageSize.A4, 36, 36, 90, 36);
    PdfWriter writer ;
    List<DatareadN> allfiles;
    
    Font blueFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL, new CMYKColor(0, 0, 0, 0));
    Font blueFont1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD, new CMYKColor(255, 255, 255, 0));
    Font normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8.5f, Font.NORMAL, new CMYKColor(255, 255, 255, 0));


    
    //First page new 
    Font filetypes = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new CMYKColor(0f,0f,0f,0f));
    Font testname = FontFactory.getFont(FontFactory.HELVETICA, 25, Font.BOLD, new CMYKColor(1f,0.34f,0f,0.29f));
    Font ftime = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.ITALIC, new BaseColor(96,96,98));
    Font fname = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL, new BaseColor(96,96,98));

    //
    Font sampleinfoq = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new BaseColor(81,81,83));
    Font sampleinfoa = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.NORMAL, new BaseColor(90, 90, 92));
    
    
    Font graphtitle = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, getColor(14));
    Font legendtitle = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new BaseColor(96,96,98));
    Font legenddot = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD);
    
 //result table data 
    Font tablehed = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD, new CMYKColor(255, 255, 255, 0));
    Font normal1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Font.NORMAL, new CMYKColor(255, 255, 255, 0));
    Font tableans = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD, new BaseColor(255,255,255));
       
    
    public static final String FONT = "OpenSansCondensed-Light.ttf";
    public static final String FONTbb = "OpenSansCondensed-Light.ttf";
    public static final String flegend = "FiraSans-Regular.ttf";
    
    
    List<String> clr=new ArrayList<String>();

    public NMultiplepororeport() {
    clr.add("#000080");	
    clr.add("#0000FF");	
    clr.add("#00FFFF");	
    clr.add("#008000");	
    clr.add("#00FF00");	
    clr.add("#FF00FF");	
    }


    
    public void Report(String path, List<DatareadN> d)
		{
    	allfiles=d;
		    try
		    {
        writer = PdfWriter.getInstance(document, new FileOutputStream(path));

        // add header and footer
       
        // write to document
        document.open();
        
        coverpage();
        
        document.newPage();
        HeaderFooterPageEvent event = new HeaderFooterPageEvent();
        writer.setPageEvent(event);


        resulttable(d);
        
        File folder = new File("mypic");
    	File[] listOfFiles = folder.listFiles();
        for(int i=0;i<listOfFiles.length;i++)
        {
        	
            if(i==0)
            {
            	Paragraph pp5=new Paragraph(15);
            	pp5.add(new Chunk("\n"));
            	document.add(pp5);
            	
            	}
        resultgraph(listOfFiles[i]);
        if(i%2==0)
        {
        	 document.newPage();
              	
        }
        else
        {
        	Paragraph pp5=new Paragraph(15);
        	pp5.add(new Chunk("\n"));
        	document.add(pp5);
        	
        	}
   
        }
    document.close();
    
	
    }	
    catch(Exception e)
    {
	e.printStackTrace();
    }
    
}	

  
    BaseColor getColor(int i)
    {
    List<BaseColor> clrs=new ArrayList<>();
    clrs.add(new BaseColor(255,00,00));
    clrs.add(new BaseColor(00,81,212));
    clrs.add(new BaseColor(255,204,00));
    clrs.add(new BaseColor(143,27,145));
    clrs.add(new BaseColor(254,153,1));
    clrs.add(new BaseColor(00,186,00));
    clrs.add(new BaseColor(3,179,255));
    clrs.add(new BaseColor(255,110,137));
    clrs.add(new BaseColor(163,144,00));
    clrs.add(new BaseColor(47,41,73));
    clrs.add(new BaseColor(162,134,219));
    clrs.add(new BaseColor(104,67,51));
    clrs.add(new BaseColor(215,167,103));
    clrs.add(new BaseColor(27,222,222));
    clrs.add(new BaseColor(62,64,149));

    return clrs.get(i);


    }

    
    
    void coverpage()
    {

    	try {
      		
      	  	Image img = Image.getInstance("dddd.jpg");
      	    img.scaleAbsolute(800, 10);
      	    img.setAbsolutePosition(0f, 832f);
      	    document.add(img);
      		  } catch (Exception e) {
      			// TODO: handle exception
      		}

    	
    	
    	try {
  		
  	  	Image img = Image.getInstance("logo.jpg");
  	    img.scaleAbsolute(100, 54);
  	//imgs.setAbsolutePosition(400f, 745f);
  	    img.setAbsolutePosition(430f, 745f);
  	      	    document.add(img);
  		  } catch (Exception e) {
  			// TODO: handle exception
  		}

  	  
  	  
  	  try {
    		
    	  	Image img1 = Image.getInstance("f1.jpg");
    	    img1.scaleAbsolute(595, 450);
    	    img1.setAbsolutePosition(0f, 250f);
    	    document.add(img1);
    		  } catch (Exception e) {
    			// TODO: handle exception
    		}

  	  

  	Paragraph pp5=new Paragraph(483);
	pp5.add(new Chunk("\n"));
	try {
		document.add(pp5);
	} catch (DocumentException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}

  	  
  	  PdfPTable resulttable = new PdfPTable(1); // 4 columns.
	resulttable.setWidthPercentage(120); //Width 100%
	try {
		resulttable.setWidths(new int[]{100});
	} catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	PdfPCell r1 = new PdfPCell(new Paragraph("",graphtitle));
	r1.setBorder(1);
	r1.setBorderWidth(3f);
	r1.setBorder(r1.BOTTOM);
	r1.setBorderColor(getColor(14));
	r1.setPaddingTop(4);
	r1.setFixedHeight(20f);
	r1.setHorizontalAlignment(Element.ALIGN_LEFT);
	r1.setVerticalAlignment(Element.ALIGN_CENTER);	
	resulttable.addCell(r1);	

		try {
			document.add(resulttable);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Paragraph pp6=new Paragraph(-13);
		pp6.add(new Chunk("\n"));
		try {
			document.add(pp6);
		} catch (DocumentException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	  	  PdfPTable filetype = new PdfPTable(1); 
	  	filetype.setWidthPercentage(40); 
	  	filetype.setHorizontalAlignment(Element.ALIGN_RIGHT);
		try {
			filetype.setWidths(new int[]{100});
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PdfPCell r3 = new PdfPCell(new Paragraph("COMPARATIVE ANALYSIS REPORT",filetypes));
		r3.setBorder(0);
		r3.setBackgroundColor(getColor(14));
		r3.setFixedHeight(25f);
		r3.setHorizontalAlignment(Element.ALIGN_CENTER);
		r3.setVerticalAlignment(Element.ALIGN_MIDDLE);	
		
		filetype.addCell(r3);	

			try {
				document.add(filetype);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			Paragraph pp7=new Paragraph(20);
			pp6.add(new Chunk("\n"));
			try {
				document.add(pp6);
			} catch (DocumentException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			
			
		  	  PdfPTable testnametab = new PdfPTable(2); // 4 columns.
		  	testnametab.setWidthPercentage(100); //Width 100%
			testnametab.setSpacingBefore(60f); //Space before table

		  	try {
		  		testnametab.setWidths(new int[]{60,40});
		  	} catch (DocumentException e) {
		  		// TODO Auto-generated catch block
		  		e.printStackTrace();
		  	}

		  	PdfPCell t0 = new PdfPCell(new Paragraph("",testname));
		  	t0.setBorder(0);
		  	t0.setBorderWidth(3f);
		  	t0.setBorderColor(BaseColor.BLUE);
		  	t0.setFixedHeight(120f);
		  	t0.setHorizontalAlignment(Element.ALIGN_RIGHT);
		  	t0.setVerticalAlignment(Element.ALIGN_CENTER);	
		  	testnametab.addCell(t0);	
		  	
//            Font titleFont=FontFactory.getFont(FONT, 45);//Main Report Title name
  //      	titleFont.setColor(getColor(6)); 


            Font testname = FontFactory.getFont("./font/BebasNeue Book.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 50);
            testname.setColor(getColor(6));

		  	
		  	
		  	PdfPCell t1 = new PdfPCell(new Paragraph("BUBBLE POINT TEST REPORT",testname));
		  	t1.setBorder(0);
		  	t1.setBorderWidth(3f);
		  	t1.setFixedHeight(120f);
		  	t1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		  	t1.setVerticalAlignment(Element.ALIGN_CENTER);	
		  	testnametab.addCell(t1);	

		  	Font covertime = FontFactory.getFont("./font/RobotoCondensed-Italic.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 10);
		  	covertime.setColor(BaseColor.BLACK);

		  	
		  	
		  	PdfPCell t2 = new PdfPCell(new Paragraph(""+getCurrentData(),covertime));
		  	t2.setBorder(0);
		  	t2.setBorderWidth(3f);
		  	t2.setBorderColor(BaseColor.BLUE);
		  	t2.setPaddingTop(4);
		  	t2.setFixedHeight(20f);
		  	t2.setHorizontalAlignment(Element.ALIGN_LEFT);
		  	t2.setVerticalAlignment(Element.ALIGN_CENTER);	
		  	testnametab.addCell(t2);	

		  	Font compname = FontFactory.getFont("./font/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 14);
		  	compname.setColor(BaseColor.BLACK);


		  	PdfPCell t3 = new PdfPCell(new Paragraph("Sun Pharmaceutical",compname));
		  	t3.setBorder(0);
		  	t3.setBorderWidth(3f);
		  	t3.setBorderColor(BaseColor.BLUE);
		  	t3.setPaddingTop(4);
		  	t3.setFixedHeight(20f);
		  	t3.setHorizontalAlignment(Element.ALIGN_RIGHT);
		  	t3.setVerticalAlignment(Element.ALIGN_CENTER);	
		  	testnametab.addCell(t3);	

		  	
		  	
		  	
		  	
		  		try {
		  			document.add(testnametab);
		  		} catch (DocumentException e) {
		  			// TODO Auto-generated catch block
		  			e.printStackTrace();
		  		}

			
			
		
		
		
    }
    
    

String getCurrentData()
{
String[] suffixes =
 //    0     1     2     3     4     5     6     7     8     9
    { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
 //    10    11    12    13    14    15    16    17    18    19
      "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
 //    20    21    22    23    24    25    26    27    28    29
      "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
 //    30    31
      "th", "st" };

Date date = new Date();
SimpleDateFormat formatDayOfMonth  = new SimpleDateFormat("d");
int day = Integer.parseInt(formatDayOfMonth.format(date));
String dayStr = day + suffixes[day];


DateFormat dateFormat = new SimpleDateFormat(" MMMM yyyy|HH:mm aa");
Calendar cal = Calendar.getInstance();

//System.out.println(dateFormat.format(cal.getTime()));

return dayStr+dateFormat.format(cal.getTime());

}

    
 void resulttable(List<DatareadN> d) {

	 int r=230;
	    int g=231;
	    int b=232; 
	    
	   
	    
	    BaseColor backcellcol=new BaseColor(r,g,b);
	   

	 
	 
try {
	document.add(new Paragraph("\n"));
} catch (DocumentException e2) {
	// TODO Auto-generated catch block
	e2.printStackTrace();
}


PdfPTable tablem = new PdfPTable(3); // 3 columns.
tablem.setWidthPercentage(100); //Width 100%
tablem.setSpacingBefore(0f); //Space before table
tablem.setSpacingAfter(0f); //Space after table

//Set Column widths
float[] columnWidths = {1f, 1f,1f};

try {
	tablem.setWidths(columnWidths);
} catch (DocumentException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}


	Font tablehed = FontFactory.getFont("./font/Roboto-Bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 9);
	tablehed.setColor(new BaseColor(105, 105, 105));


	Font tablemean = FontFactory.getFont("./font/Roboto-Bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 9);
	tablemean.setColor(BaseColor.WHITE);

	
PdfPCell cell1 = new PdfPCell(new Paragraph("Name",tablehed));
cell1.setBackgroundColor(backcellcol);
cell1.setBorder(1);
cell1.setBorder(cell1.TOP | cell1.BOTTOM);
cell1.setBorderColor(getColor(6));
cell1.setPaddingLeft(10);
cell1.setPaddingTop(4);
cell1.setFixedHeight(20f);
cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
cell1.setVerticalAlignment(Element.ALIGN_CENTER);


PdfPCell cell4 = new PdfPCell(new Paragraph("BP Pressure",tablehed));
cell4.setBackgroundColor(backcellcol);
cell4.setBorder(1);
cell4.setBorder(cell4.TOP | cell4.BOTTOM  | cell4.LEFT);
cell4.setBorderColor(getColor(6));
cell4.setPaddingLeft(10);
cell4.setPaddingTop(4);
cell4.setFixedHeight(20f);
cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
cell4.setVerticalAlignment(Element.ALIGN_CENTER);

PdfPCell cell5 = new PdfPCell(new Paragraph("BP Diameter",tablehed));
cell5.setBackgroundColor(backcellcol);
cell5.setBorder(1);
cell5.setBorder(cell5.TOP | cell5.BOTTOM  | cell5.LEFT);
cell5.setBorderColor(getColor(6));
cell5.setPaddingLeft(10);
cell5.setPaddingTop(4);
cell5.setFixedHeight(20f);
cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
cell5.setVerticalAlignment(Element.ALIGN_CENTER);

tablem.addCell(cell1);
tablem.addCell(cell4);
tablem.addCell(cell5);

for(int j=0;j<d.size();j++)
{
	
	
	Font tabledata = FontFactory.getFont("./font/Roboto-Light.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 9);
	tablehed.setColor(new BaseColor(98,98,98));
	
	
	DatareadN dr=d.get(j);
	//first column
	String st=""+dr.data.get("testname");
//	st = st.substring(0, st.indexOf('.'));
	
	
	if (j % 2 == 0) {

	
	PdfPCell record1 = new PdfPCell(new Paragraph(st,tabledata));
	record1.setBorder(0);
	record1.setBorderColor(getColor(6));
	record1.setPaddingTop(4);
	record1.setFixedHeight(20f);
	record1.setHorizontalAlignment(Element.ALIGN_CENTER);
	record1.setVerticalAlignment(Element.ALIGN_CENTER);
	tablem.addCell(record1);
	
	
    
//	double mp=dr.getMeanPressure();
	//double md=dr.getMeanDiameter();
	String bpp=""+dr.data.get("bpressure");
	String bpd=""+dr.data.get("bdiameter");
	
	
	


	
	PdfPCell record4 = new PdfPCell(new Paragraph(""+bpp,tabledata));
	record4.setBorder(1);
	record4.setBorder(record4.LEFT);
	record4.setBorderColor(getColor(6));
	record4.setPaddingTop(4);
	record4.setFixedHeight(20f);
	record4.setHorizontalAlignment(Element.ALIGN_CENTER);
	record4.setVerticalAlignment(Element.ALIGN_CENTER);
	tablem.addCell(record4);
	
	
	PdfPCell record5 = new PdfPCell(new Paragraph(""+bpd,tabledata));
	record5.setBorder(1);
	record5.setBorder(record5.LEFT);
	record5.setBorderColor(getColor(6));
	record5.setPaddingTop(4);
	record5.setFixedHeight(20f);
	record5.setHorizontalAlignment(Element.ALIGN_CENTER);
	record5.setVerticalAlignment(Element.ALIGN_CENTER);
	tablem.addCell(record5);
	}
	else
	{
	
		PdfPCell record1 = new PdfPCell(new Paragraph(st,tabledata));
		record1.setBorder(0);
		record1.setBorderColor(getColor(6));
		record1.setBackgroundColor(backcellcol);
		record1.setPaddingTop(4);
		record1.setFixedHeight(20f);
		record1.setHorizontalAlignment(Element.ALIGN_CENTER);
		record1.setVerticalAlignment(Element.ALIGN_CENTER);
		tablem.addCell(record1);
		
		
	    
//		double mp=dr.getMeanPressure();
	//	double md=dr.getMeanDiameter();
		String bpp=""+dr.data.get("bpressure");
		String bpd=""+dr.data.get("bdiameter");
		
		
		
		PdfPCell record4 = new PdfPCell(new Paragraph(""+bpp,tabledata));
		record4.setBorder(1);
		record4.setBorder(record4.LEFT);
		record4.setBorderColor(getColor(6));
		record4.setBackgroundColor(backcellcol);
		record4.setPaddingTop(4);
		record4.setFixedHeight(20f);
		record4.setHorizontalAlignment(Element.ALIGN_CENTER);
		record4.setVerticalAlignment(Element.ALIGN_CENTER);
		tablem.addCell(record4);
		
		
		PdfPCell record5 = new PdfPCell(new Paragraph(""+bpd,tabledata));
		record5.setBorder(1);
		record5.setBorder(record5.LEFT);
		record5.setBorderColor(getColor(6));
		record5.setBackgroundColor(backcellcol);
		record5.setPaddingTop(4);
		record5.setFixedHeight(20f);
		record5.setHorizontalAlignment(Element.ALIGN_CENTER);
		record5.setVerticalAlignment(Element.ALIGN_CENTER);
		tablem.addCell(record5);
		
	}
}

PdfPCell s = new PdfPCell(new Paragraph("Mean +/- Sd",tablemean));
s.setBorder(1);
s.setBorder(s.LEFT);	 
s.setBorderColor(new BaseColor(255,255,255));
s.setBackgroundColor(getColor(14));
s.setPaddingTop(4);
s.setFixedHeight(20f);
s.setHorizontalAlignment(Element.ALIGN_CENTER);
s.setVerticalAlignment(Element.ALIGN_CENTER);
tablem.addCell(s);


double avgmp=0,avgmd=0,avgbpd=0,avgbpp=0;
List<Double> gmplist=new ArrayList<Double>();
List<Double> bpdlist=new ArrayList<Double>();
List<Double> gmdlist=new ArrayList<Double>();
List<Double> bpplist=new ArrayList<Double>();

/*for(int im=0;im<mullist.size();im++)
{
	mulmen=mulmen+mullist.get(im);
	sigmen=sigmen+siglist.get(im);
	
	
	
}*/
DatareadN dr=new DatareadN();
for(int j=0;j<d.size();j++)
{

//dr.fileRead(d[j]);
 dr=d.get(j);

//double mp=dr.getMeanPressure();
//double md=dr.getMeanDiameter();
String bpp=""+dr.data.get("bpressure");
String bpd=""+dr.data.get("bdiameter");
bpplist.add(Double.parseDouble(""+bpp));
bpdlist.add(Double.parseDouble(""+bpd));
avgbpp=avgbpp+Double.parseDouble(""+bpp);
avgbpd=avgbpd+Double.parseDouble(""+bpd);

}
avgmp=avgmp/d.size();
avgmd=avgmd/d.size();
avgbpd=avgbpd/d.size();
avgbpp=avgbpp/d.size();
	
	 PdfPCell s3 = new PdfPCell(new Paragraph(""+avgbpp+" +/- "+getSd(bpplist, avgbpp),tablemean));
	 s3.setBorder(1);
	 s3.setBorder(s.LEFT);
	 s3.setBorderColor(new BaseColor(255,255,255));
	 s3.setBackgroundColor(getColor(14));
	 s3.setPaddingTop(4);
	 s3.setFixedHeight(20f);
	 s3.setHorizontalAlignment(Element.ALIGN_CENTER);
	 s3.setVerticalAlignment(Element.ALIGN_CENTER);
	tablem.addCell(s3);
	
	 PdfPCell s4 = new PdfPCell(new Paragraph(""+avgbpd+" +/- "+getSd(bpdlist, avgbpd),tablemean));
	 s4.setBorder(1);
	 s4.setBorder(s4.LEFT);
	 s4.setBorderColor(new BaseColor(255,255,255));
	 s4.setBackgroundColor(getColor(14));
	 s4.setPaddingTop(4);
	 s4.setFixedHeight(20f);
	 s4.setHorizontalAlignment(Element.ALIGN_CENTER);
	 s4.setVerticalAlignment(Element.ALIGN_CENTER);
	tablem.addCell(s4);

	
try {
	document.add(tablem);
} catch (DocumentException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

}

 void resultgraph(File f)
 {
 try{
 	
	  	Font graphtitle = FontFactory.getFont("./font/Montserrat-SemiBold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 14);
	  	graphtitle.setColor(getColor(14));

 	
 	Paragraph pp5=new Paragraph(35);
 //	pp5.add(new Chunk("\n"));
 //	document.add(pp5);
 	
 	
 	PdfPTable resulttable = new PdfPTable(3); // 4 columns.
 	resulttable.setWidthPercentage(100); //Width 100%

 	resulttable.setWidths(new int[]{ 33,33,33});
 	
 	PdfPCell r1 = new PdfPCell(new Paragraph(f.getName().substring(0,f.getName().indexOf('.')),graphtitle));
 	r1.setBorder(1);
 	r1.setColspan(4);
 	r1.setBorder(r1.BOTTOM);
 	r1.setBorderColor(getColor(14));
 	r1.setPaddingTop(4);
 	r1.setFixedHeight(20f);
 	r1.setHorizontalAlignment(Element.ALIGN_LEFT);
 	r1.setVerticalAlignment(Element.ALIGN_CENTER);	

 	resulttable.addCell(r1);	

 		document.add(resulttable);
 	
 	
 		
 	    	Image image = Image.getInstance(f.getAbsolutePath());	
 	    	image.scaleAbsolute(520,300);
 	    	
 	    	document.add(image);
 	    	
 	    	
 	    	
 	    	PdfPTable legendtab = new PdfPTable(12); // 4 columns.
 	    	legendtab.setWidthPercentage(100); //Width 100%
 	    	legendtab.setWidths(new int[]{4,12,4,13,4,13,4,13,4,13,4,12});
 	    
 	    	

             Font lt=FontFactory.getFont(flegend, 8);//Main Report Title name
         	lt.setColor(new BaseColor(96,96,98)); 

 	    	
 	    	for(int i=1;i<=allfiles.size();i++)
 	    	{
 	    		
 	    		
 	    		System.out.println((i%6)+"");
 	    	if((i==1&&i==allfiles.size()) || (i==7&&i==allfiles.size()))
 	    	{
 	    		



 	    		 PdfContentByte cb = writer.getDirectContent();
 	    	     
 	    	        
 	    	        PdfTemplate p=cb.createTemplate(20, 20);
 	    	       // p.setRGBColorFill(0xFF, 0x00, 0x00);
 	    	        p.setColorFill(getColor(i-1));
 	    	        p.circle(10, 10, 5);
 	    	       p.fill();
 	    	      
 	    	        Image img=Image.getInstance(p);
 	    	        System.out.println("Hello");
 	    		
 	    		PdfPCell l1 = new PdfPCell(img);
 	    		l1.setBorder(1);
 		    	l1.setBorder(l1.BOTTOM | l1.TOP);
 		    	l1.setBorderColor(new BaseColor(150,150,150));
 		    	l1.setFixedHeight(21f);
 		    	l1.setHorizontalAlignment(Element.ALIGN_RIGHT);
 		    	l1.setVerticalAlignment(Element.ALIGN_CENTER);
 		    	legendtab.addCell(l1);

 	    		
 	    		PdfPCell l2 = new PdfPCell(new Paragraph(""+allfiles.get(i-1).data.get("testname"),lt));
 	    		l2.setBorder(1);
 	    		l2.setBorder(l2.BOTTOM | l2.TOP |l2.RIGHT);
 		    	l2.setBorderColor(new BaseColor(150,150,150));
 		    	l2.setFixedHeight(21f);
 		    	l2.setHorizontalAlignment(Element.ALIGN_LEFT);
 		    	l2.setVerticalAlignment(Element.ALIGN_MIDDLE);
 		    	legendtab.addCell(l2);
 		    	
 		    	PdfPCell l3 = new PdfPCell(new Paragraph("",graphtitle));
 		    	l3.setBorder(1);
 		    	l3.setColspan(10);
 		    	l3.setBorder(l2.BOTTOM | l2.TOP );
 		    	l3.setBorderColor(new BaseColor(150,150,150));
 		    	l3.setFixedHeight(21f);
 		    	l3.setHorizontalAlignment(Element.ALIGN_LEFT);
 		    	l3.setVerticalAlignment(Element.ALIGN_CENTER);
 		    	legendtab.addCell(l3);
 	    	}
 	    	else if(i==1||i==7||i==13)
 	    		{

 	    		 PdfContentByte cb = writer.getDirectContent();
 	    	      PdfTemplate p=cb.createTemplate(20, 20);
 	    	       // p.setRGBColorFill(0xFF, 0x00, 0x00);
 	    	        p.setColorFill(getColor(i-1));
 	    	        p.circle(10, 10, 5);
 	    	       p.fill();
 	    	      
 	    	        Image img=Image.getInstance(p);
 	    	        System.out.println("Hello");
 	    		
 	    		
 	    		PdfPCell l2 = new PdfPCell(img);
 	    		l2.setBorder(1);
 	    		l2.setBorder(l2.BOTTOM | l2.TOP);
 		    	l2.setBorderColor(new BaseColor(150,150,150));
 		    	l2.setFixedHeight(21f);
 		    	l2.setHorizontalAlignment(Element.ALIGN_RIGHT);
 		    	l2.setVerticalAlignment(Element.ALIGN_CENTER);
 		    	legendtab.addCell(l2);

 	    		
 	    		
 	    			PdfPCell l1 = new PdfPCell(new Paragraph(""+allfiles.get(i-1).data.get("testname"),lt));
 	    			l1.setBorder(1);
 	    	    	l1.setBorder(l1.BOTTOM | l1.TOP);
 	    	    	l1.setBorderColor(new BaseColor(150,150,150));
 	    	    	l1.setFixedHeight(21f);
 		    		l1.setHorizontalAlignment(Element.ALIGN_LEFT);
 	    	    	l1.setVerticalAlignment(Element.ALIGN_MIDDLE);
 	    	    	legendtab.addCell(l1);
     		    	
 	    		}
 	    		else
 	    		{
 	    			if(i==allfiles.size())
 	    			{
 	    				
 	    				if(i%6==0&&i==6)
 	    				{
 	    					
 	    					
 	    					 PdfContentByte cb = writer.getDirectContent();
 	    		    	     
 	    		    	        
 	    		    	        PdfTemplate p=cb.createTemplate(20, 20);
 	    		    	       // p.setRGBColorFill(0xFF, 0x00, 0x00);
 	    		    	        p.setColorFill(getColor(i-1));
 	    		    	        p.circle(10, 10, 5);
 	    		    	       p.fill();
 	    		    	      
 	    		    	        Image img=Image.getInstance(p);
 	    		    	        System.out.println("Hello");
 	    		    		
 	    					
 	    		    		PdfPCell l1 = new PdfPCell(img);
 	    		    		l1.setBorder(1);
 	    			    	l1.setBorder(l1.BOTTOM | l1.TOP | l1.LEFT);
 	    			    	l1.setBorderColor(new BaseColor(150,150,150));
 	    			    	l1.setFixedHeight(21f);
 	    			    	l1.setHorizontalAlignment(Element.ALIGN_RIGHT);
 	    			    	l1.setVerticalAlignment(Element.ALIGN_CENTER);
 	    			    	legendtab.addCell(l1);


 	    			    	PdfPCell l2 = new PdfPCell(new Paragraph(""+allfiles.get(i-1).data.get("testname"),lt));
 	    			    	l2.setBorder(1);
 	    			    	l2.setBorder(l2.BOTTOM | l2.TOP );
 	    			    	l2.setBorderColor(new BaseColor(150,150,150));
 	    		        	l2.setFixedHeight(21f);
 	    	    	    	l2.setHorizontalAlignment(Element.ALIGN_RIGHT);
 	    	    	    	l2.setVerticalAlignment(Element.ALIGN_MIDDLE);
 	    	    	    	legendtab.addCell(l2);
 		    		    	
 	    			    	
 	    				}
 	    				else
 	    				{

 	    					 PdfContentByte cb = writer.getDirectContent();
 	    		    	     
 	    		    	        
 	    		    	        PdfTemplate p=cb.createTemplate(20, 20);
 	    		    	       // p.setRGBColorFill(0xFF, 0x00, 0x00);
 	    		    	        p.setColorFill(getColor(i-1));
 	    		    	        p.circle(10, 10, 5);
 	    		    	       p.fill();
 	    		    	      
 	    		    	        Image img=Image.getInstance(p);
 	    		    	        System.out.println("Hello");
 	    		    		
 	    		    		PdfPCell l1 = new PdfPCell(img);
 	    		    		l1.setBorder(1);
 	    			    	l1.setBorder(l1.BOTTOM | l1.TOP | l1.LEFT);
 	    			    	l1.setBorderColor(new BaseColor(150,150,150));
 	    			    	l1.setFixedHeight(21f);
 	    			    	l1.setHorizontalAlignment(Element.ALIGN_RIGHT);
 	    			    	l1.setVerticalAlignment(Element.ALIGN_CENTER);
 	    			    	legendtab.addCell(l1);

 	    			    	
 	    					
 	    			    	PdfPCell l2 = new PdfPCell(new Paragraph(""+allfiles.get(i-1).data.get("testname"),lt));
 	    			    	l2.setBorder(1);
 	    			    	l2.setColspan((12-(i%6))+1);
 	    			    	l2.setBorder(l2.BOTTOM | l2.TOP );
 	    			    	l2.setBorderColor(new BaseColor(150,150,150));
 	    		    		l2.setFixedHeight(21f);
 	    			    	l2.setHorizontalAlignment(Element.ALIGN_LEFT);
 	    			    	l2.setVerticalAlignment(Element.ALIGN_MIDDLE);
 	    			    	legendtab.addCell(l2);
 		    		    		
 	    			    	
 	    				}
 	    				
 	    			}
 	    			else
 	    			{
 	    				 PdfContentByte cb = writer.getDirectContent();
 	    	    	     
 	 	    	        
 	 	    	        PdfTemplate p=cb.createTemplate(20, 20);
 	 	    	       // p.setRGBColorFill(0xFF, 0x00, 0x00);
 	 	    	        p.setColorFill(getColor(i-1));
 	 	    	        p.circle(10, 10, 5);
 	 	    	       p.fill();
 	 	    	      
 	 	    	        Image img=Image.getInstance(p);
 	 	    	        System.out.println("Hello");
 	 	    		
     		    		PdfPCell l1 = new PdfPCell(img);
     		    		l1.setBorder(1);
     			    	l1.setBorder(l1.BOTTOM | l1.TOP | l1.LEFT);
     			    	l1.setBorderColor(new BaseColor(150,150,150));
     			    	l1.setFixedHeight(21f);
     			    	l1.setHorizontalAlignment(Element.ALIGN_RIGHT);
     			    	l1.setVerticalAlignment(Element.ALIGN_CENTER);
     			    	legendtab.addCell(l1);


 	    		    	PdfPCell l2 = new PdfPCell(new Paragraph(""+allfiles.get(i-1).data.get("testname"),lt));
 	    		    	l2.setBorder(1);
 	    		    	l2.setBorder(l2.BOTTOM | l2.TOP);
 	    		    	l2.setBorderColor(new BaseColor(150,150,150));
 	    	    		l2.setFixedHeight(21f);
 		    	    	l2.setHorizontalAlignment(Element.ALIGN_LEFT);
 		    	    	l2.setVerticalAlignment(Element.ALIGN_MIDDLE);
 	    		    	  	legendtab.addCell(l2);
 	    		    	
 	    			}
 	    			
 	    		}
 	    		
 	    	}
 	    		
 	    	
 	    	
 	    	document.add(legendtab);
 	    	
 	    	
 			}
 		catch(Exception e){e.printStackTrace();}

 		System.out.println("File is created");
 		//document.newPage();
 	
 	
 	}
 double getSd(List<Double> ls,Double mean)
 {
		List<Double> sq=new ArrayList<Double>();
		double sqmean=0;
		for(int i=0;i<ls.size();i++)
		{
		double dd=ls.get(i)-mean;
		sq.add(dd*dd);
		sqmean=sqmean+sq.get(i);
		}
		
		sqmean=sqmean/sq.size();
		sqmean=Math.sqrt(sqmean);
		double sp3=Math.round(Double.parseDouble(""+sqmean)*10000)/10000D;
		sqmean=sp3;
		
		
		
		return sqmean;
 }

 class HeaderFooterPageEvent extends PdfPageEventHelper {
	Font titleFont=FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD, new CMYKColor(92, 17, 0, 15));
	Font titledate=FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.ITALIC, new BaseColor(99,99,99));


	
    private PdfTemplate t;
    private Image total;

    public void onOpenDocument(PdfWriter writer, Document document) {
        t = writer.getDirectContent().createTemplate(30, 16);
        try {
            total = Image.getInstance(t);
            total.setRole(PdfName.ARTIFACT);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        addHeader(writer);
        addFooter(writer);
    }

    private void addHeader(PdfWriter writer){
       try {
            // set defaults
            	PdfPTable table = new PdfPTable(5);
        	  table.setTotalWidth(670);
              table.setLockedWidth(true);
              table.setWidths(new int[]{ 2, 1, 1, 2, 2});
                        
  		  	Font headertestname = FontFactory.getFont("./font/BebasNeue Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 15);
  		  	headertestname.setColor(getColor(6));

        	
        	
            PdfPCell cell;
            cell = new PdfPCell(new Phrase("BUBBLE POINT TEST REPORT",headertestname)); 
            cell.setBorder(1);
            cell.setBorder(cell.BOTTOM);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        	cell.setBorderColor(new BaseColor(150,150,150));
            table.addCell(cell);
            
		  	Font headerdate = FontFactory.getFont("./font/RobotoCondensed-Italic.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 7.5f);
		  	headerdate.setColor(new BaseColor(98,98,98));
            
            cell = new PdfPCell(new Phrase(""+getCurrentData(),headerdate));
            cell.setBorder(1);
            cell.setBorder(cell.BOTTOM);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        	cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        	cell.setBorderColor(new BaseColor(150,150,150));
        	cell.setColspan(3);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase());
            Image img = Image.getInstance("logo.jpg");
            // img.scalePercent(15);
            //    img.scaleToFit(100, 50);
                img.scaleToFit(50, 26);
             cell.setRowspan(2);
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        	cell.addElement(new Chunk(img, 0, 4,true));
            table.addCell(cell); 
            

            cell = new PdfPCell(new Phrase("")); 
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        	cell.setBorderColor(BaseColor.GRAY);
            table.addCell(cell);

            
            
            
            cell = new PdfPCell(new Phrase("")); 
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        	cell.setBorderColor(BaseColor.GRAY);
            table.addCell(cell);
            
            
            cell = new PdfPCell(new Phrase("")); 
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        	cell.setBorderColor(BaseColor.GRAY);
            table.addCell(cell);
            
            
            cell = new PdfPCell(new Phrase("")); 
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        	cell.setBorderColor(BaseColor.GRAY);
            table.addCell(cell);
            
            // write content
            table.writeSelectedRows(0, -1, 0, 803, writer.getDirectContent());
        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        } catch (MalformedURLException e) {
            throw new ExceptionConverter(e);
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        }
    }

    private void addFooter(PdfWriter writer){
        PdfPTable footer = new PdfPTable(3);
        try {
            // set defaults
            footer.setWidths(new int[]{24, 2, 1});
            footer.setTotalWidth(527);
            footer.setLockedWidth(true);
            footer.getDefaultCell().setFixedHeight(40);
            footer.getDefaultCell().setBorder(Rectangle.TOP);
            footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

            // add copyright
            footer.addCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));

            // add current page count
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            footer.addCell(new Phrase(String.format("Page %d of", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)));

            // add placeholder for total page count
         //   PdfPCell totalPageCount = new PdfPCell(total);
         //   totalPageCount.setBorder(Rectangle.TOP);
        //    totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
       //     footer.addCell(totalPageCount);

            // write page
            PdfContentByte canvas = writer.getDirectContent();
            canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
            footer.writeSelectedRows(0, -1, 34, 50, canvas);
            canvas.endMarkedContentSequence();
        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    public void onCloseDocument(PdfWriter writer, Document document) {
        int totalLength = String.valueOf(writer.getPageNumber()).length();
        int totalWidth = totalLength * 5;
      //  ColumnText.showTextAligned(t, Element.ALIGN_RIGHT,
      //         new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)),
      //         totalWidth, 6, 0);
    }
   
}
}

