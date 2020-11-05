package application;



import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class KeyboardController implements Initializable {


	@FXML
	AnchorPane keyanc;
	
	@FXML
	TextField  keyboardinput;
	
	@FXML
	Label lblkeyboard;
	    
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		  
		Myapp.keyboardinput=keyboardinput;
		
		 Myapp.lblkeyboard=lblkeyboard;
		
		 VirtualKeyboard vkb = new VirtualKeyboard(); 
			
			keyanc.getChildren().addAll(vkb.view());
	
					
}
}
