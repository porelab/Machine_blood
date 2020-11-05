package toast;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class infoController implements Initializable {
	@FXML
	Label errorname, errordescri, errorcode;

	@FXML
	Button btnclose, btnhelp, btnok;
	
	static String errname,errdes,errcode;

	static boolean isHome=false;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
			btnclose.getStyleClass().add("transperant_comm");
			
			errorname.setText(errname);
			errordescri.setText(errdes);
			errorcode.setText(errcode);
		
		btnclose.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				if(isHome)
				{

					Openscreen.open("/application/first.fxml");	
				}

				MyDialoug.closeDialoug();
			}
		});

		btnok.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				
				if(isHome)
				{

					Openscreen.open("/application/first.fxml");	
				}

				MyDialoug.closeDialoug();
			}
		});

		btnhelp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				MyDialoug.closeDialoug();
				try {
					Runtime.getRuntime().exec(
							"rundll32 url.dll,FileProtocolHandler "
									+ "Installationmanual.pdf");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

}
