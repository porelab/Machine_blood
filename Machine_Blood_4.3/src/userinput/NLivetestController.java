package userinput;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import com.google.common.util.concurrent.ExecutionError;

import application.DataStore;
import application.Main;
import application.Myapp;
import application.writeFormat;
import communicationProtocol.Webservices;
import data_read_write.CalculatePorometerData;
import data_read_write.CsvWriter;
import de.tesis.dynaware.javafx.fancychart.zoom.Zoom;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.colors.Bright;
import eu.hansolo.tilesfx.colors.Dark;
import extrafont.Myfont;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.LineChart.SortingPolicy;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Transform;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Popup;
import myconstant.Myconstant;
import toast.MyDialoug;
import toast.Openscreen;
import toast.Toast;

public class NLivetestController implements Initializable {

	@FXML
	StackPane videoanc;

	@FXML
	ScrollPane scrollrecord;

	static SimpleBooleanProperty isRestart;

	List<Double> recorddata, recordtime;

	static File savefile;

	@FXML
	Label lblfilename, lblbpc;

	@FXML
	Rectangle manualblock;

	boolean isAuto = true;

	double readpre = 0, readtime = 0;
	String result = "";

	List<String> bans, tlist;

	int stadycount = 0;

	int skip, skipwet = 0, skipdry = 0;
	MyDialoug mydia;
// for start popup
	static SimpleBooleanProperty stprop = new SimpleBooleanProperty(false);
	static SimpleBooleanProperty stpropcan = new SimpleBooleanProperty(false);

	static SimpleBooleanProperty isBubbleStart;

	static SimpleBooleanProperty isDryStart;

	AudioClip tones;

	@FXML
	private Button btninfo, btnabr, btnfail, starttest, starttestwet, startautotest, recordbtn;

	boolean iswaiting = false;

	@FXML
	Label lbltestnom, lbltestdurationm, status, lblcurranttest;

	@FXML
	AnchorPane guages, ancregu1, ancregu2, ancpg1, ancpg2, ancpg3, ancpg4;

	@FXML
	AnchorPane root, mainroot;

	private Tile gauge5;

	int countbp = 0;
	writeFormat wrd;
	double p1 = 0, bbp, bbf, bbd;

	String typeoftest = "";
	static int i2 = 0;
	boolean both = false, bothbw = false;
	long t1test, t2test;
	boolean once = true;
	int yi = 0;

	final NumberAxis xAxis = new NumberAxis();
	final NumberAxis yAxis = new NumberAxis();

	LineChart<Number, Number> sc = new LineChart<>(xAxis, yAxis);
	XYChart.Series series2 = new XYChart.Series();

	int testno = 1;
	String ip = "http://192.168.0.109:8080/action";

	Myfont f = new Myfont(22);

	double curpress = 0;

	XYChart.Series flowserireswet = new XYChart.Series();

	final NumberAxis xAxis2 = new NumberAxis();
	final NumberAxis yAxis2 = new NumberAxis();
	LineChart<Number, Number> sc2 = new LineChart<>(xAxis2, yAxis2);
	XYChart.Series pressureserireswet = new XYChart.Series();

	long tempt1;

	int testtype = 0; // 0 for bubble 1 for wet 2 for dry

	static SimpleBooleanProperty isSkiptest;

	@FXML
	Label lblresult, lbltesttype;

	boolean isCompletetest = false;

	long changetime = 0;

	Thread mainth;

	boolean isreading = false;

	long waittime = 0;

	int maxpsi = 50000;
	double conditionpressure = 50000;
	String teststd;

	int testindex = 0;

	void setTestStd() {
		teststd = Myconstant.getStd();
		// 1 = astm , 2 = iso

	}

	void setMode() {

		System.out.println("TH type : " + Myconstant.testmode);

		waittime = (long) Double.parseDouble(Myconstant.stepsize);

		waittime = waittime * 1000;
		System.out.println("step time : " + waittime);

	}

// stop test function it is used when test is completed or in while
// running...

