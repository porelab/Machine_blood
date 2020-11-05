package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSlider;

import data_read_write.CsvWriter;
import data_read_write.DatareadN;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import myconstant.Myconstant;
import toast.MyDialoug;
import toast.Openscreen;

public class QuicktestController1 implements Initializable {

	String path = "information/recipie/";

	@FXML
	private ImageView imgdownarrow111;

	@FXML
	private ComboBox<String> cmbsampleid;

	@FXML
	private RadioButton rdmanual;

	@FXML
	private RadioButton rdautometed;

	@FXML
	private Label lblnote, lblerror;

	@FXML
	private JFXSlider stepsizeslider;

	@FXML
	private TextField txtlotno;

	@FXML
	private CheckBox cksupport;

	@FXML
	private RadioButton rdfull;

	@FXML
	private RadioButton rdsingle;

	@FXML
	private CheckBox ck1;

	@FXML
	private CheckBox ck2;

	@FXML
	private CheckBox ck3;

	@FXML
	private CheckBox ck4;

	@FXML
	private CheckBox ck5;

	@FXML
	private CheckBox ck6;

	@FXML
	private CheckBox ck7;

	@FXML
	private CheckBox ck8;

	@FXML
	Rectangle fullenable;

	List<CheckBox> allcks = new ArrayList<CheckBox>();

	static ToggleGroup tgb1, tgb2;

	@FXML
	Button starttest, btncancel;

