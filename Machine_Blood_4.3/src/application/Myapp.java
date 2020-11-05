package application;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import toast.MyDialoug;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import myconstant.Myconstant;
import Notification.Notification;
import Notification.Notification.Notifier;
import Notification.NotificationBuilder;
import Notification.NotifierBuilder;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import firebase.FirebaseConnect;

//save some data while page change // data flow 
public class Myapp {


	public static  Stage virtualStage=null;
	public static TextField keyboardinput;
	public static TextField currTf=null;
	public static TextField nextTf=null;
	public static String nextlbl=null;
	
	public static Label lblkeyboard;
	public static DoubleProperty pg1offset=new SimpleDoubleProperty(0);

	public static DoubleProperty pg2offset=new SimpleDoubleProperty(0);
	public static DoubleProperty fm1offset=new SimpleDoubleProperty(0);

	public static DoubleProperty fm2offset=new SimpleDoubleProperty(0);
	
	
	public static SimpleIntegerProperty ftype =new SimpleIntegerProperty(1);
	public static SimpleBooleanProperty isInternetConnected=new SimpleBooleanProperty(false);
	
	public static String oldpass;
	public static String uid;
	public static Notifier notifier;

	public static SimpleBooleanProperty hb=new SimpleBooleanProperty(true);
	public static String username,email,pass,status;
	
	public static String testtrial,indtype,materialapp,fluidname,fluidvalue,classification,crossection,thresold,tfactore,splate,thikness,industryname,lotnumber,startpress;
	
	//New ADD
	public static String materialtype,accbpt="100",accstep="50",accstability="50",stabilitytype,endpress,testsequence,chambertype,crossovertgl;
	
	/*Internet connection*/
	public static String lastinternet,pdftestname;
	
	public static Map<String,String> bps=new HashMap<String, String>();
	
	public static List<String> colors;
	public static double maxpressure,maxflow;
	
	//Keyboard
	public static String tabletmode;
	
	public static void cleardata()
	{
		username="";
		email="";
		pass="";
		status="";
		
	}
	
	public static boolean netIsAvailable() {
	    try {
	        final URL url = new URL("http://www.google.com");
	        final URLConnection conn = url.openConnection();
	        conn.connect();
	        conn.getInputStream().close();
	        return true;
	    } catch (IOException e) {
	        return false;
	    }
	}
	
	
	public static void updateInDatabaseAutosync(boolean bol)
	{
		Database d=new Database();  
		
		
		if(!Myapp.uid.equals(""))
		{
		String updatequry="update userinfo set status='"+bol+"' where email='"+Myapp.email+"'";
		if(d.Insert(updatequry))
		{
			System.out.println("update : "+bol);
			Myapp.status=""+bol;
		}
		}
	}
	public static void initNotifier()
	{
		  notifier = NotifierBuilder.create()
		             //.popupLocation(Pos.TOP_RIGHT)
		             //.popupLifeTime(Duration.millis(10000))
		             //.styleSheet(getClass().getResource("mynotification.css").toExternalForm())
		             .build();
		     
		      //   notifier.notify(NotificationBuilder.create().title("Test Completed").message("Completed test "+testno).image(Notification.SUCCESS_ICON).build());

	}

	public static void showError(String title, String msg)
	{
		notifier.notify(NotificationBuilder.create().title(title).message(msg).image(Notification.ERROR_ICON).build());

	}
	public static void showSuccess(String title, String msg)
	{
		notifier.notify(NotificationBuilder.create().title(title).message(msg).image(Notification.SUCCESS_ICON).build());

	}
	public static void showWarning(String title, String msg)
	{
		notifier.notify(NotificationBuilder.create().title(title).message(msg).image(Notification.WARNING_ICON).build());

	}
	public static void showInfo(String title, String msg)
	{
		notifier.notify(NotificationBuilder.create().title(title).message(msg).image(Notification.INFO_ICON).build());

	}
	
	public static double getRoundDouble(double r, int round) {
		
		if (round == 2) {
			r = (double) Math.round(r * 100) / 100;
		} else if (round == 3) {
			r = (double) Math.round(r * 1000) / 1000;

		}
		 else if(round==4) {
				r = (double) Math.round(r * 10000) / 10000;

			}
		else {
			r = (double) Math.round(r * 100000) / 100000;

		}

		return r ;

	}

	
	public static String getRound(Double r, int round) {

		if (round == 2) {
			r = (double) Math.round(r * 100) / 100;
		} else if (round == 3) {
			r = (double) Math.round(r * 1000) / 1000;

		} else {
			r = (double) Math.round(r * 10000) / 10000;

		}

		return r + "";

	}
	