	void startReading() {
		isreading = true;
		Webservices ws = new Webservices();
		ws.setUrl(ip + "/?cmd=start");
		ws.connect();
		String data = ws.getData();
		System.out.println("Start cmd : " + data);
		if (mainth == null) {
			mainth = new Thread(new Runnable() {

				@Override
				public void run() {

					while (isreading) {
						try {
							ws.setUrl(ip + "/");
							ws.connect();

							String data = ws.getData();
							System.out.println("Data : " + data);
							if (data.equals("nc")) {

								changeStatusLable("Hardware connection problem! Restart test.");
								// isreading = false;
								// MyDialoug.showError(102);
							} else {
								String[] all = data.split(",");
								Map<String, Object> rec = new HashMap<>();
								for (int i = 0; i < all.length; i++) {
									String[] temp = all[i].split(":");
									try {
										rec.put(temp[0], temp[1]);
									} catch (Exception e) {
										System.out.println("Error :  " + temp[0]);
									}
								}

								// 26385

								double pr = Double.parseDouble(rec.get("pvalue").toString());

								int maxpre = Integer.parseInt(DataStore.getPg1());
								pr = (double) pr * maxpre / 26390;

								if (DataStore.getUnitepg1().equals("bar")) {
									pr = DataStore.barToPsi(pr);
								} else if (DataStore.getUnitepg1().equals("torr")) {
									pr = DataStore.torrToPsi(pr);
								}

								// System.out.println("Pr original : " + pr);
								if (DataStore.isabsolutepg1()) {
									pr = pr - 14.6;
									if (pr < 0) {
										pr = 0;
									}
								}

								DataStore.livepressure.set(pr);
								setBubblePoints(pr);

							}

							Thread.sleep(waittime);

						} catch (Exception e) {

							e.printStackTrace();
							mainth.stop();
							isreading = false;
							changeStatusLable("something went wrong! please Restart test");
						}
					}
				}
			});
		}
		mainth.start();

	}

	void changeStatusLable(String msg) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				status.setText(msg);
			}
		});
	}

	void stopTest1() {

		sendStopCmd();
		recorddata.clear();
		recordtime.clear();
		starttest.setDisable(false);
		status.setText("Test hase been Stop");
		bans.clear();
		isCompletetest = false;

	}

// set all shortcut
	void addShortCut() {
		KeyCombination backevent = new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_ANY);

		mainroot.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {

				if (backevent.match(ke)) {
					testabourd();
				}

				if (ke.getCode() == KeyCode.SPACE) {
					System.out.println("Record");
					recordPressure();

				}

			}
		});

	}

	void recordPressure() {
		if (!recorddata.contains(readpre)) {
			Toast.makeText(Main.mainstage, "Record Successfully", 500, 100, 100);
			recorddata.add(readpre);
			recordtime.add(readtime);
			generateList();

			if (recorddata.size() == 3) {
				completeTest();
			}

		} else {
			Toast.makeText(Main.mainstage, "Already exist", 500, 100, 100);
		}
	}

	void generateList() {
		String[] suffixes =
// 0 1 2 3 4 5 6 7 8 9
				{ "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
// 10 11 12 13 14 15 16 17 18 19
						"th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
// 20 21 22 23 24 25 26 27 28 29
						"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
// 30 31
						"th", "st" };

		VBox v = new VBox(5);
		for (int i = 0; i < recorddata.size(); i++) {
			try {
				HBox h = new HBox(3);
				Label l = new Label((i + 1) + suffixes[i + 1] + " bubble : "
						+ Myapp.getRound(DataStore.ConvertPressure(recorddata.get(i)), 2)
						+ DataStore.getUnitepressure());
				l.setFont(new Font(15));
				Button del = new Button();
				del.setText("Delete");
				del.setUserData(recordtime.get(i));
				del.setTooltip(new Tooltip(recorddata.get(i) + ""));

				del.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {

						Button b = (Button) arg0.getSource();
						recorddata.remove(Double.parseDouble(b.getTooltip().getText()));
						recordtime.remove(Double.parseDouble(b.getUserData().toString()));
						generateList();
					}
				});
				h.getChildren().add(l);
				h.getChildren().add(del);
				v.getChildren().add(h);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		scrollrecord.setPadding(new Insets(20));
		scrollrecord.setContent(v);
	}

