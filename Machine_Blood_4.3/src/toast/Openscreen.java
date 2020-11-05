package toast;

import java.io.IOException;

import application.Main;
import application.MainancController;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

// page changes util class in all page

public class Openscreen 
{
	
	
	public static void open(String s)
	{

	

		//FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/userinput/result.fxml"));
		
		String toastMsg = "Please wait..";
		int toastMsgTime = 2000; //3.5 seconds
		int fadeInTime = 500; //0.5 seconds
		int fadeOutTime= 500; //0.5 seconds
		//Toast.makeText(Main.mainstage, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);

/*		Customtoast toast=new Customtoast(Main.mainstage, toastMsg,  fadeInTime, fadeOutTime,true);
		toast.show();
		*/
		Task<Pane> loadTask = new Task<Pane>() {
            @Override
            public Pane call() throws IOException, InterruptedException {
            	System.out.println("Loading");
            	
            	Pane cmdPane=null;
        		FXMLLoader fxmlLoader = new FXMLLoader(Main.clsObj.getResource(s));
        		try {
        			cmdPane = (Pane) fxmlLoader.load();
        	 		
        			
        			} 
        			catch (Exception e)
        			{

        			 	e.printStackTrace();
        				String toastMsg = "Could not open this page";
        				int toastMsgTime = 2000; //3.5 seconds
        				int fadeInTime = 500; //0.5 seconds
        				int fadeOutTime= 500; //0.5 seconds
        				Toast.makeText(Main.mainstage, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
        			
        				
        			}
            	return cmdPane;
            }
        };

        loadTask.setOnSucceeded(e -> {
        
          
			MainancController.mainanc1.getChildren().setAll(loadTask.getValue());
			
        });
        Thread thread = new Thread(loadTask);
		thread.start();

	}
	

	
	

}
