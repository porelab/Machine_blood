package application;

import java.net.URL;
import java.util.ResourceBundle;

import toast.Openscreen;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import eu.hansolo.medusa.Gauge.SkinType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class xxController implements Initializable {

	@FXML
	AnchorPane ancregu1;
	
	@FXML
	Button back;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		Gauge gauge1 = GaugeBuilder.create()
                .skinType(SkinType.SIMPLE)
                .sections(new Section(0, 16.66666, "0", Color.web("#11632f")),
                          new Section(16.66666, 33.33333, "1", Color.web("#36843d")),
                          new Section(33.33333, 50.0, "2", Color.web("#67a328")),
                          new Section(50.0, 66.66666, "3", Color.web("#80b940")),
                          new Section(66.66666, 83.33333, "4", Color.web("#95c262")),
                          new Section(83.33333, 100.0, "5", Color.web("#badf8d")))
               // .title("700")
                .value(20)
                .threshold(50)
                .animated(true)
                .build();
 				 gauge1.setPrefSize(ancregu1.getPrefWidth(), ancregu1.getPrefHeight());
 ancregu1.getChildren().add(gauge1);
 
 
 back.setOnAction(new EventHandler<ActionEvent>() {
	
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Openscreen.open("/application/first.fxml");

	}
});
 
		
	}

}
