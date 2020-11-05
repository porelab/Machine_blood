package userinput;

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
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import application.DataStore;
import application.SerialWriter;
import application.writeFormat;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;

public class ScadaController implements Initializable{

	
	
	@FXML
	Label fm1value,fm2value,pg2value,pg1value;
	
	@FXML
	Label lblanc,lblanc2;
	
	@FXML
	AnchorPane ap,ap1,ap2,ap3,ap4,ap5,ap6,ap7;
	
	@FXML
	Button setpr,setfc;
	
	@FXML
	TextField pr,fc;
	
	@FXML
	ImageView v1,v2,v3,v4,v5new,liveimg;
	
	@FXML
	ToggleButton valve1,valve2,valve3,valve4,valve5new,valve6;
	
	public static ToggleButton valve1s,valve2s,valve3s,valve4s;
	
	public Gauge gauge23,gauge23c,gauge20,gauge12,gauge12c,gauge12c1,gauge12c2;
	writeFormat wrD;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		 Image image1 = new Image(this.getClass().getResourceAsStream("/userinput/btnimg.png"));
		 setpr.setGraphic(new ImageView(image1));
		 setfc.setGraphic(new ImageView(image1));
		 
	
		 Image image = new Image(this.getClass().getResourceAsStream("/userinput/valve OFF.png"));
		 v1.setImage(image);
		 v2.setImage(image);
		 v3.setImage(image);
		 v4.setImage(image);
		 v5new.setImage(image);
		
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
					 Image image = new Image(this.getClass().getResourceAsStream("/userinput/valve ON.png"));
					v1.setImage(image);
				

					 Image image1 = new Image(this.getClass().getResourceAsStream("/userinput/test2.png"));
					 liveimg.setImage(image1);

					
					
					wrD=new writeFormat();
						wrD.addChar('V');
						wrD.addChar('1');
						wrD.addChar('1');
						wrD.addChkSm();
						wrD.addLast();
						sendData(wrD);
						
				}
				else
				{	
					 Image image = new Image(this.getClass().getResourceAsStream("/userinput/valve OFf.png"));
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
					 Image image = new Image(this.getClass().getResourceAsStream("/userinput/valve ON.png"));
					 v2.setImage(image);
					 

					 Image image2 = new Image(this.getClass().getResourceAsStream("/userinput/test3.png"));
					 liveimg.setImage(image2);

					 
					 wrD=new writeFormat();
						wrD.addChar('V');
						wrD.addChar('3');
						wrD.addChar('1');
						wrD.addChkSm();
						wrD.addLast();
						sendData(wrD);
				}
				else
				{	
					 Image image = new Image(this.getClass().getResourceAsStream("/userinput/valve OFF.png"));
				 v2.setImage(image);
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
	
	valve3.setOnAction(new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent arg0) {
			
			if(valve3.isSelected())
			{
				 Image image = new Image(this.getClass().getResourceAsStream("/userinput/valve ON.png"));
				 v3.setImage(image);
				 
				 
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
				 Image image = new Image(this.getClass().getResourceAsStream("/userinput/valve OFF.png"));
			 v3.setImage(image);
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
	valve4.setOnAction(new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent arg0) {
			
			if(valve4.isSelected())
			{
				 Image image = new Image(this.getClass().getResourceAsStream("/userinput/valve ON.png"));
				 v4.setImage(image);
				 wrD=new writeFormat();
					wrD.addChar('V');
					wrD.addChar('5');
					wrD.addChar('1');
					wrD.addChkSm();
					wrD.addLast();
					sendData(wrD);
			}
			else
			{	
				 Image image = new Image(this.getClass().getResourceAsStream("/userinput/valve OFF.png"));
			 v4.setImage(image);
			 wrD=new writeFormat();
				wrD.addChar('V');
				wrD.addChar('5');
				wrD.addChar('0');
				wrD.addChkSm();
				wrD.addLast();
				sendData(wrD);
			}
			
			
		}
	});
	
	
valve5new.setOnAction(new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent arg0) {
			
			if(valve5new.isSelected())
			{
				
				 wrD=new writeFormat();
					wrD.addChar('V');
					wrD.addChar('2');
					wrD.addChar('1');
					wrD.addChkSm();
					wrD.addLast();
					sendData(wrD);
				
				 Image image = new Image(this.getClass().getResourceAsStream("/userinput/valve ON.png"));
				 v5new.setImage(image);
				 
				 
				 Image image1 = new Image(this.getClass().getResourceAsStream("/userinput/test1.png"));
				 liveimg.setImage(image1);

			
			}
			else
			{	
				
				 wrD=new writeFormat();
					wrD.addChar('V');
					wrD.addChar('2');
					wrD.addChar('0');
					wrD.addChkSm();
					wrD.addLast();
					sendData(wrD);
				 Image image = new Image(this.getClass().getResourceAsStream("/userinput/valve OFF.png"));
				 v5new.setImage(image);
			 
			}
			
			
		}
	});
	

