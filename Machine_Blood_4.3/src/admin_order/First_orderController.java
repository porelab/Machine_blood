package admin_order;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import toast.Openscreen;
import toast.Toast;
import application.Main;

import com.jfoenix.controls.JFXToggleButton;

public class First_orderController implements Initializable {
	
	
	OrderData curor;
	
	@FXML
    Button btnback;

    @FXML
    TextField txtnameserche;

    @FXML
    Label lbluinfo,lbluserid,lbluemail;
    
    @FXML
    ListView<String> userlist;
   
    @FXML
    private JFXToggleButton tgborderlist;

    @FXML
    private Label lblostatus;

    @FXML
    private Button btnspen,btnsrec,btnsrun,btnscom;

    @FXML
	ScrollPane scrollorder;
    VBox v = new VBox(10);
    ObservableList<String> rawData= FXCollections.observableArrayList();
	
    Map<String,OrderData> odata;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		userlist.getSelectionModel().select(0);
		userlist.getFocusModel().focus(0);
		scrollorder.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		
		
		setBtnclick();
		setTimer();
	
		btnback.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Openscreen.open("/admin_main/first_main.fxml");
			}
		});
		
		/*Search order*/
		txtnameserche.textProperty().addListener(((observable, oldValue, newValue) -> {
			//   search((String) oldValue, (String) newValue);
		
			userlist.getItems().clear();
			List<String> ddf=getFilter(newValue);
			userlist.getItems().addAll(ddf);
			
			
		
		}));
		
		
		scrollorder.setPadding(new Insets(10, 20, 10, 20));

		scrollorder.setContent(v);
	}
	
	 public List<String> getFilter(String bankName) {
	        return rawData.stream()
	                .filter(t -> t.contains(bankName))
	                .collect(Collectors.toList());
	    }
	 
	 
	 /*Selected order Order information set in table*/
	void addItemToTable(String no,String test,String sample,String material,String factore)
	{
		HBox h = new HBox(0);

		Label lblno=new Label();
		lblno.setAlignment(Pos.CENTER);
		lblno.setPrefWidth(50);
		lblno.setText(""+no);
		
		Label lbltest=new Label();
		lbltest.setAlignment(Pos.BASELINE_CENTER);

		lbltest.setPrefWidth(120);
		
		lbltest.setText(""+test);
		
		Label lblsample=new Label();
		lblsample.setAlignment(Pos.CENTER);
		lblsample.setPrefWidth(120);
		lblsample.setText(""+sample);
		
		Label lblmaterial=new Label();
		lblmaterial.setAlignment(Pos.CENTER);
		lblmaterial.setPrefWidth(120);
		lblmaterial.setText(""+material);
		
		Label lblfactor=new Label();
		lblfactor.setAlignment(Pos.CENTER);
		lblfactor.setPrefWidth(120);
		lblfactor.setText(""+factore);

	
		h.getChildren().addAll(lblno,lbltest,lblsample,lblmaterial,lblfactor);
	
		v.getChildren().add(h);
		

	}

	/*Get All completed , pending and running Order*/
	void getOrders(String sta)
	{
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Orders o=new Orders();
				odata=o.getOrders(sta);
				List<String> ls = new ArrayList<String>(odata.keySet());
				rawData.clear();
				rawData.addAll(ls);
				userlist.getItems().clear();
				userlist.getItems().addAll(ls);

			}
		});
	
	}
	
void setTimer() {
		
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
					
				
						getOrders("");
						tgborderlist.setOnAction(new EventHandler<ActionEvent>() {
						
						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
							if(tgborderlist.isSelected())
							{
								userlist.getItems().clear();
								getOrders("com");
								btnscom.setDisable(true);
								btnsrec.setDisable(true);
								btnsrun.setDisable(true);
							}
							
							else 
							{
								userlist.getItems().clear();
								getOrders("");
								btnscom.setDisable(false);
								btnsrec.setDisable(false);
								btnsrun.setDisable(false);
							}							
						}
					});
						
							
						
						userlist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

							@Override
							public void changed(
									ObservableValue<? extends String> observable,
									String oldValue, String newValue) {
								// TODO Auto-generated method stub
								curor=odata.get(newValue);
								
								setUsersData(odata.get(newValue));	
								
							}
						});

						

						
					}
				});
			}

		};
		timer.schedule(task, 2000);
	}