// set hardware connection status.. and if it connected then create
// communication bridge with it.
	void connectHardware() {

		setTimer();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// loadVideo();

		Myapp.PrintAll();
		isSkiptest = new SimpleBooleanProperty(false);
		btnfail.setDisable(false);
		tones = new AudioClip(NLivetestController.class.getResource("stoptone.mp3").toString());

		addShortCut();

		isRestart = new SimpleBooleanProperty(false);

		DataStore.getconfigdata();

		recorddata = new ArrayList<Double>();
		recordtime = new ArrayList<Double>();
		isBubbleStart = new SimpleBooleanProperty(false);
		isDryStart = new SimpleBooleanProperty(false);
		lblfilename.setText(Myconstant.sampleid);

		setMode();

		lbltesttype.setText("Hydrostatic Pressure Test");
		lbltesttype.setText("AATCC 127");

		connectHardware();
		setButtons();
		setGauges();
		setGraph();

		isSkiptest.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {

				if (arg2 == true) {
					completeTest();
					isSkiptest.set(false);
				}

			}
		});

		recordbtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
// TODO Auto-generated method stub
				recordPressure();
				// savePic(videoanc);

			}
		});
		isBubbleStart.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if (newValue) {
					System.out.println("bubble call");
					bubbleClicknew();
				}

			}
		});
		btnfail.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

// result="Fail";
				// completeTest();
				mydia = new MyDialoug(Main.mainstage, "/userinput/Skiptestpopup.fxml");
				mydia.showDialoug();

			}
		});

// setTableList();

		bans = new ArrayList<>();
		tlist = new ArrayList<>();

		startautotest.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				isRestart.set(false);
				mydia = new MyDialoug(Main.mainstage, "/userinput/Re-testpopup.fxml");
				mydia.showDialoug();

			}
		});

		isRestart.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				restartTest();
			}
		});

	}

	void restartTest() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				status.setText("Restarting test...");
				sendStopCmd();
				startPopup();

			}
		});
	}

	void bubbleClicknew() {
		btnfail.setDisable(false);
		status.setText("Hydrostatic test is runnig..");
		lblcurranttest.setText("Pressure vs Time");

		flowserireswet.getData().clear();
		pressureserireswet.getData().clear();

		bans.clear();
		tlist.clear();

		skip = 0;
		yAxis.setLabel("Pressure (" + DataStore.getUnitepressure() + ")");
		xAxis.setLabel("Time (Seconds)");

		tempt1 = System.currentTimeMillis();

		starttest.setDisable(true);

		countbp = 0;
// starttest.setVisible(false);

		t2test = System.currentTimeMillis();
		// series1.getData().clear();
		series2.getData().clear();

		// series1.getData().add(new XYChart.Data(0, conditionpressure));
		// series1.getData().add(new XYChart.Data(conditionpressure,
		// conditionpressure));
		changetime = System.currentTimeMillis();

		startReading();

		// Toast.makeText(Main.mainstage, "Test is being started!", 2400, 200, 200);

	}

// get differencial time

	double getTime() {
		double an = (double) ((System.currentTimeMillis() - tempt1) / 1000);
		return an;
	}

// set label font type
	void setLabelFont() {
		lbltestdurationm.setFont(f.getM_M());
		lbltestnom.setFont(f.getM_M());
	}

