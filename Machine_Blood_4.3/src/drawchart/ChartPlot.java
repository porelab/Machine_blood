package drawchart;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Transform;

import javax.imageio.ImageIO;

import application.DataStore;
import application.Myapp;

import com.sun.javafx.charts.Legend;

import data_read_write.DatareadN;
import de.tesis.dynaware.javafx.fancychart.zoom.Zoom;
import drawchart.SmoothedChart.ChartType;

//create chart for report section and on result popup.
public class ChartPlot {

	
	double height=0.6;
	
	List<String> grpclr;
	String[] suffixes =
			// 0 1 2 3 4 5 6 7 8 9
							{ "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
			// 10 11 12 13 14 15 16 17 18 19
									"th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
			// 20 21 22 23 24 25 26 27 28 29
									"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
			// 30 31
									"th", "st" };
	public ChartPlot(boolean bol) {

		grpclr = new ArrayList<String>();
		setColor();

		if (bol) {

			File f = new File("PororeportTool");
			if (f.exists()) {
				if (f.isDirectory()) {
					// delete files from it
					File folder = new File("PororeportTool");
					File[] listOfFiles = folder.listFiles();
					for (int i = 0; i < listOfFiles.length; i++) {
						listOfFiles[i].delete();
						System.out.println("delte file" + (i + 1));
					}

				} else {
					f.mkdir();
				}
			} else {
				f.mkdir();
			}
		}

	}

	public void setColor() {

	grpclr.addAll(Myapp.colors);

	}

	public List<String> getColorMultiple() {
	
		List<String> grpclr=new ArrayList<String>();
		grpclr.addAll(Myapp.colors);
		return grpclr;
	}
	