/*Selected order set basic information*/
void setUsersData(OrderData o)
{
	v.getChildren().clear();
	
	List<String> keys = new ArrayList<String>(o.sampledata.keySet());
	for(int i=0;i<o.sampledata.size();i++)
	{
		Map<String, String> temp = (Map<String, String>)o.sampledata.get(keys.get(i));
	addItemToTable(""+(i+1), ""+ temp .get("test"), ""+ temp .get("sample"), ""+ temp .get("material"), ""+ temp .get("fact"));
	
	
	
	if (o.data.get("status").equals("com")) {
		
		lblostatus.setStyle("-fx-text-fill :  green");
		lblostatus.setText("Completed");
	}
	else if(o.data.get("status").equals("run")) {
		lblostatus.setStyle("-fx-text-fill :  skyblue");
		lblostatus.setText("Running");
		
	}
	else if(o.data.get("status").equals("rec")) {
		lblostatus.setStyle("-fx-text-fill :  orange");
		lblostatus.setText("Received");
		
	}
	else {
		lblostatus.setStyle("-fx-text-fill :  red");		
		lblostatus.setText("Pending");
		
	}

	
	lbluserid.setText(""+o.data.get("orderid"));
	lbluemail.setText(""+o.data.get("email"));
	
	lbluinfo.setText("Name : "+o.data.get("name")+"\n"+"\n"+"User id : "+o.data.get("userid")+"\n"+"\n"+"Company Name : "+o.data.get("company")+"\n"+"\n"+"Contact no : "+o.data.get("contact")+"\n"+"\n"+"Address : " +o.data.get("address")+"\n"+"\n");

	
	}
}
	
/*All Button event in status change*/
	void setBtnclick()
	{
		/*Status change in order completed*/
		btnscom.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
	
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("confirmation");
				alert.setHeaderText("Order status");
				alert.setContentText("you want to change status Complete?");
				alert.initOwner(Main.mainstage);
				alert.initModality(Modality.WINDOW_MODAL);
		

				    
				    Optional<ButtonType> result = alert.showAndWait();
				    if (result.get().equals(ButtonType.OK)) {
				    	try {
							if(curor.setCom())
							{
							
								setUsersData(curor);
								System.out.println("new status"+curor.data.get("status"));
								 Toast.makeText(Main.mainstage, "successfully update status....", 1000, 200, 200);
							
							}
							else {
								

								 Toast.makeText(Main.mainstage, "try again later....", 1000, 200, 200);
								}
							
						} catch (Exception e) {
							// TODO: handle exception
							
							 Toast.makeText(Main.mainstage, "please select atleast one order....", 1000, 200, 200);

						
						}
				   }
				}
				
				
			
		});
		/*Status change in order running */
		btnsrun.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
		
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("confirmation");
				alert.setHeaderText("Order status");
				alert.setContentText("you want to change status Running?");
				alert.initOwner(Main.mainstage);
				alert.initModality(Modality.WINDOW_MODAL);
		

				    
				    Optional<ButtonType> result = alert.showAndWait();
				    if (result.get().equals(ButtonType.OK)) {
				    	try {
							if(curor.setRun())
							{
							
								setUsersData(curor);
								 Toast.makeText(Main.mainstage, "successfully update status....", 1000, 200, 200);
							
							}
							else {
								

								 Toast.makeText(Main.mainstage, "try again later....", 1000, 200, 200);
								}
							
						} catch (Exception e) {
							// TODO: handle exception
							
							 Toast.makeText(Main.mainstage, "please select atleast one order....", 1000, 200, 200);

						
						}
				   }
						
			}
		});
		/*Status change in order Received*/
		btnsrec.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("confirmation");
				alert.setHeaderText("Order status");
				alert.setContentText("you want to change status Received?");
				alert.initOwner(Main.mainstage);
				alert.initModality(Modality.WINDOW_MODAL);
		

				    
				    Optional<ButtonType> result = alert.showAndWait();
				    if (result.get().equals(ButtonType.OK)) {
				    	try {
				    		Platform.runLater(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									 Toast.makeText(Main.mainstage, "please wait....", 1000, 200, 200);
										
									if(curor.setRec())
									{
											
										setUsersData(curor);
										 Toast.makeText(Main.mainstage, "successfully update status....", 1000, 200, 200);
					
									}
									else {
										
										 Toast.makeText(Main.mainstage, "try again later....", 1000, 200, 200);
										}
								}
							});
							
							
						} catch (Exception e) {
							// TODO: handle exception
							
							 Toast.makeText(Main.mainstage, "please select atleast one order....", 1000, 200, 200);

						
						}
				   }			}
		});

	
	
	}
}

