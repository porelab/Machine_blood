package report;

import java.io.File;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.jfoenix.controls.JFXSpinner;

import application.Main;
import application.Myapp;
import data_read_write.DatareadN;
import extrafont.Myfont;
import firebase.FirebaseConnect;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import toast.MyDialoug;
import toast.Toast;

public class OnlinefilepopupController implements Initializable {

	int maxrow=3;
	
	@FXML
	AnchorPane root1;

	@FXML
	Button starttest, btncancel;

	@FXML
	ScrollPane scroll;

	@FXML
	JFXSpinner spin, spinload;

	@FXML
	Label lblsuccess;
	
	@FXML
	TextField txtsearch;

	static Image image;

	List<String> listofdownloadfile = new ArrayList<String>();
	int fileno = 0;

	static List<String> onlinefoldersname;

	Myfont fontss = new Myfont(12);
	List<String> folders;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		onlinefoldersname = new ArrayList<String>();
		spin.setVisible(true);
		/*Get All Online File List*/
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try{
					getOnlineList();
					}
					catch(Exception e)
					{
						System.out.println("Errorrrr");
					}
				}
			}).start();
		
			/*cloud sample Dialougbox Close*/
		btncancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

				MyDialoug.closeDialoug();

			}
		});

		/*Sample Download Starting..*/
		starttest.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				System.out.println("Selecred : " + listofdownloadfile);
				if (listofdownloadfile.size() > 0) {
					System.out.println("Download Starting");
					saveFile();
				} else {
					Toast.makeText(Main.mainstage, "No sample selected", 1000,
							200, 200);

					System.out.println("No sample selected");
				}

			}
		});
		
		txtsearch.setOnKeyReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				
				if(folders!=null)
				{
					
					if(!folders.isEmpty())
					{
						
						
						System.out.println(" ------ > New search  :  "+txtsearch.getText());

						getOnlineFolderList(folders,txtsearch.getText());
						
						
					}
					
					
					
				}
				
			}
		});

	}

	/*Get Online File List*/
	void getOnlineList() throws UnknownHostException
	{
		try {
			
		
			System.out.println("fetching...");

			DocumentReference reff = FirebaseConnect.db.collection("users")
					.document(Myapp.uid).collection("files")
					.document("hydrostatic");


			ApiFuture<DocumentSnapshot> ddd = reff.get();

			
			
			if (ddd.get().exists()) {
				System.out.println("Data fetch");

				Map<String, Object> dddd = ddd.get().getData();

				folders = (List<String>) dddd.get("files_name");
				System.out.println("Folders : "+folders);
				onlinefoldersname.clear();
				onlinefoldersname.addAll(folders);
			
				System.out.println(folders);
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
					
						txtsearch.setVisible(true);
						spin.setVisible(false);
						txtsearch.setPromptText("search from "+folders.size()+" samples");
						getOnlineFolderList(folders,"");
						
						

					}
				});
			} else {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub

						//txtsearch.setVisible(true);
						spin.setVisible(false);
						lblsuccess.setVisible(true);
						lblsuccess.setText("No data found");
					}
				});
				System.out.println("No data found");
			}

		} catch (Exception e) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub

					//txtsearch.setVisible(true);
					spin.setVisible(false);
					lblsuccess.setVisible(true);
					lblsuccess.setText("No data found");
				}
			});
			System.out.println("Error 101 : " + e.getMessage());
			e.printStackTrace();
		}
	}

	/*Download File Save Path*/
	void saveFile() {
		try{
		if (fileno >= listofdownloadfile.size()) {

			FirstController.isDelete.set(false);
			FirstController.isDelete.set(true);
			System.out.println("Finish");
			fileno = 0;
			listofdownloadfile.clear();
			spinload.setVisible(false);
			lblsuccess.setVisible(true);
		} else {
			spinload.setVisible(true);

			String name = listofdownloadfile.get(fileno);

			System.out.println("start for  : " + name);

DocumentReference reff=FirebaseConnect.db.collection("users").document(Myapp.uid).collection("files").document("bubblepoint").collection("files").document(name);

			ApiFuture<DocumentSnapshot> ddd = reff.get();

			if (ddd.get().exists()) {
				
				Platform.runLater(new Runnable() {

					@Override
					public void run() {

						try {
							Map<String, Object> data = (Map<String, Object>)ddd.get().getData();
							System.out.println(data.keySet());
							List<String> files = new ArrayList<String>(
									data.keySet());

							for (int i = 0; i < files.size(); i++) {

								Map<String, Object> dd = (Map<String, Object>) data
										.get(files.get(i));

								System.out.println("downloading... "
										+ files.get(i));
								createCsvporometers(dd, Myapp.uid,
										name, files.get(i));

							}
							fileno++;
							saveFile();
							// bt.setText("Download Successfully");
							Toast.makeText(Main.mainstage,
									"Download Successfully", 2000, 300,
									300);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});

				
			}
			else
			{

				Toast.makeText(Main.mainstage,
						"NO files in this folder", 2000, 300, 300);
			}
			
			
		}
		}
		catch(Exception e)
		{
			System.out.println("Error 102 : "+e.getMessage());
			e.printStackTrace();
		}
	}

	/*Create Save to Cloud*/
	void createCsvporometers(Map<String, Object> dr, String uid,
			String samname, String testname) {

		try {
			System.out.println("csv creating........");

			File f = new File("TableCsvs/"+Myapp.uid);
			if (!f.isDirectory()) {
				f.mkdir();
				System.out.println("Dir csv folder created");
			}

			f = new File("TableCsvs/"+Myapp.uid+"/" + samname);
			if (!f.isDirectory()) {
				f.mkdir();
			}

			DatareadN dd = new DatareadN(dr, "TableCsvs/"+Myapp.uid+"/" + samname + "/"
					+ testname + ".csv");
			if (dd.createFile()) {
				System.out.println("Created");
			} else {
				System.out.println("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*Display Online File List*/
	void getOnlineFolderList(List<String> list,String name) {

		
		
		if(name.equals(""))
		{
			VBox v = new VBox(10);
			v.setPadding(new Insets(0, 0, 10, 30));
			HBox h = new HBox(10);
			for (int i = 0; i < list.size(); i++) {
				
			
				int num = i % maxrow;
				if (num == 0) {
					h = new HBox(10);
					v.getChildren().add(h);
				}

				//System.out.println("Sample add : "+list.get(i));
				Node vtemp = getVBoxofFolderOnline(list.get(i));
				h.getChildren().add(vtemp);
			
				
			}

			scroll.setContent(v);
		}
		else
		{
		VBox v = new VBox(10);
		v.setPadding(new Insets(0, 0, 10, 30));
		HBox h = new HBox(10);
		int num=0;
		for (int i = 0; i < list.size(); i++) {
			
			if(Pattern.compile(Pattern.quote(name), Pattern.CASE_INSENSITIVE).matcher(list.get(i)).find())
			{
			
			if (num % maxrow == 0) {
				h = new HBox(10);
				v.getChildren().add(h);
			}

			System.out.println("Sample add : "+list.get(i));
			Node vtemp = getVBoxofFolderOnline(list.get(i));
			h.getChildren().add(vtemp);
			num++;
		
			}
		}

		scroll.setContent(v);
		}
		//System.out.println("set view");

	}

	/*Sample List Set Image and Button Click Event*/
	HBox getVBoxofFolderOnline(String name) {
		HBox v1 = new HBox(8);

		image = new Image(getClass().getResourceAsStream("fl.png"));
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(40);
		imageView.setFitHeight(40);
		
		
		

		CheckBox chk = new CheckBox();
		chk.setText("" + name);
		chk.setWrapText(true);
		int wid=200;
		chk.setMinWidth(wid);
		chk.setMaxWidth(wid);
		chk.setPrefWidth(wid);
		chk.setPrefHeight(40);
		chk.setMinHeight(40);
		chk.setMaxHeight(40);
//	/	v1.setAlignment(Pos.CENTER);
	
		chk.setFont(fontss.getM_M());
		chk.setTextFill(Color.web("727376"));

		chk.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if (newValue) {
					if (!listofdownloadfile.contains(chk.getText())) {
						listofdownloadfile.add(chk.getText());
					}
				} else {
					if (!listofdownloadfile.contains(chk.getText())) {
						listofdownloadfile.remove(chk.getText());
					}
				}
			}
		});
		v1.setFillHeight(true);          // Added this
		v1.setAlignment(Pos.CENTER_LEFT);
		v1.getChildren().addAll(imageView, chk);

		return v1;
	}
	
	
	VBox getVBoxofFolderOnlineOld(String name) {
		VBox v1 = new VBox(15);

		image = new Image(getClass().getResourceAsStream("fl.png"));
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(100);
		imageView.setFitHeight(100);

		Button btntg = new Button();
		// btntg.setStyle(" -fx-background-color: rgba(255, 255, 255, 0.1);");

		btntg.setGraphic(imageView);
		btntg.setStyle("-fx-text-fill:#727376");
		btntg.setWrapText(true);
		btntg.setPrefSize(100, 100);
		btntg.setTextFill(Color.web("#727376"));

		btntg.setStyle("-fx-background-color: transparent;");
		btntg.setId("" + name);
		btntg.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Button b = (Button) event.getSource();
				String ss = b.getId();
				// selectedfolde=ss;
				// openfoldrpopup();

			}
		});

		CheckBox chk = new CheckBox();
		chk.setText("" + name);
		chk.setWrapText(true);
		chk.setMinWidth(100);
		chk.setMaxWidth(100);
		chk.setPrefWidth(100);
		chk.setFont(fontss.getM_M());
		chk.setTextFill(Color.web("727376"));

		chk.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if (newValue) {
					if (!listofdownloadfile.contains(chk.getText())) {
						listofdownloadfile.add(chk.getText());
					}
				} else {
					if (!listofdownloadfile.contains(chk.getText())) {
						listofdownloadfile.remove(chk.getText());
					}
				}
			}
		});
		v1.getChildren().addAll(btntg, chk);

		return v1;
	}

}
