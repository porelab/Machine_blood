package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSlider;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import myconstant.Myconstant;
import toast.MyDialoug;
import toast.Toast;

public class QuicktestController implements Initializable {

	static String selectedrad1 = "", selectedrad2 = "";

	static ToggleGroup tgb1, tgb2;

	@FXML
	Button starttest, btncancel,btnaddsamplearea,btndelete;

	@FXML
	RadioButton rdmanual, rdautometed, rdregular, rdfast;

	@FXML
	ComboBox<String> cmbsampleid,cmbsamplearea;

	@FXML
	Label lblnote;

	@FXML
	JFXSlider stepsizeslider;

	@FXML
	AnchorPane ancaddsamplearea;
	
	@FXML
	TextField txtsamplearea,txtlotno;
	
	String teststd;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		stepsizeslider.setMin(1);
		stepsizeslider.setValue(15);
		
		setTestMode();
		setStepSize();
		LoadSamples();

		stepsizeslider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				double v = (double) newValue;

				Myconstant.stepsize = "" + (int) v;

				lblnote.setText("Note : This will plot " + (60 / (int) v) + " Readings in 1 Minutes.");

			}

		});

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

		getSampleareadata();
		
		cmbsamplearea.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				// TODO Auto-generated method stub

				try {
					if (newValue.equals("Add Fluid")) {
						ancaddsamplearea.setVisible(true);

					} else {

						ancaddsamplearea.setVisible(false);

						String deld = "select flag from samplearea where name='"
								+ newValue+ "'";

						Database d = new Database();
						List<List<String>> data = d.getData(deld);

					//	Myconstant.samplearea = "" + newValue;
						
						//if (data.get(0).get(0).equals("0")) {
							btndelete.setVisible(true);
						//} else {
							//btndelete.setVisible(false);

						//}

					}
				} catch (Exception e) {

					e.printStackTrace();
					ancaddsamplearea.setVisible(false);
				}
			}
		});
		
		btnaddsamplearea.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				setNewsamplearea();

			}
		});
		
		btndelete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Deletesamplearea();
			}
		});
		
	}

	void setTestMode() {

		tgb1 = new ToggleGroup();

		rdmanual.setToggleGroup(tgb1);
		rdmanual.setUserData("1");
		rdautometed.setToggleGroup(tgb1);
		rdautometed.setUserData("2");

		selectedrad1 = "1";
		Myconstant.testmode = "manual";

		tgb1.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {

				selectedrad1 = arg2.getUserData().toString();

				if (selectedrad1.equals("1")) {
					Myconstant.testmode = "manual";
				}

				else {
					Myconstant.testmode = "automated";
				}

			}
		});

	}

	void setStepSize() {

		tgb2 = new ToggleGroup();

		rdregular.setToggleGroup(tgb2);
		rdregular.setUserData("1");
		rdfast.setToggleGroup(tgb2);
		rdfast.setUserData("2");

		selectedrad2 = "1";
		//Myconstant.pressurerate = "regular";

		tgb2.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {

				selectedrad2 = arg2.getUserData().toString();

				if (selectedrad2.equals("1")) {
					//Myconstant.pressurerate = "regular";
				}

				else {
					//Myconstant.pressurerate = "fast";
				}

			}
		});

	}

	void teststart() {
//
//		Myconstant.sampleid = "" + cmbsampleid.getValue();
//		Myconstant.lotno= ""+txtlotno.getText();
//	//	Myconstant.samplearea=""+cmbsamplearea.getValue();
//
//		Database d1 = new Database();
//		
//
//		String laststatue = "update quicktest set last='false'";
//		d1.Insert(laststatue);
//		System.out.println("ASLASLLLLLLLLLLLLLL"+laststatue);
//
//		if (d1.isExist("select * from quicktest where sampleid='"+Myconstant.sampleid+"'")) {
//
//			
//			
//			
//				
//				String updatequick = "update quicktest set sampleid='" + cmbsampleid.getValue() +"', testmode='"+Myconstant.testmode+"', stepsize='"+Myconstant.stepsize+"' ,pressurerate='"+Myconstant.pressurerate+"',lotno='"+Myconstant.lotno+"',sarea='"+Myconstant.samplearea+"',last='true'   where sampleid='" + Myconstant.sampleid + "'";
//
//				if(d1.Insert(updatequick))
//				{
//					//System.out.println("Update Sample Data");
//				//	lastsample();
//					MyDialoug.closeDialoug();
//					Openscreen.open("/userinput/Nlivetest.fxml");
//
//				}
//				else {
//				
//				//	System.out.println("Not Update Sample Data");
//				}
//				
//				
//			}
//			
//	
//
//		
//
//		else {
//			
//			if (d1.Insert("INSERT INTO quicktest VALUES('" + Myconstant.sampleid + "','" + Myconstant.testmode + "','"
//					+ Myconstant.stepsize + "','" + Myconstant.pressurerate + "','" + Myconstant.lotno + "','" + Myconstant.samplearea + "','" + Myapp.email + "','true')")) {
//			//	System.out.println("Insert data New Sample");
//				//lastsample();
//				MyDialoug.closeDialoug();
//				Openscreen.open("/userinput/Nlivetest.fxml");
//
//			}
//
//			else {
//				System.out.println("Not Insert data New Sample");
//
//			}
//			}

	}

	void LoadSamples() {

		Database d = new Database();
		
		List<List<String>> laststatue = d.getData("select * from quicktest where last='" + true + "' ");


		List<List<String>> info = d.getData("select sampleid from quicktest");
		try {

		cmbsampleid.getItems().add(""+laststatue.get(0).get(0));
			
			for (int i = 0; i < info.size(); i++) {
				cmbsampleid.getItems().add(info.get(i).get(0));

			}
		} catch (Exception e) {
			Exception ss;
		}

		if (cmbsampleid.getItems().size() > 0) {
			cmbsampleid.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub

					if (newValue.equals(""+laststatue.get(0).get(0))) {

							setLastData();

					} else {
						LoadSampleData(newValue);
					}
				}
			});

			cmbsampleid.getSelectionModel().select(0);

		}

	}

	void LoadSampleData(String samplename) {
//		Database d = new Database();
//
//		List<List<String>> alldata = d.getData("select * from quicktest where sampleid='" + samplename + "' ");
//
//		System.out.println("AllData "+alldata);
//
//		String testmode = "" + alldata.get(0).get(1);
//
//		String stepsizesliders = (alldata.get(0).get(2));
//		Myconstant.stepsize = (stepsizesliders);
//		stepsizeslider.setValue(Double.parseDouble(stepsizesliders));
//
//		String pressurerate = "" + alldata.get(0).get(3);
//		
//		
//		Myconstant.lotno =""+ alldata.get(0).get(4);
//		
//		txtlotno.setText(Myconstant.lotno);
//
//		cmbsamplearea.setValue(""+ alldata.get(0).get(5));
//		
//	
//		/* Test Mode */
//
//		if (testmode.equals("manual")) {
//			rdmanual.selectedProperty().set(true);
//			Myconstant.testmode = "manual";
//
//		} else {
//			rdautometed.selectedProperty().set(true);
//			Myconstant.testmode = "automated";
//		}
//
//		/* Pressure Rate */
//
//		if (pressurerate.equals("regular")) {
//			rdregular.selectedProperty().set(true);
//			Myconstant.pressurerate = "regular";
//
//		} else {
//			rdfast.selectedProperty().set(true);
//			Myconstant.pressurerate = "fast";
//
//		}

	}

	
	void setLastData() {

//		Database d1 = new Database();
//		List<List<String>> alldata = d1
//				.getData("select * from quicktest where emailid='" + Myapp.email
//						+ "' and  last='true' ");
//
//		String testmode = "" + alldata.get(0).get(1);
//
//		String stepsizesliders = (alldata.get(0).get(2));
//		Myconstant.stepsize = (stepsizesliders);
//		
//		stepsizeslider.setValue(Double.parseDouble(stepsizesliders));
//
//		String pressurerate = "" + alldata.get(0).get(3);
//		
//		Myconstant.lotno =""+ alldata.get(0).get(4);
//		Myconstant.samplearea =""+ alldata.get(0).get(5);
//		
//		txtlotno.setText(Myconstant.lotno);
//
//		cmbsamplearea.setValue(Myconstant.samplearea);
//
//		
//		/* Test Mode */
//
//		if (testmode.equals("manual")) {
//			rdmanual.selectedProperty().set(true);
//			Myconstant.testmode = "manual";
//
//		} else {
//			rdautometed.selectedProperty().set(true);
//			Myconstant.testmode = "automated";
//		}
//
//		/* Pressure Rate */
//
//		if (pressurerate.equals("regular")) {
//			rdregular.selectedProperty().set(true);
//			Myconstant.pressurerate = "regular";
//
//		} else {
//			rdfast.selectedProperty().set(true);
//			Myconstant.pressurerate = "fast";
//		}


	}
	
	/* Insert and update last data */
	void lastsample() {
//		Database d1 = new Database();
//		try {
//			
//			Myconstant.sampleid = ""+cmbsampleid.getValue();
//			Myconstant.lotno= ""+txtlotno.getText();
//			Myconstant.samplearea=""+cmbsamplearea.getValue();
//
//
//			if (!d1.isExist("select * from quicklastsample where emailid='"
//					+ Myapp.email + "'")) {
//
//				if (d1.Insert("INSERT INTO quicklastsample VALUES( '"+ Myapp.email + "','"+Myconstant.sampleid+"', '"+Myconstant.testmode+"', '"+Myconstant.stepsize+"', '"+Myconstant.pressurerate+"', '"+Myconstant.lotno+"', '"+Myconstant.samplearea+"')")) 
//				
//				{
//					// System.out.println("Last Project Insert data");
//				} else {
//					// System.out.println(" Last Project  not insertetd");
//				}
//			} else {
//
//				String updatequry = "update quicklastsample set sampleid='"
//						+ Myconstant.sampleid + "',testmode='"
//						+ Myconstant.testmode + "',stepsize='"+Myconstant.stepsize+"',pressurerate='"+Myconstant.pressurerate+"',lotno='"+Myconstant.lotno+"',sarea='"+Myconstant.samplearea+"'  where emailid='" + Myapp.email
//						+ "'";
//
//				if (d1.Insert(updatequry)) {
//					// System.out.println(" Updated");
//				} else {
//
//					// System.out.println("Not Updated");
//				}
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}

	}
	
	/* Add new Fluid ame and value */
	void setNewsamplearea() {

		Database d1 = new Database();

		if (d1.Insert("INSERT INTO samplearea VALUES('" + txtsamplearea.getText()
				+ "','0')")) {
			cmbsamplearea.getItems().add(
					txtsamplearea.getText());
			cmbsamplearea.getSelectionModel().select(
					txtsamplearea.getText());

			Toast.makeText(Main.mainstage,
					"Successfully New Sample Area Added.", 1500, 500, 500);
		} else {
		}

	}
	/* Fluid delete */
	void Deletesamplearea() {
		String ss = cmbsamplearea.getSelectionModel().getSelectedItem();
		String del = "delete from samplearea where name='"
				+ ss+ "'";
		Database database = new Database();
		if (database.Insert(del)) {
			cmbsamplearea.getItems().remove(ss);
		} else {

		}
	}
	
	/* Get Fluid in Databse */
	void getSampleareadata() {
//		Database d = new Database();
//		List<List<String>> info = d.getData("select * from samplearea");
//
//		try {
//
//			cmbsamplearea.getItems().add("Add Fluid");
//			for (int i = 0; i < info.size(); i++) {
//				cmbsamplearea.getItems().add(
//						info.get(i).get(0));
//
//				Myconstant.samplearea = "" + info.get(i).get(0);
//			}
//			cmbsamplearea.getSelectionModel().select(1);
//		} catch (Exception e) {
//			Exception ss;
//		}
//
	}

	
}