	public List<String> getColor() {
		return grpclr;
	}
	
	
	public Pane drawLinechartWithScatterMultiple1 (double xsize, double ysize,
			String Title, String Xname, String Yname, List<DatareadN> d,
			String imgname) {

		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();

		yAxis.setLabel(Yname);
		xAxis.setLabel(Xname);

		 SmoothedChart<Number, Number> lineChart= new SmoothedChart<>(xAxis,
		 yAxis);
		//LineChart<Number, Number> lineChart = new LineChart<>(xAxis,
		//		yAxis);

		lineChart.setPrefSize(xsize, ysize - 50);
		lineChart.setMinSize(xsize, ysize - 50);
		lineChart.setMaxSize(xsize, ysize - 50);
		lineChart.setSmoothed(false);
		lineChart.setChartType(ChartType.LINE);
		lineChart.setInteractive(true);
		lineChart.setSubDivisions(8);

		// lineChart.setTitle(Title);
		lineChart.setLegendVisible(false);
	
		List<String> clrlist = DataStore.getColorMultiple();
		List<String> leglab = new ArrayList<String>();

		XYChart.Series[] series = new XYChart.Series[d.size() * 3];
		// XYChart.Series[] series1 = new XYChart.Series[d.size()];
		// XYChart.Series[] series2 = new XYChart.Series[d.size()];
		int ik = 0;
		for (int i = 0; i < d.size(); i++) {
			String clr = clrlist.get(i);
			series[ik] = new XYChart.Series();
			leglab.add(d.get(i).filename);
			// System.out.println("File >>"+(i+1)+" is taking in action");

			// series[i].setName(d.get(i).getFileName());
			String strokeStyle;
			DatareadN dr = d.get(i);

			List<String> x = new ArrayList<String>();
			List<String> y = new ArrayList<String>();

		
			try {

				x = dr.getValuesOf("" + dr.data.get("recordx"));
				y = DataStore.ConvertPressure(dr.getValuesOf("" + dr.data.get("recordy")));
				int min=x.size()>y.size()?y.size():x.size();
				
				for (int j = min-1; j >=0; j--) {
					double xd = 0;
					double yd = 0;
					{
						xd = Double.parseDouble(x.get(j));
						yd = Double.parseDouble(y.get(j));
					
						System.out.println("Xd : "+xd+"   Yd : "+yd);
					}
					
					
					addPoints(lineChart, xd, yd, j+1,clr);
					
					series[ik].getData().add(new XYChart.Data(xd, yd));
					
				}
				lineChart.getData().add(series[ik]);
			

				ObservableList<Data<Number, Number>> m =series[ik].getData();
				strokeStyle = "-fx-stroke:transparent ;";

				series[ik].getNode()
						.lookup(".chart-series-area-line")
						
						.setStyle(strokeStyle);
				m = series[ik].getData();

				
				for (int i2 = 0; i2 < m.size(); i2++) {
					((Data) m.get(i2)).getNode().setStyle(
							"-fx-background-color: " + clr + ","+clr+" ;"
									+ "-fx-background-radius:5px;"+"-fx-padding: 5px;");

			}
				ik++;

			} catch (Exception e) {

				e.printStackTrace();
				
			}
			// series2------
			series[ik] = new XYChart.Series();

			series[ik].setName(d.get(i).filename);

			List<String> temppre = dr
					.getValuesOf(dr.data.get("t") + "");
			List<String> tempfl = DataStore.ConvertPressure( dr.getValuesOf(dr.data.get("ans") + ""));

			
			
		
			List<Double> temphdpr = new ArrayList<Double>();
			List<Double> temphdfl = new ArrayList<Double>();

			for (int m = 0; m < temppre.size(); m++) {

				double xd = 0;
				double yd = 0;
				xd = Double.parseDouble(temppre.get(m));
				yd = Double.parseDouble(tempfl.get(m));

				
					temphdpr.add(xd);
					temphdfl.add(yd);
					
					
				
					series[ik].getData().add(new XYChart.Data(xd, yd));
					
				
				
			}

		
			try {
				lineChart.getData().add(series[ik]);
				

				ObservableList<Data<Number, Number>> m = series[ik].getData();
				strokeStyle = "-fx-stroke:" + clr + " ;";

				
				
				series[ik].getNode()
						.lookup(".chart-series-area-line")
						.setStyle(strokeStyle);
				
			/*	String symbol="";
				lineChart.getData().get(ik).getNode()
				.lookup(".chart-series-line-symbol")
				.setStyle(strokeStyle);
				*/
				m = series[ik].getData();

				for (int i2 = 0; i2 < m.size(); i2++) {
					((Data) m.get(i2)).getNode().setStyle(
							"-fx-background-color:  transparent, transparent;");

				}
				ik++;
			} catch (Exception e) {
e.printStackTrace();
			}
		
		}

		Pane p = new Pane();
		p.setStyle("-fx-background-color: rgba(0, 100, 100, 0.0);");
		p.getChildren().add(lineChart);

		p.setPrefSize(xsize, ysize - 50);
		p.setMinSize(xsize, ysize - 50);
		p.setMaxSize(xsize, ysize - 50);
		lineChart.setAnimated(false);
		Legend ll = (Legend) lineChart.lookup(".chart-legend");
		ll.getItems().clear();
		for (int y = 0; y < leglab.size(); y++) {

			@SuppressWarnings("restriction")
			Legend.LegendItem ll1 = new Legend.LegendItem(leglab.get(y),
					new Circle(6, Paint.valueOf(clrlist.get(y))));

			String backgroundColorStyle = "-fx-background-color: "
					+ clrlist.get(y) + ", white;";

			ll.setStyle(backgroundColorStyle);

			ll.getItems().add(ll1);
		}

		System.out.println("LineChart is Created");

	       Zoom zoom =new Zoom(lineChart,p);
		return p;
	}

	void addPoints(SmoothedChart<Number, Number> ch,double x,double y,int i,String clr)
	{
		XYChart.Series<Number, Number> meanserie = new XYChart.Series();
		Data data = new Data<Number, Number>(x,y);
        data.setNode(createDataNode1());
       
		meanserie.getData().add(data);
		Data data1 = new Data<Number, Number>(x,y+(y*height));
        //data.setNode(createDataNode("Mean Pore : "+Myapp.getRound(meand,2)+"\u00B5"));
		data1.setNode(createDataNode(i+suffixes[i]+" Bubble"));
		meanserie.getData().add(data1);
	
		
		height=height-0.05;
		
		ch.getData().add(meanserie);
		String strokeStyle = "-fx-stroke:" + clr + " ;";

		ch.getData().get(ch.getData().size()-1).getNode().lookup(".chart-series-area-line").setStyle(strokeStyle);
		
		ObservableList<Data<Number, Number>> m =ch.getData().get(ch.getData().size()-1).getData();
		
		
		
		for (int i2 = 0; i2 < m.size(); i2++) {
			((Data) m.get(i2)).getNode().setStyle(
					"-fx-background-color: " + clr + ","+clr+" ;"
							+ "-fx-background-radius:5px;"+"-fx-padding: 5px;");
		}
	
	}
	
