package application;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import toast.Systemtime;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.FirestoreOptions;

import firebase.FirebaseConnect;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Splashscreen implements Initializable {

	
	@FXML
	AnchorPane root;
	
	
	Stage primaryStage=new Stage();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		System.out.println("SpashScreen");
		//setTimer();
		new SplashSleep().start();
		DataStore.setLoadListener();

	}
	void setTimer() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {

				Platform.runLater(new Runnable() {

					@Override
					public void run() {

						getFirebaseConnect();
					
					}
				});
			}

		};
		timer.schedule(task, 1000);
	}

	void getFirebaseConnect()
	{

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {	
					FirebaseConnect f=new FirebaseConnect();
					FirestoreOptions options;
					Systemtime.StartTime();
					
					InputStream ii=(InputStream) this.getClass().getResourceAsStream("/application/serviceAccountKey.json");
					InputStream ii1=(InputStream) this.getClass().getResourceAsStream("/firebase/serviceAccountKey.json");
					
					 
				 
		/*				FileInputStream serviceAccount =
							  new FileInputStream("src/firebase/serviceAccountKey.json");*/
					options =
						    FirestoreOptions.getDefaultInstance().toBuilder()
						        .setProjectId("nyiapp-3a612")
						        .setCredentials(GoogleCredentials.fromStream(ii))
						        .setTimestampsInSnapshotsEnabled(true)
						        .setDatabaseId("(default)")
						        .build();	
					
								FirebaseConnect.InitApp(options,ii1);
							
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			//	new SplashSleep().start();
			}
		}).start();
		
	}
	
	class SplashSleep extends Thread
	{
		
		@Override
		public void run()
		{
			System.out.println("here");
			try {
				Thread.sleep(3000);
				System.out.println("i m here");
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Main.mainstage=primaryStage;
						
						try{Parent root1 = FXMLLoader.load(getClass().getResource("mainanc.fxml"));
					    Scene scene = new Scene(root1,1366,768);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					
					Image image = new Image(this.getClass().getResourceAsStream(
								"/application/shorticon.png"));
						primaryStage.getIcons().add(image);
						primaryStage.setTitle("NewYork-Instruments");
						primaryStage.setFullScreen(false);
						primaryStage.setScene(scene);
					//	primaryStage.setMaximized(true);
						primaryStage.setFullScreen(true);
						
						Main.mainstage.setOnCloseRequest(new EventHandler<WindowEvent>() {
							
							@Override
							public void handle(WindowEvent arg0) {
								
								try
								{
									System.exit(0);
									Platform.exit();
									
								}
								catch(Exception e)
								{
									
								}
								
							}
						});
						
						Main.shortCut();
						//primaryStage.show();
						
					}
						catch(Exception e)
						{
							e.printStackTrace();
							System.out.println("Error");
						}
					}
				});
			
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
}
