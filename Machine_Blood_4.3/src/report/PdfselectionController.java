package report;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import application.Main;
import application.Myapp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import pdfreport.ExcelReport;
import pdfreport.Multiplepororeport;
import pdfreport.Multiplepororeportblood;
import pdfreport.Singlepororeport;
import pdfreport.Singlepororeportblood;
import toast.MyDialoug;
import toast.Toast;

public class PdfselectionController implements Initializable {

	@FXML
	Button btncancel, pdfsave, btnbrows,excelsave,btnbrows1,btncancel1;

	@FXML
	TextField txtcomname;

	@FXML
	ScrollPane scrollbrows;

	@FXML
	TextArea txtnotes;

	@FXML
	CheckBox chkrow, flowvspre,chkcoverpage,chsampleinfo;

	@FXML
	ImageView pic,pic1;

	String imgpath = "", imgpath1 = "";

	@FXML
	javafx.scene.control.Label lblbrowse;

	List<String> graphs;
	
	boolean bchkcoverpage, bchkrowdata, bflowvspre,bolchsampleinfo;
	
	@FXML
	RadioButton radiohydrostatic,radiobloodpeni;
	
	static ToggleGroup tgbpdftype;
	static String selectedpdftype = "";

	Map<String,String> imgs;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		scrollbrows.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		
		setpdftesttype();
		
