package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import toast.MyDialoug;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

public class infoController implements Initializable {

	@FXML
	private Button btnclose;

	@FXML
	private Button btnok;

	@FXML
	private Button btnmanual;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		btnclose.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				MyDialoug.closeDialoug();
			}
		});

		btnok.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				MyDialoug.closeDialoug();
			}
		});

		btnmanual.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				 
					MyDialoug.closeDialoug();
						try {
							Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "Installationmanual.pdf");
						
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				
			}
		});

	}

}
