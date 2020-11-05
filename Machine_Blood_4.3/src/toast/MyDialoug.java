package toast;

import java.util.Map;

import application.Main;
import errorcodes.ErrorInfo;
import errorcodes.ErrorList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


//dialoug class for popups

public class MyDialoug {

	public static Stage dialog, mainstage;
	public static boolean bol = true;
	public static int timer = 200;
	
	public static String data="";
	public static Map<String,Object> inputdata;

	public static void closeDialoug() {
		if (bol) {
			try {
				mainstage.getScene().getRoot().setEffect(null);
			//	inputdata=null;
				dialog.close();
					
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("fxml error");
		}
	}

	public static void showDialoug() {
		if (bol) {
			try {

				// double centerXPosition = mainstage.getX() +
				// mainstage.getWidth()/2d;
				// double centerYPosition = mainstage.getY() +
				// mainstage.getHeight()/2d;

				// dialog.setX(centerXPosition - dialog.getWidth());
				// dialog.setY(centerYPosition - dialog.getHeight()/2d);
				// dialog.show();
				enableBlur();
				dialog.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("fxml error");
		}
	}
	
	
	public static String showDialougWithValue(Map<String,Object> indata) {
		if (bol) {
			try {

				data="";
				inputdata=indata;
				System.out.println("input data : "+inputdata);
				// double centerXPosition = mainstage.getX() +
				// mainstage.getWidth()/2d;
				// double centerYPosition = mainstage.getY() +
				// mainstage.getHeight()/2d;

				// dialog.setX(centerXPosition - dialog.getWidth());
				// dialog.setY(centerYPosition - dialog.getHeight()/2d);
				// dialog.show();
				enableBlur();
				dialog.showAndWait();
			
				System.out.println("In showdialog");
				return data;
			} catch (Exception e) {
				e.printStackTrace();
				
				return "";
			}
		} else {
			System.out.println("fxml error");
			return "";
		}
	}

	public static void enableBlur() {
		ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);

		ColorAdjust adj1 = new ColorAdjust(0, 0, -0.8, 0);

		mainstage.getScene().getRoot().setEffect(adj1);

	}

	public static void hideDialoug() {
		if (bol) {
			try {
				mainstage.getScene().getRoot().setEffect(null);
				dialog.hide();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("fxml error");
		}
	}

	public MyDialoug(Stage mainstage, String page, int timer) {
		if (dialog == null) {
			dialog = new Stage();
			dialog.initStyle(StageStyle.UNDECORATED);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(mainstage);
		}

		this.timer = timer;
		this.mainstage = mainstage;
		setBase(page);
	}
	
	
	
	public static void showError(String errname,String errcode,String errdes)
	{
	
		if (dialog == null) {
			dialog = new Stage();
			dialog.initStyle(StageStyle.UNDECORATED);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(Main.mainstage);
		}

		mainstage = Main.mainstage;
		
		infoController.errname=errname;
		infoController.errcode="Error code : "+errcode;
		infoController.errdes=errdes;
		infoController.isHome=false;


		setBase("/toast/info.fxml");
		MyDialoug.showDialoug();

	}
	public static void showError(int error)
	{
	javafx.application.Platform.runLater(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
		
		if (dialog == null) {
			dialog = new Stage();
			dialog.initStyle(StageStyle.UNDECORATED);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(Main.mainstage);
		}

		mainstage = Main.mainstage;
		
		
		if(ErrorList.list.containsKey(""+error))
		{
		ErrorInfo e=ErrorList.list.get(""+error);
		infoController.errname=e.name;
		infoController.errcode="Error code : #"+error;
		infoController.errdes=e.des;
		infoController.isHome=false;

		setBase("/toast/info.fxml");
		MyDialoug.showDialoug();
		}
		else
		{
			
			System.out.println("NO ERROR FOUND IN LIST : "+error);
		}
		
		}
	});
	}

	public static void showErrorHome(int error)
	{
	javafx.application.Platform.runLater(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
		
		if (dialog == null) {
			dialog = new Stage();
			dialog.initStyle(StageStyle.UNDECORATED);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(Main.mainstage);
		}

		mainstage = Main.mainstage;
		
		
		if(ErrorList.list.containsKey(""+error))
		{
		ErrorInfo e=ErrorList.list.get(""+error);
		infoController.errname=e.name;
		infoController.errcode="Error code : #"+error;
		infoController.errdes=e.des;
		infoController.isHome=true;

		setBase("/toast/info.fxml");
		MyDialoug.showDialoug();
		}
		else
		{
			System.out.println("NO ERROR FOUND IN LIST : "+error);
		}
		
		}
	});
	}

	public MyDialoug(Stage mainstage, String page) {

		if (dialog == null) {
			dialog = new Stage();
			dialog.initStyle(StageStyle.UNDECORATED);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(mainstage);
		}

		this.mainstage = mainstage;
		setBase(page);

	}

	void setShortCut() {
		KeyCombination fullscreenshortcut = new KeyCodeCombination(KeyCode.B,
				KeyCombination.CONTROL_ANY);
		dialog.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {

				if (ke.getCode() == KeyCode.ESCAPE || fullscreenshortcut.match(ke)) {
					if (bol) {
						try {
							mainstage.getScene().getRoot().setEffect(null);
							dialog.hide();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						System.out.println("fxml error");
					}
				}

			}
		});
	}

	static void setBase(String page) {

		

				try {
					Parent root = FXMLLoader.load(Main.clsObj.getResource(page));
					Scene dialogScene = new Scene(root);
					dialog.setScene(dialogScene);
				
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		

}
