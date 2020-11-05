package report;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberExpression;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.SnapshotResult;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.util.Callback;

import javax.imageio.ImageIO;

import toast.MyDialoug;
import toast.Openscreen;
import application.DataStore;
import application.Main;
import application.Myapp;
import data_read_write.DatareadN;
import de.tesis.dynaware.javafx.fancychart.zoom.Zoom;
import drawchart.ChartPlot;
import drawchart.SmoothedChart;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Gauge.SkinType;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import extrafont.Myfont;

public class ReportController implements Initializable
{
	@FXML
	AnchorPane anc;
	
	 private static final String CHART_FILE_PREFIX = "chart_";
	  private static final String WORKING_DIR = System.getProperty("user.dir")+"/mypic/";
	  
	  private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
	  private final Random random = new Random();
	  
	  private final int N_CHARTS     = 1;
	  private final int PREVIEW_SIZE = 800;
	  private final int CHART_SIZE   = 600;

	  final ExecutorService saveChartsExecutor = createExecutor("SaveCharts");
	          
	  ChartPlot c;
	  
	 ArrayList<Boolean> onlyonce;
	 
	

	 

	 
	  
	@FXML
	Button report,btnback,btnsavereport,btnhome;
	
	@FXML
	ScrollPane scrfilename;
	
	@FXML
	AnchorPane slides;
	
	@FXML
	Label avgbub,lblmporetype;
	
	double avgbubpt=0;

	@FXML
	Label grlable;
	
	@FXML
	AnchorPane pagination1;

	 
	 Map<String,Map<String,DatareadN>> alldata;
	 
	 List<String> grpclr;
	 
	 static File file;
		
	 int max=20;
	
	    Myfont fontss=new Myfont(14);

	 
 int totsample=0;
 static List<Pane> listofchart=null;
 static List<DatareadN> list_d;
 static File pdffilepath=null;
 MyDialoug mydia;
 
 
 private ImageView createImageViewForChartFile(Integer chartNumber) {
   ImageView imageView = new ImageView(new Image("file:///" + getChartFilePath(chartNumber)));
   imageView.setFitWidth(PREVIEW_SIZE);
   imageView.setPreserveRatio(true);
   return imageView;
 }

 private Pane createProgressPane(SaveChartsTask saveChartsTask) {
   GridPane progressPane = new GridPane();
   
   progressPane.setHgap(5);
   progressPane.setVgap(5);
   progressPane.addRow(0, new Label("Create:"),     createBoundProgressBar(saveChartsTask.chartsCreationProgressProperty()));
   progressPane.addRow(1, new Label("Snapshot:"),   createBoundProgressBar(saveChartsTask.chartsSnapshotProgressProperty()));
   progressPane.addRow(2, new Label("Save:"),       createBoundProgressBar(saveChartsTask.imagesExportProgressProperty()));
   progressPane.addRow(3, new Label("Processing:"), 
     createBoundProgressBar(
       Bindings
         .when(saveChartsTask.stateProperty().isEqualTo(Worker.State.SUCCEEDED))
           .then(new SimpleDoubleProperty(1))
           .otherwise(new SimpleDoubleProperty(ProgressBar.INDETERMINATE_PROGRESS))
     )
   );

   return progressPane;
 }

 private ProgressBar createBoundProgressBar(NumberExpression progressProperty) {
   ProgressBar progressBar = new ProgressBar();
   progressBar.setMaxWidth(Double.MAX_VALUE);
   progressBar.progressProperty().bind(progressProperty);
   GridPane.setHgrow(progressBar, Priority.ALWAYS);
   return progressBar;
 }

 class ChartsCreationTask extends Task<Void> {
   private final int nCharts;
   private final BlockingQueue<Parent> charts;
   
   ChartsCreationTask(BlockingQueue<Parent> charts, final int nCharts) {
     this.charts = charts;
     this.nCharts = nCharts;
     updateProgress(0, nCharts);
   }
   