// find file last added digit
	int findInt(String[] s) {
		try {

			List<String> s1 = Arrays.asList(s);
			ArrayList<String> ss = new ArrayList<String>(s1);

			ArrayList<Integer> ll = new ArrayList<Integer>();
			for (int i = 0; i < ss.size(); i++) {
// System.out.println(ss.get(i));

				try {
					String temp = ss.get(i).toString().substring(0, ss.get(i).indexOf("."));
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

	int findInt1(String[] s) {
		try {
			Date d1 = new Date();
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("ddMMyy");
			String date1 = DATE_FORMAT.format(d1);

			List<String> s1 = Arrays.asList(s);
			ArrayList<String> ss = new ArrayList<String>(s1);

			ArrayList<Integer> ll = new ArrayList<Integer>();
			for (int i = 0; i < ss.size(); i++) {
				if (ss.get(i).contains(date1)) {
// System.out.println(ss.get(i));

					String temp = ss.get(i).toString().substring(0, ss.get(i).indexOf("."));
					String[] data = temp.split("_");
					System.out.println(temp);

					ll.add(Integer.parseInt(data[2]));

				} else {
					ss.remove(i);
				}
			}

// return 0;
			return Collections.max(ll) + 1;
		} catch (Exception e) {
			return 1;
		}
	}

// set round off all points
	public String getRound(Double r, int round) {

		r = (double) Math.round(r * 100) / 100;

		return r + "";

	}

// set main graphs....
	void setGraph() {
		root.getChildren().add(sc);
		sc.setAxisSortingPolicy(SortingPolicy.Y_AXIS.NONE);
		sc.setAxisSortingPolicy(SortingPolicy.X_AXIS.NONE);

		sc.setAnimated(false);
		sc.setLegendVisible(false);
		yAxis.setLabel("Pressure");
		xAxis.setLabel("Time");
		sc.setCreateSymbols(true);
		// series1.setName("Dry-Test");
		series2.setName("Wet-Test");
		sc.getData().addAll(series2);

// sc.setTitle("Flow Vs Pressure");

		sc.prefWidthProperty().bind(root.widthProperty());
		sc.prefHeightProperty().bind(root.heightProperty());

		// xAxis.setUpperBound(conditionpressure);
		xAxis.setAutoRanging(true);
		Zoom zoom = new Zoom(sc, root);

	}

// set pressure and flow gauges
	void setGauges() {
		gauge5 = TileBuilder.create().skinType(SkinType.BAR_GAUGE)
// .prefSize(TILE_WIDTH, TILE_HEIGHT)
				.minValue(0)

				.barBackgroundColor(Color.GRAY).backgroundColor(Color.valueOf("#f1f1f1")).maxValue(conditionpressure)
				.startFromZero(true).thresholdVisible(false).title("Pressure").unit(DataStore.getUnitepressure())

				.textColor(Color.GRAY).unitColor(Color.GRAY).titleColor(Color.GRAY).valueColor(Color.GRAY)
				.gradientStops(new Stop(0, Bright.BLUE), new Stop(0.1, Bright.BLUE_GREEN), new Stop(0.2, Bright.GREEN),
						new Stop(0.3, Bright.GREEN_YELLOW), new Stop(0.4, Bright.YELLOW),
						new Stop(0.5, Bright.YELLOW_ORANGE), new Stop(0.6, Bright.ORANGE),
						new Stop(0.7, Bright.ORANGE_RED), new Stop(0.8, Bright.RED), new Stop(1.0, Dark.RED))
				.strokeWithGradient(true).animated(true).build();

		gauge5.setMaxValue(DataStore.ConvertPressure(conditionpressure));
		gauge5.prefHeight(guages.getPrefHeight());
		gauge5.prefWidth(guages.getPrefWidth());
		gauge5.maxHeight(guages.getPrefHeight());
		gauge5.maxWidth(guages.getPrefWidth());
		gauge5.minHeight(guages.getPrefHeight());
		gauge5.minWidth(guages.getPrefWidth());
		guages.getChildren().add(gauge5);

		DataStore.livepressure.addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				String pp = "" + newValue;

				Platform.runLater(new Runnable() {

					@Override
					public void run() {

						gauge5.setValue(DataStore.ConvertPressure((double) newValue));

					}
				});

			}

		});

	}

	void setTimer() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {

				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						startPopup();

					}
				});
			}

		};
		timer.schedule(task, 1000);
	}

