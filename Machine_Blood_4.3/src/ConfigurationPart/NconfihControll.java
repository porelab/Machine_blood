package ConfigurationPart;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXToggleButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;

public class NconfihControll implements Initializable {
	
	@FXML
	JFXToggleButton tgb2;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		tgb2.setOnAction(new EventHandler<ActionEvent>() {
		      @Override
		      public void handle(ActionEvent actionEvent) {
		        JFXToggleButton source = (JFXToggleButton) actionEvent.getSource();
		        if (source.isSelected())
		        {
		        	
		         System.out.println("High");
		        
		        } else {
			    
		        	System.out.println("LOW");		
			   
		        }
		      }
		    });
		
	}

}
