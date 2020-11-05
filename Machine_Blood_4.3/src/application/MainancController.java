package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainancController implements Initializable {

	@FXML
	AnchorPane mainanc;

	public static AnchorPane mainanc1;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		mainanc1 = mainanc;
		System.out.println("Load Main");

		Checklogin();
		// Myapp.setInternetWatch();

	}

	void Checklogin() {
		Database d = new Database();
		if (d.isExist("select * from userinfo where login='" + "yes" + "'")) {
			System.out.println("if");

			List<List<String>> info = d.getData("select * from userinfo where login='yes'");

			System.out.println("email:-" + info.get(0).get(0));
			System.out.println("pass:-" + info.get(0).get(1));
			System.out.println("login:-" + info.get(0).get(2));
			System.out.println("name:-" + info.get(0).get(3));

			Myapp.username = "" + info.get(0).get(3);
			Myapp.email = "" + info.get(0).get(0);
			Myapp.pass = "" + info.get(0).get(1);
			Myapp.status = "" + info.get(0).get(4);
			Myapp.uid = "" + info.get(0).get(6);

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/first.fxml"));
			try {

				Pane cmdPane = (Pane) fxmlLoader.load();

				MainancController.mainanc1.getChildren().clear();
				MainancController.mainanc1.getChildren().add(cmdPane);
				System.out.println("Direct login");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("else");
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login/Onlinelogin.fxml"));
			try {
				Pane cmdPane = (Pane) fxmlLoader.load();

				mainanc.getChildren().clear();
				mainanc.getChildren().add(cmdPane);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
