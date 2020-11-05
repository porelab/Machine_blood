package toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Duration;

// for printing date in all page

public class Systemtime {

	public static SimpleStringProperty currenttime=new SimpleStringProperty();
	
	public static void StartTime()
	{
		getTime();
		Timeline timeline = new Timeline(new KeyFrame(
		        Duration.millis(60*1000),
		        ae -> getTime()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
	
	static void getTime()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy | hh:mm aa");
		String formattedDate = dateFormat.format(new Date()).toString();
		//System.out.println(formattedDate);
		currenttime.set(formattedDate);
	}
	
	
	
}
