package login;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import extrafont.Myfont;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import toast.Openscreen;
import toast.Systemtime;
import toast.Toast;
import application.Database;
import application.Main;
import application.Myapp;

public class SignupController implements Initializable {
	
	@FXML
	ImageView imgbgu,imguicon;

	@FXML
    TextField txtuname,txtemail,txtindname;

    @FXML
    private PasswordField txtpass;

    @FXML
    private Button btncreatact;

    @FXML
    private Label error,lbllogin,lbltime;
    
	Database d=new Database();

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
    	Image image;
    	
    	lbltime.textProperty().bind(Systemtime.currenttime);
    	
		 image = new Image(this.getClass().getResourceAsStream("/login/new account.png"));
	     imgbgu.setImage(image);
	     
	     image = new Image(this.getClass().getResourceAsStream("/login/login.png"));
	     imguicon.setImage(image);
		
	     Myfont f=new Myfont(20);	     
	     btncreatact.setFont(f.getM_M());

		
		lbllogin.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				
				Openscreen.open("/login/loginn.fxml");
				
			}
		});
		
		btncreatact.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				System.out.println("Login Server");
			
				if(!txtindname.getText().trim().equals("")&& !txtuname.getText().trim().equals("")&& !txtemail.getText().trim().equals("")&& !txtpass.getText().trim().equals(""))
				{
				if(!d.isExist("select * from userinfo where email='"+txtemail.getText()+"'"))
				{
				
				d.Insert("INSERT INTO userinfo VALUES('"+txtemail.getText()+"','"+txtpass.getText()+"','"+"yes"+ "','"+txtuname.getText()+"','false','"+txtindname.getText()+"','')");
				
				 Myapp.username=""+txtuname.getText();
				 Myapp.email=""+txtemail.getText();
				 Myapp.pass=""+txtpass.getText();
				 Myapp.uid="";
				 Myapp.status="false";

					Myapp.industryname=""+txtindname.getText();
					
					 File f=new File("CsvFolder");
					 if(!f.isDirectory())
					 {
						 f.mkdir();
						 System.out.println("Dir csv folder created");
					 }
					 f=new File("CsvFolder/"+Myapp.email);
						if(!f.isDirectory())
						{
								f.mkdir(); 	
						}
							
						
						
						
				 
				 Toast.makeText(Main.mainstage, "Account successfully created", 3000, 500, 500);
				 
				 Openscreen.open("/application/first.fxml");
				 System.out.println("Succesfully Created new Account.->Email"+Myapp.email+"--Pass--"+Myapp.pass);
				
				}
				else
				{
				
				Toast.makeText(Main.mainstage, "Email id already exist.", 3000, 500, 500);
					   
				}
				}
				else {
					
					Toast.makeText(Main.mainstage, "Fill up the required details", 3000, 500, 500);
					
				}
				
				}
			});
		
		
		
    }			
		}