   @Override protected Void call() throws Exception {
     int i = nCharts;
     while (i > 0) {
       if (isCancelled()) {
         break;
       }
       System.out.println("charts : "+i);
    
       if(i==1)
       {
    	  // Pane p=c.drawLinechart(pagination1.getPrefWidth(),pagination1.getPrefHeight(),"1F/PT vs Time", "Time (Second)", "Pressure",list_d,false,11,12,"(3) Incremental Filter-Flow % vs Diameter");
    	 
    	   Pane p=c.drawLinechartWithScatterMultiple(pagination1.getPrefWidth(),pagination1.getPrefHeight(),"1Pressure vs Time", "Time (Second)", "Pressure ("+DataStore.getUnitepressure()+")",list_d,"(3) Incremental Filter-Flow % vs Diameter");
      	 
    	   charts.put(p);
    	   listofchart.add(p);
    	 
    	   
    	
       }
       i--;
       updateProgress(nCharts - i, nCharts);
     }
     
     return null;
   }
  
 }
 
 class ChartsSnapshotTask extends Task<Void> {
   private final int nCharts;
   
   private final BlockingQueue<Parent> charts;
   private final BlockingQueue<BufferedImage> images;
   
   ChartsSnapshotTask(BlockingQueue<Parent> charts, BlockingQueue<BufferedImage> images, final int nCharts) {
     this.charts = charts;
     this.images = images;
     this.nCharts = nCharts;
     updateProgress(0, nCharts);
     
   }
   
   @Override protected Void call() throws Exception {
     int i = nCharts;
     while (i > 0) {
       if (isCancelled()) {
         break;
       }
       images.put(snapshotChart(charts.take()));
         i--;
       updateProgress(nCharts - i, nCharts);
     }
     
     return null;
   }
   
   private BufferedImage snapshotChart(final Parent chartContainer) throws InterruptedException {
     final CountDownLatch latch = new CountDownLatch(1);
     // render the chart in an offscreen scene (scene is used to allow css processing) and snapshot it to an image.
     // the snapshot is done in runlater as it must occur on the javafx application thread.
     final SimpleObjectProperty<BufferedImage> imageProperty = new SimpleObjectProperty();
     Platform.runLater(new Runnable() {
       @Override public void run() {
         Scene snapshotScene = new Scene(chartContainer);
        snapshotScene.getStylesheets().add(getClass().getResource("dynamicgraph.css").toExternalForm());
        chartContainer.getStylesheets().add(getClass().getResource("dynamicgraph.css").toExternalForm());
        
         final SnapshotParameters params = new SnapshotParameters();
        
         chartContainer.snapshot(
           new Callback<SnapshotResult, Void>() {
             @Override public Void call(SnapshotResult result) {
               imageProperty.set(SwingFXUtils.fromFXImage(result.getImage(), null));
               latch.countDown();
               return null;
             }
           },
           params, 
           null
         );
       }
     });

     latch.await();
     
     return imageProperty.get();
   }
 }
 
 class PngsExportTask extends Task<Void> {
   private final int nImages;
   private final BlockingQueue<BufferedImage> images;
   
   PngsExportTask(BlockingQueue<BufferedImage> images, final int nImages) {
     this.images = images;
     this.nImages = nImages;
     updateProgress(0, nImages);
   }
   
   @Override protected Void call() throws Exception {
     int i = nImages;
     while (i > 0) {
       if (isCancelled()) {
         break;
       }
       exportPng(images.take(), getChartFilePath(nImages - i));
       i--;
       updateProgress(nImages - i, nImages);
     }
     
     return null;
   }
   
   private void exportPng(BufferedImage image, String filename) {
     try {
       ImageIO.write(image, "png", new File(filename));
     } catch (IOException ex) {
       Logger.getLogger(ChartPlot.class.getName()).log(Level.SEVERE, null, ex);
     }
   }
 }
 
 class SaveChartsTask<Void> extends Task {
   private final BlockingQueue<Parent>        charts         = new ArrayBlockingQueue(10);
   private final BlockingQueue<BufferedImage> bufferedImages = new ArrayBlockingQueue(10);
   private final ExecutorService    chartsCreationExecutor   = createExecutor("CreateCharts");
   private final ExecutorService    chartsSnapshotExecutor   = createExecutor("TakeSnapshots");
   private final ExecutorService    imagesExportExecutor     = createExecutor("ExportImages");
   private final ChartsCreationTask chartsCreationTask;
   private final ChartsSnapshotTask chartsSnapshotTask;
   private final PngsExportTask     imagesExportTask;
   