	  private  Node createDataNode1() {
			
	       
	        Pane pane = new Pane();
	        pane.setShape(new Circle(7.0));
	        pane.setScaleShape(false);
	       //label.translateYProperty().bind(label.heightProperty().divide(-1.5));

	        
	        return pane;
	    }
	  
	  Node createDataNode(String meand) {
		    Label label = new Label();
	        label.setText(meand);
	        label.setPadding(new Insets(5));
	        label.setFont(new Font(12));
	       label.setStyle("-fx-background-color: "
					+ "#ffffff" + "; -fx-border-color: "
					+ "#000000" + ";"
							+ "-fx-font-weight: bold;"
							+ "-fx-border-radius:5px");
	        
	       
	        label.setTextFill(Paint.valueOf("#000000"));
	        label.setTextAlignment(TextAlignment.CENTER);
	        Pane pane = new Pane(label);
	        pane.setShape(new Circle(1.0));
	        pane.setScaleShape(false);
	       //label.translateYProperty().bind(label.heightProperty().divide(-1.5));

	        
	        return pane;
	    }

	public Pane drawLinechartWithScatterMultiple(double xsize, double ysize,
			String Title, String Xname, String Yname, List<DatareadN> d,
			String imgname) {

		
		
		if(d.size()==1)
		{
			return drawLinechartWithScatterMultiple1(xsize, ysize, Title, Xname, Yname, d, imgname);
			
		}
		else
		{
		
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();

		yAxis.setLabel(Yname);
		xAxis.setLabel(Xname);

		 SmoothedChart<Number, Number> lineChart= new SmoothedChart<>(xAxis,
		 yAxis);
		//LineChart<Number, Number> lineChart = new LineChart<>(xAxis,
		//		yAxis);

		lineChart.setPrefSize(xsize, ysize - 50);
		lineChart.setMinSize(xsize, ysize - 50);
		lineChart.setMaxSize(xsize, ysize - 50);
		lineChart.setSmoothed(true);
		lineChart.setChartType(ChartType.LINE);
		lineChart.setInteractive(true);
		lineChart.setSubDivisions(15);
		// creating the chart

		// lineChart.setTitle(Title);
		lineChart.setLegendVisible(false);
	
		List<String> clrlist = DataStore.getColorMultiple();
		List<String> leglab = new ArrayList<String>();

		XYChart.Series[] series = new XYChart.Series[d.size() * 2];
		// XYChart.Series[] series1 = new XYChart.Series[d.size()];
		// XYChart.Series[] series2 = new XYChart.Series[d.size()];
		int ik = 0;
		for (int i = 0; i < d.size(); i++) {
			String clr = clrlist.get(i);
			series[ik] = new XYChart.Series();
			leglab.add(d.get(i).filename);
			// System.out.println("File >>"+(i+1)+" is taking in action");

			// series[i].setName(d.get(i).getFileName());
			String strokeStyle;
			DatareadN dr = d.get(i);

			List<String> x = new ArrayList<String>();
			List<String> y = new ArrayList<String>();

		
			
			try {
				x = dr.getValuesOf("" + dr.data.get("recordx"));
				y = DataStore.ConvertPressure(dr.getValuesOf("" + dr.data.get("recordy")));
				for (int j = 0; j < x.size(); j++) {
					double xd = 0;
					double yd = 0;
					{
						xd = Double.parseDouble(x.get(j));
						yd = Double.parseDouble(y.get(j));
					
						System.out.println("Xd : "+xd+"   Yd : "+yd);
					}
					
					

					if(yd>=0)
					{
					series[ik].getData().add(new XYChart.Data(xd, yd));
					}
					
			

				}
				lineChart.getData().add(series[ik]);

				ObservableList<Data<Number, Number>> m = lineChart.getData()
						.get(ik).getData();
				strokeStyle = "-fx-stroke:transparent ;";

				lineChart.getData().get(ik).getNode()
						.lookup(".chart-series-area-line")
						
						.setStyle(strokeStyle);
				m = lineChart.getData().get(ik).getData();

				
				for (int i2 = 0; i2 < m.size(); i2++) {
					((Data) m.get(i2)).getNode().setStyle(
							"-fx-background-color: " + clr + ","+clr+" ;"
									+ "-fx-background-radius:5px;"+"-fx-padding: 5px;");

			}
				ik++;

			} catch (Exception e) {

				//e.printStackTrace();
			}
			// series2------
			series[ik] = new XYChart.Series();

			series[ik].setName(d.get(i).filename);

			List<String> temppre = dr
					.getValuesOf(dr.data.get("t") + "");
			List<String> tempfl =  DataStore.ConvertPressure(dr.getValuesOf(dr.data.get("ans") + ""));

			
			
		
			List<Double> temphdpr = new ArrayList<Double>();
			List<Double> temphdfl = new ArrayList<Double>();

			for (int m = 0; m < temppre.size(); m++) {

				double xd = 0;
				double yd = 0;
				xd = Double.parseDouble(temppre.get(m));
				yd = Double.parseDouble(tempfl.get(m));

				
					temphdpr.add(xd);
					temphdfl.add(yd);
					
					
					if(yd>=0)
					{
					series[ik].getData().add(new XYChart.Data(xd, yd));
					}
				
				
			}

		
			try {
				lineChart.getData().add(series[ik]);
				

				ObservableList<Data<Number, Number>> m = lineChart.getData()
						.get(ik).getData();
				strokeStyle = "-fx-stroke:" + clr + " ;";

				
				
				lineChart.getData().get(ik).getNode()
						.lookup(".chart-series-area-line")
						.setStyle(strokeStyle);
				
			/*	String symbol="";
				lineChart.getData().get(ik).getNode()
				.lookup(".chart-series-line-symbol")
				.setStyle(strokeStyle);
				*/
				m = lineChart.getData().get(ik).getData();

				for (int i2 = 0; i2 < m.size(); i2++) {
					((Data) m.get(i2)).getNode().setStyle(
							"-fx-background-color:  transparent, transparent;");

				}
				ik++;
			} catch (Exception e) {

			//	e.printStackTrace();
			}
		
		}

		Pane p = new Pane();
		p.setStyle("-fx-background-color: rgba(0, 100, 100, 0.0);");
		p.getChildren().add(lineChart);

		p.setPrefSize(xsize, ysize - 50);
		p.setMinSize(xsize, ysize - 50);
		p.setMaxSize(xsize, ysize - 50);
		lineChart.setAnimated(false);
		Legend ll = (Legend) lineChart.lookup(".chart-legend");
		ll.getItems().clear();
		for (int y = 0; y < leglab.size(); y++) {

			@SuppressWarnings("restriction")
			Legend.LegendItem ll1 = new Legend.LegendItem(leglab.get(y),
					new Circle(6, Paint.valueOf(clrlist.get(y))));

			String backgroundColorStyle = "-fx-background-color: "
					+ clrlist.get(y) + ", white;";

			ll.setStyle(backgroundColorStyle);

			ll.getItems().add(ll1);
		}

		System.out.println("LineChart is Created");

	       Zoom zoom =new Zoom(lineChart,p);
		return p;
		}
	}

	
	public Pane drawLinechart(double xsize, double ysize, String Title,
			String Xname, String Yname, List<DatareadN> d, boolean reverse,
			int xm, int ym,String mm) {
		System.out.println("In DrawLineChart multiple file");

		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel(Xname);
		yAxis.setLabel(Yname);
		// creating the chart

		LineChart<Number, Number> lineChart=new LineChart<>(xAxis, yAxis);
		//	SmoothedChart<Number, Number> lineChart = new SmoothedChart<>(xAxis,
	//			yAxis);
	//	lineChart.setSmoothed(true);
	//	lineChart.setChartType(ChartType.LINE);
	//	lineChart.setInteractive(true);
	//	lineChart.setSubDivisions(8);

		lineChart.setPrefSize(xsize, ysize - 50);
		lineChart.setMinSize(xsize, ysize - 50);
		lineChart.setMaxSize(xsize, ysize - 50);

		// lineChart.setTitle(Title);
		lineChart.setLegendVisible(false);
		lineChart
				.setStyle("-fx-background-color: rgba(255,255,355,0.05);-fx-background-radius: 10;");

		// xAxis.setStyle("-fx-tick-label-font-size: 1.0em;-fx-tick-label-fill: #000000;-fx-tick-length: 18;-fx-minor-tick-length: 10;");
		// yAxis.setStyle("-fx-tick-label-font-size: 1.0em;-fx-tick-label-fill: #000000;-fx-tick-length: 18;-fx-minor-tick-length: 10;");
		String strokeStyle,clr;
		List<String> clrss=getColor();
		XYChart.Series[] series = new XYChart.Series[d.size()*2];
		int cnt=0;
		for (int i = 0; i < d.size(); i++) {
			series[cnt] = new XYChart.Series();
			// System.out.println("File "+(i+1)+" is taking in action");

			clr=clrss.get(i);
			DatareadN dr = d.get(i);

			List<String> x = null, y = null;

			
				x = dr.getValuesOf(dr.data.get("t") + "");
				y = dr.getValuesOf(dr.data.get("ans") + "");
				
				System.out.println(x+"\n"+y);
			
				double dd=0.0;
			try {

				for (int j = 0; j < x.size(); j++) {
					double xd = 0;
					double yd = 0;

					
						xd = Double.parseDouble(x.get(j));
						yd = Double.parseDouble(y.get(j));
					
						if(dd<xd)
						{
							dd=xd;
						}
					
						System.out.println("DD : "+dd);

					series[cnt].getData().add(new XYChart.Data(xd, yd));

				}
				lineChart.getData().add(series[cnt]);

				ObservableList<Data<Number, Number>> m = lineChart.getData()
						.get(cnt).getData();
				strokeStyle = "-fx-stroke:" + clr + " ;";
				lineChart.getData().get(cnt).getNode()
						.lookup(".chart-series-line")
						.setStyle(strokeStyle);
				m = lineChart.getData().get(cnt).getData();

				for (int i2 = 0; i2 < m.size(); i2++) {
					((Data) m.get(i2)).getNode().setStyle(
							"-fx-background-color: " + clr + ", transparent;");

				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			cnt++;
			series[cnt] = new XYChart.Series();
			series[cnt].getData().add(new XYChart.Data(0,Double.parseDouble(dr.data.get("bpressure")+"")));
			series[cnt].getData().add(new XYChart.Data(dd, Double.parseDouble(dr.data.get("bpressure")+"")));
			lineChart.getData().add(series[cnt]);
			ObservableList<Data<Number, Number>> m = lineChart.getData()
					.get(cnt).getData();
			strokeStyle = "-fx-stroke:" + clr + " ;";

			lineChart.getData().get(cnt).getNode()
					.lookup(".chart-series-line")
					.setStyle(strokeStyle);
			m = lineChart.getData().get(cnt).getData();

			for (int i2 = 0; i2 < m.size(); i2++) {
				((Data) m.get(i2)).getNode().setStyle(
						"-fx-background-color: " + clr + ", transparent;");

			}
			cnt++;
		}
		lineChart.getStylesheets().add(
				getClass().getResource("dynamicgraph.css").toExternalForm());
		Pane p = new Pane();
		p.setStyle("-fx-background-color: rgba(0, 100, 100, 0.0);");
		p.setPrefSize(xsize, ysize - 50);
		p.setMinSize(xsize, ysize - 50);
		p.setMaxSize(xsize, ysize - 50);
		p.getChildren().add(lineChart);
		lineChart.setAnimated(false);
	//	lineChart.getData().addAll(series);


	       Zoom zoom =new Zoom(lineChart,p);
		return p;

	}


	/*Bubble Point Chart*/

	
	public Pane drawLinechartold(double xsize, double ysize, String Title,
			String Xname, String Yname, List<DatareadN> d, boolean reverse,
			int xm, int ym,String mm) {
		System.out.println("In DrawLineChart multiple file");

		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel(Xname);
		yAxis.setLabel(Yname);
		// creating the chart

		LineChart<Number, Number> lineChart=new LineChart<>(xAxis, yAxis);
		//	SmoothedChart<Number, Number> lineChart = new SmoothedChart<>(xAxis,
	//			yAxis);
	//	lineChart.setSmoothed(true);
	//	lineChart.setChartType(ChartType.LINE);
	//	lineChart.setInteractive(true);
	//	lineChart.setSubDivisions(8);

		lineChart.setPrefSize(xsize, ysize - 50);
		lineChart.setMinSize(xsize, ysize - 50);
		lineChart.setMaxSize(xsize, ysize - 50);

		// lineChart.setTitle(Title);
		lineChart.setLegendVisible(false);
		lineChart
				.setStyle("-fx-background-color: rgba(255,255,355,0.05);-fx-background-radius: 10;");

		// xAxis.setStyle("-fx-tick-label-font-size: 1.0em;-fx-tick-label-fill: #000000;-fx-tick-length: 18;-fx-minor-tick-length: 10;");
		// yAxis.setStyle("-fx-tick-label-font-size: 1.0em;-fx-tick-label-fill: #000000;-fx-tick-length: 18;-fx-minor-tick-length: 10;");
		String strokeStyle,clr;
		List<String> clrss=getColor();
		XYChart.Series[] series = new XYChart.Series[d.size()*2];
		int cnt=0;
		for (int i = 0; i < d.size(); i++) {
			series[cnt] = new XYChart.Series();
			// System.out.println("File "+(i+1)+" is taking in action");

			clr=clrss.get(i);
			DatareadN dr = d.get(i);

			List<String> x = null, y = null;

			
				x = dr.getValuesOf(dr.data.get("t") + "");
				y = dr.getValuesOf(dr.data.get("ans") + "");
				
				System.out.println(x+"\n"+y);
			
				double dd=0.0;
			try {

				for (int j = 0; j < x.size(); j++) {
					double xd = 0;
					double yd = 0;

					
						xd = Double.parseDouble(x.get(j));
						yd = Double.parseDouble(y.get(j));
					
						if(dd<xd)
						{
							dd=xd;
						}
					
						System.out.println("DD : "+dd);

					series[cnt].getData().add(new XYChart.Data(xd, yd));

				}
				lineChart.getData().add(series[cnt]);

				ObservableList<Data<Number, Number>> m = lineChart.getData()
						.get(cnt).getData();
				strokeStyle = "-fx-stroke:" + clr + " ;";
				lineChart.getData().get(cnt).getNode()
						.lookup(".chart-series-line")
						.setStyle(strokeStyle);
				m = lineChart.getData().get(cnt).getData();

				for (int i2 = 0; i2 < m.size(); i2++) {
					((Data) m.get(i2)).getNode().setStyle(
							"-fx-background-color: " + clr + ", transparent;");

				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			cnt++;
			series[cnt] = new XYChart.Series();
			series[cnt].getData().add(new XYChart.Data(0,Integer.parseInt(dr.data.get("thresold")+"")));
			series[cnt].getData().add(new XYChart.Data(dd, Integer.parseInt(dr.data.get("thresold")+"")));
			lineChart.getData().add(series[cnt]);
			ObservableList<Data<Number, Number>> m = lineChart.getData()
					.get(cnt).getData();
			strokeStyle = "-fx-stroke:" + clr + " ;";

			lineChart.getData().get(cnt).getNode()
					.lookup(".chart-series-line")
					.setStyle(strokeStyle);
			m = lineChart.getData().get(cnt).getData();

			for (int i2 = 0; i2 < m.size(); i2++) {
				((Data) m.get(i2)).getNode().setStyle(
						"-fx-background-color: " + clr + ", transparent;");

			}
			cnt++;
		}
		lineChart.getStylesheets().add(
				getClass().getResource("dynamicgraph.css").toExternalForm());
		Pane p = new Pane();
		p.setStyle("-fx-background-color: rgba(0, 100, 100, 0.0);");
		p.setPrefSize(xsize, ysize - 50);
		p.setMinSize(xsize, ysize - 50);
		p.setMaxSize(xsize, ysize - 50);
		p.getChildren().add(lineChart);
		lineChart.setAnimated(false);
	//	lineChart.getData().addAll(series);


	       Zoom zoom =new Zoom(lineChart,p);
		return p;

	}

	
		
	
	
	// Barchart
	public String getRound(Double r, int round) {
		if (round == 2) {
			r = (double) Math.round(r * 100) / 100;
		} else if (round == 3) {
			r = (double) Math.round(r * 1000) / 1000;

		} else {
			r = (double) Math.round(r * 10000) / 10000;

		}

		return r + "";

	}

	public Pane drawBarchart(double xsize, double ysize, String Title,
			String Xname, String Yname, Map<String, Double> data) {
		System.out.println("In DrawBarChart single");

		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis,
				yAxis);
		// bc.setTitle(Title);
		xAxis.setLabel(Xname);
		yAxis.setLabel(Yname);
		bc.setLegendVisible(false);
		// bc.getStylesheets().add(ChartPlot.class.getResource("bar.css")
		// .toExternalForm());
		// bc.setStyle("-fx-background-color: rgba(255,255,355,0.05);-fx-background-radius: 10;");
		XYChart.Series series1 = new XYChart.Series();

		bc.setPrefSize(xsize, ysize - 50);
		bc.setMinSize(xsize, ysize - 50);
		bc.setMaxSize(xsize, ysize - 50);

		List<String> keys = new ArrayList<String>();
		keys.addAll(data.keySet());

		for (int i = 0; i < data.size(); i++) {
			// double dx=data.get(keys.get(i)).size();
			String dy = keys.get(i);
			xAxis.setTickLabelRotation(20);
			series1.getData().add(new XYChart.Data(dy, data.get(keys.get(i))));
		}

		bc.setAnimated(false);
		bc.getData().addAll(series1);

		bc.getStylesheets().add(
				getClass().getResource("dynamicgraph.css").toExternalForm());

		System.out.println("Image writing is done");

		Pane p = new Pane();
		p.setStyle("-fx-background-color: rgba(0, 100, 100, 0.0);");
		p.getChildren().add(bc);

		p.setPrefSize(xsize, ysize - 50);
		p.setMinSize(xsize, ysize - 50);
		p.setMaxSize(xsize, ysize - 50);
		return p;
	}

	public Chart drawPieChart(String title, Map<String, List<Double>> data,
			int total) {

		List<String> keys = new ArrayList<String>();
		keys.addAll(data.keySet());
		List<Double> per = new ArrayList<Double>();
		ObservableList<PieChart.Data> pieChartData = FXCollections
				.observableArrayList();

		for (int i = 0; i < keys.size(); i++) {
			double d = (double) 100 * data.get(keys.get(i)).size() / total;
			per.add(d);
			pieChartData.add(new PieChart.Data(keys.get(i) + " : "
					+ getRound(d, 2) + " %", d));
		}

		PieChart chart = new PieChart(pieChartData);
		chart.getStylesheets().add(
				ChartPlot.class.getResource("pie.css").toExternalForm());
		chart.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 10;");
		chart.minHeight(600);
		chart.maxHeight(600);
		chart.prefHeight(600);

		chart.minWidth(800);
		chart.maxWidth(800);
		chart.prefWidth(800);
		chart.setTitle(title);
		chart.setLabelLineLength(1);
		chart.setLegendSide(Side.BOTTOM);
		chart.setLabelLineLength(10);
		Scene scene = new Scene(chart, 800, 600);

		final Label caption = new Label("");
		caption.setTextFill(Color.DARKORANGE);
		caption.setStyle("-fx-font: 24 arial;");

		for (final PieChart.Data data1 : chart.getData()) {
			data1.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
					new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent e) {
							caption.setTranslateX(e.getSceneX());
							caption.setTranslateY(e.getSceneY());
							caption.setText(String.valueOf(data1.getPieValue())
									+ "%");
						}
					});
		}

		chart.setAnimated(false);

		WritableImage image = chart.snapshot(new SnapshotParameters(), null);
		// get file name
		String fileName = title;
		int pos = fileName.lastIndexOf(".");
		if (pos > 0) {
			fileName = fileName.substring(0, pos);
		}
		// end...
		File newfile = new File("PororeportTool/z" + fileName + "pieDis.png");
		System.out.println("ImageWriting is Starting");
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", newfile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return chart;
	}

}
