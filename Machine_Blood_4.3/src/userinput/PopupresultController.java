package userinput;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.DataStore;
import application.Myapp;
import toast.MyDialoug;
import toast.Openscreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import data_read_write.DatareadN;
import drawchart.ChartPlot;

public class PopupresultController implements Initializable {

    @FXML
    private Label lblsamplename,lblbpp,lblbpdiamter,msg;

    @FXML
    private Button btnhome,startautotest;

    @FXML
    private AnchorPane pagination1;
    
    ChartPlot c;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		/*Test Complete After Open Result Popup*/
		
		//File f=new File("test_sample0221.csv");
		DatareadN d=new DatareadN();
		d.fileRead(NLivetestController.savefile);
		
		System.out.println("File : "+NLivetestController.savefile);
		System.out.println("Data : "+d.data);
		
		setData(d);
		
		btnhome.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				MyDialoug.closeDialoug();
				
				Openscreen.open("/application/first.fxml");
				
			}
		});
		
		startautotest.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
			
				MyDialoug.closeDialoug();
				Openscreen.open("/userinput/Nlivetest.fxml");
				
				
			}
		});
	}
	
	void setData(DatareadN dr)
	{
		
		List<DatareadN> list_d =new ArrayList<DatareadN>();
			c=new ChartPlot(true);
			
		list_d.add(dr);
		System.out.println(dr.data);
	//pagination1.getChildren().add(c.drawBarchart(pagination1.getPrefWidth(),pagination1.getPrefHeight(),"Pore size Distribution", "Diameter (micron)", "Percentage (%)", list_d.get(0).getDistributionChart(list_d.get(0).getValuesOf(list_d.get(0).data.get("diameter")+""),list_d.get(0).getValuesOf(list_d.get(0).data.get("psd")+""),30)));
	//pagination1.getChildren().add(c.drawBarchartNumber(pagination1.getPrefWidth(),pagination1.getPrefHeight(),"Pore size Distribution", "Diameter (micron)", "Percentage (%)", list_d.get(0).getValuesOf(list_d.get(0).data.get("diameter").toString()),list_d.get(0).getValuesOf(list_d.get(0).data.get("psd").toString())));
	
		//pagination1.getChildren().add(c.drawLinechart(pagination1.getPrefWidth(),pagination1.getPrefHeight(),"1Pressure vs Time", "Time (Second)", "Pressure",list_d,false,11,12,"(3) Pressure vs Time"));
  	  
		
		
		pagination1.getChildren().add(c.drawLinechartWithScatterMultiple(pagination1.getPrefWidth(),pagination1.getPrefHeight(),"1F/PT vs Time", "Time (Second)", "Pressure",list_d,"(3) Incremental Filter-Flow % vs Diameter"));
    	 
		
		
		lblsamplename.setText(""+dr.data.get("sample"));
		lblbpdiamter.setText(dr.data.get("result").toString());
		msg.setText(""+dr.data.get("resultmsg"));
		
		lblbpp.setText(""+DataStore.ConvertPressure("" + dr.data.get("bpressure")));


	}
}