	@FXML
	AnchorPane ancaddsamplearea;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		stepsizeslider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				double v = (double) newValue;
				Myconstant.stepsize = "" + (int) v;
				lblnote.setText("Note : This will plot " + (60 / (int) v) + " Readings in 1 Minutes.");

			}

		});

		setTestMode();
		setSampleType();

		allcks.add(ck1);
		allcks.add(ck2);
		allcks.add(ck3);
		allcks.add(ck4);
		allcks.add(ck5);
		allcks.add(ck6);
		allcks.add(ck7);
		allcks.add(ck8);

		btncancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

				MyDialoug.closeDialoug();

			}
		});

		starttest.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				teststart();

			}
		});

		cmbsampleid.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {

				loadFile(arg2);

			}
		});

		getAllFiles();
	}

	void loadFile(String file) {
		File f = new File(path + file + ".csv");
		if (f.exists()) {
			DatareadN dr = new DatareadN();
			dr.fileRead(f);
			System.out.println(dr.data);

			String testtype = dr.data.get("testmode").toString();
			String mtype = dr.data.get("mtype").toString();
			String lotno = dr.data.get("lotno").toString();
			List<String> ck = dr.getValuesOf(dr.data.get("ck").toString());
			List<String> ckname = dr.getValuesOf(dr.data.get("ckname").toString());

			String supportplate = dr.data.get("supportplate").toString();
			String stepsize = dr.data.get("stepsize").toString();

			txtlotno.setText(lotno);

			if (supportplate.equals("true")) {
				cksupport.setSelected(true);
			} else {
				cksupport.setSelected(false);
			}

			if (testtype.endsWith("1")) {
				rdmanual.setSelected(true);
			} else {
				rdautometed.setSelected(true);
			}
			if (mtype.endsWith("1")) {
				rdsingle.setSelected(true);
			} else {

				rdfull.setSelected(true);
			}

			for (int i = 0; i < allcks.size(); i++) {
				if (ck.contains(allcks.get(i).getId())) {
					allcks.get(i).setSelected(true);
				} else {

					allcks.get(i).setSelected(false);
				}

			}

			stepsizeslider.setValue(Double.parseDouble(stepsize));

		}

	}

	void setTestMode() {

		tgb1 = new ToggleGroup();

		rdmanual.setToggleGroup(tgb1);
		rdmanual.setUserData("1");
		rdautometed.setToggleGroup(tgb1);
		rdautometed.setUserData("2");

	}

	void setSampleType() {

		tgb2 = new ToggleGroup();

		rdsingle.setToggleGroup(tgb2);
		rdsingle.setUserData("1");
		rdfull.setToggleGroup(tgb2);
		rdfull.setUserData("2");

		rdfull.setSelected(false);
		tgb2.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {

				System.out.println("Selection changed");
				Myconstant.sampletype = arg2.getUserData().toString();

				if (Myconstant.sampletype.equals("2")) {
					fullenable.setVisible(false);

					stepsizeslider.setMax(60);
					stepsizeslider.setMin(15);
					stepsizeslider.setBlockIncrement(15);
					stepsizeslider.setMajorTickUnit(15);

					stepsizeslider.setValue(15);

				} else {
					stepsizeslider.setMax(20);
					stepsizeslider.setMin(2);
					stepsizeslider.setBlockIncrement(2);
					stepsizeslider.setMajorTickUnit(2);

					stepsizeslider.setValue(2);
					fullenable.setVisible(true);
				}

			}
		});

		rdfull.setSelected(true);
	}

	void teststart() {

//		Myconstant.sampleid = "" + cmbsampleid.getValue();
//		Myconstant.lotno= ""+txtlotno.getText();
//		MyDialoug.closeDialoug();
//		Openscreen.open("/userinput/Nlivetest.fxml");
//		

		List<String> cks = new ArrayList<String>();
		List<String> ckname = new ArrayList<String>();

		for (int i = 0; i < allcks.size(); i++) {
			if (allcks.get(i).isSelected()) {
				cks.add(allcks.get(i).getId());
				ckname.add(allcks.get(i).getText());
			}
		}

		String sampleid = cmbsampleid.getValue();
		String lotno = txtlotno.getText();
		String sampletype = tgb2.getSelectedToggle().getUserData().toString();
		String testmode = tgb1.getSelectedToggle().getUserData().toString();

		boolean flag = true;

		if (sampleid != null ? sampleid.isEmpty() || lotno.isEmpty() : true) {
			flag = false;
			lblerror.setText("Please add sampleid and lotno");
		} else if (sampletype.equals("2")) {
			if (cks.size() == 0) {
				flag = false;
				lblerror.setText("you have selected full material, please select variants");
			}
		}

		if (flag == true) {
			lblerror.setText("");

			if (filesave(sampleid, testmode, sampletype, lotno, cks, ckname)) {
				lblerror.setText("saved");
				Openscreen.open("/userinput/Nlivetest.fxml");
				MyDialoug.closeDialoug();

			} else {

				lblerror.setText("not saved");
			}
			// MyDialoug.closeDialoug();
//			Openscreen.open("/userinput/Nlivetest.fxml");

		}

	}

	boolean filesave(String sampleid, String testtype, String mtype, String lotno, List<String> ck,
			List<String> ckname) {

		try {

			File check = new File(path);
			if (!check.exists()) {
				check.mkdirs();
			}

			File f = new File(path + sampleid + ".csv");
			CsvWriter cw = new CsvWriter();
			cw.wtirefile(f.getPath());
			cw.firstLine(sampleid);
			cw.newLine("testmode", testtype);
			cw.newLine("mtype", mtype);
			cw.newLine("ck", ck);
			cw.newLine("lotno", lotno);
			cw.newLine("ckname", ckname);
			cw.newLine("supportplate", cksupport.isSelected() + "");
			cw.newLine("stepsize", stepsizeslider.getValue() + "");
			cw.closefile();

			Myconstant.lotno = lotno;
			Myconstant.sampleid = sampleid;
			Myconstant.sampletype = mtype;
			Myconstant.testmode = testtype;
			Myconstant.supportplate = cksupport.isSelected() + "";
			Myconstant.stepsize = stepsizeslider.getValue() + "";

			if (mtype.equals("1")) {
				Myconstant.cknames = new ArrayList<String>();
				Myconstant.cknames.add("single");
			} else {
				Myconstant.cknames = ckname;
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	void getAllFiles() {
		try {
			File[] f = new File(path).listFiles();

			Arrays.sort(f, new Comparator<File>() {
				public int compare(File f1, File f2) {
					return Long.compare(f2.lastModified(), f1.lastModified());
				}
			});

			List<String> fname = new ArrayList<String>();
			int lastPeriodPos;

			for (int i = 0; i < f.length; i++) {

				lastPeriodPos = f[i].getName().lastIndexOf('.');
				if (lastPeriodPos > 0) {
					fname.add(f[i].getName().substring(0, lastPeriodPos));
				}

			}

			cmbsampleid.getItems().addAll(fname);

			if (fname.size() > 0) {
				cmbsampleid.getSelectionModel().select(0);
			}

		} catch (Exception e) {
			System.out.println("No file found");
		}
	}

	void saveData() {

	}

}
