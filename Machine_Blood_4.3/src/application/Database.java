package application;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

//this class is for accessing local database 
public class Database {
	
	
	 String sDriverName = "org.sqlite.JDBC";
     String sTempDb = "information//mydb.db";
     String sJdbc = "jdbc:sqlite";
     String sDbUrl = sJdbc + ":" + sTempDb;
     Connection conn=null;
     

     
     
	public Database()
	{
		 try 
	        { 
	       Class.forName(sDriverName);
	   	  
	        conn = DriverManager.getConnection(sDbUrl);
		
	        }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 System.out.println("Error Constructor:"+e.getMessage());
		 }
	}
	
	public ResultSet getResultSet(String query)
	{
		   try{
			   Statement stmt = conn.createStatement();
			   ResultSet rs = stmt.executeQuery(query);
	          
	         
		
		     return rs;
		   }
		   catch(Exception e)
		   {
			   return null;
		   }
	}
	public ObservableMap<String, Blob> getImages(String query)
	{
		ObservableMap<String, Blob> rs1=FXCollections.observableHashMap();
		
		   try{
			   Statement stmt = conn.createStatement();
			   ResultSet rs = stmt.executeQuery(query);
	          
	           
	           if(rs.next())
	           {
	        	   rs = stmt.executeQuery(query);
	        	   ResultSetMetaData rsmd = rs.getMetaData();
	        	   int cn= rsmd.getColumnCount();
	           	while(rs.next())
	           	{
	           		rs1.put(rs.getString(0), rs.getBlob(1)); 
	           	}  
	        	   
	           	return rs1;
	           }
	           else
	           {
	           return null;
	           }
	           
	           
	           
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
			
	
	}
	public Connection getConnection()
	{
		return conn;
	}
	public boolean InsertWithPrepared(PreparedStatement p)
	{
		try
		{
			int s=p.executeUpdate();
	        
              
	     return true;	
		}
		catch(Exception e)
		{
			
		return false;
		}
	}
	
	public boolean Insert(String query)
	{
		try
		{
		     Statement stmt = conn.createStatement();
	        
              stmt.executeUpdate(query);
		     stmt.close();
	     return true;	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		return false;
		}
    }
	
	public List<List<String>> getData(String query)
	{
		List<List<String>> data=new ArrayList<List<String>>();
		
	   try{
		   Statement stmt = conn.createStatement();
		   ResultSet rs = stmt.executeQuery(query);
          
           
           if(rs.next())
           {
        	   rs = stmt.executeQuery(query);
        	   ResultSetMetaData rsmd = rs.getMetaData();
        	   int cn= rsmd.getColumnCount();
           	while(rs.next())
           	{List<String> rowdata=new ArrayList<String>();
        	   for(int i=1;i<=cn;i++)
        	   {
        		   rowdata.add(rs.getString(i)); 
        	   }
        	  data.add(rowdata); 
           	}  
        	   
           	return data;
           }
           else
           {
           return null;
           }
           
           
           
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getColumn(String query)
	{
		List<String> data=new ArrayList<String>();
		
	   try{
		   Statement stmt = conn.createStatement();
		   ResultSet rs = stmt.executeQuery(query);
          
           
          
      
           	while(rs.next())
           	{
        	  data.add(rs.getString(1)); 
           	}  
        	  
           	if(data.size()>=1)
           	{
           	   	return data;
                   		
           	}
           	else
           	{
           		return null;
           	}
        
           
           
           
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean isExist(String query)
	{
		try{ Statement stmt = conn.createStatement();
			   
		ResultSet rs = stmt.executeQuery(query);
	     
			   if(rs.next())
			   {
				   rs.close();
			       stmt.close(); 
				   return true;
			   }
			   else
			   { 
				   rs.close();
			       stmt.close(); 
				   return false;
			   }
         
		}
		catch(Exception e)
		{
			
			return false;
		}
		
	}
	

}
