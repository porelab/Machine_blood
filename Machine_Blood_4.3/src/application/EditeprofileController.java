package application;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import toast.MyDialoug;
import toast.Openscreen;
import toast.Systemtime;
import toast.Toast;

import com.google.cloud.firestore.DocumentReference;
import com.google.firebase.tasks.OnCompleteListener;
import com.google.firebase.tasks.OnFailureListener;
import com.google.firebase.tasks.Task;

import firebase.FirebaseConnect;

public class EditeprofileController implements Initializable {

	MyDialoug mydia;
	
		@FXML
	    private Label txtuname,lblerror,lbluid,lbltime;

		@FXML
		TextField txtindname,txtuname1,txtnewpass,txtemail;
		
		static TextField txtnewpassdup,txtemaildup;
		
	    @FXML
	    private Button btnind,btnpass,btnname,btnback,btnemail,btnlogout;

	    Database d=new Database();
	    
	   static int whichIsLastClicked = -1;
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		lbltime.textProperty().bind(Systemtime.currenttime);

		
		txtnewpassdup=txtnewpass;
		txtemaildup=txtemail;
	
		getuserprofile();
		buttonclick();
	
btnlogout.setOnAction(new EventHandler<ActionEvent>() {
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		logoutpopup();
	}
});
	
	
	}
	
	public void logoutpopup()
	{
	
		mydia=new MyDialoug(Main.mainstage, "/application/Nlogoutpopup.fxml");
		mydia.showDialoug();
	}
	
	void getuserprofile()
	{

		List<List<String>> alldata=d.getData("select * from userinfo where uid='"+Myapp.uid+"'");
		System.out.println("alalalalal"+alldata);
		
		txtindname.setText(alldata.get(0).get(5));
		txtuname.setText(alldata.get(0).get(3));
		txtuname1.setText(alldata.get(0).get(3));
		txtemail.setText(alldata.get(0).get(0));
		lbluid.setText(alldata.get(0).get(6));
		Myapp.oldpass=""+alldata.get(0).get(1);
	}

	public void buttonclick()
	{		
		btnind.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent arg0) {
				updateOnlineCompanyname();
			   }
		});

		btnname.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent arg0) {
				 updateOnlineUsername();
			   }
		});

		btnemail.setOnAction(new EventHandler<ActionEvent>() {
			
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

				whichIsLastClicked=1;
		        
				if(!txtemail.getText().trim().equals(""))
				{

				MyDialoug mm=new MyDialoug(Main.mainstage, "/application/changeprofilepopup.fxml");	
				 mm.showDialoug();
				}
				else
				{
					Toast.makeText(Main.mainstage, "Please enter email", 1500, 500, 500);

				}

				
			}
		});
		
		btnpass.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent arg0) {
				whichIsLastClicked=2;
		        
			if(!txtnewpass.getText().trim().equals(""))
				{
				MyDialoug mm=new MyDialoug(Main.mainstage, "/application/changeprofilepopup.fxml");	
				 mm.showDialoug();
				}
				else
				{
					Toast.makeText(Main.mainstage, "Please enter all details", 1500, 500, 500);

				}
			}
		});

		btnback.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Openscreen.open("/application/first.fxml");
			}
		});
		
	}
	
	
	
	void updateOnlineCompanyname()
	{
		
		DocumentReference reff = FirebaseConnect.db.collection("users").document(Myapp.uid);
		Map<String,Object> dd=new HashMap<>();
		dd.put("companyname", txtindname.getText());
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
					
    					 String updatequry="update userinfo set indname='"+txtindname.getText()+"'  where uid='"+Myapp.uid+"'";
						    System.out.println("Updated Industry----->"+updatequry);
						  if(d.Insert(updatequry))
							{
							
							  	System.out.println(" Updated inds");
							    Myapp.industryname=txtindname.getText();
									
								Toast.makeText(Main.mainstage, "Industry name successfully updated", 1000, 100, 100);
							}
						   else 
						   {
							System.out.println("Not Updated");   
						   }
					}
				});
			}
		});
		
		
	}
	
	
	void updateOnlineUsername()
	{
		
		DocumentReference reff = FirebaseConnect.db.collection("users").document(Myapp.uid);
		Map<String,Object> dd=new HashMap<>();
		dd.put("name", txtuname1.getText());
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
					
						 String updatequry="update userinfo set name='"+txtuname1.getText()+"'  where email='"+Myapp.email+"'";
						    
						  if(d.Insert(updatequry))
							{
							  txtuname.setText(txtuname1.getText());
							  Myapp.username=txtuname1.getText();
								Toast.makeText(Main.mainstage, "User name successfully updated", 1000, 100, 100);
								}
						   else 
						   {
							System.out.println("Not Updated");   
						   }
					}
				});
			}
		});
	
	}
	
}
