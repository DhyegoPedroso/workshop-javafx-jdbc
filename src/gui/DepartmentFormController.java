package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DepartmentFormController implements Initializable {

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtName;

	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

	@FXML
	private Label labelErrorName;

	@FXML
	public void onBtSaveAction() {
		System.out.println("Salvar");
	}

	@FXML
	public void onBtCancelAction() {
		System.out.println("Cancelar");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		initializeNodes();

	}

	private void initializeNodes() {

		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);

	}

}
