package calibration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;

import javax.mail.internet.PreencodedMimeBodyPart;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.LineChart.SortingPolicy;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import toast.Openscreen;
import toast.Toast;
import userinput.FunLevelGauge;
import application.DataStore;
import application.Main;
import application.Myapp;
import application.SerialWriter;
import application.writeFormat;
import data_read_write.CalculatePorometerData;
import data_read_write.CsvWriter;
import de.tesis.dynaware.javafx.fancychart.zoom.Zoom;
import eu.hansolo.medusa.Gauge;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class PressureCalibrationController implements Initializable {



	double  conditionpressure,conditionpressure1,conditionpressure2;

    @FXML
    private Label lbltime;

    @FXML
    private Label lblpressure;

  
	SerialReader in;
	
	@FXML
	ImageView backimg;
	
	int b = 0;

	@FXML
	AnchorPane root;


	List<Double> pressuredata1,pressureg1,pressuredata2,pressureg2,pressuredata3,pressureg3;
	
	
	int countbp = 0;
	writeFormat wrd;
	double p1 = 0, p2, bbp, bbf;

	static int i2 = 0;
	long t1, t2;
	int yi = 0;
	int ind = 0;
	int data_length = 0;
	double flowint = 0;
	private FunLevelGauge gauge;
	private Gauge gauge5;

	final NumberAxis xAxis = new NumberAxis();
	final NumberAxis yAxis = new NumberAxis();

	LineChart<Number, Number> sc = new LineChart<>(xAxis, yAxis);
	XYChart.Series series1 = new XYChart.Series();
	XYChart.Series series2 = new XYChart.Series();
	XYChart.Series series3 = new XYChart.Series();
	
	int type=0;
	
	@FXML
	Button lstart, btnback, lstop;

	@FXML
	TextField txtpress,txtpressstart;
	
	int endtime,endpressure;
	void connectHardware() {
		Myapp.testtrial = "4";
		in = new SerialReader(DataStore.in);

		try {
			DataStore.serialPort.removeEventListener();
			DataStore.serialPort.addEventListener(in);
			DataStore.serialPort.notifyOnDataAvailable(true);
		} catch (TooManyListenersException e) {
			Toast.makeText(Main.mainstage, "Hardware Problem", 1000, 100, 100);
		} catch (Exception e) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(Main.mainstage, "Hardware Problem", 1000,
							100, 100);

				}
			});

		}

	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		connectHardware();
		DataStore.getconfigdata();
		

		
		pressuredata1=new ArrayList<Double>();
		pressureg1=new ArrayList<Double>();
		pressuredata2=new ArrayList<Double>();
		pressureg2=new ArrayList<Double>();
		pressuredata2=new ArrayList<Double>();
		pressureg2=new ArrayList<Double>();
		
		
		
		
		root.getChildren().add(sc);

		lstop.setDisable(true);
		btnback.setStyle("-fx-background-color: #f8f8f85c;");

		sc.setAxisSortingPolicy(SortingPolicy.Y_AXIS.NONE);
		sc.setAnimated(false);
		sc.setLegendVisible(false);
		sc.setCreateSymbols(true);
		sc.getData().addAll(series1,series2,series3);
		

		sc.setTitle("Pressure Gauge  Vs Pressure Regulator");
		sc.prefWidthProperty().bind(root.widthProperty());
		sc.prefHeightProperty().bind(root.heightProperty());

		xAxis.setLabel("Pressure Regulator ( psi )");
		yAxis.setLabel("Pressure Gauge ( psi ) ");
		Zoom zoom = new Zoom(sc, root);

		 Image image = new Image(this.getClass().getResourceAsStream("/ConfigurationPart/back.png"));
	     backimg.setImage(image);

		writeFormat wrD = new writeFormat();

		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

		btnback.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Openscreen.open("/ConfigurationPart/Nconfigurepage.fxml");


			}
		});
		lstart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ind=0;
				series1.getData().clear();
				pressuredata1.clear();
				pressureg1.clear();
				pressuredata2.clear();
				pressureg2.clear();
				pressuredata3.clear();
				pressureg3.clear();
				
				xAxis.setUpperBound(10);
				conditionpressure = Integer.parseInt(txtpress.getText());
				writeFormat wrD = new writeFormat();
				wrD.addChar('S');
				wrD.addChar('C');
				wrD.addChar('P');
				wrD.addData1(getValueList(Integer.parseInt(txtpressstart.getText())));
				wrD.addLast();
				sendData(wrD);
				

				System.out.println("Send data");
				txtpress.setDisable(true);
				lstart.setText("Start Test");

				lstop.setDisable(false);
				lstart.setDisable(true);
			}
		});

		lstop.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				lstart.setDisable(false);
				lstop.setDisable(true);

				txtpress.setDisable(false);
				try {
					
				writeFormat wrD = new writeFormat();
				
				wrD.addChar('X');
				wrD.addChar('C');
				wrD.addChar('P');
				wrD.addBlank(3);
				wrD.addLast();
				sendData(wrD);
			
				
				if(pressuredata3.size()>2)
				{
					createCsvTable();
				}
				else
				{

					Toast.makeText(Main.mainstage, "No enough data for Csv file.", 1000, 100,100);
				}
				
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	public static List<Integer> getValueList(int val) {
		String pad = "000000";
		String st = "" + Integer.toHexString(val);
		String st1 = (pad + st).substring(st.length());
		List<Integer> ls = new ArrayList<Integer>();

		int n = (int) Long.parseLong(st1.substring(0, 2), 16);
		int n1 = (int) Long.parseLong(st1.substring(2, 4), 16);
		int n2 = (int) Long.parseLong(st1.substring(4, 6), 16);
		ls.add(n);
		ls.add(n1);
		ls.add(n2);

		return ls;
	}

	private void setDataPointPopup(XYChart<Number, Number> sc) {
		final Popup popup = new Popup();
		popup.setHeight(20);
		popup.setWidth(60);

		for (int i = 0; i < sc.getData().size(); i++) {
			final int dataSeriesIndex = i;
			final XYChart.Series<Number, Number> series = sc.getData().get(i);
			for (final Data<Number, Number> data : series.getData()) {
				final Node node = data.getNode();
				node.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET,
						new EventHandler<MouseEvent>() {

							private static final int X_OFFSET = 15;
							private static final int Y_OFFSET = -5;
							Label label = new Label();

							@Override
							public void handle(final MouseEvent event) {
								System.out.println("MOuse Event");
								final String colorString = "#cfecf0";
								label.setFont(new Font(20));
								popup.getContent().setAll(label);
								label.setStyle("-fx-background-color: "
										+ colorString + "; -fx-border-color: "
										+ colorString + ";");
								label.setText("x=" + data.getXValue() + ", y="
										+ data.getYValue());
								popup.show(data.getNode().getScene()
										.getWindow(), event.getScreenX()
										+ X_OFFSET, event.getScreenY()
										+ Y_OFFSET);
								event.consume();
							}

							public EventHandler<MouseEvent> init() {
								label.getStyleClass().add("chart-popup-label");
								return this;
							}

						}.init());

				node.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET,
						new EventHandler<MouseEvent>() {

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

	void sendSetting(int delay) {
		System.out.println("Sending settings for delay");
		writeFormat wrD = new writeFormat();
		wrD.addChar('A');
		wrD.addChar('S');
		wrD.addBlank(3);
		wrD.addData1(getValueList(400));
		
		wrD.addChar('1');
			wrD.addBlank(3);
		
			wrD.addData1(getValueList(200));


		wrD.addBlank(3);
		wrD.addLast();
		sendData(wrD, delay);

		
		
		
		
	}
	

void setPressureCalibrationPoint(double p,double f) 
	{	
	
	if(type==0)
	{
		if(ind>0)
		{
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							lblpressure.setText(Myapp.getRound(p, 2)+" psi");
							lbltime.setText(Myapp.getRound(f, 2)+" psi");
												
							series1.getData().add(
									new XYChart.Data(p, f));
							pressuredata1.add(p);
							pressureg1.add(f);
							ind++;

							if (p >= conditionpressure) {
								try {
									
								writeFormat wrD = new writeFormat();
								wrD.addChar('X');
								wrD.addChar('C');
								wrD.addChar('P');
								wrD.addBlank(3);
								wrD.addLast();
								sendData(wrD);
								
								createCsvTable();
									
								} catch (Exception e) {

								}
							}

						}
					});
				}

		else
		{
			ind++;
		}
	}
	else if(type==0)
	{
		if(ind>0)
		{
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							lblpressure.setText(Myapp.getRound(p, 2)+" psi");
							lbltime.setText(Myapp.getRound(f, 2)+" psi");
												
							series1.getData().add(
									new XYChart.Data(p, f));
							pressuredata1.add(p);
							pressureg1.add(f);
							ind++;

							if (p >= conditionpressure) {
								try {
									
								writeFormat wrD = new writeFormat();
								wrD.addChar('X');
								wrD.addChar('C');
								wrD.addChar('P');
								wrD.addBlank(3);
								wrD.addLast();
								sendData(wrD);
								
								createCsvTable();
									
								} catch (Exception e) {

								}
							}

						}
					});
				}

		else
		{
			ind++;
		}
	}
	else if(type==2)
	{
		if(ind>0)
		{
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							lblpressure.setText(Myapp.getRound(p, 2)+" psi");
							lbltime.setText(Myapp.getRound(f, 2)+" psi");
												
							series1.getData().add(
									new XYChart.Data(p, f));
							pressuredata1.add(p);
							pressureg1.add(f);
							ind++;

							if (p >= conditionpressure) {
								try {
									
								writeFormat wrD = new writeFormat();
								wrD.addChar('X');
								wrD.addChar('C');
								wrD.addChar('P');
								wrD.addBlank(3);
								wrD.addLast();
								sendData(wrD);
								
								createCsvTable();
									
								} catch (Exception e) {

								}
							}

						}
					});
				}

		else
		{
			ind++;
		}
	}
	
	}


	
	private void setDataPointPopup() {
		final Popup popup = new Popup();
		popup.setHeight(15);
		popup.setWidth(30);

		for (int i = 0; i < sc.getData().size(); i++) {
			final int dataSeriesIndex = i;
			final XYChart.Series<Number, Number> series = sc.getData().get(i);
			for (final Data<Number, Number> data : series.getData()) {
				final Node node = data.getNode();
				node.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET,
						new EventHandler<MouseEvent>() {

							private static final int X_OFFSET = 15;
							private static final int Y_OFFSET = -5;
							final Label label = new Label();

							@Override
							public void handle(final MouseEvent event) {
								System.out.println("MOuse Event");
								final String colorString = "#cfecf0";
								popup.getContent().setAll(label);
								label.setStyle("-fx-background-color: "
										+ colorString + "; -fx-border-color: "
										+ colorString + ";");
								String xdata = data.getXValue() + "";
								String ydata = data.getYValue() + "";

								label.setText("x="
										+ xdata.substring(0,
												xdata.indexOf(".") + 3)
										+ ",\ny="
										+ ydata.substring(0,
												ydata.indexOf(".") + 3));
								popup.show(data.getNode().getScene()
										.getWindow(), event.getScreenX()
										+ X_OFFSET, event.getScreenY()
										+ Y_OFFSET);
								event.consume();
							}

							public EventHandler<MouseEvent> init() {
								label.getStyleClass().add("chart-popup-label");
								return this;
							}

						}.init());

				node.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET,
						new EventHandler<MouseEvent>() {

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
	void createCsvTable() {

		try {
			System.out.println("csv creating........");
			CsvWriter cs = new CsvWriter();

			Date d1 = new Date();
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("ddMMyy");
			String date1 = DATE_FORMAT.format(d1);

			File fff = new File("Calibration");
			if (!fff.exists()) {
				fff.mkdir();
			}
			File f = new File(fff.getPath() + "/"+"Pressure");
			if (!f.isDirectory()) {
				f.mkdir();
				System.out.println("Dir csv folder created");
			}
		
			String[] ff = f.list();

			CalculatePorometerData c = new CalculatePorometerData();

			cs.wtirefile(f.getPath()+ "Pressure_" + findInt(ff)
					+ ".csv");

			System.out.println("File Path-->" + f.getPath());

			cs.firstLine("pressurecalibration");
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

			Date date = new Date();

		
			cs.newLine("testtime", timeFormat.format(date));
			cs.newLine("testdate", dateFormat.format(date));
			cs.newLine("customerid", Myapp.uid);


		
			cs.newLineDouble("pressuregauge1", pressureg1);
			cs.newLineDouble("pressureregulator1", pressuredata1);
			cs.newLineDouble("pressuregauge2", pressureg2);
			cs.newLineDouble("pressureregulator2", pressuredata2);
			cs.newLineDouble("pressuregauge3", pressureg3);
			cs.newLineDouble("pressureregulator4", pressuredata3);
			


			cs.closefile();
			System.out.println("csv Created");
			
			pressuredata1.clear();
			pressuredata2.clear();
			pressuredata3.clear();
			pressureg1.clear();
			pressureg2.clear();
			pressureg3.clear();
			
			
			
			Toast.makeText(Main.mainstage, "Csv saved.", 1000, 100,100);
			// Openscreen.open("/userinput/Nresult.fxml");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	
	public void sendData(writeFormat w) {
		System.out.println("Sending Data......");
		w.showData();
		Thread t = new Thread(new SerialWriter(DataStore.out, w));
		t.start();

	}

	void sendData(writeFormat w, int slp) {
		System.out.println("Sending Data......");
		w.showData();
		Thread t = new Thread(new SerialWriter(DataStore.out, w, slp));
		try {

			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	int findInt(String[] s) {
		try {

			List<String> s1 = Arrays.asList(s);
			ArrayList<String> ss = new ArrayList<String>(s1);

			ArrayList<Integer> ll = new ArrayList<Integer>();
			for (int i = 0; i < ss.size(); i++) {
				// System.out.println(ss.get(i));

				try {
					String temp = ss.get(i).toString()
							.substring(0, ss.get(i).indexOf("."));
					String[] data = temp.split("_");
					System.out.println(temp);

					ll.add(Integer.parseInt(data[data.length - 1]));
				} catch (Exception e) {

				}

			}

			if (ll.size() > 0) {

				return Collections.max(ll) + 1;
			} else {

				return 1;
			}
			// return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	public class SerialReader implements SerialPortEventListener {

		InputStream in;
		int ind = 0;
		List<Integer> readData = new ArrayList<Integer>();

		public SerialReader(InputStream in) {
			this.in = in;
			DataStore.getconfigdata();
		}

		public void serialEvent(SerialPortEvent arg0) {
			int data;
			try {
				int len = 0;
				char prev = '\0';
				// System.out.println("Reading Started:");

				while ((data = in.read()) > -1) {

					if (data == '\n' && prev == 'E') {
						break;
					}
					if (len > 0 || (data == '\r' && prev == '\n')) {
						readData.add(data);

						len++;
					}
					prev = (char) data;
					System.out.print(prev);

					// System.out.print(new String(buffer,0,len));
				}

				for (int i = 1; i < readData.size(); i++) {

					if (readData.get(i) == (int) 'T'
							&& readData.get(i + 1) == (int) 'D') {
						int prd, fld;
						double pr = 0, fl = 0;

						// pressure.....
						int a = 0, a1, a2, a3;
						a1 = readData.get(i + 5);
						a2 = readData.get(i + 6);
						a3 = readData.get(i + 7);

						System.out.println("Original Pressure Data BIT :" + a1
								+ " : " + a2 + " : " + a3);

						a = a1 << 16;
						a2 = a2 << 8;
						a = a | a2;
						a = a | a3;
						prd = a;

						if (readData.get(i + 2) == (int) 'P'
								&& readData.get(i + 3) == (int) 'R') {
							if (readData.get(i + 4) == (int) '1') {
								int maxpre = Integer
										.parseInt(DataStore.getPr());
								pr = (double) a * maxpre / 65535;

								System.out
										.println(" Pressure Regulator..... : "
												+ pr);

							}

						} else if (readData.get(i + 2) == (int) 'P'
								&& readData.get(i + 3) == (int) 'G') {
							if (readData.get(i + 4) == (int) '1') {
								int maxpre = Integer.parseInt(DataStore
										.getPg1());
								pr = (double) a * maxpre / 65535;

								// b1 = b1 - Myapp.pg1offset.get();
								System.out
										.println(" Pressure guage1 original..... : "
												+ a);

								System.out.println(" Pressure guage1 ..... : "
										+ pr);

							} else if (readData.get(i + 4) == (int) '2') {
								int maxpre = Integer.parseInt(DataStore
										.getPg2());
								System.out.println("Pressure Gauge2 Org Data "
										+ a);
								pr = (double) a * maxpre / 65535;

								// b1 = b1 - Myapp.pg2offset.get();

								System.out.println(" Pressure gauge2 ..... : "
										+ pr);

							} else {
								char c = (char) (int) readData.get(i + 6);
								System.out.println("Not found : "
										+ readData.get(i + 6) + " : " + c);
							}
						}

						// flow.....
						a1 = readData.get(i + 11);
						a2 = readData.get(i + 12);
						a3 = readData.get(i + 13);

						System.out.println("Original Pressure Data BIT 1:" + a1
								+ " : " + a2 + " : " + a3);
						a = a1 << 16;
						a2 = a2 << 8;
						a = a | a2;
						a = a | a3;
						fld = a;

						if (readData.get(i + 8) == (int) 'P'
								&& readData.get(i + 9) == (int) 'R') {
							if (readData.get(i + 10) == (int) '1') {
								int maxpre = Integer
										.parseInt(DataStore.getPr());
								fl = (double) a * maxpre / 65535;

								System.out
										.println(" Pressure Regulator..... : "
												+ fl);

							}

						} else if (readData.get(i + 8) == (int) 'P'
								&& readData.get(i + 9) == (int) 'G') {
							if (readData.get(i + 10) == (int) '1') {
								int maxpre = Integer.parseInt(DataStore
										.getPg1());
								fl = (double) a * maxpre / 65535;

								// b1 = b1 - Myapp.pg1offset.get();
								System.out
										.println(" Pressure guage1 original..... : "
												+ a);

								System.out.println(" Pressure guage1 ..... : "
										+ fl);

							} else if (readData.get(i + 10) == (int) '2') {
								int maxpre = Integer.parseInt(DataStore
										.getPg2());
								System.out.println("Pressure Gauge2 Org Data "
										+ a);
								fl = (double) a * maxpre / 65535;

								// b1 = b1 - Myapp.pg2offset.get();

								System.out.println(" Pressure gauge2 ..... : "
										+ fl);

							} else {
								System.out.println("I m else");
							}
						}

					
						setPressureCalibrationPoint(pr,fl);

						

					}

					readData.clear();
					break;

				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
