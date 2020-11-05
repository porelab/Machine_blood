package data_read_write;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

//create csv file 
public class CsvWriter 
{
	
	public String filename="";
	FileWriter fw=null;
	 private static final String COMMA_DELIMITER = ",";
	 private static final String NEW_LINE_SEPARATOR = "\n";

	 BufferedWriter writer=null;
	
	public void wtirefile(String filename)
	{
		try{
		this.filename=filename;
		fw=new FileWriter(filename);
		
		writer= new BufferedWriter(fw);

	    
	
		
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public void newLine(String st,String data)
	{
	
		try
		{
			writer.newLine();
		writer.write(st);
		writer.write(COMMA_DELIMITER);
		writer.write(data);
		
		}
		catch(Exception e)
		{
			System.out.println("Error in new Single data");
		}
	}
	
	
	
	public void closefile()
	{
		try{
			writer.close();
			fw.close();

		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void firstLine(String st)
	{
		try{
			writer.write(st);
			
		}
		catch(Exception e)
		{
			System.out.println("Error: "+e.getMessage());
		}
	}
	public void newLine(String st)
	{
	
		try
		{
			writer.newLine();
		writer.write(st);
		
		}
		catch(Exception e)
		{
			System.out.println("Error in new Single data");
		}
	}
	
	public void blankLine()
	{
		try{
			writer.newLine();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void newLine(String title,List<String> data)
	{
		
		try
		{
		//fw.append(title);
		//fw.append(COMMA_DELIMITER);
			writer.newLine();
			writer.write(title);
			writer.write(COMMA_DELIMITER);
			
		for(int i=0;i<data.size();i++)
		{
			writer.write(data.get(i));
			if(i<data.size()-1)
			{
			writer.write(COMMA_DELIMITER);
			}
		}
	
	//	fw.append(NEW_LINE_SEPARATOR);
		}
		catch(Exception e)
		{
			System.out.println("Error in new multiple data");
		}
		
		
		
	}
	public void newLineDouble(String title,List<Double> data)
	{
		
		try
		{
		//fw.append(title);
		//fw.append(COMMA_DELIMITER);
			writer.newLine();
			writer.write(title);
			writer.write(COMMA_DELIMITER);
			
		for(int i=0;i<data.size();i++)
		{
			writer.write(data.get(i)+"");
			if(i<data.size()-1)
			{
			writer.write(COMMA_DELIMITER);
			}
		}
	
	//	fw.append(NEW_LINE_SEPARATOR);
		}
		catch(Exception e)
		{
			System.out.println("Error in new multiple data");
		}
		
		
		
	}

}
