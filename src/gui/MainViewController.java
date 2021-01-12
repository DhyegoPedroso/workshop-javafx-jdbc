package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemSobre;

	@FXML
	public void onMenuItemSellerAction() {

		System.out.println("Vendedor");
	}

	@FXML
	public void onMenuItemDepartmentAction() {
		System.out.println("Departamento");
	}

	@FXML
	public void onMenuItemSobreAction() {

		Alerts.showAlert("Projeto do Curso da Udemy", "Java COMPLETO 2020 Programação Orientada a Objetos + Projetos",
				"\nProfessor: Nelio Alves " + "\nAluno: Dhyego Pedroso", AlertType.INFORMATION);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

}
