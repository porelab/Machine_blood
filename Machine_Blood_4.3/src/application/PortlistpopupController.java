package application;

import gnu.io.CommPortIdentifier;

import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;

import toast.MyDialoug;
import toast.Openscreen;
import toast.Toast;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class PortlistpopupController implements Initializable {

	@FXML
	Button btncancel;

	@FXML
	private ComboBox<String> cmbcom;

	@FXML
	private Button btnapply, btnrefrace;

	@FXML
	private Label lblcmb;

	MyDialoug mydia;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setPortList();
		btncancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			
			public void handle(ActionEvent arg0) {
				
				mydia.closeDialoug();

			}
		});

		btnapply.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				comsave();
			
			}
		});

		btnrefrace.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				setPortList();
			}
		});

	}

	public void setPortList() {
		//System.out.println("Loading list of ports");
		cmbcom.getItems().clear();
		Enumeration pList = CommPortIdentifier.getPortIdentifiers();

		while (pList.hasMoreElements()) {

			CommPortIdentifier cpi = (CommPortIdentifier) pList.nextElement();
			System.out.print("Port " + cpi.getName() + " " + cpi.getPortType());
			cmbcom.getItems().add(cpi.getName());

		}
		
		if(cmbcom.getItems().size()==0)
		{
			lblcmb.setText("No Ports found");
		}
		else
		{
			lblcmb.setText("");
		}
		
		

	}

	void comsave() {
		try {
			if (cmbcom.getSelectionModel().getSelectedItem() == null) {
				lblcmb.setText("No Ports found");
			} else {
				// notifier.notify(
				// NotificationBuilder.create().title("Save.").message(cmbcom.getSelectionModel().getSelectedItem()+" save.").image(Notification.SUCCESS_ICON).build());
				Toast.makeText(Main.mainstage, "Successfully saved "
						+ cmbcom.getSelectionModel().getSelectedItem(), 1000,
						200, 200);

				System.out.println("Com set to:"
						+ cmbcom.getSelectionModel().getSelectedItem());

				String query = "update configdata set comport='"
						+ cmbcom.getSelectionModel().getSelectedItem() + "'";

				Database dd = new Database();
				dd.Insert(query);
				lblcmb.setText(""
						+ cmbcom.getSelectionModel().getSelectedItem());
				mydia.closeDialoug();

				Myapp.restartApp();
			}
		} catch (Exception e) {

			lblcmb.setText("something wrongs.");
		}


	}

}
