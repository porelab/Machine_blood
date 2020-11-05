package toast;

import com.jfoenix.controls.JFXSpinner;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

// for customized notification


public class Customtoast 
{
	Stage ownerStage;
	String toastMsg;
	 Stage toastStage;
	 int fadeOutDelay;
	 int fadeInDelay;
	 Text text;
	public Customtoast(Stage ownerStage, String toastMsg,int fadeInDelay, int fadeOutDelay,boolean isloder)
	{
		this.fadeInDelay=fadeInDelay;
		this.fadeOutDelay=fadeOutDelay;
		ownerStage.getScene().setFill(Color.TRANSPARENT);
		this.ownerStage=ownerStage;
		this.toastMsg=toastMsg;
	
		    toastStage=new Stage();
	        toastStage.initOwner(ownerStage);
	        toastStage.setResizable(false);
	        toastStage.initStyle(StageStyle.TRANSPARENT);

	       text = new Text(toastMsg);
	        text.setFont(Font.font("Verdana", 20));
	        text.setFill(Color.WHITE);

	        JFXSpinner sp=new JFXSpinner();
	        HBox h=new HBox(10);
	        h.getChildren().addAll(sp,text);
	        StackPane root = new StackPane(h);
	        root.setStyle("-fx-background-radius: 20; -fx-background-color: rgba(0, 0, 0, 0.4); -fx-padding: 10 20 10 20;");
	        root.setOpacity(0);

	        Scene scene = new Scene(root);
	        scene.setFill(Color.TRANSPARENT);
	        toastStage.setScene(scene);
	       
		
	}
	
	public void setMessage(String msg)
	{
		text.setText(msg);
	}
	
	public void show()
	{
		 toastStage.show();

	        Timeline fadeInTimeline = new Timeline();
	        KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(fadeInDelay), new KeyValue (toastStage.getScene().getRoot().opacityProperty(), 1)); 
	        fadeInTimeline.getKeyFrames().add(fadeInKey1);   
	        fadeInTimeline.play();
	
	}
	public void close()
	{
	    Timeline fadeOutTimeline = new Timeline();
        KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(fadeOutDelay), new KeyValue (toastStage.getScene().getRoot().opacityProperty(), 0)); 
        fadeOutTimeline.getKeyFrames().add(fadeOutKey1);   
        fadeOutTimeline.setOnFinished((aeb) -> toastStage.close()); 
        fadeOutTimeline.play();
	}
	

}
