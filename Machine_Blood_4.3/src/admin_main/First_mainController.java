package admin_main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import toast.Openscreen;
import toast.Systemtime;

public class First_mainController implements Initializable {

    @FXML
    private Button btnuser,btnfiles,btnorder,btnmainback;

    
    
    @FXML
    private ImageView imgback;

    @FXML
    private Label lbltimedate;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		lbltimedate.textProperty().bind(Systemtime.currenttime);
		setMainBtns();
		setBtnClicks();
	}

	/*set image in button*/
	void setMainBtns()
	{
		
		Image image = new Image(this.getClass().getResourceAsStream("/admin_main/users.png"));
		ImageView imageView = new ImageView(image);
	 	imageView.setFitWidth(140);
		imageView.setFitHeight(150);
		btnuser.setGraphic(imageView);
       
       	Image image1 = new Image(this.getClass().getResourceAsStream("/admin_main/order.png"));
		ImageView imageView1 = new ImageView(image1);
	 	imageView1.setFitWidth(140);
		imageView1.setFitHeight(150);
		btnorder.setGraphic(imageView1);
      
      	Image image2 = new Image(this.getClass().getResourceAsStream("/admin_main/file.png"));
		ImageView imageView2 = new ImageView(image2);
	 	imageView2.setFitWidth(140);
		imageView2.setFitHeight(150);
		btnfiles.setGraphic(imageView2);
		
		Image image3 = new Image(this.getClass().getResourceAsStream(
				"/admin_main/back-white.png"));
		imgback.setImage(image3);
     
      
	}

	/*All button click event*/
	void setBtnClicks()
	{
		btnmainback.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Openscreen.open("/application/first.fxml");
			}
		});
		
		btnuser.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				Openscreen.open("/admin_user/first_user.fxml");
				
			}
		});

		btnorder.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				Openscreen.open("/admin_order/first_order.fxml");
				
			}
		});

		btnfiles.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Openscreen.open("/admin_analytics/first_analytics.fxml");
			}
		});

		
	}
	
}