// set all button events
	void setButtons() {
		btninfo.getStyleClass().add("transperant_comm");
		btnabr.getStyleClass().add("transperant_comm");
		startautotest.getStyleClass().add("transperant_comm");
		btnfail.getStyleClass().add("transperant_comm");

		btninfo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
// TODO Auto-generated method stub
				testinfo();
			}
		});

		btnabr.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
// TODO Auto-generated method stub
				testabourd();
			}
		});

	}

// set test stop popup
	void testinfo() {

		mydia = new MyDialoug(Main.mainstage, "/userinput/Testinfopopup.fxml");

		mydia.showDialoug();

	}

// stop test popup
	void testabourd() {

		mydia = new MyDialoug(Main.mainstage, "/userinput/Nabourdpopup.fxml");
// mydia = new MyDialoug(Main.mainstage, "/userinput/Nresult.fxml");

		mydia.showDialoug();

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
				node.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, new EventHandler<MouseEvent>() {

					private static final int X_OFFSET = 15;
					private static final int Y_OFFSET = -5;
					Label label = new Label();

					@Override
					public void handle(final MouseEvent event) {
// System.out.println("MOuse Event");
						final String colorString = "#cfecf0";
						label.setFont(new Font(20));
						popup.getContent().setAll(label);
						label.setStyle(
								"-fx-background-color: " + colorString + "; -fx-border-color: " + colorString + ";");
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

// set value to ascii and package
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

	void setBubblePoints(double pr) {

		readpre = pr;
		readtime = getTime();

		bans.add("" + pr);
		tlist.add("" + readtime);

		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				series2.getData().add(new XYChart.Data(readtime, DataStore.ConvertPressure(pr)));

			}
		});

		/*
		 * if (pr <= maxpsi) {
		 * 
		 * if (pr > curpress) { curpress = pr; stadycount = 0; } else { if (pr > 0.2) {
		 * stadycount++; } if (pr > 0.4) {
		 * 
		 * int per = 10; double diff = (double) curpress * per / 100;
		 * 
		 * System.out.println("High : " + curpress); System.out.println("Current  : " +
		 * pr);
		 * 
		 * System.out.println("Diff : " + (curpress - diff)); if (pr < (curpress -
		 * diff)) { isCompletetest = true; }
		 * 
		 * }
		 * 
		 * if (stadycount == 5) { isCompletetest = true; } }
		 * 
		 * if (pr > conditionpressure || isCompletetest) {
		 * 
		 * Platform.runLater(new Runnable() {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub
		 * completeTest(); starttest.setDisable(false); } }); } }else {
		 * Platform.runLater(new Runnable() {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub
		 * completeTest(); starttest.setDisable(false); } }); }
		 */

	}

	void completeTest() {
		isreading = false;
		createCsvTableBubble();
		sendStopCmd();

	}

