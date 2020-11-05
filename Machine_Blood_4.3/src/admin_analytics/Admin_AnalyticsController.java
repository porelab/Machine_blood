package admin_analytics;


import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import toast.Openscreen;

public class Admin_AnalyticsController implements Initializable {

	@FXML
	private Button btnmainback;

	@FXML
	AnchorPane pierect;

	@FXML
	BarChart<String, String> bc, bc1;

	@FXML
	ComboBox<String> dropyear, dropyear1;

	@FXML
	ImageView imgdownarrow1, imgdownarrow11;

	@FXML
	Label lbltotalorder, lbltotaluser;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		Image image = new Image(this.getClass().getResourceAsStream(
				"/admin_analytics/downarrow.png"));
		imgdownarrow1.setImage(image);

		Image image1 = new Image(this.getClass().getResourceAsStream(
				"/admin_analytics/downarrow.png"));
		imgdownarrow11.setImage(image1);

		btnmainback.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Openscreen.open("/admin_main/first_main.fxml");
			}
		});
		setTimer();
	}

	void setTimer() {

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {

						setPiechart();
						setUsers();

						setOrderwithdate();

					}
				});
			}

		};
		timer.schedule(task, 2000);
	}

	/*Set User order details in Piechart*/
	void setPiechart() {
		OrderA oa = new OrderA();

		Map<String, Integer> data = oa.getOrders();
		PieChart pieChart = new PieChart();

		lbltotalorder.setText("" + oa.totalord);

		PieChart.Data slice1 = new PieChart.Data("Pending", data.get("pen"));
		PieChart.Data slice2 = new PieChart.Data("Recieved", data.get("rec"));
		PieChart.Data slice3 = new PieChart.Data("Running", data.get("run"));
		PieChart.Data slice4 = new PieChart.Data("Completed", data.get("com"));

		pieChart.getData().add(slice1);
		pieChart.getData().add(slice2);
		pieChart.getData().add(slice3);
		pieChart.getData().add(slice4);

		pieChart.setPrefSize(400, 250);

		pieChart.setLegendSide(Side.LEFT);
		pieChart.setStartAngle(30);

		pierect.getChildren().addAll(pieChart);

	}

	/*Order Details in moth wise display in barchart */
	void setBarchart(Map<Integer, Integer> data, String year) {

		bc.getData().clear();
		// bc.setLegendVisible(false);

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("" + year);
		series1.getData().add(new XYChart.Data("Jan", data.get(0)));
		series1.getData().add(new XYChart.Data("Feb", data.get(1)));
		series1.getData().add(new XYChart.Data("Mar", data.get(2)));
		series1.getData().add(new XYChart.Data("Apr", data.get(3)));
		series1.getData().add(new XYChart.Data("May", data.get(4)));
		series1.getData().add(new XYChart.Data("Jun", data.get(5)));
		series1.getData().add(new XYChart.Data("Jal", data.get(6)));
		series1.getData().add(new XYChart.Data("Aug", data.get(7)));
		series1.getData().add(new XYChart.Data("Sep", data.get(8)));
		series1.getData().add(new XYChart.Data("Oct", data.get(9)));
		series1.getData().add(new XYChart.Data("Nav", data.get(10)));
		series1.getData().add(new XYChart.Data("Dec", data.get(11)));

		bc.getData().addAll(series1);
	}

	/*Seleted user display all order in barchart*/
	void setBarchartorder(Map<Integer, Integer> data, String year) {

		bc1.getData().clear();
		bc1.setLegendVisible(false);

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("" + year);
		series1.getData().add(new XYChart.Data("Jan", data.get(0)));
		series1.getData().add(new XYChart.Data("Feb", data.get(1)));
		series1.getData().add(new XYChart.Data("Mar", data.get(2)));
		series1.getData().add(new XYChart.Data("Apr", data.get(3)));
		series1.getData().add(new XYChart.Data("May", data.get(4)));
		series1.getData().add(new XYChart.Data("Jun", data.get(5)));
		series1.getData().add(new XYChart.Data("Jal", data.get(6)));
		series1.getData().add(new XYChart.Data("Aug", data.get(7)));
		series1.getData().add(new XYChart.Data("Sep", data.get(8)));
		series1.getData().add(new XYChart.Data("Oct", data.get(9)));
		series1.getData().add(new XYChart.Data("Nav", data.get(10)));
		series1.getData().add(new XYChart.Data("Dec", data.get(11)));

		bc1.getData().addAll(series1);
	}

	/*Display order Id with Date..*/
	void setOrderwithdate() {

		OrderAbyDate oad = new OrderAbyDate();

		Map<Integer, Map<Integer, Integer>> data = oad.getOrderByDate();

		java.util.List<Integer> ls = new ArrayList<>(data.keySet());

		for (int i = 0; i < ls.size(); i++) {

			dropyear1.getItems().add("" + ls.get(i));

		}

		if (ls.size() > 0) {

			dropyear1.getSelectionModel().selectedItemProperty()
					.addListener(new ChangeListener<String>() {

						@Override
						public void changed(
								ObservableValue<? extends String> observable,
								String oldValue, String newValue) {
							// TODO Auto-generated method stub

							setBarchartorder(
									data.get(Integer.parseInt(newValue)),
									newValue);

						}
					});

			dropyear1.getSelectionModel().select(0);

		}

	}

	/*get and set all register user*/
	void setUsers() {

		UserA ua = new UserA();

		Map<Integer, Map<Integer, Integer>> data = ua.getUsers();
		lbltotaluser.setText("" + ua.totaluser);

		java.util.List<Integer> ls = new ArrayList<>(data.keySet());

		for (int i = 0; i < ls.size(); i++) {

			dropyear.getItems().add("" + ls.get(i));

		}

		if (ls.size() > 0) {

			dropyear.getSelectionModel().selectedItemProperty()
					.addListener(new ChangeListener<String>() {

						@Override
						public void changed(
								ObservableValue<? extends String> observable,
								String oldValue, String newValue) {
							// TODO Auto-generated method stub

							setBarchart(data.get(Integer.parseInt(newValue)),
									newValue);

						}
					});

			dropyear.getSelectionModel().select(0);

		}
	}

}
