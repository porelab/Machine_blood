package report;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import toast.MyDialoug;
import toast.Openscreen;
import toast.Toast;
import application.Main;
import data_read_write.DatareadN;
import extrafont.Myfont;

public class DeletepopupController implements Initializable {
	
	@FXML
	    private Button btnstart,btncancel;
	
	@FXML
	 Label lab;

	    Myfont f=new Myfont(25);

	 List<File> allfiles=new ArrayList<File>();  

	 List<String> allfolders=new ArrayList<String>();  
	    

	    @FXML
	    private ProgressIndicator delind;

	 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/*Selected file delete or not popup*/
		
		//setLabelFont();
		
		allFiles();
		
		
		
		btncancel.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

				

				MyDialoug.closeDialoug();

			}
		});
		
		btnstart.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				startDetele();
				
			}
		});
				
	}

	
	void startDetele()
	{
		try{
		delind.setVisible(true);
		lab.setText("Deleting...");
		btnstart.setDisable(true);
		for(int i=0;i<allfiles.size();i++)
		{
			allfiles.get(i).delete();
			delind.setProgress((double)i/allfiles.size());
		}
		
		for(int i=0;i<allfolders.size();i++)
		{
			File ff=new File(allfolders.get(i));
			if(ff.list().length==0)
			{
				
					ff.delete();
			}
		}
		FirstController.isDelete.set(false);
		Toast.makeText(Main.mainstage, "Successfully deleted", 1000,100,100);
		FirstController.isDelete.set(true);
		MyDialoug.closeDialoug();
	
		Openscreen.open("/report/first.fxml");
		}
		catch(Exception e)
		{
			delind.setVisible(false);
			lab.setText("Try again later"+e.getMessage());
			btnstart.setDisable(true);
			//e.printStackTrace();
	 MyDialoug.closeDialoug();
			
			Openscreen.open("/report/first.fxml");
		}
		
	}
	
	void allFiles()
	{
	try{
		List<String> s =new ArrayList<String>( FirstController.selectedbox.keySet());
		for(int i=0;i<s.size();i++)
		{
			List<String> templist=FirstController.selectedbox.get(s.get(i));
			Map<String,DatareadN> datas=new HashMap<String, DatareadN>();
			System.out.println("Sample name : "+s.get(i));
			
			for(int j=0;j<templist.size();j++)
			{
				File f=new File(templist.get(j));
			String parent = f.getAbsoluteFile().getParent();
			
			if(!allfolders.contains(parent))
			{
				allfolders.add(parent);
				System.out.println("Addingggggg to folder list : "+parent);
			}
				allfiles.add(f);
			}
			
		}
		lab.setText("Note : "+allfiles.size()+" Files will be deleted.");
		
		if(allfiles.size()==0)
		{
			
		}
		
		}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	}
	
	void setLabelFont()
	{
		btncancel.setFont(f.getM_M());
		btnstart.setFont(f.getM_M());

	}
	
	
	
	
}