// csv create function
	void createCsvTableBubble() {

		if (bans.size() != 0) {
			try {

				System.out.println("csv creating........");
				CsvWriter cs = new CsvWriter();

				Date d1 = new Date();
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("ddMMyy");
				String date1 = DATE_FORMAT.format(d1);

				File fff = new File("TableCsvs");
				if (!fff.exists()) {
					fff.mkdir();
				}

				File fffff = new File("TableCsvs/" + Myapp.uid);
				if (!fffff.exists()) {
					fffff.mkdir();
				}

				File f = new File(fffff.getPath() + "/" + Myconstant.sampleid);
				if (!f.isDirectory()) {
					f.mkdir();
					System.out.println("Dir csv folder created");
				}

				String[] ff = f.list();

				CalculatePorometerData c = new CalculatePorometerData();

				cs.wtirefile(f.getPath() + "/" + Myconstant.sampleid + "_" + findInt(ff) + ".csv");

				String msg = "";
				double level2 = 0.284;
				double level3 = 0.711;

				cs.firstLine("hydrostatic");
				cs.newLine("testname", "hydrostatic");
				if (recorddata.size() >= 3) {
					if (recorddata.get(2) >= level3) {
						result = "pass";
						msg = "AAMI PB:70 Level 3";
					} else if (recorddata.get(2) >= level2) {
						result = "pass";
						msg = "AAMI PB:70 Level 2";
					} else {
						result = "fail";
						msg = "AAMI PB:70 Level 3";
					}

					cs.newLine("bpressure", "" + recorddata.get(2));
				} else {
					result = "pass";
					msg = "AAMI PB:70 Level 3";

					cs.newLine("bpressure", "" + curpress);
				}

				cs.newLine("result", result);
				cs.newLine("resultmsg", msg);
				cs.newLine("sample", Myconstant.sampleid);
				cs.newLineDouble("recordy", recorddata);
				cs.newLineDouble("recordx", recordtime);
				cs.newLine("stepsize", Myconstant.stepsize);
				cs.newLine("lotno", Myconstant.lotno);
				// cs.newLine("side",Myconstant.samplearea);

				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

				Date date = new Date();
				t1test = System.currentTimeMillis();
				int s = (int) (t1test - t2test) / 1000;

				int hour = (s / 3600);
				int min = (s / 60) % 60;
				int remsec = (s % 60);
				String durr = "";
				if (hour != 0) {
					durr = hour + " hr:" + min + " min:" + remsec + " sec";
				} else {
					durr = min + " min:" + remsec + " sec";
				}

				cs.newLine("duration", durr);
				cs.newLine("durationsecond", s + "");
				cs.newLine("testtime", timeFormat.format(date));
				cs.newLine("testdate", dateFormat.format(date));
				cs.newLine("testmode", Myconstant.testmode);
				// cs.newLine("rate",Myconstant.pressurerate);
				// cs.newLine("customerid", Myapp.uid);

				// cs.newLine("indistry", Myapp.indtype);
				// cs.newLine("application", Myapp.materialapp);
				// cs.newLine("splate", Myapp.splate);

				cs.newLine("ans", bans);
				cs.newLine("t", tlist);

				savefile = new File(cs.filename);
				cs.closefile();

				Platform.runLater(new Runnable() {

					@Override
					public void run() {
// TODO Auto-generated method stub

						showResultPopup();
					}
				});
				isCompletetest = false;
				System.out.println("csv Created");

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					status.setText("Please Re-test this sample");

					Toast.makeText(Main.mainstage, "No Data found for test", 1000, 100, 100);
				}
			});

		}
//LoadAnchor.LoadCreateTestPage();
//LoadAnchor.LoadReportPage();
	}

// show result popup
	void showResultPopup() {
		tones.play();
		mydia = new MyDialoug(Main.mainstage, "/userinput/popupresult.fxml");

		mydia.showDialoug();
		System.out.println("Result show");
	}

// show start test popup
	void startPopup() {
		if(testindex<Myconstant.cknames.size())
		{
			Map<String,Object> inputdata=new HashMap();
			inputdata.put("test", Myconstant.cknames.get(testindex));
			MyDialoug.inputdata=inputdata;
		mydia = new MyDialoug(Main.mainstage, "/userinput/Wetpopup.fxml");
		
	
		testindex++;
		
		String data=
				mydia.showDialougWithValue(inputdata);
		
		System.out.println("Data : "+data);
		
		if(data.equals("exit"))
		{

			Openscreen.open("/application/first.fxml");
		}
		else if(data.equals("start"))
		{
			System.out.println("Start test");
			
		}
		else
		{
			System.out.println("Skip test");
			startPopup();
		}
		}
		else
		{
			System.out.println("Complete test");
		}
	
	}

// send stop protocol to MCU
	void sendStopCmd() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				isreading = false;

				Webservices ws = new Webservices();
				ws.setUrl(ip + "/?cmd=stop");
				ws.connect();
				try {
					mainth.stop();
					mainth = null;
				} catch (Exception e) {

				}
			}
		}).start();

	}

}