		bchkcoverpage = true;
		chkcoverpage.selectedProperty().addListener(
				new ChangeListener<Boolean>() {

					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean arg1, Boolean arg2) {
						if (arg2) {
							bchkcoverpage = true;
							pic1.setVisible(true);
							btnbrows1.setVisible(true);

						} else {
							bchkcoverpage = false;
							btnbrows1.setVisible(false);
							pic1.setVisible(false);

						}

					}
				});

		imgs = new HashMap<String, String>();
		
		
		graphs = new ArrayList<String>();
		
		if(ReportController.list_d.size()>1)
		{
			
			lblbrowse.setVisible(false);
			btnbrows.setVisible(false);
			excelsave.setVisible(false);
			chsampleinfo.setVisible(true);
			showBrowsebtn();
		}

		txtnotes.setText("The following test Procedure is based on AATCC 127 Hydrostatic Pressure Level 3.");

		/*Close Popup*/
		btncancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				MyDialoug.closeDialoug();
			}
		});
		btncancel1.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MyDialoug.closeDialoug();
			}
		});
		/*Excel File Save*/
		excelsave.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!txtcomname.getText().equals(""))
				{
				MyDialoug.closeDialoug();
				try {
					ExcelReport e=new ExcelReport();
					e.exportToExcel(ReportController.pdffilepath.getPath()+".xlsx", ReportController.list_d.get(0), txtnotes.getText(),txtcomname.getText());
				
					Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + ReportController.pdffilepath.getAbsolutePath()+".xlsx");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				else {
					Toast.makeText(Main.mainstage, "Please enter companyname", 1500, 500, 500);

				}

			}
		});


		/*PDF File Save*/
		pdfsave.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				if (!txtcomname.getText().equals("")) {
					MyDialoug.closeDialoug();

					 saveReport(ReportController.pdffilepath.getPath()+".pdf");
					 try {
							Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + ReportController.pdffilepath.getAbsolutePath()+".pdf");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				} else {
					Toast.makeText(Main.mainstage, "Please enter companyname",
							1500, 500, 500);

				}

			}
		});

		/*Sample Image Browse*/
		btnbrows.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				handleUpload();
			}
		});

		btnbrows1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				handleUpload1();
			}
		});

	}
	
	void setpdftesttype() {
try {
		tgbpdftype = new ToggleGroup();

		radiohydrostatic.setToggleGroup(tgbpdftype);
		radiohydrostatic.setUserData("1");
		radiobloodpeni.setToggleGroup(tgbpdftype);
		radiobloodpeni.setUserData("2");
	
		

		selectedpdftype = "1";
		Myapp.pdftestname = "hydro";
		tgbpdftype.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				// TODO Auto-generated method stub
				selectedpdftype = newValue.getUserData().toString();

				if (selectedpdftype.equals("1")) {					
					Myapp.pdftestname = "hydro";
				}
				else {
					Myapp.pdftestname = "blood";
				}
			}
		});
}
catch (Exception e) {
	// TODO: handle exception
}
		System.out.println("PDF______________>"+Myapp.pdftestname);

}


	
	void showBrowsebtn() {
		VBox v = new VBox(20);
				
		int j=0;
		for (j=0; j <ReportController.list_d.size(); j++) {
			
	        ImageView myimg = new ImageView();
	        myimg.setFitWidth(150);
	        myimg.setFitHeight(150);
	        imgs.put(ReportController.list_d.get(j).filename,"_");

			FileChooser fileChooser = new FileChooser();			
	        Button buttonimg = new Button(""+ReportController.list_d.get(j).filename);
	        buttonimg.setOnAction(e -> {
	            File selectedFile = fileChooser.showOpenDialog(MyDialoug.dialog);
	            
	            Button b=(Button)e.getSource();
	        	try {
	    			BufferedImage bufferedImage = ImageIO.read(selectedFile);
	    			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
	    			myimg.setImage(image);
	    	        imgs.replace(b.getText(), selectedFile.getPath());
	    			
	    		} catch (IOException ex) {
	    			  imgs.replace(b.getText(), "_");
		    			
	    			System.out.println(ex);
	    		}
	            
	        });
	
	    	VBox hs = new VBox(20);
			BorderPane bp = new BorderPane();
			bp.setPadding(new Insets(10, 10, 10, 10));

			bp.setTop(buttonimg);
			bp.setBottom(myimg);

			v.getChildren().add(bp);
	}

		scrollbrows.setContent(v);
	}

	void saveReport(String path) {
		boolean bchkrowdata, bflowvspre;
		
		if (flowvspre.isSelected()) {
			graphs.add("1");
		} else {
			graphs.add("0");
		}

		if (chkrow.isSelected()) {
			bchkrowdata = true;
		} else {
			bchkrowdata = false;
		}
		
		if (chsampleinfo.isSelected()) {
			bolchsampleinfo = true;
		} else {
			bolchsampleinfo = false;
		}
		
		if (ReportController.list_d.size() == 1) {

			if(Myapp.pdftestname.equals("hydro"))
			{
				
				System.out.println("Hydro");
			Singlepororeport sp = new Singlepororeport();
			sp.Report(path, ReportController.list_d.get(0), txtnotes.getText(),
					txtcomname.getText(), imgpath, graphs, bchkrowdata, bchkcoverpage, imgpath1);
			}
			else {
				System.out.println("Blood");
				Singlepororeportblood sp2 = new Singlepororeportblood();
				sp2.Report(path, ReportController.list_d.get(0), txtnotes.getText(),
						txtcomname.getText(), imgpath, graphs, bchkrowdata, bchkcoverpage, imgpath1);

			}
		} else {
			System.out.println("Selected images : "+imgs);
			if(Myapp.pdftestname.equals("hydro"))
			{				
			Multiplepororeport mp = new Multiplepororeport();
			mp.Report(path, ReportController.list_d, txtnotes.getText(), txtcomname.getText(),
					graphs, bchkrowdata,bchkcoverpage, imgpath1,bolchsampleinfo,imgs);
			}
			else
			{
				Multiplepororeportblood mp = new Multiplepororeportblood();
				mp.Report(path, ReportController.list_d, txtnotes.getText(), txtcomname.getText(),
						graphs, bchkrowdata,bchkcoverpage, imgpath1,bolchsampleinfo,imgs);	
				
			}
			
		}
	}

	@FXML
	public void handleUpload() {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter

		// Show open file dialog
		File file = fileChooser.showOpenDialog(MyDialoug.dialog);

		try {

			BufferedImage bufferedImage = ImageIO.read(file);
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			pic.setImage(image);
			imgpath = file.getPath();
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	public void handleUpload1() {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter

		// Show open file dialog
		File file = fileChooser.showOpenDialog(MyDialoug.dialog);

		try {

			BufferedImage bufferedImage = ImageIO.read(file);
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			pic1.setImage(image);
			imgpath1 = file.getPath();
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	
}
