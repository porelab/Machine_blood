package application;

import java.net.URL;
import java.util.ResourceBundle;

import toast.MyDialoug;
import toast.Openscreen;
import application.Database;
import application.Main;
import application.Myapp;

import com.jfoenix.controls.JFXTextField;

import extrafont.Myfont;
import javafx.beans.binding.SetBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class NLogoutpopupController implements Initializable {
	
	@FXML
	    private Button btnstart,btncancel;

	Database db=new Database();
	
	    Myfont f=new Myfont(25);

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
		
		btnstart.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				logout();
				MyDialoug.closeDialoug();
				
			}
		});
				
	}

	public void logout()
	{
		   String updatequry="update userinfo set login=' ' where uid='"+Myapp.uid+"'";
			if(db.Insert(updatequry))
			{
		   FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login/Onlinelogin.fxml"));
			try {
			Pane cmdPane = (Pane) fxmlLoader.load();
	 		
			MainancController.mainanc1.getChildren().clear();
			MainancController.mainanc1.getChildren().add(cmdPane);
			} 
			catch (Exception e)
			{
			 	e.printStackTrace();
			}
		}

	}

	
	void setLabelFont()
	{
		btncancel.setFont(f.getM_M());
		btnstart.setFont(f.getM_M());

	}
	
	
	
	
}
