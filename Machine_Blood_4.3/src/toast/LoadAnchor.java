package toast;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.Anchor;

import application.DataStore;
import application.Main;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class LoadAnchor {

	public static AnchorPane temp;
	public static Pane createtestfxml,reportfxml,manualcontrolfxml,configurationfxml;
	public static void loadCreateTest()
	{
		createtestfxml=null;
		FXMLLoader fxmlLoader = new FXMLLoader(Main.clsObj.getResource("/userinput/Nselectproject1.fxml"));
		try {
			createtestfxml = (Pane) fxmlLoader.load();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void loadConfugration()
	{
		configurationfxml=null;
		FXMLLoader fxmlLoader = new FXMLLoader(Main.clsObj.getResource("/ConfigurationPart/Nconfigurepage.fxml"));
		try {
			configurationfxml = (Pane) fxmlLoader.load();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void LoadManualControl()
	{
		manualcontrolfxml=null;
		FXMLLoader fxmlLoader = new FXMLLoader(Main.clsObj.getResource("/userinput/manualcontrol.fxml"));
		try {
			manualcontrolfxml = (Pane) fxmlLoader.load();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void LoadReport()
	{
		reportfxml=null;
		FXMLLoader fxmlLoader = new FXMLLoader(Main.clsObj.getResource("/report/first.fxml"));
		try {
			reportfxml = (Pane) fxmlLoader.load();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public LoadAnchor() {
	
		
	
	}
	
	public static void LoadAllPages()
	{

		FirstLineService service = new FirstLineService();
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

           @Override
           public void handle(WorkerStateEvent t) {
        	   if(DataStore.isconfigure.get()==false)
        	   {
        	   DataStore.isconfigure.set(true);
        	   }
           }
       });
       service.start();
	}
	
	public static void LoadReportPage()
	{
		ReportService service = new ReportService();
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

           @Override
           public void handle(WorkerStateEvent t) {
        	System.out.println("REPORT AGAIN LOAD");
           }
       });
       service.start();
	}
	
	public static void LoadCreateTestPage()
	{
		CreateTestService service = new CreateTestService();
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

           @Override
           public void handle(WorkerStateEvent t) {
        	System.out.println("TEST SETUP AGAIN LOAD");
           }
       });
       service.start();
	}
	
	 private static class FirstLineService extends Service {
		  

	        protected Task<String> createTask() {
	            return new Task<String>() {
	                protected String call() 
	                    throws IOException, MalformedURLException {
	                	Platform.runLater(new Runnable() {

							@Override
							public void run() {
								
								 loadCreateTest();
								 LoadReport();
								 loadConfugration();
								// LoadManualControl();
							}
						});
	                        return "";
	                }
	            };
	        }
	    }
	
	 
	 private static class ReportService extends Service {
		  

	        protected Task<String> createTask() {
	            return new Task<String>() {
	                protected String call() 
	                    throws IOException, MalformedURLException {
	                	Platform.runLater(new Runnable() {

							@Override
							public void run() {
								
								
								 LoadReport();
							
							}
						});
	                        return "";
	                }
	            };
	        }
	    }
	
	 private static class CreateTestService extends Service {
		  

	        protected Task<String> createTask() {
	            return new Task<String>() {
	                protected String call() 
	                    throws IOException, MalformedURLException {
	                	Platform.runLater(new Runnable() {

							@Override
							public void run() {

								 loadCreateTest();
							
							}
						});
	                        return "";
	                }
	            };
	        }
	    }
	
}
