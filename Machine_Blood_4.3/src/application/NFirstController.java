package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXDialogLayout;

import extrafont.Myfont;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import myconstant.Myconstant;
import toast.MyDialoug;
import toast.Openscreen;
import toast.Toast;

public class NFirstController implements Initializable {

	MyDialoug mydia;

	@FXML
	Label lblpg1offset, lblpg2offset, lblconnection,std;

	@FXML
	StackPane maincontent;
	
	
	@FXML
	Button btnswitch;
	

	SimpleBooleanProperty ispopup = new SimpleBooleanProperty(false);

	public static StackPane maincontent1;
	static JFXDialog df;
	JFXDialogLayout dll;

	@FXML
	Button livetest, report, btncloud, btnsetting, txtuname, qtest, btnscada,
			btnrefresh, btnhelp, btnclose, btnport,btnzoom,btnrestart;

	@FXML
	Rectangle recmain;

	Database d = new Database();

	boolean bolConnected;

	void addShortCut() {

		KeyCombination manushortcut = new KeyCodeCombination(KeyCode.M,
				KeyCombination.CONTROL_ANY);
		KeyCombination quickshortcut = new KeyCodeCombination(KeyCode.Q,
				KeyCombination.CONTROL_ANY);
		KeyCombination startshortcut = new KeyCodeCombination(KeyCode.S,
				KeyCombination.CONTROL_ANY);
		KeyCombination confishortcut = new KeyCodeCombination(KeyCode.C,
				KeyCombination.CONTROL_ANY);
		KeyCombination refreshshortcut = new KeyCodeCombination(KeyCode.R,
				KeyCombination.CONTROL_ANY);
		KeyCombination exitshortcut = new KeyCodeCombination(KeyCode.E,
				KeyCombination.CONTROL_ANY);

		KeyCombination hardreset = new KeyCodeCombination(KeyCode.H,
				KeyCombination.CONTROL_ANY);

		maincontent.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {

				if (manushortcut.match(ke)) {

					Toast.makeText(Main.mainstage, "Opening Manualcontrol..",
							1000, 200, 200);

					Openscreen.open("/userinput/manualcontrol.fxml");

				}

				else if (refreshshortcut.match(ke)) {

					if (DataStore.connect_hardware.get()) {
						DataStore.hardReset();
						Toast.makeText(Main.mainstage, "Refresing...", 1000,
								200, 200);

					} else {
						connectHardware();

						if (DataStore.connect_hardware.get()) {
							DataStore.hardReset();
							Toast.makeText(Main.mainstage, "Refresing...",
									1000, 200, 200);
						} else {
							MyDialoug.showError(102);

						}

					}

				}

				else if (hardreset.match(ke)) {

					if (DataStore.connect_hardware.get()) {
						DataStore.hardReset();
						Toast.makeText(Main.mainstage, "Hard reset...", 1000,
								200, 200);

					} else {
						connectHardware();

						if (DataStore.connect_hardware.get()) {
							DataStore.hardReset();
							Toast.makeText(Main.mainstage, "Hard reset...",
									1000, 200, 200);
						} else {

							MyDialoug.showError(102);
						}
					}

				}

				else if (confishortcut.match(ke)) {

					Toast.makeText(Main.mainstage, "Opening Configuration..",
							1000, 200, 200);

					Openscreen.open("/ConfigurationPart/Nconfigurepage.fxml");
				} else if (quickshortcut.match(ke)) {
					quicktest();
				} else if (startshortcut.match(ke)) {

					Toast.makeText(Main.mainstage, "Opening Test..", 1000, 200,
							200);

					Openscreen.open("/userinput/Nselectproject1.fxml");

				} else if (exitshortcut.match(ke)) {

					exitpopup();

				}

			}
		});

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		setStd();
		DataStore.isconfigure.set(true);
		addShortCut();
		if (DataStore.connect_hardware.get()) {

			lblconnection.setText("Connected(" + DataStore.getCom() + ")");
			btnport.setVisible(false);
		} else {
			lblconnection.setText("");
			btnport.setVisible(true);
		}

		DataStore.connect_hardware.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean arg1, Boolean arg2) {
				// TODO Auto-generated method stub
				System.out.println("Connection listener : " + arg2);

				if (arg2) {

					lblconnection.setText("Connected(" + DataStore.getCom()
							+ ")");
					btnport.setVisible(false);
				} else {
					lblconnection.setText("");
					btnport.setVisible(true);
				}
			}
		});

		connectHardware();

		txtuname.setText("Welcome, " + Myapp.username);
		System.out.println("usename--:" + Myapp.username);

		Myfont f = new Myfont(15);
		txtuname.setFont(f.getM_M());

		setMainBtns();
		setAllThings();
		setBtnClicks();

		DataStore.isconfigure.set(true);
		
		if (DataStore.connect_hardware.get()) {
			
			DataStore.hardReset();

		}

	}
	
	void setStd()
	{
		String st=Myconstant.getStd();
		if(st.equals("1"))
		{
			std.setText("( ASTM F1670 )");
			btnswitch.setText("switch to ISO 16603");
		}
		else
		{
			std.setText("( ISO 16603 )");
			btnswitch.setText("switch to ASTM F1670");
		}
		
	}

	void switchClick()
	{
		if(Myconstant.getStd().equals("1"))
		{
			Myconstant.setStd("2");
			setStd();
		}
		else
		{
			Myconstant.setStd("1");
			setStd();
		}
		
		
	}
	

	void setBtnClicks() {

		btnport.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				mydia = new MyDialoug(Main.mainstage,
						"/application/Portlistpopup.fxml");
				String data=mydia.showDialougWithValue(null);
			

			}
		});

		btnswitch.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				switchClick();
			}
		});
		btnrestart.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				mydia = new MyDialoug(Main.mainstage, "/application/restartapplication.fxml");
				mydia.showDialoug();
			}
		});
		
		btnrefresh.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if (DataStore.connect_hardware.get()) {
				
				} else {
					
				}

			}
		});

	
		btnsetting.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Open Setting-------------->>>>");
				
				Openscreen.open("/ConfigurationPart/Nconfigurepage.fxml");

			}
		});
		
		btnzoom.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

				Main.mainstage.setMaximized(true);
				Main.mainstage.setFullScreen(true);

			}
		});

		txtuname.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				viewprofile();

			}
		});

		qtest.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				quicktest();

			}
		});

		livetest.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
					//	MainancController.mainanc1.getChildren().setAll(LoadAnchor.createtestfxml);
						
						
					Openscreen.open("/userinput/Nselectproject1.fxml");

					}
				});

			}
		});

		report.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				Openscreen.open("/report/first.fxml");
				//MainancController.mainanc1.getChildren().setAll(LoadAnchor.reportfxml);
				
			}
		});

		btnscada.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				Openscreen.open("/userinput/manualcontrol.fxml");
				// TODO Auto-generated method stub
			}
		});

	

		btnclose.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				exitpopup();
			}
		});

		btnhelp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				mydia = new MyDialoug(Main.mainstage, "/application/info.fxml");
				mydia.showDialoug();

			}
		});

	}
	
	public void connectHardware()
	{
		
		
		
	}

	public void quicktest() {
		Database db = new Database();
try {
//		if (db.isExist("select * from lastprojects where lid='" + Myapp.email
//				+ "' ")) {
//			System.out.println("1 Last project.");
			// AnchorPane popanc=new AnchorPane();

			// popanc.setStyle("-fx-background-color: rgba(100, 300, 100, 0.5); -fx-background-radius: 10;");

			// mydia=new MyDialoug(Main.mainstage,
			// "/userinput/popupresult.fxml");

//			mydia = new MyDialoug(Main.mainstage, "/application/popfxml.fxml");
	mydia = new MyDialoug(Main.mainstage, "/application/Quicktest1.fxml");

	mydia.showDialoug();
	
			/*
			 * FXMLLoader fxmlLoader = new
			 * FXMLLoader(getClass().getResource("/application/popfxml.fxml"));
			 * try {
			 * 
			 * Pane cmdPane = (Pane) fxmlLoader.load();
			 * 
			 * popanc.getChildren().add(cmdPane);
			 * System.out.println("Load Anchorpane....."); } catch (Exception e)
			 * { e.printStackTrace(); }
			 */

			// dll.setBody(popanc);

			// df.show();
//			System.out.println("No Last project.");
//			Toast.makeText(Main.mainstage, "You Don't have any previous test",
//					2000, 300, 300);
//		}
}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

	void setAllThings() {
		maincontent1 = maincontent;
		// h.visibleProperty().bind(Myapp.hb);

		recmain.visibleProperty().bind(ispopup);

		dll = new JFXDialogLayout();
		df = new JFXDialog(NFirstController.maincontent1, dll,
				DialogTransition.CENTER);

		df.visibleProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				ispopup.set(newValue);
				System.out.println(newValue + "");
			}
		});

	}

	public void exitpopup() {

		mydia = new MyDialoug(Main.mainstage, "/application/Nexitpopup.fxml");
		mydia.showDialoug();
	}

	public void viewprofile() {

		Openscreen.open("/application/editeprofile.fxml");
	}

	
	void sendData(writeFormat w, int slp) {
		System.out.println("Sending Data......");
		w.showData();
		Thread t = new Thread(new SerialWriter(DataStore.out, w, slp));
		try {

			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendData(writeFormat w) {
		System.out.println("Sending Data......");
		w.showData();
		Thread t = new Thread(new SerialWriter(DataStore.out, w));
		t.start();
	}

	

	void setMainBtns() {

		Image image = new Image(this.getClass().getResourceAsStream(
				"/application/quickimg.png"));
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(100);
		imageView.setFitHeight(110);
		qtest.setGraphic(imageView);

		Image image1 = new Image(this.getClass().getResourceAsStream(
				"/application/reportimg.png"));
		ImageView imageView1 = new ImageView(image1);
		imageView1.setFitWidth(100);
		imageView1.setFitHeight(110);
		report.setGraphic(imageView1);
		Image image2 = new Image(this.getClass().getResourceAsStream(
				"/application/startimg.png"));
		ImageView imageView2 = new ImageView(image2);
		imageView2.setFitWidth(100);
		imageView2.setFitHeight(110);
		livetest.setGraphic(imageView2);

		Image image4 = new Image(this.getClass().getResourceAsStream(
				"/application/scada.png"));
		ImageView imageView4 = new ImageView(image4);
		imageView4.setFitWidth(100);
		imageView4.setFitHeight(100);

		btnscada.setGraphic(imageView4);

		Image image5 = new Image(this.getClass().getResourceAsStream(
				"/application/setting.png"));
		ImageView imageView5 = new ImageView(image5);
		imageView5.setFitWidth(100);
		imageView5.setFitHeight(100);
		btnsetting.setGraphic(imageView5);

		Image image6 = new Image(this.getClass().getResourceAsStream(
				"/application/about.png"));
		ImageView imageView6 = new ImageView(image6);
		imageView6.setFitWidth(100);
		imageView6.setFitHeight(120);
		btnhelp.setGraphic(imageView6);

		btncloud.getStyleClass().add("transperant_comm");
	}

}
