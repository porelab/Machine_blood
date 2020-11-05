package userinput;

import java.net.URL;
import java.util.ResourceBundle;

import application.Myapp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import myconstant.Myconstant;
import toast.MyDialoug;

public class TestinfopopupContoller implements Initializable {
	
	
		@FXML
	    private AnchorPane root;

	    @FXML
	    private Button btnclose;


	    @FXML
	    private Label lblsname,lblsplate,lblfluid,lblbptthresold,lbltfactore,lblbptacc,lbltestpressure;

	    
	  	@Override
	  	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		lblsname.setText(Myconstant.sampleid);
				
		if (Myapp.splate.equals("Small")) {
			lblsplate.setText(Myapp.splate +" : 1");
			
			} else if (Myapp.splate.equals("Large")) {

				lblsplate.setText(Myapp.splate +" : 7.2");
				} else {

					lblsplate.setText(Myapp.splate +" : 2.3");
			}
		
		
			
		if (Myapp.thresold.equals("First Bubble")) {

			lblbptthresold.setText(Myapp.thresold+" : 1000");

		} else if (Myapp.thresold.equals("Moderate")) {

			lblbptthresold.setText(Myapp.thresold+" : 3000");
		} else if (Myapp.thresold.equals("Continous")) {

			lblbptthresold.setText(Myapp.thresold+" : 5000");

		}
		
		
	
		lbltfactore.setText(""+Myapp.tfactore);
		lblfluid.setText(Myapp.fluidname+" : "+Myapp.fluidvalue+" mN/m");
		lblbptacc.setText(Myapp.accbpt+" %");
		lbltestpressure.setText(Myapp.endpress+" psi");
		
		btnclose.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
			
				MyDialoug.closeDialoug();
			}
		});
		
	}

}
