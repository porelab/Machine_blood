package userinput;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import toast.MyDialoug;
import toast.Openscreen;
import toast.Toast;
import application.DataStore;
import application.Main;
import application.MainancController;
import application.SerialWriter;
import application.writeFormat;
import extrafont.Myfont;

public class NabourdpopupController implements Initializable {
	
	@FXML
	AnchorPane root;
	
	@FXML
	    private Button btnyes;

	    @FXML
	    private Button btncancel;
	    
	    
	    Myfont f=new Myfont(22);
	    void addShortCut() {
			
					root.setOnKeyPressed(new EventHandler<KeyEvent>() {
						@Override
						public void handle(KeyEvent ke) {
							
							if(ke.getCode()==KeyCode.ENTER)
							{

								 Toast.makeText(Main.mainstage, "Test Aborting...", 1000, 200, 200);
								 MyDialoug.closeDialoug();
									Openscreen.open("/application/first.fxml");
									
									
							}
							
						}
					});

					

			}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		/*Test aboard Warning Popup*/
		
		
		//setLabelFont();
		addShortCut();
		
		btncancel.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MyDialoug.closeDialoug();
				System.out.println("canclelll NTNTN");
			}
		});
		
		btnyes.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				MyDialoug.closeDialoug();
				Openscreen.open("/application/first.fxml");
				
				
				/*
				 * 	writeFormat	wrd=new writeFormat();
			
					wrd.addStop();
					wrd.addChar('T');
					wrd.addBlank(4);
					wrd.addChkSm();
					wrd.addLast();
					sendData(wrd);
					
					try{
						MyDialoug.closeDialoug();
						
						File f=new File("CsvFolder/"+"N0073"+"/"+"New folder");
						File[] ff=f.listFiles();
						if(ff.length>0)
						{

							Openscreen.open("/userinput/Nresult.fxml");
						}
						else
						{
							Openscreen.open("/application/Nfirst.fxml");
							
						}
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
						Openscreen.open("/application/Nfirst.fxml");
					}
					
					*/
					
			}
		});
				
	}

	void setLabelFont()
	{
		btncancel.setFont(f.getM_M());
		btnyes.setFont(f.getM_M());

	}
	void gotohome()
	{
		

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/first.fxml"));
		try {
		Pane cmdPane = (Pane) fxmlLoader.load();
 		
		MainancController.mainanc1.getChildren().clear();
		MainancController.mainanc1.getChildren().add(cmdPane);
		} 
		catch (Exception e)
		{
		 	e.printStackTrace();
		}
	}
	public void sendData(writeFormat w)
	{
		System.out.println("Sending Data......");
		w.showData();
		Thread t=new Thread(new SerialWriter(DataStore.out, w));
		t.start();
	
	}
	
	
	
	
	
}