	public static Double getRound(String r1, int round) {
		Double r=Double.parseDouble(r1);
		if (round == 2) {
			r = (double) Math.round(r * 100) / 100;
		} else if (round == 3) {
			r = (double) Math.round(r * 1000) / 1000;

		} else {
			r = (double) Math.round(r * 10000) / 10000;

		}

		return r ;

	}

	public static void setColor()
	{
		colors=new ArrayList<String>();
		colors.add("#DBBA4F");
		colors.add("#3F76B5");
		colors.add("#D67479");
		colors.add("#12B59F");
		colors.add("#F5903D");
		colors.add("#BC4644");
		colors.add("#AD4F73");
		colors.add("#40A7C1");
		colors.add("#95B64F");
		
		
		colors.add("#613769");
		colors.add("#234882");
		colors.add("#A1846A");
		
	}

	
	public static void setInternetWatch()
	{
		DatabaseReference connectedRef = FirebaseConnect.fb.getReference(".info/connected");
		connectedRef.addValueEventListener(new ValueEventListener() {
		  @Override
		  public void onDataChange(DataSnapshot snapshot) {
		    boolean connected = snapshot.getValue(Boolean.class);
		    if (connected) {
		     

				javafx.application.Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						//status.setText("Connected !");
			
						isInternetConnected.set(true);
						System.out.println("Internet Connected");
					}
				});
		      
		    } else {

				javafx.application.Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
			

						isInternetConnected.set(false);

						System.out.println("Internet Disconnected");
					}
				});
		    }
		  }

		  @Override
		  public void onCancelled(DatabaseError error) {
		    System.err.println("Listener was cancelled");
		  }
		});
	}
	public static void restartApp()
	{
		try {
			 Main.mainstage.close();
				
			
			DataStore.isconfigure.set(false);    
		

	          Platform.runLater( new Runnable() {
				
				@Override
				public void run() {
					try{
						Thread.sleep(1000);
						Main.fileLock.release();
						Main.randomAccessFile.close();
						MyDialoug.dialog=null;
						
					}catch(Exception e)
					{
						
					}
			
					new Main().start( new Stage() );
					
				}
			} );
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	public static void showTooltip(Control control, String tooltipText,
		    ImageView tooltipGraphic,int x,int y)
		{
		
		
			Stage owner=Main.mainstage;
		    Point2D p = control.localToScene(x,y);

		    final Tooltip customTooltip = new Tooltip();
		    customTooltip.setText(tooltipText);
		    String SQUARE_BUBBLE =
		            "M30 0 h100 a6,6 0 0,1 6,6 v50 a6,6 0 0,1 -6,6 h-88 L38 68 L34 62 h-4 a6,6 0 0,1 -6,-6 v-50 a6,6 0 0,1 6,-6 z";
		    String SQUARE_BUBBLE1 =
		            "M12 1c-6.628 0-12 4.573-12 10.213 0 2.39.932 4.591 2.427 6.164l-2.427 5.623 7.563-2.26c9.495 2.598 16.437-3.251 16.437-9.527 0-5.64-5.372-10.213-12-10.213z";
		    String path1="M12 1c-6.628 0-12 4.573-12 10.213 0 2.39.932 4.591 2.427 6.164l-2.427 5.623 7.563-2.26c9.495 2.598 16.437-3.251 16.437-9.527 0-5.64-5.372-10.213-12-10.213z";
		    customTooltip.setStyle("-fx-font-size: 18px;");// -fx-shape: \"" + SQUARE_BUBBLE1 + "\"; -fx-padding: 0.0px 0.0px 20px 0px;");
		  //  customTooltip.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_BOTTOM_LEFT);

		    control.setTooltip(customTooltip);
		    customTooltip.setAutoHide(true);
		    

		    customTooltip.show(owner, p.getX()
		        + control.getScene().getX() + control.getScene().getWindow().getX(), p.getY()
		        + control.getScene().getY() + control.getScene().getWindow().getY());

			control.setTooltip(null);
		}
	public static void PrintAll() {
		System.out.println("sample id--"+Myconstant.sampleid);
		System.out.println("Lot no --"+Myconstant.lotno);
		System.out.println("Test Mode--"+Myconstant.testmode);
		System.out.println("stepsize -"+Myconstant.stepsize);
		System.out.println("material type : "+Myconstant.sampletype);
	//	System.out.println("pressurerate--"+Myconstant.pressurerate);
	//	System.out.println("samplearea--"+Myconstant.samplearea);

		
	}
	
	
}
