package userinput;

import java.net.URL;
import java.util.ResourceBundle;

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
import toast.MyDialoug;
import toast.Openscreen;
import toast.Toast;
import application.DataStore;
import application.Main;
import application.MainancController;
import application.SerialWriter;
import application.writeFormat;
import extrafont.Myfont;

public class WetpopupController implements Initializable {

	@FXML
	AnchorPane root;

	@FXML
	private Button btnyes,btnskip;

	@FXML
	private Button btncancel;

	Myfont f = new Myfont(22);
	
	@FXML
	Label lbl;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	/*Wet test Popup*/
		
		System.out.println("Data  :"+MyDialoug.inputdata);
		String msg=MyDialoug.inputdata.get("test").toString();
		
		
		lbl.setText("Start test for "+msg+" sample");
	
		btncancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0)
			{
				MyDialoug.data="exit";				
				MyDialoug.closeDialoug();	
			}
		});

		btnyes.setOnAction(new EventHandler<ActionEvent>() 
		{

			@Override
			public void handle(ActionEvent arg0) {
				MyDialoug.data="start";
				MyDialoug.closeDialoug();
			}
		});

		
		btnskip.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				MyDialoug.data="skip";
				MyDialoug.closeDialoug();
			}
		});
	}

	void setLabelFont() {
		btncancel.setFont(f.getM_M());
		btnyes.setFont(f.getM_M());

	}

	void gotohome() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"/application/first.fxml"));
		try {
			Pane cmdPane = (Pane) fxmlLoader.load();

			MainancController.mainanc1.getChildren().clear();
			MainancController.mainanc1.getChildren().add(cmdPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
