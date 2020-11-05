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
import javafx.application.Platform;
import javafx.beans.binding.SetBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;

public class NExitpopupController implements Initializable {
	
	@FXML
	    private Button btnstart,btncancel;
	
	@FXML
	AnchorPane root1;

	Database db=new Database();
	
	    Myfont f=new Myfont(25);

	    void addShortCut() {
			
					root1.setOnKeyPressed(new EventHandler<KeyEvent>() {
						@Override
						public void handle(KeyEvent ke) {
							
							if(ke.getCode()==KeyCode.ENTER)
							{
								MyDialoug.closeDialoug();
											System.exit(0);
											Platform.exit();
											
								
								Main.mainstage.close();
							}
							
						}
					});

			}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		//setLabelFont();
		
		addShortCut();
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

				MyDialoug.closeDialoug();
				System.exit(0);
				Platform.exit();
				
	
	Main.mainstage.close();
				
			}
		});
				
	}


	
	void setLabelFont()
	{
		btncancel.setFont(f.getM_M());
		btnstart.setFont(f.getM_M());

	}
	
	
	
	
}
