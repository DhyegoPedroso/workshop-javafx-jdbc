package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.SellerService;

public class SellerFormController implements Initializable {

	private Seller entity;
	private SellerService service;
	private List<DataChangeListener> dataChangeListeners = new ArrayList<DataChangeListener>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtEmail;

	@FXML
	private DatePicker dpBirthDate;

	@FXML
	private TextField txtBasesalary;

	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

	@FXML
	private Label labelErrorName;

	@FXML
	private Label labelErrorEmail;

	@FXML
	private Label labelErrorBirthData;

	@FXML
	private Label labelErrorBaseSalary;

	@FXML
	private Label labelErrorDepartment;

	public void setSeller(Seller entity) {
		this.entity = entity;
	}

	public void setSellerService(SellerService service) {
		this.service = service;
	}

	public void subscribersDataChangeListener(DataChangeListener listener) {

		dataChangeListeners.add(listener);

	}

	@FXML
	public void onBtSaveAction(ActionEvent event) {

		if (entity == null) {
			throw new IllegalStateException("Entity está nula");
		}

		if (service == null) {
			throw new IllegalStateException("Service está nula");
		}

		try {

			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListener();
			Utils.currentStage(event).close();

		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}

	}

	private void notifyDataChangeListener() {

		for (DataChangeListener listener : dataChangeListeners) {

			listener.onDataChange();

		}

	}

	private Seller getFormData() {

		Seller obj = new Seller();
		ValidationException exception = new ValidationException("validação de Error");

		obj.setId(Utils.tryParseToInt(txtId.getText()));

		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "O campo não pode ser vazio");
		}

		if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
			exception.addError("email", "O campo não pode ser vazio");
		}

//		if (dpBirthDate.getD == null || dpBirthDate.getText().trim().equals("")) {
//			exception.addError("birthdata", "O campo não pode ser vazio");
//		}

		if (txtBasesalary.getText() == null || txtBasesalary.getText().trim().equals("")) {
			exception.addError("baseSalary", "O campo não pode ser vazio");
		}

		obj.setName(txtName.getText());
		obj.setEmail(txtEmail.getText());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;

	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		initializeNodes();

	}

	private void initializeNodes() {

		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 70);
		Constraints.setTextFieldMaxLength(txtEmail, 60);
		Constraints.setTextFieldDouble(txtBasesalary);
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");

	}

	public void updateFormData() {

		if (entity == null) {
			throw new IllegalStateException("Entity está nula");
		}

		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtEmail.setText(entity.getEmail());
		Locale.setDefault(Locale.US);
		txtBasesalary.setText(String.format("%.2f", entity.getBaseSalary()));

		if (entity.getBirthdate() != null) {

			dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthdate().toInstant(), ZoneId.systemDefault()));

		}
	}

	private void setErrorMessages(Map<String, String> errors) {

		Set<String> fields = errors.keySet();

		if (fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}

	}

}