valve6.setOnAction(new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent arg0) {
			
			if(valve6.isSelected())
			{
				
				 wrD=new writeFormat();
					wrD.addChar('V');
					wrD.addChar('6');
					wrD.addChar('1');
					wrD.addChkSm();
					wrD.addLast();
					sendData(wrD);
				

			
			}
			else
			{	
				
				 wrD=new writeFormat();
					wrD.addChar('V');
					wrD.addChar('6');
					wrD.addChar('0');
					wrD.addChkSm();
					wrD.addLast();
					sendData(wrD);
			
			}
			
			
		}
	});
		/*
		
		//pressure regulator
		  gauge23 = GaugeBuilder.create()
                  .skinType(SkinType.SIMPLE_DIGITAL).maxSize(100, 100)
                  
                  .foregroundBaseColor(Color.rgb(0,0,0))
                  .barColor(Color.rgb(0,0, 0))
                   .maxValue(Integer.parseInt(DataStore.getPr()))
                  .animated(true)
                  .build();
		ap.getChildren().add(gauge23);
		
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
		
		
		*/



		
		//NEW Gauge


		 Gauge gauge1 = GaugeBuilder.create()
                 .skinType(SkinType.SIMPLE)
               	 .needleColor(Color.WHITE)// Dial Color
               	 .needleBorderColor(Color.web("#228CCC"))//Dial Border
               	 .foregroundBaseColor(Color.WHITE)//Value Color
               	 .sections(new Section(0, 16.66666, "0", Color.web("#E7E7E8")),
                           new Section(16.66666, 33.33333, "1", Color.web("#C4D0E2")),
                           new Section(33.33333, 50.0, "2", Color.web("#A4BBDB")),
                           new Section(50.0, 66.66666, "3", Color.web("#83A9D6")),
                           new Section(66.66666, 83.33333, "4", Color.web("#5F99D0")),
                           new Section(83.33333, 100.0, "5", Color.web("#228CCC")))
                // .title("700")
                 //.value(20)
                 .threshold(50)
                 .animated(true)
                 .build();
  				 gauge1.setPrefSize(ap.getPrefWidth(), ap.getPrefHeight());
  				 ap.getChildren().add(gauge1);
  
  				 
  				 
		
  				 Gauge gauge2 = GaugeBuilder.create()
                       .skinType(SkinType.SIMPLE)
		                 .skinType(SkinType.SIMPLE)
		               	 .needleColor(Color.WHITE)// Dial Color
		               	 .needleBorderColor(Color.web("#228CCC"))//Dial Border
		               	 .foregroundBaseColor(Color.WHITE)//Value Color
		               	 
		                 
		                 .sections(new Section(0, 16.66666, "0", Color.web("#E7E7E8")),
		                           new Section(16.66666, 33.33333, "1", Color.web("#C4D0E2")),
		                           new Section(33.33333, 50.0, "2", Color.web("#A4BBDB")),
		                           new Section(50.0, 66.66666, "3", Color.web("#83A9D6")),
		                           new Section(66.66666, 83.33333, "4", Color.web("#5F99D0")),
		                           new Section(83.33333, 100.0, "5", Color.web("#228CCC")))
		                      // .title("700")
                 //      .value(50)
                       .threshold(50)
                       .animated(true)
                       .build();
 	      				 gauge2.setPrefSize(ap2.getPrefWidth(), ap2.getPrefHeight());
 	      ap2.getChildren().add(gauge2);
  				 
  				 
		
 	     
 	     Gauge gauge3 = GaugeBuilder.create()
 	                                  .skinType(SkinType.DASHBOARD)
 	                                  .animated(true)
 	    //                              .title("Pressure")
 	                                  .unit("\u00B0C")
 	                                  .value(0.25)
 	                                  .maxValue(40)
 	                                  .barColor(Color.CRIMSON)
 	                                  .valueColor(Color.GRAY)
 	                                  .titleColor(Color.GRAY)
 	                                  .unitColor(Color.GRAY)
 	                                  .thresholdVisible(true)
 	                                  .threshold(35)
 	                                  .shadowsEnabled(true)
 	                                  .gradientBarEnabled(true)
 	                                  .gradientBarStops(new Stop(0.00, Color.BLUE),
 	                                                    new Stop(0.25, Color.CYAN),
 	                                                    new Stop(0.50, Color.LIME),
 	                                                    new Stop(0.75, Color.YELLOW),
 	                                                    new Stop(1.00, Color.RED))
 	                                  .build();


 						 gauge3.setPrefSize(ap3.getPrefWidth(), ap3.getPrefHeight());

 						 
 						gauge3.setMaxValue(Double.parseDouble(DataStore.getFm1()));
 						 ap3.getChildren().add(gauge3);      

 						 
 						 //PG-2
 						 
 						 Gauge gauge4 = GaugeBuilder.create()
                                  .skinType(SkinType.DASHBOARD)
                                  .animated(true)
          //                        .title("Pressure")
                                  .unit("\u00B0C")
                                  .value(0.75)
                                  .maxValue(40)
                                  .barColor(Color.CRIMSON)
                                  .valueColor(Color.GRAY)
                                  .titleColor(Color.GRAY)
                                  .unitColor(Color.GRAY)
                                  .thresholdVisible(true)
                                  .threshold(35)
                                  .shadowsEnabled(true)
                                  .gradientBarEnabled(true)
                                  .gradientBarStops(new Stop(0.00, Color.BLUE),
                                                    new Stop(0.25, Color.CYAN),
                                                    new Stop(0.50, Color.LIME),
                                                    new Stop(0.75, Color.YELLOW),
                                                    new Stop(1.00, Color.RED))
                                     .build();

 					gauge4.valueProperty().bind(DataStore.sfm2);
 					gauge4.setMaxValue(Double.parseDouble(DataStore.getFm2()));
 					 gauge4.setPrefSize(ap4.getPrefWidth(), ap4.getPrefHeight());
 					 ap4.getChildren().add(gauge4);      

 						 
 					 //PG3........................
 				     Gauge gauge5 = GaugeBuilder.create()
                              .skinType(SkinType.DASHBOARD)
                              .animated(true)
            //                  .title("Pressure")
                              .unit("\u00B0C")
                              .value(0.50)
                              .maxValue(40)
                              .barColor(Color.CRIMSON)
                              .valueColor(Color.GRAY)
                              .titleColor(Color.GRAY)
                              .unitColor(Color.GRAY)
                              .thresholdVisible(true)
                              .threshold(35)
                              .shadowsEnabled(true)
                              .gradientBarEnabled(true)
                              .gradientBarStops(new Stop(0.00, Color.BLUE),
                                                new Stop(0.25, Color.CYAN),
                                                new Stop(0.50, Color.LIME),
                                                new Stop(0.75, Color.YELLOW),
                                                new Stop(1.00, Color.RED))
                              .build();

 				     
 				 gauge5.setPrefSize(ap6.getPrefWidth(), ap6.getPrefHeight());
 				 ap6.getChildren().add(gauge5);      

 				 
 				 //PG-4
 				 
 				 Gauge gauge6 = GaugeBuilder.create()
                         .skinType(SkinType.DASHBOARD)
                         .animated(true)
              //           .title("Pressure")
                         .unit("\u00B0C")
                         .value(0.25)
                         .maxValue(40)
                         .barColor(Color.CRIMSON)
                         .valueColor(Color.GRAY)
                         .titleColor(Color.GRAY)
                         .unitColor(Color.GRAY)
                         .thresholdVisible(true)
                         .threshold(35)
                         .shadowsEnabled(true)
                         .gradientBarEnabled(true)
                         .gradientBarStops(new Stop(0.00, Color.BLUE),
                                           new Stop(0.25, Color.CYAN),
                                           new Stop(0.50, Color.LIME),
                                           new Stop(0.75, Color.YELLOW),
                                           new Stop(1.00, Color.RED))
                                           .maxValue(Integer.parseInt(DataStore.getPg1()))
                                           .build();


 			 gauge6.setPrefSize(ap5.getPrefWidth(), ap5.getPrefHeight());
 			 gauge6.valueProperty().bind(DataStore.spg1);
			 pg2value.textProperty().bind(DataStore.sspg2);
			 
 			ap5.getChildren().add(gauge6);      	

 			
 			
 			
 			
			
			 
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
					
					lblanc.setText(""+d);
					gauge1.setValue(d);
					
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
						
						gauge2.setValue(d);
						lblanc2.setText(""+d);
						
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