   SaveChartsTask(final int nCharts) {
     chartsCreationTask = new ChartsCreationTask(charts, nCharts);
     chartsSnapshotTask = new ChartsSnapshotTask(charts, bufferedImages, nCharts);
     imagesExportTask   = new PngsExportTask(bufferedImages, nCharts);

     setOnCancelled(new EventHandler() {
       @Override public void handle(Event event) {
         chartsCreationTask.cancel();
         chartsSnapshotTask.cancel();
         imagesExportTask.cancel();
       }
     });
     
     imagesExportTask.workDoneProperty().addListener(new ChangeListener<Number>() {
       @Override public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number workDone) {
         updateProgress(workDone.intValue(), nCharts);
       }
     });
   }
   
   ReadOnlyDoubleProperty chartsCreationProgressProperty() {
     return chartsCreationTask.progressProperty();
   }
          
   ReadOnlyDoubleProperty chartsSnapshotProgressProperty() {
     return chartsSnapshotTask.progressProperty();
   }
          
   ReadOnlyDoubleProperty imagesExportProgressProperty() {
     return imagesExportTask.progressProperty();
   }
          
   @Override protected Void call() throws Exception {
     chartsCreationExecutor.execute(chartsCreationTask);
     chartsSnapshotExecutor.execute(chartsSnapshotTask);
     imagesExportExecutor.execute(imagesExportTask);
     
     chartsCreationExecutor.shutdown();
     chartsSnapshotExecutor.shutdown();
     imagesExportExecutor.shutdown();
     
     try {
       imagesExportExecutor.awaitTermination(1, TimeUnit.DAYS);
     } catch (InterruptedException e) {
       /** no action required */
     } 
     
     return null;
   }
 }
 
 private String getChartFilePath(int chartNumber) {
	 String name="";
	 if(chartNumber == 0)
	 {
		 name="1Pressure Vs Time";
	 }

   return new File(WORKING_DIR,""+ name + ".png").getPath();
 }

 private ExecutorService createExecutor(final String name) {       
   ThreadFactory factory = new ThreadFactory() {
     @Override public Thread newThread(Runnable r) {
       Thread t = new Thread(r);
       t.setName(name);
       t.setDaemon(true);
       return t;
     }
   };
   
   return Executors.newSingleThreadExecutor(factory);
 }  
 
 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	
		System.out.println("Genrate Result....");
		list_d=new ArrayList<DatareadN>();
    	listofchart=new ArrayList<Pane>();
    	
		grpclr=new ArrayList<String>();
		
		scrfilename.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		
		alldata=new HashMap<String, Map<String,DatareadN>>();
		
	


    	listofchart=new ArrayList<Pane>();
		c=new ChartPlot(true);
		//
		
		btnhome.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Openscreen.open("/application/first.fxml");
				
			}
		});
		
			btnsavereport.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

			
				 FileChooser fileChooser = new FileChooser();
				  
	             //Set extension filter
	            // FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
	           //  fileChooser.getExtensionFilters().add(extFilter);
	             
	             if(list_d.size()==1)
	             {
	            	 fileChooser.setInitialFileName(list_d.get(0).filename+".pdf");
	             }
	             
	             //Show save file dialog
	            pdffilepath = fileChooser.showSaveDialog(Main.mainstage);
	             
	             if(pdffilepath!=null)
	             {
	         	
					mydia=new MyDialoug(Main.mainstage, "/report/pdfselection.fxml");
						
						mydia.showDialoug();
	             
	             }

				
			  
					
			}
		});
	
		
		btnback.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Openscreen.open("/report/first.fxml");
				
			}
		});

		
		report.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Openscreen.open("/application/first.fxml");
			}
		});
		
		

	    final SaveChartsTask saveChartsTask = new SaveChartsTask(N_CHARTS);

	    	createChartImagePagination(saveChartsTask);
	    
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
					setColor();
					setTableAndGuage();

					setResult();	
					 saveChartsExecutor.execute(saveChartsTask);
					    
			}
		});
		

			     //root.getChildren().add(createChartImagePagination(saveChartsTask));
			 			     System.out.println("hello");
	}
	


	/*Set result data in table*/
	void setResult()
	{

		VBox v=new VBox(10);
		v.setPadding(new Insets(0,0,0,0));
		
	   	   String fff=null,wpp=null,wff=null;
	   	   BigDecimal bb=BigDecimal.valueOf(0);
	   	   List<String> clrs=DataStore.getColorMultiple();
		for(int i=0;i<list_d.size();i++)
		{	
			fff=""+list_d.get(i).filename;
	
			wff = "" + DataStore.ConvertPressure(list_d.get(i).data.get("bpressure").toString());
			wpp =list_d.get(i).data.get("result").toString();
			
			bb.add(BigDecimal.valueOf(Double.parseDouble(wff)));
			
			
			v.getChildren().add(getVBox(fff, Myapp.getRound(Double.parseDouble(wff), 2),wpp,clrs.get(i)));
			
		
		}
		
		
	
		
		
		
		scrfilename.setContent(v);
	}
	


	/*Set data point in graph*/
    private void setDataPointPopup(XYChart<Number, Number> sc) {
		final Popup popup = new Popup();
		popup.setHeight(20);
		popup.setWidth(60);

		for (int i = 0; i < sc.getData().size(); i++) {
			final int dataSeriesIndex = i;
			final XYChart.Series<Number, Number> series = sc.getData().get(i);
			for (final Data<Number, Number> data : series.getData()) {
				final Node node = data.getNode();
				node.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, new EventHandler<MouseEvent>() {

					private static final int X_OFFSET = 15;
					private static final int Y_OFFSET = -5;
					 Label label = new Label();
        
					@Override
					public void handle(final MouseEvent event) {
					//	System.out.println("MOuse Event");
						final String colorString = "#cfecf0";
						label.setFont(new Font(20));
						popup.getContent().setAll(label);
						label.setStyle("-fx-background-color: " + colorString + "; -fx-border-color: " + colorString
								+ ";");
						label.setText("x=" + data.getXValue() + ", y=" + data.getYValue());
						popup.show(data.getNode().getScene().getWindow(), event.getScreenX() + X_OFFSET,
								event.getScreenY() + Y_OFFSET);
						event.consume();
					}
					
					
					
					public EventHandler<MouseEvent> init() {
						label.getStyleClass().add("chart-popup-label");
						return this;
					}

				}.init());

				node.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, new EventHandler<MouseEvent>() {

					@Override
					public void handle(final MouseEvent event) {
						popup.hide();
						event.consume();
					}
				});

				// this handler selects the corresponding table item when a data
				// item in the chart was clicked.
		
				
			}
		}

	}
    
	
	VBox getVBox(String name,double single,double bub)
	{
		VBox v=new VBox(10);
		v.setLayoutX(single*bub);
		
		Label l=new Label();
		l.setText(name);
		l.setTextFill(Color.web("#727376"));
		l.setFont(fontss.getM_M());

		v.getChildren().add(l);
		Line li=new Line();
		li.setStroke(Color.web("#727376"));
		li.setStrokeWidth(3);
		li.setStartY(50);
		li.setEndY(-50);

		li.setStartX(0);
		li.setEndX(0);
		li.setLayoutX(0);
		li.setLayoutY(0);
		v.getChildren().add(li);
		
		
		
		
		return v;
	}
	
	public void setColor()
	{
		
		grpclr.addAll(Myapp.colors);
		
	}
	
	/*Set gauge with result data*/
	void setTableAndGuage()
	{
		double wid=slides.getPrefWidth();
		double singlepart=wid/max;
		System.out.println("width : "+wid +  " - singlepart : "+singlepart);
		
		List<String> s =new ArrayList<String>( FirstController.selectedbox.keySet());
		for(int i=0;i<s.size();i++)
		{
			List<String> templist=FirstController.selectedbox.get(s.get(i));
			Map<String,DatareadN> datas=new HashMap<String, DatareadN>();
			System.out.println("Sample name : "+s.get(i));
			for(int j=0;j<templist.size();j++)
			{
				File f=new File(templist.get(j));
				DatareadN d=new DatareadN();
				d.fileRead(f);
				list_d.add(d);
				datas.put(f.getName(), d);
			//	avgbubpt=avgbubpt+Double.parseDouble(d.getDarcy());
				
			//	slides.getChildren().add(getVBox(f.getName(),singlepart,Double.parseDouble(d.getDarcy())));
				
				totsample++;
			//	v.getChildren().add(getVBox(f.getName(), d.getDarcy()));
				System.out.println(templist.get(j));
				
			}
		//	alldata.put(s.get(i), datas);
			
		}
		//avgbub.setText(""+(avgbubpt/totsample));
		
	//	gauge.setValue(avgbubpt/totsample);
		//scrfilename.setContent(v);
		
		
	}
	
	
	HBox getVBox(String name,String bubp,String bubd,String clr)
	{
		
		HBox v1 = new HBox(5);
		v1.setPadding(new Insets(5, 0, 0, 3));

		Label l1 = new Label();
		l1.setMaxWidth(200);
		l1.setMinWidth(200);
		l1.setPrefWidth(200);
		l1.setWrapText(true);
		l1.setFont(fontss.getM_M());
		l1.setTextFill(Color.web(clr));
		l1.setText(name);

		Label lv = new Label();
		lv.setText(bubp);

		lv.setMaxWidth(60);
		lv.setMinWidth(60);
		lv.setPrefWidth(60);
		lv.setFont(fontss.getM_M());
		lv.setTextFill(Color.web("#727376"));

		Label lv1 = new Label();
		lv1.setText(bubd);

		lv1.setMaxWidth(60);
		lv1.setMinWidth(60);
		lv1.setPrefWidth(60);
		lv1.setFont(fontss.getM_M());
		lv1.setTextFill(Color.web("#727376"));

	

		
		Circle rectangle=new Circle(7);
		rectangle.setFill(Paint.valueOf(clr));
		


		v1.getChildren().addAll(rectangle,l1, lv, lv1);
		return v1;
	}
		
	
