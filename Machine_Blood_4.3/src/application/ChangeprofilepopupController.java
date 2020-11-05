package application;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import toast.MyDialoug;
import toast.Toast;

import com.google.cloud.firestore.DocumentReference;
import com.google.firebase.tasks.OnCompleteListener;
import com.google.firebase.tasks.OnFailureListener;
import com.google.firebase.tasks.Task;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;

import extrafont.Myfont;
import firebase.FirebaseConnect;

public class ChangeprofilepopupController implements Initializable {
	
	 @FXML
	    private JFXPasswordField txtconfirmpass;

	    @FXML
	    private Button btnsave,btncancel;
	    
	    @FXML
	    Label lblerror;

	    
	    @FXML
	    private JFXSpinner loadicon;

	    @FXML
	    private Label loadlab;
	    
	    Myfont f=new Myfont(22);

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		
		//setLabelFont();
		
		
		btncancel.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MyDialoug.closeDialoug();
				
			}
		});
		
		btnsave.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub


				lblerror.setVisible(false);
				if(txtconfirmpass.getText().equals(Myapp.oldpass))
				{

					loadicon.setVisible(true);
					loadlab.setVisible(true);
				if(EditeprofileController.whichIsLastClicked == 1)
				{
					updateOnlineEmail();
				}
				else {
					
					updateOnlinePassword();
				}
					setTimer();
				
				
				}
				else
				{
					
					lblerror.setVisible(true);
					lblerror.setText("Wrong Password");
					System.out.println("Wrong Pass");
				}
							
			}
		});
				
	}

	void setLabelFont()
	{
		btnsave.setFont(f.getM_M());
		txtconfirmpass.setFont(f.getM_M());

	}
	
	
	void updatepass()
	{
		
		Database d=new Database();
		
			  String updatequry="update userinfo set pass='"+EditeprofileController.txtnewpassdup.getText()+"' where uid='"+Myapp.uid+"'";
			    
			  if(d.Insert(updatequry))
				{
					MyDialoug.closeDialoug();
					Myapp.oldpass=""+EditeprofileController.txtnewpassdup.getText();
					Toast.makeText(Main.mainstage, "Succefully updated password", 1500, 500, 500);
					
				}
			   else 
			   {
				System.out.println("Not Updated");   
			   }
	

	}
	
	
	void updateOnlineEmail()
	{
	
		DocumentReference reff = FirebaseConnect.db.collection("users").document(Myapp.uid);
		Map<String,Object> dd=new HashMap<>();
		dd.put("email", EditeprofileController.txtemaildup.getText());
		reff.update(dd).addListener(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		}, new Executor() {
			
			@Override
			public void execute(Runnable arg0) {

				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
					
						updateemail();
					}
				});
			}
		});
	}
	
	
	void setTimer()
	{
	    Timer timer = new Timer();
	    TimerTask task = new TimerTask()
	    {
	            public void run()
	            {
	            	System.out.println("Connection : "+Myapp.isInternetConnected.get());
	            Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(Myapp.isInternetConnected.get()==false)
		            	{
		            		loadicon.setVisible(false);
		            		loadlab.setText("Please check your internet connection");
		            	}
					}
				});	
	            }

	    };
	    timer.schedule(task, 15000);
	}
	
	void updateOnlinePassword()
	{
		
		DocumentReference reff = FirebaseConnect.db.collection("users").document(Myapp.uid);
		Map<String,Object> dd=new HashMap<>();
		dd.put("pass", EditeprofileController.txtnewpassdup.getText());
		reff.update(dd).addListener(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		}, new Executor() {
			
			@Override
			public void execute(Runnable arg0) {

				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
					
						updatepass();
					}
				});
			}
		});
	
	}
	
	void updateemail()
	{
		Database d=new Database();
		
		
			  String updatequry="update userinfo set email='"+EditeprofileController.txtemaildup.getText()+"' where uid='"+Myapp.uid+"'";			    
			  if(d.Insert(updatequry))
				{
					MyDialoug.closeDialoug();
					Toast.makeText(Main.mainstage, "Succefully updated email id", 1500, 500, 500);		
					
				}
			   else 
			   {
				System.out.println("Not Updated");   
			   }
			
		

	}
	
	
}
