package calibration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
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
import data_read_write.CsvWriter;
import eu.hansolo.medusa.Gauge;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class LeacktestController implements Initializable {

	@FXML
	private Label lbltime;

	@FXML
	private Label lblpressure;

	@FXML
	private Label lblresult;

	SerialReader in;

	@FXML
	ImageView backimg;

	int b = 0;

	@FXML
	AnchorPane root;

	List<Double> pressuredata, timedata;

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

	@FXML
	Button lstart, btnback, lstop;

	@FXML
	TextField txttime, txtpress;

	int endtime, endpressure;

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
		pressuredata = new ArrayList<Double>();
		timedata = new ArrayList<Double>();
		root.getChildren().add(sc);

		lstop.setDisable(true);
		btnback.setStyle("-fx-background-color: #f8f8f85c;");

		sc.setAxisSortingPolicy(SortingPolicy.Y_AXIS.NONE);
		sc.setAnimated(false);
		sc.setLegendVisible(false);
		sc.setCreateSymbols(true);
		sc.getData().add(series1);

		sc.setTitle("Pressure Vs Time");
		sc.prefWidthProperty().bind(root.widthProperty());
		sc.prefHeightProperty().bind(root.heightProperty());

		xAxis.setLabel("Time ( Second )");
		yAxis.setLabel("Pressure ( psi ) ");

		Image image = new Image(this.getClass().getResourceAsStream(
				"/ConfigurationPart/back.png"));
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
				Openscreen.open("/application/first.fxml");
			
			}
		});
		lstart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
					if (!txtpress.getText().trim().isEmpty()
							&& !txttime.getText().trim().isEmpty()) {
						try {	
						endtime = Integer.parseInt(txttime.getText()) * 60;
						endpressure = Integer.parseInt(txtpress.getText());
						
						sendSetting(100);
						ind = 0;
						series1.getData().clear();
						pressuredata.clear();
						timedata.clear();
					
						xAxis.setUpperBound(10);
					
						int pdata=endpressure*65535/Integer.parseInt(DataStore.getPr());
						
						List<Integer> lps = getValueList(pdata);
						List<Integer> lin = getValueList((int) endtime / 30);

						System.out.println("Pressure :" + pdata+" : "+endpressure);
				
						writeFormat wrD = new writeFormat();
						wrD.addChar('S');
						wrD.addChar('L');
						wrD.addData1(lps);
						wrD.addLast();
						sendData(wrD,1500);

						txttime.setDisable(true);
						txtpress.setDisable(true);
						lstart.setText("Start Test");

						lstop.setDisable(false);
						lstart.setDisable(true);
						} catch (Exception e) {
							Toast.makeText(Main.mainstage, "Hardware Problem...", 1000, 100, 100);
							
						}
					} else {

						Toast.makeText(Main.mainstage, "Please enter all information", 1000, 100, 100);
					}
				
			}
		});

		lstop.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				lstart.setDisable(false);
				lstop.setDisable(true);

				txttime.setDisable(false);
				txtpress.setDisable(false);
				wrd = new writeFormat();
				wrd.addChar('X');
				wrd.addChar('L');
				wrd.addBlank(3);
				wrd.addLast();
				sendData(wrd);
				try {
			if(pressuredata.size()>6)
			{
					leaksummary();
			}
			else
			{
				Toast.makeText(Main.mainstage, "There are not enough data for result calculation.", 1000, 100, 100);
			}
				} catch (Exception e) {

				}

			}
		});

	}

	
	void sendSetting(int delay) {
		System.out.println("Sending settings for delay");
		writeFormat wrD = new writeFormat();
		wrD.addChar('A');
		wrD.addChar('S');
		wrD.addBlank(6);
		wrD.addChar('1');
			wrD.addBlank(3);
		
			wrD.addData1(getValueList(4000));


		wrD.addBlank(3);
		wrD.addLast();
		sendData(wrD, delay);

		
		
		
		
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

	void stopTest() {
		writeFormat wr = new writeFormat();
		wr.addStop();
		wr.addBlank(5);
		wr.addChkSm();
		wr.addLast();
		sendData(wr);

		try {
			DataStore.Refresh();
		} catch (Exception e) {
			System.out.println("Error 202:" + e.getMessage());
		}
	}


	void setLeakPoint(double p) {

		if (ind == 0) {

			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					t1 = System.currentTimeMillis() / 1000;
					System.out.println("Time : 0 min");
					//series1.getData().add(new XYChart.Data(0, p));
					// pressuredata.add(p);
					// timedata.add((double)t1);
					ind++;
					lbltime.setText((endtime / 60) + " min remaining");
					lblpressure.setText(Myapp.getRound(p, 2) + " psi");
				}
			});
		} else if(ind>2) {
			
			Platform.runLater(new Runnable() {

				@Override
				public void run() {

					t2 = System.currentTimeMillis() / 1000;
					double time = (double) (t2 - t1);

					lblpressure.setText(Myapp.getRound(p, 2) + " psi");
					int min = (int) (endtime - time) / 60;

					if (min == 0) {
						lbltime.setText("0 min: " + (endtime - time) + "sec");
					} else {
						int sec = (int) (endtime - time) - (60 * min);

						lbltime.setText(min + " min: " + sec + "sec");
					}

					System.out.println("Time : " + time + "min");
					series1.getData().add(new XYChart.Data(time, p));
					pressuredata.add(p);
					timedata.add(time);
					ind++;

					if (time >= endtime) {
						wrd = new writeFormat();
						wrd.addChar('X');
						wrd.addChar('L');
						wrd.addBlank(3);
						wrd.addLast();
						sendData(wrd);
						try {

							leaksummary();

						} catch (Exception e) {

							e.printStackTrace();
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

	public void leaksummary() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				if (pressuredata.size() > 0) {
					double firstpre = pressuredata.get(4);
					double endpre = pressuredata.get(pressuredata.size() - 1);

					if (firstpre > endpre) {
						int per = (int) (endpre * 100 / firstpre);
						if (per > 5) {

							lblresult.setText("Leakage :" + per
									+ "%\nthere is some leakage in system");
						} else {

							lblresult.setText("System is Leakproof");
						}
					} else if (firstpre < endpre) {

						int per = (int) (firstpre * 100 / endpre);
						if (per > 5) {

							lblresult.setText("Leakage :" + per
									+ "%\nthere is some leakage in system");
						} else {

							lblresult.setText("System is Leakproof");
						}
					} else {

						lblresult.setText("System is Leakproof");
					}
				} else {

					lblresult.setText("");
				}
			}
		});

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

						System.out.println("Original Flow Data BIT :" + a1
								+ " : " + a2 + " : " + a3);
						a = a1 << 16;
						a2 = a2 << 8;
						a = a | a2;
						a = a | a3;
						fld = a;

						if (readData.get(i + 8) == (int) 'F'
								&& readData.get(i + 9) == (int) 'C') {
							if (readData.get(i + 10) == (int) '1') {
								fl = (double) a
										* Integer.parseInt(DataStore.getFc())
										/ 65535;

								System.out.println("Flow Controller :  ... :"
										+ fl);

							}

						} else if (readData.get(i + 8) == (int) 'F'
								&& readData.get(i + 9) == (int) 'M') {
							if (readData.get(i + 10) == (int) '1') {
								fl = (double) a
										* Integer.parseInt(DataStore.getFm1())
										/ 65535;
								System.out.println(" Flow meter 1.... : " + a);

								// b=(double)a*8000/65535;
								System.out.println("Flow Meter 1 : ... :" + fl);

							} else if (readData.get(i + 10) == (int) '2') {
								fl = (double) a
										* Integer.parseInt(DataStore.getFm2())
										/ 65535;
								// b=(double)a*8000/65535;
								System.out.println("Flow Meter 2 : ... :" + fl);

							}
						}

					
						setLeakPoint(pr);

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
