package login;

import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import toast.Openscreen;
import toast.Systemtime;
import toast.Toast;
import application.DataStore;
import application.Database;
import application.Main;
import application.Myapp;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.jfoenix.controls.JFXSpinner;

import extrafont.Myfont;
import firebase.FirebaseConnect;

public class OnlineLogin implements Initializable {
	@FXML
	ImageView uicon;
	
	@FXML
	Button btnlogin;
	
	@FXML
	TextField txtemail,txtpass;

	@FXML
	Label error,lbltime;
	
	@FXML
    private JFXSpinner loadicon;

    @FXML
    private Label lblcreatea;
    
    boolean isInternet=false;
    
	Database d=new Database();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
System.out.println("Online login");
DataStore.isconfigure.set(true);    

		lbltime.textProperty().bind(Systemtime.currenttime);
		
		
		 Image image = new Image(this.getClass().getResourceAsStream("/login/login.png"));
	     uicon.setImage(image);

	     Myfont f=new Myfont(20);	     
	     btnlogin.setFont(f.getM_M());
	     
	     login();
			
		//Checklogin();

		
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
		            		lblcreatea.setText("It takes too time\nplease check your internet connection");
		            		lblcreatea.setVisible(true);
		            		btnlogin.setDisable(false);
		            	}
					}
				});	
	            }

	    };
	    timer.schedule(task, 15000);
	}
	void setTimerforlogin()
	{
	    Timer timer = new Timer();
	    TimerTask task = new TimerTask()
	    {
	            public void run()
	            {
	            	
	            
	            	new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								
								onlineLoginMethod();
							} catch (UnknownHostException e) {
								// TODO Auto-generated catch block
								System.out.println("Error");
								e.printStackTrace();
							}
					   	
						}
					}).start();
		
							
					
	            }

	    };
	    timer.schedule(task, 1000);
	}
	
	void setTimerforInternetCheck()
	{
	    Timer timer = new Timer();
	    TimerTask task = new TimerTask()
	    {
	            public void run()
	            {
	            	
	            	
							// TODO Auto-generated method stub
							Platform.runLater(new Runnable() {
								
								@Override
								public void run() {
									
									if(!isInternet)
									{
					    	loadicon.setVisible(false);
							lblcreatea.setText("No internet connection");
							lblcreatea.setVisible(true);
							btnlogin.setDisable(false);
									}
								}
							});
							
						
	            
	            
					
	            }

	    };
	    timer.schedule(task, 2000);
	}
	
	
	void onlineLoginMethod() throws UnknownHostException
	{
		try{
			
			Query query = FirebaseConnect.db.collection("users").whereEqualTo("email",txtemail.getText()).whereEqualTo("pass", txtpass.getText());;
			System.out.println("ls1");
			ApiFuture<QuerySnapshot> querySnapshot = query.get();
			System.out.println("ls2");
			
	if(querySnapshot.get().getDocuments().size()>0)
	{
		isInternet=true;

		DocumentSnapshot dd=querySnapshot.get().getDocuments().get(0);
		  Map<String,Object> data=(Map<String, Object>)dd.getData();
		  insertTable(data.get("userid")+"",data.get("email")+"",data.get("pass")+"",data.get("name")+"",data.get("companyname")+"");
          //	Toast.makeText(Main.mainstage, "Login Successfully", 2000, 100, 100);
  			System.out.println("login successfully");
          
		
		
	}
	else
	{
		isInternet=true;
		Query query1 = FirebaseConnect.db.collection("users").whereEqualTo("userid",txtemail.getText()).whereEqualTo("pass", txtpass.getText());;
		ApiFuture<QuerySnapshot> querySnapshot1 = query1.get();
		if(querySnapshot1.get().getDocuments().size()>0)
		{
			DocumentSnapshot dd1=querySnapshot1.get().getDocuments().get(0);
			  Map<String,Object> data=(Map<String, Object>)dd1.getData();
			  insertTable(data.get("userid")+"",data.get("email")+"",data.get("pass")+"",data.get("name")+"",data.get("companyname")+"");
              //	Toast.makeText(Main.mainstage, "Login Successfully", 2000, 100, 100);
      			System.out.println("login successfully");
			
		}
		else
		{
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
        	loadicon.setVisible(false);
			lblcreatea.setText("Wrong credentials");
			lblcreatea.setVisible(true);
			btnlogin.setDisable(false);
				}
			});
		}

	}
	
	
	}
	catch(Exception e)
	{
		System.out.println("ls3");
		
		
		System.out.println("Error 103 : "+e.getMessage());
		e.printStackTrace();
	}
	}
	
	
	void checkLoginWithEmail()
    {
	
		
	//setTimer();
	Platform.runLater(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			loadicon.setVisible(true);
			lblcreatea.setText("login...");
			lblcreatea.setVisible(true);
				btnlogin.setDisable(true);
				
				try{
				setTimerforInternetCheck();	
				setTimerforlogin();
				}
				catch(Exception e)
				{
					System.out.println("Error 11112: "+e.getMessage());
				}
		}
	});
	


    }

	
	
	void Offlinelogin()
	{
				
					boolean isId=false;	
				if(d.isExist("select * from userinfo where email='"+txtemail.getText()+"' and pass='"+txtpass.getText()+"'"))
				{
					String updatequry="update userinfo set login='yes' where email='"+txtemail.getText()+"'";
					if(d.Insert(updatequry))
					{
						  Database d1=new Database();
						  List<List<String>> alldata=d1.getData("select * from userinfo where email='"+txtemail.getText()+"' and pass='"+txtpass.getText()+"' ");
					
						
						 Myapp.email=""+txtemail.getText();
						 Myapp.pass=""+txtpass.getText();
						Myapp.username=alldata.get(0).get(3);
						Myapp.industryname=alldata.get(0).get(5);
						Myapp.uid=alldata.get(0).get(6);
						 Myapp.status=alldata.get(0).get(4);
						 
						 //Industry Application //
					        

						 System.out.println("DATA"+updatequry);
						
						 Openscreen.open("/application/first.fxml");
							
						
					}
				}
				else if(d.isExist("select * from userinfo where uid='"+txtemail.getText()+"' and pass='"+txtpass.getText()+"'"))
				{
					String updatequry="update userinfo set login='yes' where uid='"+txtemail.getText()+"'";
					if(d.Insert(updatequry))
					{
						  Database d1=new Database();
						  List<List<String>> alldata=d1.getData("select * from userinfo where uid='"+txtemail.getText()+"' ");
					
						
						 Myapp.email=""+txtemail.getText();
						 Myapp.pass=""+txtpass.getText();
						Myapp.username=alldata.get(0).get(3);
						Myapp.industryname=alldata.get(0).get(5);
						Myapp.uid=alldata.get(0).get(6);
						 Myapp.status=alldata.get(0).get(4);
						 
						 //Industry Application //
					        

						 System.out.println("DATA"+updatequry);
						
						 Openscreen.open("/application/first.fxml");
							
						
					}
				}
				else
				{
					checkLoginWithEmail();
				}
				

	}
	
	
	void insertTable(String uid,String email,String pass,String name,String indname)
	{
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Database d=new Database();  
				Myapp.uid=uid;
				
				
				
				if(!d.isExist("select * from userinfo where uid='"+uid+"'"))
				{
				String insertQuery="INSERT INTO userinfo VALUES('"+email+"','"+pass+"','"+"yes"+ "','"+name+"','true','"+indname+"','"+uid+"')";
				if(d.Insert(insertQuery))
				{
					
					
					  Database d1=new Database();
					  List<List<String>> alldata=d1.getData("select * from userinfo where uid='"+uid+"' ");
				
					
					 Myapp.email=alldata.get(0).get(0);
					 Myapp.pass=alldata.get(0).get(1);
					Myapp.username=alldata.get(0).get(3);
					Myapp.industryname=alldata.get(0).get(5);
					Myapp.uid=alldata.get(0).get(6);
					 Myapp.status=alldata.get(0).get(4);
					 Openscreen.open("/application/first.fxml");
						
						
					 
				}
				else
				{
					loadicon.setVisible(false);
					lblcreatea.setText("try again");
					lblcreatea.setVisible(true);
				}
				}
				else
				{
					
					String insertQuery="INSERT INTO userinfo VALUES('"+email+"','"+pass+"','"+"yes"+ "','"+name+"','true','"+indname+"','"+uid+"')";
					
					String upquery="update userinfo set email='"+email+"',pass='"+pass+"',name='"+name+"',login='yes',indname='"+indname+"' where uid='"+uid+"'";
					if(d.Insert(upquery))
					{
						
						 Database d1=new Database();
						  List<List<String>> alldata=d1.getData("select * from userinfo where uid='"+uid+"' ");
					
						
						 Myapp.email=alldata.get(0).get(0);
						 Myapp.pass=alldata.get(0).get(1);
						 Myapp.username=alldata.get(0).get(3);
						 Myapp.industryname=alldata.get(0).get(5);
						 Myapp.uid=alldata.get(0).get(6);
						 Myapp.status=alldata.get(0).get(4);
						 Openscreen.open("/application/first.fxml");
							
							
						
					}
					else
					{
						loadicon.setVisible(false);
						lblcreatea.setText("try again");
						lblcreatea.setVisible(true);
					}	
					
					
				}
				
				
			}
		});
	}

	
	void login()
	{
		btnlogin.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				System.out.println("Login Server");
				if(!txtemail.getText().trim().equals("")&& !txtpass.getText().trim().equals(""))
				{

					isInternet=false;
					Offlinelogin();
				
				}
				else {
					Toast.makeText(Main.mainstage, "Fill up the required details.", 3000, 500, 500);
				}
				
				}
			});

	}
	
	private final class EscapeKeyHandler implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent event) {

			// the ESCAPE key lets the user reset the zoom level
			if (KeyCode.ENTER.equals(event.getCode())) {
				
				login();
			}
		}

	
	}

}
