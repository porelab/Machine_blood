package admin_user;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import toast.Openscreen;

public class First_userController implements Initializable {
	
	@FXML
    Button btnback;

    @FXML
    TextField txtnameserche;

    @FXML
    Label filelab,lbltorder,lbltfile,lblpen,lblplac,lblrunn,lblcompl,lblporo,lblper,lblbp,lbluinfo,lbluserid,lbluemail;
    
    @FXML
    ListView<String> userlist;
    
    Map<String,UserData> users;
    
	ObservableList<String> rawData= FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		userlist.getSelectionModel().getSelectedItem();
		
		btnback.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Openscreen.open("/admin_main/first_main.fxml");
			}
		});
	
		setTimer();
		/*Search register user*/
		txtnameserche.textProperty().addListener(((observable, oldValue, newValue) -> {
			//   search((String) oldValue, (String) newValue);
		
			userlist.getItems().clear();
			List<String> ddf=getFilter(newValue);
			userlist.getItems().addAll(ddf);
			
			
		
		}));
		
	}
	
	 public List<String> getFilter(String bankName) {
	        return rawData.stream()
	                .filter(t -> t.contains(bankName))
	                .collect(Collectors.toList());
	    }
	 
	 
	void setTimer() {
		
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
					
						Users u=new Users();
						users=u.getUsers();
						
						List<String> ls = new ArrayList<String>(users.keySet());
						rawData.addAll(ls);
						userlist.getItems().addAll(ls);
		
						userlist.getSelectionModel().getSelectedItem();
						
						userlist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

							@Override
							public void changed(
									ObservableValue<? extends String> observable,
									String oldValue, String newValue) {
								// TODO Auto-generated method stub
								setUsersData(users.get(newValue));	
								
							}
						});

						

						
					}
				});
			}

		};
		timer.schedule(task, 2000);
	}

	/*Selected order basic information set in table*/
	void setUsersData(UserData u)
	{
		
		
		lbluemail.setText(u.alldata.get("email")+"");
		
		lbluserid.setText(u.alldata.get("userid")+"");
		
		lbluinfo.setText("Name : "+u.alldata.get("name")+"\n"+"\n"+"Company Name : "+u.alldata.get("companyname")+"\n"+"\n"+"Contact no : "+u.alldata.get("contactno")+"\n"+"\n"+"Country : " +u.alldata.get("country")+"\n"+"\n"+"Address : " +u.alldata.get("companyaddress")+"\n"+"\n");
		
	
		
		lblbp.setText(""+u.bubblefile);
		lblporo.setText(""+u.porometerfile);
		lblper.setText(""+u.permiabilityfile);
		
		int tfile= u.bubblefile +u.porometerfile+u.permiabilityfile;
		
		int torder= u.pen +u.run+u.rec+u.com;
	
		lbltfile.setText(""+tfile);
		lbltorder.setText(""+torder);
		
		
		lblpen.setText(""+u.pen);
		lblrunn.setText(""+u.run);
		lblplac.setText(""+u.rec);
		lblcompl.setText(""+u.com);
			
		
		
	}
	
}
