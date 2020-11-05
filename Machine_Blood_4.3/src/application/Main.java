package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.FileLock;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import toast.MyDialoug;
import toast.Systemtime;
import toast.Toast;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;

import errorcodes.ErrorList;
import firebase.FirebaseConnect;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import myconstant.Myconstant;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;


	public class Main extends Application {
	public static Stage mainstage;
	public static Class<? extends Main> clsObj;
	public static FileLock fileLock;
	public static RandomAccessFile randomAccessFile;
	public static Stage splashstage;

	@Override
	public void start(Stage primaryStage) {
	repoen(primaryStage);
	}

	void repoen(Stage primaryStage)
	{
		try {
			mainstage=primaryStage;
			splashstage=primaryStage;
			ErrorList.setErrorList();

			Myconstant.inIt();
			
			clsObj=getClass();
			if(lockInstance("temp.log"))
			{
			
		    Myapp.initNotifier();
		    Myapp.setColor();
			Parent root = FXMLLoader.load(getClass().getResource("/application/splashscreen.fxml"));
			Scene scene = new Scene(root, 575, 350);
			 primaryStage.initStyle(StageStyle.UNDECORATED);
			scene.getStylesheets().add(getClass().getResource("new_application.css").toExternalForm());
			fontload();
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("NewYork-Instruments");
			primaryStage.show(); 
			}
			else
			{
				Parent root = FXMLLoader.load(getClass().getResource("/newstageerror/splashscreen.fxml"));
				Scene scene = new Scene(root,576,328);
				 primaryStage.initStyle(StageStyle.UNDECORATED);
				 Image image = new Image(this.getClass().getResourceAsStream(
							"/application/shorticon.png"));
					primaryStage.getIcons().add(image);
				primaryStage.setScene(scene);
				primaryStage.setTitle("NewYork-Instruments");
				primaryStage.show(); 
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean lockInstance(final String lockFile) {
	    try {
	        final File file = new File(lockFile);
	         randomAccessFile = new RandomAccessFile(file, "rw");
	       fileLock = randomAccessFile.getChannel().tryLock();
	        if (fileLock != null) {
	            Runtime.getRuntime().addShutdownHook(new Thread() {
	                public void run() {
	                    try {
	                        fileLock.release();
	                        randomAccessFile.close();
	                        file.delete();
	                    } catch (Exception e) {
	                        System.out.println("Error : "+e.getMessage());

	                    	e.printStackTrace();
	                    }
	                }
	            });
	            return true;
	        }
	    } catch (Exception e) { 
	    	System.out.println("Error1 : "+e.getMessage());

    	e.printStackTrace();
	    }
	    return false;
	}
	
	static void shortCut()
	{
		  KeyCombination fullscreenshortcut = new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_ANY);
		  Main.mainstage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent ke) {
					
					
					
					 if(fullscreenshortcut.match(ke))
					 {

							Main.mainstage.setMaximized(true);
							Main.mainstage.setFullScreen(true);
					 }
					
				}
			});

	}
	
	void fontload()
	{
		
		Font.loadFont(Main.class.getResource("Montserrat-SemiBold.ttf").toExternalForm(), 100);
		Font.loadFont(Main.class.getResource("Roboto-Regular.ttf").toExternalForm(), 100);
		Font.loadFont(Main.class.getResource("Montserrat-Medium.ttf").toExternalForm(), 100);
		Font.loadFont(Main.class.getResource("BebasNeue.otf").toExternalForm(), 100);
		     
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