/*Bubble Point Diamter Gauge*/
/*Chart Image Snapshot*/
	private void createChartImagePagination(final SaveChartsTask saveChartsTask) {
		
		
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setMaxSize(PREVIEW_SIZE * 1/10, PREVIEW_SIZE * 1/10);
        BorderPane b=new BorderPane();
        b.setPrefSize(pagination1.getPrefWidth(), pagination1.getPrefHeight());
        b.setCenter(progressIndicator);
        
        pagination1.getChildren().setAll(b);
        
        final ChangeListener<Number> WORK_DONE_LISTENER = new ChangeListener<Number>() {
          @Override public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            if (0 < saveChartsTask.getWorkDone()) {
           	  Pane ap=listofchart.get(0);
           	   
           	  try {
	         	  LineChart<Number, Number>  lineChart=(LineChart<Number, Number>) ap.getChildren().get(0);
	         	 
	         	    setDataPointPopup(lineChart);
	     	    
	         	     //  Zoom zoom =new Zoom(lineChart,ap);    	
	         	
	           pagination1.getChildren().setAll(ap);

              saveChartsTask.workDoneProperty().removeListener(this);
           	  }
           	  catch(Exception e)
           	  {
           		 SmoothedChart<Number, Number>  lineChart=(SmoothedChart<Number, Number>) ap.getChildren().get(0);
	         	 
	         	    setDataPointPopup(lineChart);
	     	    
	         	     //  Zoom zoom =new Zoom(lineChart,ap);    	
	         	
	           pagination1.getChildren().setAll(ap);

           saveChartsTask.workDoneProperty().removeListener(this);
           	  }
            
       	   
            }
          }
        };

       
        saveChartsTask.workDoneProperty().addListener(WORK_DONE_LISTENER);
      }
   
	/*Save Scale*/
	void savePic(AnchorPane n)
	{

        double pixelScale=2.0;
	        
	        WritableImage writableImage = new WritableImage((int)Math.rint(pixelScale*n.getPrefWidth()), (int)Math.rint(pixelScale*n.getPrefHeight()));
		    SnapshotParameters spa = new SnapshotParameters();
		    spa.setTransform(Transform.scale(pixelScale, pixelScale));
		    WritableImage image = n.snapshot(spa, writableImage); 
       // WritableImage image = lineChart.snapshot(new SnapshotParameters(), null);
		    
        File newfile = new File("mypic/scale.png");
       
        System.out.println("ImageWriting is Starting");
      try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", newfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Image writing is done");
	}
	void savePic(AnchorPane n,String name)
	{

        double pixelScale=2.0;
	        
	        WritableImage writableImage = new WritableImage((int)Math.rint(pixelScale*n.getPrefWidth()), (int)Math.rint(pixelScale*n.getPrefHeight()));
		    SnapshotParameters spa = new SnapshotParameters();
		    spa.setTransform(Transform.scale(pixelScale, pixelScale));
		    WritableImage image = n.snapshot(spa, writableImage); 
       // WritableImage image = lineChart.snapshot(new SnapshotParameters(), null);
		    
        File newfile = new File("mypic/"+name);
       
        System.out.println("ImageWriting is Starting");
      try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", newfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Image writing is done");
	}
	
}
