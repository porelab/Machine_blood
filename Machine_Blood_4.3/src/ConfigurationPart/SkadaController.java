package ConfigurationPart;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import toast.Openscreen;
import application.DataStore;
import application.SerialWriter;
import application.writeFormat;

import com.jfoenix.controls.JFXToggleButton;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;

public class SkadaController implements Initializable{

	
	@FXML
	Button back;
	
	
	@FXML
	Label fm1value,fm2value,pg2value,pg1value;
	
	@FXML
	AnchorPane ap,ap1,ap2,ap3,ap4,ap5,ap6,ap7;
	
	@FXML
	Button setpr,setfc;
	
	@FXML
	TextField pr,fc;
	
	@FXML
	ImageView v1,v2,v3,v4;
	
	@FXML
	JFXToggleButton valve1,valve2,valve3,valve4;
	
	public static JFXToggleButton valve1s,valve2s,valve3s,valve4s;
	
	public Gauge gauge23,gauge23c,gauge20,gauge12,gauge12c,gauge12c1,gauge12c2;
	writeFormat wrD;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		back.setStyle("-fx-background-color: #f8f8f85c;");
		File file = new File("images/new/valve.png");
		 Image image = new Image(file.toURI().toString()); 
		 v2.setImage(image);
		 
		 v3.setImage(image);
		 v1.setImage(image);
		 file = new File("images/new/off.png");
		  image = new Image(file.toURI().toString()); 
		 v4.setImage(image);
		back.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("BacK To Home-------------->>>>");
				Openscreen.open("/application/first.fxml");
			
			}
		});	
		DataStore.getconfigdata();
		
		valve1s=valve1;
		valve2s=valve2;
		valve3s=valve3;
		valve4s=valve4;
		
		//valve1.selectedProperty().bind(DataStore.sv1);
		//valve2.selectedProperty().bind(DataStore.sv2);
		//valve3.selectedProperty().bind(DataStore.sv3);
		//valve4.selectedProperty().bind(DataStore.sv4);
		
	
		
		valve1.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				
		
				
				if(valve1.isSelected())
				{
					File file = new File("images/new/valveo.png");
					 Image image = new Image(file.toURI().toString()); 
					 v1.setImage(image);
					 wrD=new writeFormat();
						wrD.addChar('V');
						wrD.addChar('1');
						wrD.addChar('1');
						wrD.addChkSm();
						wrD.addLast();
						sendData(wrD);
						
				}
				else
				{	File file = new File("images/new/valve.png");
				 Image image = new Image(file.toURI().toString()); 
				 v1.setImage(image);
				 wrD=new writeFormat();
					wrD.addChar('V');
					wrD.addChar('1');
					wrD.addChar('0');
					wrD.addChkSm();
					wrD.addLast();
					sendData(wrD);
				}
				
				
				
			}
		});
		
	valve2.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				
				if(valve2.isSelected())
				{
					File file = new File("images/new/valveo.png");
					 Image image = new Image(file.toURI().toString()); 
					 v2.setImage(image);
					 wrD=new writeFormat();
						wrD.addChar('V');
						wrD.addChar('2');
						wrD.addChar('1');
						wrD.addChkSm();
						wrD.addLast();
						sendData(wrD);
				}
				else
				{	File file = new File("images/new/valve.png");
				 Image image = new Image(file.toURI().toString()); 
				 v2.setImage(image);
				 wrD=new writeFormat();
					wrD.addChar('V');
					wrD.addChar('2');
					wrD.addChar('0');
					wrD.addChkSm();
					wrD.addLast();
					sendData(wrD);
				}
				
				
			}
		});
	
	valve3.setOnAction(new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent arg0) {
			
			if(valve3.isSelected())
			{
				File file = new File("images/new/valveo.png");
				 Image image = new Image(file.toURI().toString()); 
				 v3.setImage(image);
				 wrD=new writeFormat();
					wrD.addChar('V');
					wrD.addChar('3');
					wrD.addChar('1');
					wrD.addChkSm();
					wrD.addLast();
					sendData(wrD);
			}
			else
			{	File file = new File("images/new/valve.png");
			 Image image = new Image(file.toURI().toString()); 
			 v3.setImage(image);
			 wrD=new writeFormat();
				wrD.addChar('V');
				wrD.addChar('3');
				wrD.addChar('0');
				wrD.addChkSm();
				wrD.addLast();
				sendData(wrD);
			}
			
			
		}
	});
	valve4.setOnAction(new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent arg0) {
			
			if(valve4.isSelected())
			{
				File file = new File("images/new/on.png");
				 Image image = new Image(file.toURI().toString()); 
				 v4.setImage(image);
				 wrD=new writeFormat();
					wrD.addChar('V');
					wrD.addChar('4');
					wrD.addChar('1');
					wrD.addChkSm();
					wrD.addLast();
					sendData(wrD);
			}
			else
			{	
				File file = new File("images/new/off.png");
			 Image image = new Image(file.toURI().toString()); 
			 v4.setImage(image);
			 wrD=new writeFormat();
				wrD.addChar('V');
				wrD.addChar('4');
				wrD.addChar('0');
				wrD.addChkSm();
				wrD.addLast();
				sendData(wrD);
			}
			
			
		}
	});
	
		
		
		//pressure regulator
		  gauge23 = GaugeBuilder.create()
                  .skinType(SkinType.SIMPLE_DIGITAL).maxSize(100, 100)
                  
                  .foregroundBaseColor(Color.rgb(0,0,0))
                  .barColor(Color.rgb(0,0, 0))
                   .maxValue(Integer.parseInt(DataStore.getPr()))
                  .animated(true)
                  .build();
		ap.getChildren().add(gauge23);
		
		//
		   gauge20 = GaugeBuilder.create()
                   .skinType(SkinType.LEVEL)
                   .title("Capacity")
                   .titleColor(Color.WHITE)
                   .animated(true)
                   .maxValue(Integer.parseInt(DataStore.getFc()))
                   .gradientBarEnabled(true)
                   .maxSize(140, 200)
                   .gradientBarStops(new Stop(0.0, Color.RED),
                                     new Stop(0.25, Color.ORANGE),
                                     new Stop(0.5, Color.YELLOW),
                                     new Stop(0.75, Color.YELLOWGREEN),
                                     new Stop(1.0, Color.LIME))
                   .build();
		
			ap1.getChildren().add(gauge20);
		//
			  gauge23c = GaugeBuilder.create()
	                  .skinType(SkinType.SIMPLE_DIGITAL).maxSize(100, 100)
	                  	.maxValue(Integer.parseInt(DataStore.getFc()))
	                  .foregroundBaseColor(Color.rgb(0,0,0))
	                  .barColor(Color.rgb(0,0, 0))
	                  .unit("KPH")
	                  .animated(true)
	                  .build();
			ap2.getChildren().add(gauge23c);
		
		//......
			 gauge12 = GaugeBuilder.create()
                     .skinType(SkinType.KPI)
                      .foregroundBaseColor(Color.BLACK)
                     .needleColor(Color.BLACK)
                     .barBackgroundColor(Color.GREEN)
                      .maxValue(Integer.parseInt(DataStore.getFm1()))
                     .animated(true)
                     //.threshold(75)
                     .maxSize(100, 100)
                     .build();
			 ap3.getChildren().add(gauge12);
			gauge12.valueProperty().bind(DataStore.sfm1);
			 fm1value.textProperty().bind(DataStore.ssfm1);
			 
			 
			 gauge12c = GaugeBuilder.create()
                     .skinType(SkinType.KPI)
                     .foregroundBaseColor(Color.BLACK)
                     .needleColor(Color.BLACK)
                     .animated(true)
                     .maxValue(Integer.parseInt(DataStore.getFm2()))
                   //  .threshold(75)
                  .maxSize(100, 100)
                     .build();
			 ap4.getChildren().add(gauge12c);
			gauge12c.valueProperty().bind(DataStore.sfm2);
			 fm2value.textProperty().bind(DataStore.ssfm2);
			 
			 gauge12c1 = GaugeBuilder.create()
                     .skinType(SkinType.KPI)
                     .foregroundBaseColor(Color.BLACK)
                     .needleColor(Color.BLACK)
                     .animated(true)
                     //.threshold(75)
                     
                      .maxValue(Integer.parseInt(DataStore.getPg2()))
                  .maxSize(100, 100)
                     .build();
			 gauge12c1.valueProperty().bind(DataStore.spg2);
			 pg1value.textProperty().bind(DataStore.sspg1);
			 ap5.getChildren().add(gauge12c1);
			 
			 gauge12c2 = GaugeBuilder.create()
                     .skinType(SkinType.KPI)
                     .foregroundBaseColor(Color.BLACK)
                     .needleColor(Color.BLACK)
                     .animated(true)
                      .maxValue(Integer.parseInt(DataStore.getPg1()))
                   //  .threshold(75)
                  .maxSize(100, 100)
                     .build();
			 ap6.getChildren().add(gauge12c2);

			 gauge12c2.valueProperty().bind(DataStore.spg1);
			 pg2value.textProperty().bind(DataStore.sspg2);
			
			 
			 setpr.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					
					try{
						
					double d=Double.parseDouble(pr.getText());
					List<Integer> ss=getValueList((int)d);
					wrD=new writeFormat();
					wrD.addChar('P');
					wrD.addChar('R');
					wrD.addData1(ss);
					wrD.addChkSm();
					wrD.addLast();
					sendData(wrD);
					
					gauge23.setValue(d);
					
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
				}
			});
			 setfc.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						
						try{
							double d=Double.parseDouble(fc.getText());
							//double d=Double.parseDouble(pr.getText());
							List<Integer> ss=getValueList((int)d);
							wrD=new writeFormat();
							wrD.addChar('F');
							wrD.addChar('C');
							wrD.addData1(ss);
							wrD.addChkSm();
							wrD.addLast();
							sendData(wrD);
						
						gauge23c.setValue(d);
						
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						
					}
				});
	}
	public void sendData(writeFormat w)
	{
		System.out.println("Sending Data......");
		w.showData();
		Thread t=new Thread(new SerialWriter(DataStore.out,w));
	    t.start();
		//start.setDisable(true);
	}

public static List<Integer> getValueList(int val)
{
	String pad="000000";
	String st=""+Integer.toHexString(val);
	String st1=(pad + st).substring(st.length());
	List<Integer> ls=new ArrayList<Integer>();
	
	int n = (int) Long.parseLong(st1.substring(0, 2), 16);
	int n1 = (int) Long.parseLong(st1.substring(2, 4), 16);
	int n2 = (int) Long.parseLong(st1.substring(4, 6), 16);
	ls.add(n);
	ls.add(n1);
	ls.add(n2);
	
	
	
	return ls;
}

}
