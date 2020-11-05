package login;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import extrafont.Myfont;
import application.NFirstController;
import javafx.event.ActionEvent;
import javafx.event.Event;
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

public class LoginController implements Initializable {
	@FXML
	ImageView uicon;
	
	@FXML
	Button btnlogin;
	
	@FXML
	TextField txtemail,txtpass;

	@FXML
	Label error,lblcact,lbltime;
	
	Database d=new Database();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
System.out.println("login");
DataStore.isconfigure.set(true);    

		lbltime.textProperty().bind(Systemtime.currenttime);
		
		
		 Image image = new Image(this.getClass().getResourceAsStream("/login/login.PNG"));
	     uicon.setImage(image);

	     Myfont f=new Myfont(20);	     
	     btnlogin.setFont(f.getM_M());
	     
	     login();
	     
		lblcact.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				 Openscreen.open("/login/signupn.fxml");	
			}
		});
		

		
		//Checklogin();

		
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
						
				if(!d.isExist("select * from userinfo where email='"+txtemail.getText()+"'"))
				{
				
					Toast.makeText(Main.mainstage, "Please eneter Conrrect Email id.", 3000, 500, 500);
					 
				
				}
				else
				{
					if(!d.isExist("select * from userinfo where email='"+txtemail.getText()+"' and pass='"+txtpass.getText()+"'"))
					{
						System.out.println("Wrong pass");
						Toast.makeText(Main.mainstage, "Please eneter Conrrect password.", 3000, 500, 500);
					//	error.setVisible(true);
					}
					
					else
					{
						
						
						System.out.println("Already Exist ACT-->>>>");

						String updatequry="update userinfo set login='yes' where email='"+txtemail.getText()+"'";
						if(d.Insert(updatequry))
						{
							  Database d1=new Database();
							  List<List<String>> alldata=d1.getData("select * from userinfo where email='"+txtemail.getText()+"' ");
						
							
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
					   
				}
				
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
